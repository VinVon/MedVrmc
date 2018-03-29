package com.medvision.vrmed.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cs.common.utils.ToastUtil;
import com.medvision.vrmed.R;
import com.medvision.vrmed.adapter.TrainGrideAdapter;
import com.medvision.vrmed.base.BaseMvpActivity;
import com.medvision.vrmed.base.BasePresenter;
import com.medvision.vrmed.utils.Logger;
import com.medvision.vrmed.utils.PopWindowUtil;

import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 训练中心
 * Created by 向文圣 on 2018/3/21.
 */

public class TrainingActivity extends BaseMvpActivity {
    @BindView(R.id.training_back)
    TextView trainingBack;
    @BindView(R.id.training_user)
    TextView trainingUser;
    @BindView(R.id.training_menu)
    ImageView trainingMenu;
    @BindView(R.id.training_headpicture)
    ImageView trainingHeadpicture;
    @BindView(R.id.training_name)
    TextView trainingName;
    @BindView(R.id.training_sex)
    TextView trainingSex;
    @BindView(R.id.training_age)
    TextView trainingAge;
    @BindView(R.id.training_checkrecord)
    ImageView trainingCheckrecord;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.training_method)
    ScrollView trainingMethod;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.training_aatention)
    ScrollView trainingAatention;
    @BindView(R.id.img_imformation)
    RelativeLayout imgImformation;
    @BindView(R.id.radioALL)
    RadioButton radioALL;
    @BindView(R.id.radioA)
    RadioButton radioA;
    @BindView(R.id.radioB)
    RadioButton radioB;
    @BindView(R.id.radioC)
    RadioButton radioC;
    @BindView(R.id.radioD)
    RadioButton radioD;
    @BindView(R.id.radioGroup1)
    RadioGroup radioGroup1;
    @BindView(R.id.huadong)
    HorizontalScrollView huadong;
    @BindView(R.id.training_recycle)
    RecyclerView trainingRecycle;
    @BindView(R.id.training_fre)
    SwipeRefreshLayout trainingFre;
    @BindView(R.id.radio_point)
    ImageView radioPoint;
    private TrainGrideAdapter grideAdapter;

    @Override
    public void onError() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        final int[] startPosion = {radioGroup1.indexOfChild(radioGroup1.findViewById(radioGroup1.getCheckedRadioButtonId()))};
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int endPosition = group.indexOfChild(group.findViewById(checkedId));
                //移动间隔
                int childCount = radioGroup1.getChildCount();
                int curr = radioGroup1.getWidth() / childCount;
                ObjectAnimator translationX = new ObjectAnimator().ofFloat(radioPoint,"translationX",radioPoint.getTranslationX(),radioPoint.getTranslationX()+((endPosition- startPosion[0])*curr));
                Logger.e(startPosion[0]+"=="+endPosition+"移动距离 : "+((endPosition- startPosion[0])*curr));
                translationX.setDuration(200).start();
                translationX.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startPosion[0] = endPosition;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
        });

    }
    public float sp2px(float sp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }

    @Override
    protected void initView() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            strings.add("hello" + i);
        }
        trainingRecycle.setLayoutManager(new GridLayoutManager(this, 3));
        grideAdapter = new TrainGrideAdapter(this, R.layout.item_training);
        grideAdapter.setDatas(strings);
        trainingRecycle.setAdapter(grideAdapter);
        trainingFre.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                trainingFre.setRefreshing(false);
            }
        });
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.training_activity);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.training_back, R.id.training_menu, R.id.training_user})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.training_back:
                finish();
                break;
            case R.id.training_user:
                PopWindowUtil.getInstance().makePopupWindow(this, trainingUser, R.layout.admin_layout, 0, 1).showLocationWithAnimation(this, trainingUser, -130, 0, 0);
                break;
            case R.id.training_menu:
                PopWindowUtil.getInstance().makePopupWindow(this, trainingMenu, R.layout.meu_layout, 0, 2).showLocationWithAnimation(this, trainingMenu, -20, 0, 0);
                break;
        }
    }


}
