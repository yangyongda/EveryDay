package com.fjsdfx.yyd.everyday.api;

import com.fjsdfx.yyd.everyday.bean.NBA;

import io.reactivex.Observable;
import retrofit2.http.GET;


/**
 * Created by Administrator on 2017/1/14.
 */

public interface NBAApi {
    @GET("Nba/NomalRace?key=41f5fd2f50b54e869d9411831826d988")
    Observable<NBA> getNBAData();
}
