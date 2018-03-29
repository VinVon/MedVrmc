package com.medvision.vrmed.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cs.common.utils.ToastUtil;
import com.medvision.vrmed.R;
import com.medvision.vrmed.activity.ChangeActivity;
import com.medvision.vrmed.activity.CleanActivity;
import com.medvision.vrmed.activity.LogoutActivity;
import com.medvision.vrmed.activity.SystemActivity;

/**
 * Created by 向文圣 on 2018/3/21.
 */

public class PopWindowUtil {
    private static PopWindowUtil instance;

    private PopupWindow mPopupWindow;
    private TextView t1, t2, t3;

    // 私有化构造方法，变成单例模式
    private PopWindowUtil() {

    }

    // 对外提供一个该类的实例，考虑多线程问题，进行同步操作
    public static PopWindowUtil getInstance() {
        if (instance == null) {
            synchronized (PopWindowUtil.class) {
                if (instance == null) {
                    instance = new PopWindowUtil();
                }
            }
        }
        return instance;
    }

    //    /**
//     * @param cx    activity
//     * @param view  传入需要显示在什么控件下
//     * @param layid 传入内容的id
//     * @return
//     */
    public PopWindowUtil makePopupWindow(Context cx, View view, int layid, int color, int type) {

        View view1 = LayoutInflater.from(cx).inflate(layid, null);
        if (type == 1) {
            t1 = (TextView) view1.findViewById(R.id.admin_name);
            t2 = (TextView) view1.findViewById(R.id.admin_pass);
            t3 = (TextView) view1.findViewById(R.id.admin_phone);
        } else {
            t1 = (TextView) view1.findViewById(R.id.menu_logout);
            t2 = (TextView) view1.findViewById(R.id.menu_clean);
            t3 = (TextView) view1.findViewById(R.id.menu_system);
        }
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    Intent intent = new Intent(cx, ChangeActivity.class);
                    intent.putExtra("type",1);
                    cx.startActivity(intent);
                } else {
                    cx.startActivity(new Intent(cx, LogoutActivity.class));
                }
                mPopupWindow.dismiss();
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    Intent intent = new Intent(cx, ChangeActivity.class);
                    intent.putExtra("type",2);
                    cx.startActivity(intent);
                } else {
                    cx.startActivity(new Intent(cx, CleanActivity.class));
                }
                mPopupWindow.dismiss();
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    Intent intent = new Intent(cx, ChangeActivity.class);
                    intent.putExtra("type",3);
                    cx.startActivity(intent);
                } else {
                    cx.startActivity(new Intent(cx, SystemActivity.class));
                }
                mPopupWindow.dismiss();
            }
        });
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wmManager = (WindowManager) cx.getSystemService(Context.WINDOW_SERVICE);
        wmManager.getDefaultDisplay().getMetrics(dm);
        int Hight = dm.heightPixels;
        mPopupWindow = new PopupWindow(cx);
        mPopupWindow.setBackgroundDrawable(cx.getResources().getDrawable(R.drawable.set_background));

        view1.setPadding(0, 30, 0, 0);
//        mPopupWindow.setBackgroundDrawable(new ColorDrawable(color));
//        view1.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
        // 设置PopupWindow的大小（宽度和高度）
//        mPopupWindow.setWidth(view.getWidth());
//        mPopupWindow.setHeight((Hight + view.getBottom()) * 2 / 3);
        // 设置PopupWindow的内容view
        mPopupWindow.setContentView(view1);
        mPopupWindow.setFocusable(true); // 设置PopupWindow可获得焦点
        mPopupWindow.setTouchable(true); // 设置PopupWindow可触摸
        mPopupWindow.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
        return instance;
    }


    /**
     * @param cx   此处必须为Activity的实例
     * @param view 显示在该控件之下
     * @param xOff 距离view的x轴偏移量
     * @param yOff 距离view的y轴偏移量
     * @param anim 弹出及消失动画
     * @return
     */
    public PopupWindow showLocationWithAnimation(final Context cx, View view,
                                                 int xOff, int yOff, int anim) {
        // 弹出动画
        mPopupWindow.setAnimationStyle(anim);

        // 弹出PopupWindow时让后面的界面变暗
        WindowManager.LayoutParams parms = ((Activity) cx).getWindow().getAttributes();
        parms.alpha = 0.5f;
        ((Activity) cx).getWindow().setAttributes(parms);

        int[] positon = new int[2];
        view.getLocationOnScreen(positon);
        // 弹窗的出现位置，在指定view之下
        mPopupWindow.showAsDropDown(view, xOff, yOff);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // PopupWindow消失后让后面的界面变亮
                WindowManager.LayoutParams parms = ((Activity) cx).getWindow().getAttributes();
                parms.alpha = 1.0f;
                ((Activity) cx).getWindow().setAttributes(parms);
                //自定义接口进行弹出框消失时的操作
                if (mListener != null) {
                    mListener.dissmiss();
                }
            }
        });

        return mPopupWindow;
    }

    private interface OnDissmissListener {

        void dissmiss();

    }

    private OnDissmissListener mListener;

    public void setOnDissmissListener(OnDissmissListener listener) {
        mListener = listener;
    }
}
