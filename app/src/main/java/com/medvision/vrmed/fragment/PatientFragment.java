package com.medvision.vrmed.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medvision.vrmed.R;
import com.medvision.vrmed.adapter.HeaderBottomAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 患者管理
 * Created by raytine on 2018/1/2.
 */

public class PatientFragment extends Fragment {
    private static PatientFragment fragment;
    @BindView(R.id.patient_recycle)
    RecyclerView patientRecycle;
    Unbinder unbinder;
    private LinearLayoutManager layoutManager;
    private HeaderBottomAdapter headerBottomAdapter;
    public static PatientFragment newInstance() {
        if (fragment == null) {
            fragment = new PatientFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.patient_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        patientRecycle.setLayoutManager(layoutManager);
        patientRecycle.setAdapter(headerBottomAdapter = new HeaderBottomAdapter(getContext()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
