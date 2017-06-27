package com.fjsdfx.yyd.everyday.api;

import com.fjsdfx.yyd.everyday.MyApplication;
import com.fjsdfx.yyd.everyday.util.NetUtil;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by xinghongfei on 16/8/12.
 */
public class ApiManage {
    //使用OKHttp进行缓存处理
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (NetUtil.isNetWorkAvailable(MyApplication.getContext())) {
                int maxAge = 60; // 在线缓存在1分钟内可读取
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };
    public static ApiManage apiManage;
    private static File httpCacheDirectory = new File(MyApplication.getContext().getCacheDir(), "zhihuCache");
    private static int cacheSize = 10 * 1024 * 1024; // 10 MiB
    private static Cache cache = new Cache(httpCacheDirectory, cacheSize);
    //OKHttp客户端，供Retrofit使用
    private static OkHttpClient client = new OkHttpClient.Builder()
            .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .cache(cache)
            .build();
    public ZhihuApi zhihuApi;
    public TopNews topNews;
    private Object zhihuMonitor = new Object(); //为了同步处理创建的对象
    //实例对象
    public static ApiManage getInstence() {
        if (apiManage == null) {
            synchronized (ApiManage.class) {
                if (apiManage == null) {
                    apiManage = new ApiManage();
                }
            }
        }
        return apiManage;
    }
    //创建zhihu的Retrofit对象
    public ZhihuApi getZhihuApiService() {
        if (zhihuApi == null) {
            synchronized (zhihuMonitor) {
                if (zhihuApi == null) {
                    zhihuApi = new Retrofit.Builder()
                            .baseUrl("http://news-at.zhihu.com")
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(ZhihuApi.class);
                }
            }
        }

        return zhihuApi;
    }

    public TopNews getTopNewsService() {
        if (topNews == null) {
            synchronized (zhihuMonitor) {
                if (topNews == null) {
                    topNews = new Retrofit.Builder()
                            .baseUrl("http://c.m.163.com/nc/")
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(TopNews.class);

                }
            }
        }

        return topNews;
    }


    public MeiziApi ganK;
    public MeiziApi getGankService(){
        if (ganK==null){
            synchronized (zhihuMonitor){
                if (ganK==null){
                    ganK=new Retrofit.Builder()
                            .baseUrl("http://gank.io/")
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(MeiziApi.class);
                }


            }


        }
        return ganK;
    }

    public NBAApi nbaApi;
    public NBAApi getNbaService(){
        if (nbaApi==null){
            synchronized (zhihuMonitor){
                if (nbaApi==null){
                    nbaApi=new Retrofit.Builder()
                            .baseUrl("http://api.avatardata.cn/")
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(NBAApi.class);
                }
            }
        }
        return nbaApi;
    }

    public AnecdoteApi anecdoteApi;
    public AnecdoteApi getAnecdoteService(){
        if (anecdoteApi==null){
            synchronized (zhihuMonitor){
                if (anecdoteApi==null){
                    anecdoteApi=new Retrofit.Builder()
                            .baseUrl("https://route.showapi.com/")
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(AnecdoteApi.class);
                }
            }
        }
        return anecdoteApi;
    }

}
