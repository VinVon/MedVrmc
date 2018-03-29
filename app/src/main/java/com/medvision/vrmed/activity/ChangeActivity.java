package com.medvision.vrmed.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class ChangeActivity extends BaseMvpActivity {
    @BindView(R.id.changge_item_title)
    TextView changgeItemTitle;
    @BindView(R.id.changge_item_value)
    TextView changgeItemValue;
    @BindView(R.id.change_item2_value)
    EditText changeItem2Value;
    @BindView(R.id.change_l2)
    LinearLayout changeL2;
    @BindView(R.id.change_back)
    TextView changeBack;
    @BindView(R.id.change_comple)
    TextView changeComple;

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
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        if (type == 1) {
            changgeItemTitle.setText("治疗师");
        } else if (type == 2) {//
            changgeItemTitle.setText("新密码");
        } else if (type == 3) {
            changgeItemTitle.setText("手机号");
        }
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.change_activity);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.change_back, R.id.change_comple})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.change_back:
                this.finish();
                break;
            case R.id.change_comple:
                break;
        }
    }
}
