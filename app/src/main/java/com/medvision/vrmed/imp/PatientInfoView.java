package com.medvision.vrmed.imp;

import com.medvision.vrmed.base.BaseView;
import com.medvision.vrmed.bean.PatientNophoneInfo;

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
