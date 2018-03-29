package com.medvision.vrmed.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.medvision.vrmed.R;
import com.medvision.vrmed.base.BaseMvpActivity;
import com.medvision.vrmed.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 向文圣 on 2018/3/22.
 */

public class SystemActivity extends BaseMvpActivity {
    @BindView(R.id.system_back)
    TextView systemBack;
    @BindView(R.id.system_comple)
    TextView systemComple;

    @Override
    public void onError() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.system_activity);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.system_back, R.id.system_comple})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.system_back:
                this.finish();
                break;
            case R.id.system_comple:
                break;
        }
    }
}
