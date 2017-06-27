package com.fjsdfx.yyd.everyday.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @SerializedName("T1348647909107")的作用：
 * 本来ArrayList<NewBean> newsList应该定义为ArrayList<NewBean> T1348647909107
 * 但是这样不友好，所以使用@SerializedName("T1348647909107")来解决这个问题。这样我们
 * 就可以自己随意命名了
 */
public class NewsList {
    @SerializedName("T1348647909107")
    ArrayList<NewsBean> newsList;
    public ArrayList<NewsBean> getNewsList() {
        return newsList;
    }
    public void setNewsList(ArrayList<NewsBean> newsList) {
        this.newsList = newsList;
    }
}
