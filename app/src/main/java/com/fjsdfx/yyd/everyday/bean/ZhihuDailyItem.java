package com.fjsdfx.yyd.everyday.bean;

/**
 * Created by Administrator on 2016/12/23.
 */

public class ZhihuDailyItem {
    private String[] images;
    private int type;
    private String id;
    private String title;
    private String date;
    public boolean hasFadedIn = false;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
