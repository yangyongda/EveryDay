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
import com.fjsdfx.yyd.everyday.adapter.MeiziAdapter;
import com.fjsdfx.yyd.everyday.api.ApiManage;
import com.fjsdfx.yyd.everyday.bean.MeiziData;
import com.fjsdfx.yyd.everyday.util.CacheUtil;

import com.fjsdfx.yyd.everyday.util.Config;
import com.fjsdfx.yyd.everyday.util.NetUtil;
import com.fjsdfx.yyd.everyday.view.GridItemDividerDecoration;
import com.fjsdfx.yyd.everyday.view.WrapContentLinearLayoutManager;
import com.google.gson.Gson;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/1/7.
 */

public class MeiziFragment extends Fragment {
    private CacheUtil mCacheUtil;
    private MeiziAdapter mMeiziAdapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Gson gson = new Gson();
    int currentIndex = 1;
    private boolean loading;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.meizi_fragment_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intialDate();
        initialView();
        if(NetUtil.checkConnectivity(getActivity(),view)){
            loadDate();
        }

    }

    private void initialView() {
        mMeiziAdapter = new MeiziAdapter(getContext());
        recyclerView = (RecyclerView)view.findViewById(R.id.recycle_meizi);
        progressBar = (ProgressBar)view.findViewById(R.id.prograss);
        //自定义的WrapContentLinearLayoutManager解决IndexOutOfBoundsException异常
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new GridItemDividerDecoration(getContext(), R.dimen.divider_height, R.color.divider));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setAdapter(mMeiziAdapter);
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
                        currentIndex+=1;
                        loadMoreDate();
                    }
                }
            }
        });

    }
    private void loadDate() {
        if (mMeiziAdapter.getItemCount() > 0) {
            mMeiziAdapter.clearData();
        }
        ApiManage.getInstence().getGankService().getMeizhiData(currentIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MeiziData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MeiziData value) {
                        Log.v("meizi","onnext");
                        progressBar.setVisibility(View.INVISIBLE);
                        mCacheUtil.put(Config.ZHIHU, gson.toJson(value));
                        mMeiziAdapter.loadingfinish();
                        loading=false;
                        mMeiziAdapter.addItems(value.getResults());
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
    private void loadMoreDate() {
        mMeiziAdapter.loadingStart();
        ApiManage.getInstence().getGankService().getMeizhiData(currentIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MeiziData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MeiziData value) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (loading) {
                            loading = false;
                            mMeiziAdapter.loadingfinish();
                        }
                        mMeiziAdapter.addItems(value.getResults());
                        //如果加载的数据没有把屏幕填满则需要再加载更多数据
                        if (!recyclerView.canScrollVertically(View.SCROLL_INDICATOR_BOTTOM)) {
                            currentIndex += 1;
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


    private void intialDate() {
        mCacheUtil = CacheUtil.get(getContext());
    }
}
