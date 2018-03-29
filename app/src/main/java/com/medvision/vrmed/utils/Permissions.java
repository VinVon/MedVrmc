package com.medvision.vrmed.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by raytine on 2018/1/4.
 */

public class Permissions {
    private Context mContext;

    public Permissions(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 检查是否拥有指定的所有权限
     */
    public boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mContext, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }
}
