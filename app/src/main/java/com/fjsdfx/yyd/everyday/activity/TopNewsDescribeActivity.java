package com.fjsdfx.yyd.everyday.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fjsdfx.yyd.everyday.R;
import com.fjsdfx.yyd.everyday.util.WebUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class TopNewsDescribeActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_top_news_describe);
        initData();
        initView();
        getTopNews(id);
    }

    private void initView(){
        mToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        mWebView = (WebView) findViewById(R.id.wv_news);
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
                TopNewsDescribeActivity.this.finish();
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

    protected void initData() {
        id = getIntent().getStringExtra("docid");
        title = getIntent().getStringExtra("title");
        mImageUrl = getIntent().getStringExtra("image");
        //Log.v("id",id+"");
    }

    public void getTopNews(final String id) {
        Glide.with(this)
                .load(mImageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mImageView);
        //使用volley来解析JSON数据
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://c.m.163.com/nc/article/"+id+"/full.html", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    //响应数据处理
                    JSONObject retData = jsonObject.getJSONObject(id);
                    String body = retData.getString("body");
                    mWebView.loadDataWithBaseURL(null,body, WebUtil.MIME_TYPE, WebUtil.ENCODING,null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        Volley.newRequestQueue(TopNewsDescribeActivity.this).add(request); //发送请求

    }
}
