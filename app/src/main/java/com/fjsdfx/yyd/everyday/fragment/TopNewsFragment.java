package com.fjsdfx.yyd.everyday.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fjsdfx.yyd.everyday.R;
import com.fjsdfx.yyd.everyday.adapter.TopNewsAdapter;
import com.fjsdfx.yyd.everyday.api.ApiManage;
import com.fjsdfx.yyd.everyday.bean.NewsList;
import com.fjsdfx.yyd.everyday.bean.ZhihuDaily;
import com.fjsdfx.yyd.everyday.bean.ZhihuDailyItem;
import com.fjsdfx.yyd.everyday.util.CacheUtil;
import com.fjsdfx.yyd.everyday.util.NetUtil;
import com.fjsdfx.yyd.everyday.view.GridItemDividerDecoration;
import com.fjsdfx.yyd.everyday.view.WrapContentLinearLayoutManager;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/28.
 */

public class TopNewsFragment extends Fragment {
    private View view = null;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private boolean loading;
    private TopNewsAdapter mTopNewsAdapter;
    boolean connected = true;
    int currentIndex;
    private CacheUtil mCacheUtil;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);    //当Activity重新创建时保留Fragment实例
        view = inflater.inflate(R.layout.topnews_fragment_layout, container, false);//加载布局
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialDate();
        initialView();

    }

    private void initialView() {
        mTopNewsAdapter = new TopNewsAdapter(getContext());
        recyclerView = (RecyclerView)view.findViewById(R.id.recycle_topnews);
        progressBar = (ProgressBar)view.findViewById(R.id.prograss);
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new GridItemDividerDecoration(getContext(), R.dimen.divider_height, R.color.divider));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mTopNewsAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) //向下滚动
                {
                    //获取显示的item数，总共的item数，第一个显示item的位置
                    int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                    int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                    int pastVisiblesItems = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    //当不处于加载状态并且第一个显示的item位置加上显示的总数大于总item数时则开始加载数据
                    if (!loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = true;
                        currentIndex += 1;
                        loadMoreDate();
                    }
                }
            }
        });
        if (NetUtil.checkConnectivity(getActivity(),view)) {
            if (mTopNewsAdapter.getItemCount() > 0) {
                mTopNewsAdapter.clearData();
            }
            currentIndex = 0;
            getNewsList();
        }
    }

    private void getNewsList() {
        ApiManage.getInstence().getTopNewsService().getNews(currentIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsList value) {
                        loading=false;
                        progressBar.setVisibility(View.INVISIBLE);
                        mTopNewsAdapter.addItems(value.getNewsList());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initialDate() {
        mCacheUtil = CacheUtil.get(getContext());
    }
    private void loadMoreDate() {
        //设置加载标志
        mTopNewsAdapter.loadingStart();
        //使用rxjava和retrofit2进行网络数据获取
        ApiManage.getInstence().getTopNewsService().getNews(currentIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsList value) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (loading) {
                            loading = false;
                            mTopNewsAdapter.loadingfinish();
                        }
                        mTopNewsAdapter.addItems(value.getNewsList());
                        //如果加载的数据没有把屏幕填满则需要再加载更多数据
                        if (!recyclerView.canScrollVertically(View.SCROLL_INDICATOR_BOTTOM)) {
                            loadMoreDate();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        currentIndex += 1;
                        loadMoreDate();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
