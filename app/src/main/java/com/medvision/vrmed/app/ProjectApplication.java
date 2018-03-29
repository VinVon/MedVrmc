package com.medvision.vrmed.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.cs.networklibrary.util.PropertiesUtil;
import com.medvision.vrmed.R;
import com.medvision.vrmed.activity.LoginActivity;
import com.medvision.vrmed.activity.MainActivity2;
import com.medvision.vrmed.bean.User;
import com.medvision.vrmed.utils.SpUtils;
import com.medvision.vrmed.utils.StatusBarUtil;
import com.orhanobut.logger.Logger;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by raytine on 2017/12/29.
 */

public class ProjectApplication extends Application {
    private static final String TAG = "ProjectApplication";
    private static ProjectApplication appaplication;

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
        appaplication = this;
        PropertiesUtil.init(this);
        SpUtils.getInstance().init(this);
        Logger.init("MedVrmc");
        User user = SpUtils.getInstance().getUser();
        if (user == null) {
            startActivity(new Intent(appaplication, LoginActivity.class));
        } else {
            if (user.isFirstLogin()) {
                startActivity(new Intent(appaplication, LoginActivity.class));
            } else {//MainActivity
                startActivity(new Intent(appaplication, MainActivity2.class));
            }
        }

    }

    public static Context getContext() {
        return appaplication;
    }
}
