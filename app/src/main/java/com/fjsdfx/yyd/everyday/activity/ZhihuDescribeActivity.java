package com.fjsdfx.yyd.everyday.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fjsdfx.yyd.everyday.R;
import com.fjsdfx.yyd.everyday.api.ApiManage;
import com.fjsdfx.yyd.everyday.bean.ZhihuStory;
import com.fjsdfx.yyd.everyday.util.Config;
import com.fjsdfx.yyd.everyday.util.WebUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ZhihuDescribeActivity extends AppCompatActivity {
    private CollapsingToolbarLayout mToolbarLayout;
    private WebView mWebView;
    private NestedScrollView mNest;
    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private String id;
    private String title;
    private String mImageUrl;
    private ImageView mImageView;
    private boolean isEmpty;
    private String mBody;
    private String[] scc;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu_describe);
        initData();
        initView();
        getZhihuStory(id);
    }

    private void initView(){
        mToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        mWebView = (WebView)findViewById(R.id.wv_zhihu);
        mNest = (NestedScrollView)findViewById(R.id.nest);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mImageView = (ImageView)findViewById(R.id.image);

        setSupportActionBar(mToolbar);
        //设置返回箭头
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setBlockNetworkImage(false);
        //settings.setUseWideViewPort(true);造成文字太小
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCachePath(getCacheDir().getAbsolutePath() + "/webViewCache");
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;//不调用系统浏览器
            }
        });
        //设置监听器
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZhihuDescribeActivity.this.finish();
            }
        });
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNest.smoothScrollTo(0,0);
            }
        });
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "感谢你的喜欢！", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
    }
    //获取传输的参数
    protected void initData() {
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        mImageUrl = getIntent().getStringExtra("image");
    }
    //获取网络数据并显示出来
    public void getZhihuStory(String id) {
        ApiManage.getInstence().getZhihuApiService().getZhihuStory(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZhihuStory>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ZhihuStory value) {
                        Glide.with(ZhihuDescribeActivity.this)
                                .load(value.getImage())
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into(mImageView);
                        url = value.getmShareUrl();
                        isEmpty= TextUtils.isEmpty(value.getBody());
                        mBody=value.getBody();
                        scc=value.getCss();
                        if (isEmpty) {
                            mWebView.loadUrl(url);
                        } else {
                            String data = WebUtil.buildHtmlWithCss(mBody, scc, Config.isNight);
                            mWebView.loadDataWithBaseURL(WebUtil.BASE_URL, data, WebUtil.MIME_TYPE, WebUtil.ENCODING, WebUtil.FAIL_URL);
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
