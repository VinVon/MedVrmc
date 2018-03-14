package com.med.vrmc.tablet.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by raytine on 2017/8/10.
 */

public class StatusBarUtil {
    private static Object statusBarHeight;

    /**
     * 为activity的状态栏设置颜色
     *
     * @param activity
     * @param color
     */
    public static void setStatusBarColor(Activity activity, int color) {
        //5.0以上的手机
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(color);
        }
        //4.4-5.0之间
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //在状态栏加一个布局
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            //状态栏的高度根据手机实际高度获取
            View view = new View(activity);
            ViewGroup.LayoutParams params =new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getStatusBarHeight(activity));
            view.setLayoutParams(params);
            view.setBackgroundColor(color);
            decorView.addView(view);
            //获取activity的根布局
            ViewGroup viewById = (ViewGroup) activity.findViewById(android.R.id.content);
            View childAt = viewById.getChildAt(0);
            childAt.setFitsSystemWindows(true);
        }
    }

    public static int getStatusBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int statusBarHeight = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelOffset(statusBarHeight);
    }

    /**
     * 设置activity 全屏
     * @param activity
     */
    public static  void setActivityTranslucent(Activity activity){
        //5.0以上的手机
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN );
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
//            ViewGroup viewById = (ViewGroup) activity.findViewById(android.R.id.content);
//            View childAt = viewById.getChildAt(0);
//            childAt.setFitsSystemWindows(true);
        }
        //4.4-5.0之间
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
