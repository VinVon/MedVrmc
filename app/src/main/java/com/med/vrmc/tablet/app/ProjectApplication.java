package com.med.vrmc.tablet.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.cs.networklibrary.util.PropertiesUtil;
import com.med.vrmc.tablet.activity.LoginActivity;
import com.med.vrmc.tablet.activity.MainActivity;
import com.med.vrmc.tablet.activity.MainActivity2;
import com.med.vrmc.tablet.activity.WelcomeActivity;
import com.med.vrmc.tablet.bean.User;
import com.med.vrmc.tablet.utils.SpUtils;
import com.orhanobut.logger.Logger;

/**
 * Created by raytine on 2017/12/29.
 */

public class ProjectApplication extends Application {
    private static final String TAG = "ProjectApplication";
    private static ProjectApplication appaplication;

    @Override
    public void onCreate() {
        super.onCreate();
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
