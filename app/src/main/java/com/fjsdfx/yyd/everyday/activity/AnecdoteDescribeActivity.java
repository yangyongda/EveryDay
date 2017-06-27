package com.fjsdfx.yyd.everyday.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fjsdfx.yyd.everyday.R;

public class AnecdoteDescribeActivity extends AppCompatActivity {
    private CollapsingToolbarLayout mToolbarLayout;
    private WebView mWebView;
    private NestedScrollView mNest;
    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private String picUrl;
    private ImageView mImageView;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anecdote_describe);
        initData();
        initView();
        getDate();
    }

    private void initView(){
        mToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        mWebView = (WebView) findViewById(R.id.wv_anecdote);
        mNest = (NestedScrollView)findViewById(R.id.nest);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mImageView = (ImageView)findViewById(R.id.image);

        setSupportActionBar(mToolbar);
        //设置返回箭头
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        //设置监听器
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnecdoteDescribeActivity.this.finish();
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
    }

    protected void initData() {
        picUrl = getIntent().getStringExtra("picurl");
        url = getIntent().getStringExtra("url");
        
        //Log.v("id",id+"");
    }

    public void getDate() {
        Glide.with(this)
                .load(picUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mImageView);
        mWebView.loadUrl(url);
    }
}
