package com.medvision.vrmed.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs.common.utils.ToastUtil;
import com.cs.networklibrary.http.HttpMethods;
import com.medvision.vrmed.R;
import com.medvision.vrmed.adapter.GrideAdapter;
import com.medvision.vrmed.base.BaseMvpActivity;
import com.medvision.vrmed.base.BasePresenter;
import com.medvision.vrmed.network.UserService;
import com.medvision.vrmed.utils.KeybordS;
import com.medvision.vrmed.utils.PopWindowUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by raytine on 2018/3/9.
 */

public class MainActivity2 extends BaseMvpActivity {
    @BindView(R.id.patient_recycle)
    RecyclerView patirntRecycle;
    @BindView(R.id.main_menu)
    ImageView mainMenu;
    @BindView(R.id.main_user)
    TextView mainUser;
    @BindView(R.id.main_search)
    ImageView mainSearch;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.total)
    LinearLayout total;
    @BindView(R.id.main_add)
    ImageView mainAdd;
    @BindView(R.id.main_title1)
    RelativeLayout mainTitle1;
    @BindView(R.id.main_search_two)
    EditText mainSearchTwo;
    @BindView(R.id.main_search_no)
    TextView mainSearchNo;
    @BindView(R.id.main_title2)
    RelativeLayout mainTitle2;
    private GrideAdapter grideAdapter;

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
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            strings.add("hello" + i);
        }
        patirntRecycle.setLayoutManager(new GridLayoutManager(this, 5));
        grideAdapter = new GrideAdapter(this,R.layout.item_patient);
        grideAdapter.setDatas(strings);
        patirntRecycle.setAdapter(grideAdapter);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh.setRefreshing(false);
            }
        });
        mainSearchTwo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    //点击搜索要做的操作
                    ToastUtil.showMessage(MainActivity2.this,v.getText().toString());
                }
                return false;
            }
        });
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
    }

    @OnClick({ R.id.main_menu, R.id.main_add, R.id.main_search,R.id.main_search_no,R.id.main_user})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.main_menu:
                PopWindowUtil.getInstance().makePopupWindow(this, mainMenu, R.layout.meu_layout, 0,2).showLocationWithAnimation(this, mainMenu, -20, 0, 0);
                break;
            case R.id.main_user:
                PopWindowUtil.getInstance().makePopupWindow(this, mainUser, R.layout.admin_layout, 0,1).showLocationWithAnimation(this, mainUser, -130, 0, 0);
                break;
            case R.id.main_add:
                this.startActivity(new Intent(this, AddPatientActivity.class));
                break;
            case R.id.main_search:
                    mainTitle1.setVisibility(View.GONE);
                    mainTitle2.setVisibility(View.VISIBLE);
                break;
            case R.id.main_search_no:
                mainTitle1.setVisibility(View.VISIBLE);
                mainTitle2.setVisibility(View.GONE);
                KeybordS.closeKeybord(mainSearchTwo,this);
                break;

        }
    }


}
