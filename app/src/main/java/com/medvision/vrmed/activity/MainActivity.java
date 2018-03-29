package com.medvision.vrmed.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cs.common.utils.ToastUtil;
import com.medvision.vrmed.R;
import com.medvision.vrmed.base.FuncBaseActivity;
import com.medvision.vrmed.bean.User;
import com.medvision.vrmed.fragment.DataFragment;
import com.medvision.vrmed.fragment.HelpFragment;
import com.medvision.vrmed.fragment.MainFragment;
import com.medvision.vrmed.fragment.PatientFragment;
import com.medvision.vrmed.fragment.TrainFragment;
import com.medvision.vrmed.utils.Functions;
import com.medvision.vrmed.utils.Logger;
import com.medvision.vrmed.utils.SpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FuncBaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.main_title)
    TextView mainTitle;
    @BindView(R.id.main_bar)
    LinearLayout mainBar;
    @BindView(R.id.contain)
    FrameLayout contain;
    @BindView(R.id.radio_btn1)
    RadioButton radioBtn1;
    @BindView(R.id.radio_btn2)
    RadioButton radioBtn2;
    @BindView(R.id.radio_btn3)
    RadioButton radioBtn3;
    @BindView(R.id.radio_btn4)
    RadioButton radioBtn4;
    @BindView(R.id.main_radiogroup)
    RadioGroup mainRadiogroup;
    @BindView(R.id.radio_btn0)
    RadioButton radioBtn0;
    private String TAG = "MainActivity" ;
    private Fragment[] mFragments;
    private int mIndex;
    MainFragment mainFragment = MainFragment.newInstance();
    TrainFragment trainFragment = TrainFragment.newInstance();
    PatientFragment patientFragment = PatientFragment.newInstance();
    DataFragment dataFragment = DataFragment.newInstance();
    HelpFragment helpFragment = HelpFragment.newInstance();

    @Override
    public void setFunctionsForFragment(int fragmentId) {
        super.setFunctionsForFragment(fragmentId);
        switch (fragmentId){
            case 1:
                mainFragment.setFunctions(new Functions().addFunction(new Functions.FunctionWithParam<User>(mainFragment.FUNCTION_HAS_PARAM_NO_RESULT) {

                    @Override
                    public void function(User user) {
                        Logger.e("------MainA",user.getUsername());
                        mainRadiogroup.check(R.id.radio_btn1);
//                        setIndexSelected(1);
                    }
                }));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainRadiogroup.setOnCheckedChangeListener(this);
        Logger.e("---------", SpUtils.getInstance().getUser()+"");
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
//        ActivityManager.getInstance().deAttachbefor();
//        Logger.e(TAG);
        //添加到数组
        mFragments = new Fragment[]{mainFragment,patientFragment,trainFragment, dataFragment, helpFragment};
        //开启事务
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //添加首页
        ft.add(R.id.contain, mainFragment).commit();
        //默认设置为第0个
        setIndexSelected(0);
    }

    private void setIndexSelected(int index) {
        if (mIndex == index) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        //隐藏
        ft.hide(mFragments[mIndex]);
        //判断是否添加
        if (!mFragments[index].isAdded()) {
            ft.add(R.id.contain, mFragments[index]).show(mFragments[index]);
        } else {
            ft.show(mFragments[index]);
        }
        ft.commit();
        //再次赋值
        mIndex = index;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radio_btn0:
                setIndexSelected(0);
                break;
            case R.id.radio_btn1:
                setIndexSelected(1);
                break;
            case R.id.radio_btn2:
                setIndexSelected(2);
                break;
            case R.id.radio_btn3:
                setIndexSelected(3);
                break;
            case R.id.radio_btn4:
                setIndexSelected(4);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            ToastUtil.showMessage(this,"haha");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
