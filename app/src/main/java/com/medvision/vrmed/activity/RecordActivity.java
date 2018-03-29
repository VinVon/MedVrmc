package com.medvision.vrmed.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.medvision.vrmed.R;
import com.medvision.vrmed.adapter.HeaderBottomAdapter;
import com.medvision.vrmed.base.BaseMvpActivity;
import com.medvision.vrmed.base.BasePresenter;
import com.medvision.vrmed.bean.RecordInfo;
import com.medvision.vrmed.utils.PopWindowUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 向文圣 on 2018/3/21.
 */

public class RecordActivity extends BaseMvpActivity {
    @BindView(R.id.record_back)
    TextView recordBack;
    @BindView(R.id.record_menu)
    ImageView recordMenu;
    @BindView(R.id.record_recycle)
    RecyclerView recordRecycle;
    @BindView(R.id.record_fre)
    SwipeRefreshLayout recordFre;
    @BindView(R.id.record_user)
    TextView recordUser;
    @BindView(R.id.record_date)
    TextView recordDate;
    private HeaderBottomAdapter recordAdapter;
    private List<RecordInfo> recordInfoList;
    private TimePickerView pvCustomTime;
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
        recordInfoList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            recordInfoList.add(new RecordInfo("name" + i, "number" + i, "hops" + i, "date" + i, "haha" + i));
        }
        recordAdapter = new HeaderBottomAdapter(this, recordInfoList);
        recordRecycle.setLayoutManager(new LinearLayoutManager(this));
        recordRecycle.setAdapter(recordAdapter);
        recordFre.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recordFre.setRefreshing(false);
            }
        });
            initDate();
    }
    private void initDate() {
        pvCustomTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                recordDate.setText(getTime(date));
            }
        })
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
        setContentView(R.layout.record_activity);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.record_back, R.id.record_menu, R.id.record_user,R.id.record_date})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.record_back:
                finish();
                break;
            case R.id.record_date:
                pvCustomTime.show();
                break;
            case R.id.record_user:
                PopWindowUtil.getInstance().makePopupWindow(this, recordUser, R.layout.admin_layout, 0, 1).showLocationWithAnimation(this, recordUser, -130, 0, 0);
                break;
            case R.id.record_menu:
                PopWindowUtil.getInstance().makePopupWindow(this, recordMenu, R.layout.meu_layout, 0, 2).showLocationWithAnimation(this, recordMenu, -20, 0, 0);
                break;
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

}
