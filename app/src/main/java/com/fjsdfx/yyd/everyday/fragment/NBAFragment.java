package com.fjsdfx.yyd.everyday.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fjsdfx.yyd.everyday.R;
import com.fjsdfx.yyd.everyday.adapter.NBAAdapter;
import com.fjsdfx.yyd.everyday.api.ApiManage;
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

public class NBAFragment extends Fragment {
    private View view = null;
    private RecyclerView mRecyclerView;
    private NBAAdapter mNBAAdapter;
    private ProgressBar mProgressBar;
    private CacheUtil mCacheUtil;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);    //当Activity重新创建时保留Fragment实例
        view = inflater.inflate(R.layout.nba_fragment_layout, container, false);//加载布局
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initialDate();
        initialView();
    }

    private void initialView() {
        mNBAAdapter = new NBAAdapter(getContext());
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recycle_nba);
        mProgressBar = (ProgressBar)view.findViewById(R.id.prograss);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new GridItemDividerDecoration(getContext(), R.dimen.divider_height, R.color.divider));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mNBAAdapter);

        //网络连上才加载数据
        if (NetUtil.checkConnectivity(getActivity(),view)) {
            if (mNBAAdapter.getItemCount() > 0) {
                mNBAAdapter.clearData();  //第一次加载先清空数据
            }
            loadDate();
        }
    }

    private void initialDate() {
        mCacheUtil = CacheUtil.get(getContext());
    }

    public void loadDate(){
        mProgressBar.setVisibility(View.VISIBLE);
        ApiManage.getInstence().getNbaService().getNBAData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NBA>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NBA value) {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        ArrayList<TrBean> trBean = new ArrayList<TrBean>();
                        for(int i = 0; i< value.getResult().getList().size(); i++){
                            for(int j = 0; j<value.getResult().getList().get(i).getTr().size(); j++){
                                trBean.add(value.getResult().getList().get(i).getTr().get(j));
                            }
                        }
                        mNBAAdapter.addItems(trBean);
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
