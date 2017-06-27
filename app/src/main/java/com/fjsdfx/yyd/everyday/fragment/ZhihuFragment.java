package com.fjsdfx.yyd.everyday.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fjsdfx.yyd.everyday.R;
import com.fjsdfx.yyd.everyday.adapter.ZhihuAdapter;
import com.fjsdfx.yyd.everyday.api.ApiManage;
import com.fjsdfx.yyd.everyday.bean.ZhihuDaily;
import com.fjsdfx.yyd.everyday.bean.ZhihuDailyItem;
import com.fjsdfx.yyd.everyday.util.CacheUtil;
import com.fjsdfx.yyd.everyday.util.Config;
import com.fjsdfx.yyd.everyday.util.NetUtil;
import com.fjsdfx.yyd.everyday.view.GridItemDividerDecoration;
import com.google.gson.Gson;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;



/**
 * Created by Administrator on 2016/12/22.
 */

public class ZhihuFragment extends Fragment {
    private View view = null;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private boolean loading;
    boolean connected = true;
    private ZhihuAdapter zhihuAdapter;
    private String currentLoadDate;
    private CacheUtil mCacheUtil;
    private Gson gson = new Gson();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);    //当Activity重新创建时保留Fragment实例
        view = inflater.inflate(R.layout.zhihu_fragment_layout, container, false);//加载布局
        return view;
    }
    /**
     * onCreateView调用完就调用该方法
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //Log.v("TAG","TAG");
        initialDate();
        initialView();
    }

    private void initialDate() {
        mCacheUtil = CacheUtil.get(getContext());
    }

    private void initialView() {
        zhihuAdapter = new ZhihuAdapter(getContext());
        recyclerView = (RecyclerView)view.findViewById(R.id.recycle_zhihu);
        progressBar = (ProgressBar)view.findViewById(R.id.prograss);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new GridItemDividerDecoration(getContext(), R.dimen.divider_height, R.color.divider));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(zhihuAdapter);
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
                        loadMoreDate();
                    }
                }
            }
        });
        //网络连上才加载数据
        if (NetUtil.checkConnectivity(getActivity(),view)) {
            if (zhihuAdapter.getItemCount() > 0) {
                zhihuAdapter.clearData();  //第一次加载先清空数据
            }
            currentLoadDate = "0";  //从0开始加载
            getLastZhihuNews();    //加载数据，和loadMoreDate方法差不多，只是一个是第一次加载，一个是以后加载更多
        }
    }

    private void loadMoreDate() {
        //设置加载标志
        zhihuAdapter.loadingStart();
        //使用rxjava和retrofit2进行网络数据获取
        ApiManage.getInstence().getZhihuApiService().getTheDaily(currentLoadDate)
                .map(new Function<ZhihuDaily, ZhihuDaily>() {
                    @Override
                    public ZhihuDaily apply(ZhihuDaily zhihuDaily) throws Exception {
                        String date = zhihuDaily.getDate();
                        for (ZhihuDailyItem zhihuDailyItem : zhihuDaily.getStories()) {
                            zhihuDailyItem.setDate(date);
                        }
                        return zhihuDaily;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuDaily>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ZhihuDaily value) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (loading) {
                            loading = false;
                            zhihuAdapter.loadingfinish();
                        }
                        currentLoadDate = value.getDate();
                        zhihuAdapter.addItems(value.getStories());
                        //如果加载的数据没有把屏幕填满则需要再加载更多数据
                        if (!recyclerView.canScrollVertically(View.SCROLL_INDICATOR_BOTTOM)) {
                            loadMoreDate();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getLastZhihuNews() {
        progressBar.setVisibility(View.VISIBLE);
        ApiManage.getInstence().getZhihuApiService().getLastDaily()
                .map(new Function<ZhihuDaily, ZhihuDaily>() {
                    @Override
                    public ZhihuDaily apply(ZhihuDaily zhihuDaily) throws Exception {
                        String date = zhihuDaily.getDate();
                        for (ZhihuDailyItem zhihuDailyItem : zhihuDaily.getStories()) {
                            zhihuDailyItem.setDate(date);
                        }
                        return zhihuDaily;
                    }
                })
                .subscribeOn(Schedulers.io())   //网络请求在IO线程
                .observeOn(AndroidSchedulers.mainThread()) //处理在主线程
                .subscribe(new Observer<ZhihuDaily>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ZhihuDaily value) {
                        progressBar.setVisibility(View.INVISIBLE);
                        mCacheUtil.put(Config.ZHIHU, gson.toJson(value)); //缓存数据
                        if (loading) {
                            loading = false;
                            zhihuAdapter.loadingfinish();
                        }
                        currentLoadDate = value.getDate();
                        zhihuAdapter.addItems(value.getStories());
                        //没到底就继续加载
                        if (!recyclerView.canScrollVertically(View.SCROLL_INDICATOR_BOTTOM)) {
                            loadMoreDate();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
