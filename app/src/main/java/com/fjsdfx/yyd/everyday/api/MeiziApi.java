package com.fjsdfx.yyd.everyday.api;

import com.fjsdfx.yyd.everyday.bean.MeiziData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/1/7.
 */

public interface MeiziApi {
    @GET("api/data/福利/10/{page}")
    Observable<MeiziData> getMeizhiData(@Path("page") int page);
}
