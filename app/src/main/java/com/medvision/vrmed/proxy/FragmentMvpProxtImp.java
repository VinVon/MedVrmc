package com.medvision.vrmed.proxy;

import com.medvision.vrmed.base.BaseView;

/**
 * Created by raytine on 2018/1/9.
 */

public class FragmentMvpProxtImp<V extends BaseView> extends MvpProxyImpl<V> implements FragmentMvpProxy {

    public FragmentMvpProxtImp(V view) {
        super(view);
    }
}
