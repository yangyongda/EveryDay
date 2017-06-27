package com.fjsdfx.yyd.everyday.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.fjsdfx.yyd.everyday.R;
import com.fjsdfx.yyd.everyday.activity.ZhihuDescribeActivity;
import com.fjsdfx.yyd.everyday.bean.ZhihuDailyItem;
import com.fjsdfx.yyd.everyday.util.Config;
import com.fjsdfx.yyd.everyday.util.DBUtils;
import com.fjsdfx.yyd.everyday.util.ObservableColorMatrix;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/23.
 */

public class ZhihuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_LOADING_MORE = -1;
    private static final int NOMAL_ITEM = 1;
    private Context mContext;
    private ArrayList<ZhihuDailyItem> zhihuDailyItems = new ArrayList<>();
    private String mImageUrl;
    boolean showLoadingMore;

    public ZhihuAdapter(Context context){
        mContext = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {  //不同类型选择不同的布局
            case NOMAL_ITEM:
                return new ZhihuViewHolder(LayoutInflater.from(mContext).inflate(R.layout.zhihu_layout_item, parent, false));
            case TYPE_LOADING_MORE:
                return new LoadingMoreHolder(LayoutInflater.from(mContext).inflate(R.layout.infinite_loading, parent, false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < zhihuDailyItems.size() && zhihuDailyItems.size() > 0) {
            return NOMAL_ITEM; //还没滑到底
        }
        return TYPE_LOADING_MORE;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case NOMAL_ITEM:
                bindViewHolderNormal((ZhihuViewHolder) holder, position);
                break;
            case TYPE_LOADING_MORE:
                bindLoadingViewHold((LoadingMoreHolder) holder, position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return zhihuDailyItems.size();
    }

    class ZhihuViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;
        final LinearLayout linearLayout;
        ImageView imageView;

        ZhihuViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_icon_id);
            textView = (TextView) itemView.findViewById(R.id.item_text_id);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.zhihu_item_layout);
        }
    }

    public static class LoadingMoreHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingMoreHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView;
        }
    }
    //设置正常显示的内容
    private void bindViewHolderNormal(final ZhihuViewHolder holder, final int position) {

        final ZhihuDailyItem zhihuDailyItem = zhihuDailyItems.get(holder.getAdapterPosition());

        if (DBUtils.getDB(mContext).isRead(Config.ZHIHU, zhihuDailyItem.getId(), 1))
            holder.textView.setTextColor(Color.GRAY); //读过的显示灰色
        else
            holder.textView.setTextColor(Color.BLACK); //没读过的显示黑色
        //设置ImageView点击监听器
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goDescribeActivity(holder,zhihuDailyItem);

            }
        });
        //设置标题及监听器
        holder.textView.setText(zhihuDailyItem.getTitle());
        holder.linearLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goDescribeActivity(holder,zhihuDailyItem);
                    }
                });

        //加载图片并设置到ImageView上
        Glide.with(mContext)
                .load(zhihuDailyItems.get(position).getImages()[0])
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (!zhihuDailyItem.hasFadedIn) {
                            holder.imageView.setHasTransientState(true);
                            final ObservableColorMatrix cm = new ObservableColorMatrix();
                            final ObjectAnimator animator = ObjectAnimator.ofFloat(cm, ObservableColorMatrix.SATURATION, 0f, 1f);
                            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                    holder.imageView.setColorFilter(new ColorMatrixColorFilter(cm));
                                }
                            });
                            animator.setDuration(2000L);
                            animator.setInterpolator(new AccelerateInterpolator());
                            animator.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    holder.imageView.clearColorFilter();
                                    holder.imageView.setHasTransientState(false);
                                    animator.start();
                                    zhihuDailyItem.hasFadedIn = true;

                                }
                            });
                        }

                        return false;
                    }
                }).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.imageView);

    }
    //加载更多
    private void bindLoadingViewHold(LoadingMoreHolder holder, int position) {
        holder.progressBar.setVisibility(showLoadingMore == true ? View.VISIBLE : View.INVISIBLE);
    }
    //跳转到显示具体内容的Activity
    private void goDescribeActivity(ZhihuViewHolder holder,ZhihuDailyItem zhihuDailyItem){
        //保存已经读过的到数据库
        DBUtils.getDB(mContext).insertHasRead(Config.ZHIHU, zhihuDailyItem.getId(), 1);
        holder.textView.setTextColor(Color.GRAY);
        Intent intent = new Intent(mContext, ZhihuDescribeActivity.class);
        intent.putExtra("id", zhihuDailyItem.getId());
        intent.putExtra("title", zhihuDailyItem.getTitle());
        intent.putExtra("image",mImageUrl);
        mContext.startActivity(intent);

    }
    //增加item
    public void addItems(ArrayList<ZhihuDailyItem> list) {
        int n = list.size();
        zhihuDailyItems.addAll(list);
        notifyDataSetChanged();
    }
    //清除所有的数据
    public void clearData() {
        zhihuDailyItems.clear();
        notifyDataSetChanged();
    }

    private int getLoadingMoreItemPosition() {
        //当处于加载状态时返回最后一行的位置，否则返回-1
        return showLoadingMore ? getItemCount() - 1 : RecyclerView.NO_POSITION;
    }

    public void loadingStart() {
        if (showLoadingMore) return;//加载开始时showLoadingMore原本的状态应该为false
        showLoadingMore = true;     //开始加载设置showLoadingMore为true
        notifyItemInserted(getLoadingMoreItemPosition()); //在最后插入数据
    }

    public void loadingfinish() {
        if (!showLoadingMore) return;
        //当先调用loadingStart时，showLoadingMore被设置为true,所以调用getLoadingMoreItemPosition时返回RecyclerView.NO_POSITION
        final int loadingPos = getLoadingMoreItemPosition();
        showLoadingMore = false;     //结束加载后设置showLoadingMore为false
        notifyItemRemoved(loadingPos); //正常情况下这行不会有任何的效果。
    }
}
