package com.med.vrmc.tablet.utils;

import android.content.Context;

import com.cs.common.database.MyModulePreference;
import com.cs.common.database.SpDictionary;
import com.cs.common.database.SpUtilsBase;
import com.med.vrmc.tablet.bean.User;
import com.orhanobut.logger.Logger;

/**
 * Created by raytine on 2018/1/8.
 */

public class SpUtils extends SpUtilsBase {
    private static SpUtils instance;

    public SpUtils() {
    }

    public static SpUtils getInstance() {
        if (instance == null) {
            instance = new SpUtils();
            return instance;
        } else {
            return instance;
        }
    }

    public void init(Context context) {
        if (mAppPreferences == null) {
            mAppPreferences = new MyModulePreference(context, context.getPackageName());
        }
    }

    public User getUser() {
        Object module = getModule(SpDictionary.SP_USER);
        if (module != null) {
            return (User) module;
        } else {
            Logger.wtf("用户信息为空");
            return null;
        }
    }

    public void saveUser(User user) {
        putModule(SpDictionary.SP_USER, user);
    }

    public String getToken() {
        User user = getUser();
        if (user != null) {
            return user.getToken();
        }
        return "";
    }
}
