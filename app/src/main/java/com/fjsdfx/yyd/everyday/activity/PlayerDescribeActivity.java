package com.fjsdfx.yyd.everyday.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.fjsdfx.yyd.everyday.R;
import com.fjsdfx.yyd.everyday.util.WebUtil;

public class PlayerDescribeActivity extends AppCompatActivity {
    private WebView mWebView;
    private RelativeLayout mRelativeLayout;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_describe);
        intialData();
        initalView();
        mWebView.loadUrl(url);
    }

    private void intialData(){
        url = getIntent().getStringExtra("playerUrl");
    }

    private void initalView(){
        mRelativeLayout = (RelativeLayout)findViewById(R.id.activity_player_describe);
        mWebView = (WebView)findViewById(R.id.wv_nba);
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        PlayerDescribeActivity.this.finish();
        //要将RelativeLayout中的WebView移除才能调用WebView的destroy()方法，否则会崩
        mRelativeLayout.removeAllViews();
        //解决解决Activity后后台还有声音
        mWebView.destroy();  //销毁WebView ,否则WebView所在的线程并没销毁。
    }


}
