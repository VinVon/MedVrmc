package com.medvision.vrmed.model;

import com.cs.networklibrary.http.HttpResultFunc;
import com.medvision.vrmed.base.BaseModel;
import com.medvision.vrmed.bean.PatientNophoneInfo;
import com.medvision.vrmed.bean.req.PatientReq;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by raytine on 2018/1/9.
 */

public class PatientModel extends BaseModel {
    public Observable<PatientNophoneInfo> getPatient(PatientReq req) {
        Observable<PatientNophoneInfo> userObservable = mUserService.requestPatientByPhone(req)
                .map(new HttpResultFunc<PatientNophoneInfo>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return userObservable;
    }
}
