package com.fjsdfx.yyd.everyday.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fjsdfx.yyd.everyday.R;

/**
 * Created by Administrator on 2016/12/22.
 */

public class SharePreferenceUtil {
    public static void putNevigationItem(Context context, int t){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt(context.getString(R.string.nevigation_item),t);
        editor.commit();
    }
    public static int getNevigationItem(Context context){
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(context.getString(R.string.nevigation_item),-1);
    }
}
