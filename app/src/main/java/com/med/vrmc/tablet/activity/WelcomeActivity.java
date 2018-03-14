package com.med.vrmc.tablet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.med.vrmc.tablet.R;
import com.med.vrmc.tablet.bean.User;
import com.med.vrmc.tablet.fragment.MainFragment;
import com.med.vrmc.tablet.utils.SpUtils;

/**
 * 欢迎页
 * Created by raytine on 2018/1/8.
 */

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
    }
}
