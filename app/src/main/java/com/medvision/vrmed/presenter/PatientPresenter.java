package com.medvision.vrmed.presenter;

import com.medvision.vrmed.base.BasePresenter;
import com.medvision.vrmed.bean.PatientNophoneInfo;
import com.medvision.vrmed.bean.req.PatientReq;
import com.medvision.vrmed.imp.PatientInfoView;
import com.medvision.vrmed.model.PatientModel;

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
