package com.fjsdfx.yyd.everyday.bean;



/**
 * Created by Administrator on 2017/1/19.
 */
public class NewsListBean {
    /**
     * title : 15张吓掉半条命的照片!看完不敢关灯
     * picUrl : http://img9.lieqi.com/upload1/thumb/28/132559.jpg
     * description : 猎奇奇闻
     * ctime : 2016-09-07 00:00
     * url : http://www.lieqi.com/read/45/132559/
     */

    private String title;
    private String picUrl;
    private String description;
    private String ctime;
    private String url;
    public boolean hasFadedIn = false;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
