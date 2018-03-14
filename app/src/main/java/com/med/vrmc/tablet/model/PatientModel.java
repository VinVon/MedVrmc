package com.med.vrmc.tablet.model;

import com.cs.networklibrary.http.HttpResultFunc;
import com.med.vrmc.tablet.base.BaseModel;
import com.med.vrmc.tablet.bean.PatientNophoneInfo;
import com.med.vrmc.tablet.bean.User;
import com.med.vrmc.tablet.bean.req.BaseReq;
import com.med.vrmc.tablet.bean.req.PatientReq;
import com.med.vrmc.tablet.bean.req.UserReq;

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
