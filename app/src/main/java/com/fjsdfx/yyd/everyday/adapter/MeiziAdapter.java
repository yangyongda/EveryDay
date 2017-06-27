package com.fjsdfx.yyd.everyday.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorMatrixColorFilter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.fjsdfx.yyd.everyday.R;
import com.fjsdfx.yyd.everyday.activity.MeiziPhotoDescribeActivity;
import com.fjsdfx.yyd.everyday.bean.Meizi;
import com.fjsdfx.yyd.everyday.util.ObservableColorMatrix;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/7.
 */

public class MeiziAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_LOADING_MORE = -1;
    private static final int NOMAL_ITEM = 1;
    private  Context mContext;
    boolean showLoadingMore;
    private ArrayList<Meizi> meiziItemes = new ArrayList<>();

    public MeiziAdapter(Context context){
        mContext = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NOMAL_ITEM:
                return new MeiziViewHolder(LayoutInflater.from(mContext).inflate(R.layout.meizi_layout_item, parent, false));
            case TYPE_LOADING_MORE:
                return new LoadingMoreHolder(LayoutInflater.from(mContext).inflate(R.layout.infinite_loading, parent, false));

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case NOMAL_ITEM:
                bindViewHolderNormal((MeiziAdapter.MeiziViewHolder) holder, position);
                break;
            case TYPE_LOADING_MORE:
                bindLoadingViewHold((MeiziAdapter.LoadingMoreHolder) holder, position);
                break;
        }
    }
    //设置正常显示的内容
    private void bindViewHolderNormal(final MeiziAdapter.MeiziViewHolder holder, final int position) {

        final Meizi meizi = meiziItemes.get(holder.getAdapterPosition());
        String url;
        //设置ImageView点击监听器
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goDescribeActivity(meizi);
            }
        });
        //加载图片并设置到ImageView上
        Glide.with(mContext)
                .load(meizi.getUrl())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (!meizi.hasFadedIn) {
                            holder.mImageView.setHasTransientState(true);
                            final ObservableColorMatrix cm = new ObservableColorMatrix();
                            final ObjectAnimator animator = ObjectAnimator.ofFloat(cm, ObservableColorMatrix.SATURATION, 0f, 1f);
                            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                    holder.mImageView.setColorFilter(new ColorMatrixColorFilter(cm));
                                }
                            });
                            animator.setDuration(2000L);
                            animator.setInterpolator(new AccelerateInterpolator());
                            animator.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    holder.mImageView.clearColorFilter();
                                    holder.mImageView.setHasTransientState(false);
                                    animator.start();
                                    meizi.hasFadedIn = true;
                                }
                            });
                        }

                        return false;
                    }
                }).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(holder.mImageView);

    }

    private void goDescribeActivity(Meizi meizi) {
        Intent intent = new Intent(mContext, MeiziPhotoDescribeActivity.class);
        intent.putExtra("image",meizi.getUrl());
        mContext.startActivity(intent);
    }

    //加载更多
    private void bindLoadingViewHold(MeiziAdapter.LoadingMoreHolder holder, int position) {
        holder.progressBar.setVisibility(showLoadingMore == true ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemViewType(int position) {
        if (position < meiziItemes.size() && meiziItemes.size() > 0) {
            return NOMAL_ITEM; //还没滑到底
        }
        return TYPE_LOADING_MORE;
    }

    @Override
    public int getItemCount() {
        return meiziItemes.size();
    }

    public static class LoadingMoreHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        public LoadingMoreHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView;
        }
    }

    class MeiziViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        MeiziViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.item_image);

        }
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

    //增加item
    public void addItems(List<Meizi> list) {
        meiziItemes.addAll(list);
        notifyDataSetChanged();
    }
    //清除所有的数据
    public void clearData() {
        meiziItemes.clear();
        notifyDataSetChanged();
    }
}
