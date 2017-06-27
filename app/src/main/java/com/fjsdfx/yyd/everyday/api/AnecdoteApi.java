package com.fjsdfx.yyd.everyday.api;

import com.fjsdfx.yyd.everyday.bean.AnedoteDate;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/1/19.
 */

public interface AnecdoteApi {
    @GET("231-1?num=10&showapi_appid=30875&showapi_sign=0cb441f98d9d43e9a645691e975e8399")
    Observable<AnedoteDate> getAnecdote(@Query("page") int page);
}
