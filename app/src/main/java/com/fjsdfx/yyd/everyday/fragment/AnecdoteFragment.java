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
import com.fjsdfx.yyd.everyday.adapter.AnecdoteAdapter;
import com.fjsdfx.yyd.everyday.adapter.NBAAdapter;
import com.fjsdfx.yyd.everyday.api.ApiManage;
import com.fjsdfx.yyd.everyday.bean.AnedoteDate;
import com.fjsdfx.yyd.everyday.bean.NBA;
import com.fjsdfx.yyd.everyday.bean.TrBean;
import com.fjsdfx.yyd.everyday.util.CacheUtil;
import com.fjsdfx.yyd.everyday.util.NetUtil;
import com.fjsdfx.yyd.everyday.view.GridItemDividerDecoration;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/1/14.
 */

public class AnecdoteFragment extends Fragment {
    private View view = null;
    private RecyclerView mRecyclerView;
    private AnecdoteAdapter mAnecdoteAdapter;
    private ProgressBar mProgressBar;
    private boolean loading;
    private CacheUtil mCacheUtil;
    private int currentIndex;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);    //当Activity重新创建时保留Fragment实例
        view = inflater.inflate(R.layout.anecdote_fragment_layout, container, false);//加载布局
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initialDate();
        initialView();
    }

    private void initialView() {
        mAnecdoteAdapter = new AnecdoteAdapter(getContext());
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycle_anecdote);
        mProgressBar = (ProgressBar)view.findViewById(R.id.prograss);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new GridItemDividerDecoration(getContext(), R.dimen.divider_height, R.color.divider));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAnecdoteAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        //网络连上才加载数据
        if (NetUtil.checkConnectivity(getActivity(),view)) {
            if (mAnecdoteAdapter.getItemCount() > 0) {
                mAnecdoteAdapter.clearData();  //第一次加载先清空数据
            }
            currentIndex = 1;
            loadDate();
        }
    }

    private void initialDate() {
        mCacheUtil = CacheUtil.get(getContext());
    }

    public void loadDate(){
        mProgressBar.setVisibility(View.VISIBLE);
        ApiManage.getInstence().getAnecdoteService().getAnecdote(currentIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AnedoteDate>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AnedoteDate value) {
                        loading=false;
                        mProgressBar.setVisibility(View.INVISIBLE);
                        //Log.v("TAG",value.getShowapi_res_body().getMsg());
                        mAnecdoteAdapter.addItems(value.getShowapi_res_body().getNewslist());
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

    public void loadMoreDate(){
        mProgressBar.setVisibility(View.VISIBLE);
        ApiManage.getInstence().getAnecdoteService().getAnecdote(currentIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AnedoteDate>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AnedoteDate value) {
                        loading=false;
                        mProgressBar.setVisibility(View.INVISIBLE);

                        mAnecdoteAdapter.addItems(value.getShowapi_res_body().getNewslist());
                        //如果加载的数据没有把屏幕填满则需要再加载更多数据
                        if (!mRecyclerView.canScrollVertically(View.SCROLL_INDICATOR_BOTTOM)) {
                            loadMoreDate();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
