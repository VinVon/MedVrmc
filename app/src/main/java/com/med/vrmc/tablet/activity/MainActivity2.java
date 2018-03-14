package com.med.vrmc.tablet.activity;

import com.med.vrmc.tablet.R;
import com.med.vrmc.tablet.base.BaseMvpActivity;
import com.med.vrmc.tablet.base.BasePresenter;
import com.med.vrmc.tablet.utils.StatusBarUtil;

/**
 * Created by raytine on 2018/3/9.
 */

public class MainActivity2 extends BaseMvpActivity {
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
        setContentView(R.layout.activity_main2);
    }
}
