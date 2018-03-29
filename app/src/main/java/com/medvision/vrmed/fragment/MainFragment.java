package com.medvision.vrmed.fragment;

import android.content.Context;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.cs.common.utils.ToastUtil;
import com.medvision.vrmed.R;
import com.medvision.vrmed.aop.CheckNet;
import com.medvision.vrmed.base.BaseFragment;
import com.medvision.vrmed.base.FuncBaseActivity;
import com.medvision.vrmed.bean.PatientNophoneInfo;
import com.medvision.vrmed.bean.User;
import com.medvision.vrmed.bean.req.UserReq;
import com.medvision.vrmed.imp.PatientInfoView;
import com.medvision.vrmed.imp.UserInfoView;
import com.medvision.vrmed.inject.InjectPresenter;
import com.medvision.vrmed.presenter.PatientPresenter;
import com.medvision.vrmed.presenter.UserInfoPresenter;
import com.medvision.vrmed.utils.FunctionException;
import com.medvision.vrmed.utils.Logger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by raytine on 2018/1/5.
 */

public class MainFragment extends BaseFragment<PatientPresenter> implements PatientInfoView, UserInfoView {
    /**
     * 没有参数没有返回值的函数
     */
    public static final String FUNCTION_NO_PARAM_NO_RESULT = "FUNCTION_NO_PARAM_NO_RESULT";
    /**
     * 有参数没有返回值的函数
     */
    public static final String FUNCTION_HAS_PARAM_NO_RESULT = "FUNCTION_HAS_PARAM_NO_RESULT";
    /**
     * 有参数没有返回值的函数
     */
    public static final String FUNCTION_NO_PARAM_HAS_RESULT = "FUNCTION_NO_PARAM_HAS_RESULT";

    private static MainFragment fragment;
    @BindView(R.id.main_search_edit)
    EditText mainSearchEdit;
    @BindView(R.id.main_search_btn)
    Button mainSearchBtn;
    @BindView(R.id.main_hosptial_name)
    TextView mainHosptialName;

    @InjectPresenter
    UserInfoPresenter mPresenter;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        //呼叫activity进行回调方法的设置
        if (activity instanceof FuncBaseActivity) {
            mBaseActivity = (FuncBaseActivity) activity;
            mBaseActivity.setFunctionsForFragment(1);
        }
    }

    public static MainFragment newInstance() {
        if (fragment == null) {
            fragment = new MainFragment();
        }
        return fragment;
    }

    @Override
    protected PatientPresenter createPresenter() {
        return new PatientPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View v) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment;
    }

    @CheckNet
    @OnClick(R.id.main_search_btn)
    public void OnClick(View v) {
        String key = mainSearchEdit.getText().toString();
        if (key.isEmpty()) {
            ToastUtil.showMessage(getActivity(), "请输入患者信息");
            return;
        }
        mPresenter.getUsers(new UserReq("easy888", "med007"));
    }

    @Override
    public void onError() {
        Logger.e("----fragment", "没有网络");
        dismissProgressDialog();
    }

    @Override
    public void onLoading() {
        initProgressDialog();
    }

    @Override
    public void onSucceed(User userInfo) {
        dismissProgressDialog();
        //调用无参无返回值的方法
        try {
            mFunctions.invokeFunc(
                    FUNCTION_HAS_PARAM_NO_RESULT, userInfo);
        } catch (FunctionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSucceed(PatientNophoneInfo userInfo) {
        //调用无参无返回值的方法
        try {
            mFunctions.invokeFunc(
                    FUNCTION_HAS_PARAM_NO_RESULT, userInfo);
        } catch (FunctionException e) {
            e.printStackTrace();
        }
    }
}
