package com.medvision.vrmed.base;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cs.common.utils.ActivityManager;

/**
 * Created by raytine on 2017/12/29.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().attach(this);
        initView();
        initData();
}

    public abstract void initData();

    public abstract void initView();

    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().deAttach(this);
        super.onDestroy();

    }
}
