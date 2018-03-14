package com.med.vrmc.tablet.imp;

import com.med.vrmc.tablet.base.BaseView;
import com.med.vrmc.tablet.bean.PatientNophoneInfo;
import com.med.vrmc.tablet.bean.User;

/**
 * Created by raytine on 2018/1/9.
 */

public interface PatientInfoView extends BaseView {
    // 1.正在加载中
    // 2.获取出错了
    // 3.成功了要显示数据
    void onLoading();
    void onSucceed(PatientNophoneInfo userInfo);
}
