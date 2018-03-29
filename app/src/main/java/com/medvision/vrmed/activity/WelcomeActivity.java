package com.medvision.vrmed.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.medvision.vrmed.R;

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
