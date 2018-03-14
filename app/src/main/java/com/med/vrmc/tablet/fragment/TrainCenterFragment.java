package com.med.vrmc.tablet.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.med.vrmc.tablet.R;

/**
 * Created by raytine on 2018/1/5.
 */

public class TrainCenterFragment extends Fragment {
    private static TrainCenterFragment fragment;

    public static TrainCenterFragment newInstance() {
        if (fragment == null){
            fragment = new TrainCenterFragment();
        }
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.center_fragment,container,false);
    }
}
