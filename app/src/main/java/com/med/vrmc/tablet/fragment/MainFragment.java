package com.med.vrmc.tablet.fragment;

import android.content.Context;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.cs.common.utils.ToastUtil;
import com.med.vrmc.tablet.R;
import com.med.vrmc.tablet.aop.CheckNet;
import com.med.vrmc.tablet.base.BaseActivity;
import com.med.vrmc.tablet.base.BaseFragment;
import com.med.vrmc.tablet.base.FuncBaseActivity;
import com.med.vrmc.tablet.bean.PatientNophoneInfo;
import com.med.vrmc.tablet.bean.User;
import com.med.vrmc.tablet.bean.req.PatientReq;
import com.med.vrmc.tablet.bean.req.UserReq;
import com.med.vrmc.tablet.imp.PatientInfoView;
import com.med.vrmc.tablet.imp.UserInfoView;
import com.med.vrmc.tablet.inject.InjectPresenter;
import com.med.vrmc.tablet.presenter.PatientPresenter;
import com.med.vrmc.tablet.presenter.UserInfoPresenter;
import com.med.vrmc.tablet.utils.FunctionException;
import com.med.vrmc.tablet.utils.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
