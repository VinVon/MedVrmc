package com.medvision.vrmed.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cs.common.utils.ToastUtil;
import com.medvision.vrmed.R;
import com.medvision.vrmed.base.BaseMvpActivity;
import com.medvision.vrmed.bean.User;
import com.medvision.vrmed.bean.req.UserReq;
import com.medvision.vrmed.imp.UserInfoView;
import com.medvision.vrmed.network.UserService;
import com.medvision.vrmed.presenter.UserInfoPresenter;
import com.medvision.vrmed.utils.IConstant;
import com.medvision.vrmed.utils.SpUtils;
import com.medvision.vrmed.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        startActivity(new Intent(LoginActivity.this,MainActivity2.class));
    }
}
