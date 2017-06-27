package com.fjsdfx.yyd.everyday;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


public class MyApplication extends Application {

    public final static String TAG = "BaseApplication";
    public final static boolean DEBUG = true;
    private static MyApplication myApplication;
    private static int mainTid;

    /**
     * Activity集合，来管理所有的Activity
     */
    private static List<AppCompatActivity> activities;

    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    public static Application getContext() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
        activities = new LinkedList<>();
        mainTid = android.os.Process.myTid();
    }

    /**
     * 获取application
     *
     * @return
     */
    public static Context getApplication() {
        return myApplication;
    }

    /**
     * 获取主线程ID
     *
     * @return
     */
    public static int getMainTid() {
        return mainTid;
    }

    /**
     * 添加一个Activity
     *
     * @param activity
     */
    public void addActivity(AppCompatActivity activity) {
        activities.add(activity);
    }

    /**
     * 结束一个Activity
     *
     * @param activity
     */
    public void removeActivity(AppCompatActivity activity) {
        activities.remove(activity);
    }

    /**
     * 结束当前所有Activity
     */
    public static void clearActivities() {
        ListIterator<AppCompatActivity> iterator = activities.listIterator();
        AppCompatActivity activity;
        while (iterator.hasNext()) {
            activity = iterator.next();
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 退出应运程序
     */
    public static void quiteApplication() {
        clearActivities();
        System.exit(0);
    }

}
