package com.medvision.vrmed.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medvision.vrmed.R;


/**
 * Created by raytine on 2018/1/2.
 */

public class DataFragment extends Fragment {
    private static DataFragment fragment;

    public static DataFragment newInstance() {
        if (fragment == null){
            fragment = new DataFragment();
        }
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.data_fragment,container,false);
    }
}
