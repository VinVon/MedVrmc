package com.med.vrmc.tablet.presenter;

import com.med.vrmc.tablet.base.BasePresenter;
import com.med.vrmc.tablet.bean.PatientNophoneInfo;
import com.med.vrmc.tablet.bean.User;
import com.med.vrmc.tablet.bean.req.PatientReq;
import com.med.vrmc.tablet.bean.req.UserReq;
import com.med.vrmc.tablet.imp.PatientInfoView;
import com.med.vrmc.tablet.model.PatientModel;

import rx.Subscriber;

/**
 * Created by raytine on 2018/1/9.
 */

public class PatientPresenter extends BasePresenter<PatientInfoView,PatientModel> {

    public void getPatient(PatientReq userReq) {
        // RxJava  + OkHttp + Retrofit
        // 网络引擎 + OkHttp
        // 显示正在加载中
        getView().onLoading();
        // 耗时 3s
        getModel().getPatient(userReq).subscribe(new Subscriber<PatientNophoneInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().onError();
            }

            @Override
            public void onNext(PatientNophoneInfo user) {
                // 成功
                getView().onSucceed(user);
            }
        });
    }
}
