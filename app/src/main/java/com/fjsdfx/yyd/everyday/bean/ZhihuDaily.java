package com.fjsdfx.yyd.everyday.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/23.
 */

public class ZhihuDaily {
    private String date;
    private ArrayList<ZhihuDailyItem> mZhihuDailyItems;
    private ArrayList<ZhihuDailyItem> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<ZhihuDailyItem> getmZhihuDailyItems() {
        return mZhihuDailyItems;
    }

    public void setmZhihuDailyItems(ArrayList<ZhihuDailyItem> mZhihuDailyItems) {
        this.mZhihuDailyItems = mZhihuDailyItems;
    }

    public ArrayList<ZhihuDailyItem> getStories() {
        return stories;
    }

    public void setStories(ArrayList<ZhihuDailyItem> stories) {
        this.stories = stories;
    }
}

