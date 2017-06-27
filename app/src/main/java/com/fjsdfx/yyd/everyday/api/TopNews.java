package com.fjsdfx.yyd.everyday.api;


import com.fjsdfx.yyd.everyday.bean.NewsList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2016/12/29.
 */

public interface TopNews {
    @GET("article/headline/T1348647909107/{id}-20.html")
    Observable<NewsList> getNews(@Path("id") int id );

}
