package com.fjsdfx.yyd.everyday.api;

import com.fjsdfx.yyd.everyday.bean.ZhihuDaily;
import com.fjsdfx.yyd.everyday.bean.ZhihuStory;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2016/12/23.
 */

public interface ZhihuApi {
    @GET("/api/4/news/latest")
    Observable<ZhihuDaily> getLastDaily();

    @GET("/api/4/news/before/{date}")
    Observable<ZhihuDaily> getTheDaily(@Path("date") String date);

    @GET("/api/4/news/{id}")
    Observable<ZhihuStory> getZhihuStory(@Path("id") String id);
}
