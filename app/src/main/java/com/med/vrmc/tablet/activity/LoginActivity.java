package com.med.vrmc.tablet.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cs.common.utils.ToastUtil;
import com.cs.networklibrary.http.HttpMethods;
import com.cs.networklibrary.http.HttpResultFunc;
import com.cs.networklibrary.subscribers.ProgressSubscriber;
import com.med.vrmc.tablet.R;
import com.med.vrmc.tablet.base.BaseActivity;
import com.med.vrmc.tablet.base.BaseMvpActivity;
import com.med.vrmc.tablet.bean.User;
import com.med.vrmc.tablet.bean.req.UserReq;
import com.med.vrmc.tablet.imp.UserInfoView;
import com.med.vrmc.tablet.inject.InjectPresenter;
import com.med.vrmc.tablet.network.UserService;
import com.med.vrmc.tablet.presenter.UserInfoPresenter;
import com.med.vrmc.tablet.utils.IConstant;
import com.med.vrmc.tablet.utils.SpUtils;
import com.med.vrmc.tablet.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by raytine on 2018/1/2.
 */

public class LoginActivity extends BaseMvpActivity<UserInfoPresenter> implements UserInfoView{
    @BindView(R.id.login_admin)
    EditText loginAdmin;
    @BindView(R.id.login_pass)
    EditText loginPass;
    @BindView(R.id.login_btn)
    Button loginBtn;
    private UserService mUserService;
    private String device_model = "";
    private String version_release = "";
    private String version;
    private String device_id = "";
    private String username;
    private String passworld;

    @Override
    protected UserInfoPresenter createPresenter() {
        return new UserInfoPresenter();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        StatusBarUtil.setActivityTranslucent(this);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        } else {
            device_id = IConstant.devoceId(this);//String
        }
        version = IConstant.getVersion(this);
        device_model = IConstant.getModel();
        version_release = IConstant.getVersionRelease();
    }

    @OnClick(R.id.login_btn)
    public void OnClick(View v) {
        username = loginAdmin.getText().toString();
        if (username.isEmpty()){
            ToastUtil.showMessage(this,"账号不能为空");
            return;
        }
        passworld = loginPass.getText().toString();
        if (passworld.isEmpty()){
            ToastUtil.showMessage(this,"请输入密码");
            return;
        }
        UserReq userReq = new UserReq();
        userReq.setAppId(device_id);
        userReq.setAppVersion(version);
        userReq.setChannel("null");
        userReq.setDeviceModel(device_model);
        userReq.setDeviceSystem("android");
        userReq.setDeviceVersion(version_release);
        userReq.setPassword(passworld);
        userReq.setUsername(username);
        getPresenter().getUsers(userReq);
    }

    @Override
    public void onLoading() {
        initProgressDialog();
    }

    @Override
    public void onError() {
        dismissProgressDialog();
    }

    @Override
    public void onSucceed(User o) {
        dismissProgressDialog();
        User user = SpUtils.getInstance().getUser();
        if (user == null){
            user = new User();
        }
        user.setUsername(o.getUsername());
        user.setPassword(o.getPassword());
        user.setToken(o.getToken());
        user.setFirstLogin(false);
        SpUtils.getInstance().saveUser(user);
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
    }
}
