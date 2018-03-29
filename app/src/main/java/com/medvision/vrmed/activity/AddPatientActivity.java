package com.medvision.vrmed.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.medvision.vrmed.R;
import com.medvision.vrmed.base.BaseMvpActivity;
import com.medvision.vrmed.base.BasePresenter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加患者信息
 * Created by 向文圣 on 2018/3/21.
 */

public class AddPatientActivity extends BaseMvpActivity {
    @BindView(R.id.add_no)
    TextView addNo;
    @BindView(R.id.add_yes)
    TextView addYes;
    @BindView(R.id.information_sex)
    TextView informationSex;
    @BindView(R.id.information_marry)
    TextView informationMarry;
    @BindView(R.id.information_education)
    TextView informationEducation;
    @BindView(R.id.information_date)
    TextView informationDate;
    private TimePickerView pvCustomTime;
    private OptionsPickerView eductionView, sexsView, marryView;
    private List<String> sexs, marrys, educations;

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
        educations = new ArrayList<>();
        sexs = new ArrayList<>();
        marrys = new ArrayList<>();
        initDate();
        for (String s : getResources().getStringArray(R.array.education)) {
            educations.add(s);
        }
        for (String s : getResources().getStringArray(R.array.sex)) {
            sexs.add(s);
        }
        for (String s : getResources().getStringArray(R.array.marry)) {
            marrys.add(s);
        }
        initEducation();
        initsex();
        initmarry();

    }

    private void initmarry() {
        marryView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = marrys.get(options1);
                informationMarry.setText(tx);
            }

        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("婚姻")//标题
//                .setSubCalSize(18)//确定和取消文字大小
//                .setTitleSize(20)//标题文字大小
//                .setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
//                .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
//                .setContentTextSize(18)//滚轮文字大小
//                .setLinkage(false)//设置是否联动，默认true
//                .setLabels("省", "市", "区")//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0)  //设置默认选中项
                .setOutSideCancelable(true)//点击外部dismiss default true
                .isDialog(true)//是否显示为对话框样式
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();

        marryView.setPicker(marrys);//添加数据源
    }

    private void initsex() {
        sexsView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = sexs.get(options1);
                informationSex.setText(tx);
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("性别")//标题
//                .setSubCalSize(18)//确定和取消文字大小
//                .setTitleSize(20)//标题文字大小
//                .setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
//                .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
//                .setContentTextSize(18)//滚轮文字大小
//                .setLinkage(false)//设置是否联动，默认true
//                .setLabels("省", "市", "区")//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0)  //设置默认选中项
                .setOutSideCancelable(true)//点击外部dismiss default true
                .isDialog(true)//是否显示为对话框样式
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();

        sexsView.setPicker(sexs);//添加数据源
    }

    private void initEducation() {
        eductionView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = educations.get(options1);
                informationEducation.setText(tx);
            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("文化程度")//标题
//                .setSubCalSize(18)//确定和取消文字大小
//                .setTitleSize(20)//标题文字大小
//                .setTitleColor(Color.BLACK)//标题文字颜色
//                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
//                .setCancelColor(Color.BLUE)//取消按钮文字颜色
//                .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
//                .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
//                .setContentTextSize(18)//滚轮文字大小
//                .setLinkage(false)//设置是否联动，默认true
//                .setLabels("省", "市", "区")//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(0)  //设置默认选中项
                .setOutSideCancelable(true)//点击外部dismiss default true
                .isDialog(true)//是否显示为对话框样式
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .build();

        eductionView.setPicker(educations);//添加数据源
    }

    private void initDate() {
//        Calendar selectedDate = Calendar.getInstance();//系统当前时间
//        Calendar startDate = Calendar.getInstance();
//        startDate.set(2014, 1, 23);
//        Calendar endDate = Calendar.getInstance();
//        endDate.set(2027, 2, 28);
        pvCustomTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
//                btn_CustomTime.setText(getTime(date));
                informationDate.setText(getTime(date));
            }
        })
//                /*.setType(TimePickerView.Type.ALL)//default is all
//                .setCancelText("Cancel")
//                .setSubmitText("Sure")
//                .setContentTextSize(18)
//                .setTitleSize(20)
//                .setTitleText("Title")
//                .setTitleColor(Color.BLACK)
//               /*.setDividerColor(Color.WHITE)//设置分割线的颜色
//                .setTextColorCenter(Color.LTGRAY)//设置选中项的颜色
//                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
//                .setTitleBgColor(Color.DKGRAY)//标题背景颜色 Night mode
//                .setBgColor(Color.BLACK)//滚轮背景颜色 Night mode
//                .setSubmitColor(Color.WHITE)
//                .setCancelColor(Color.WHITE)*/
//               /*.animGravity(Gravity.RIGHT)// default is center*/
//                .setDate(selectedDate)
//                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                }).isDialog(true)
                .setContentTextSize(18)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "时", "分", "秒")
//                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.addpatient_activity);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.add_no, R.id.add_yes, R.id.information_date, R.id.information_sex, R.id.information_marry, R.id.information_education})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.add_no:
                this.finish();
                break;
            case R.id.add_yes:
                break;
            case R.id.information_date:
                pvCustomTime.show();
                break;
            case R.id.information_sex:
                sexsView.show();
                break;
            case R.id.information_marry:
                marryView.show();
                break;
            case R.id.information_education:
                eductionView.show();
                break;
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
