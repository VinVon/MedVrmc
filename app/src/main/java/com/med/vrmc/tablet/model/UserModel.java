package com.med.vrmc.tablet.model;

import android.content.Intent;
import android.util.Log;

import com.cs.networklibrary.http.HttpMethods;
import com.cs.networklibrary.http.HttpResultFunc;
import com.cs.networklibrary.subscribers.ProgressSubscriber;
import com.med.vrmc.tablet.activity.LoginActivity;
import com.med.vrmc.tablet.activity.MainActivity;
import com.med.vrmc.tablet.base.BaseModel;
import com.med.vrmc.tablet.bean.User;
import com.med.vrmc.tablet.bean.req.UserReq;
import com.med.vrmc.tablet.network.UserService;
import com.med.vrmc.tablet.utils.SpUtils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by raytine on 2018/1/9.
 */

public class UserModel extends BaseModel {

    public UserModel() {
        super();
    }

    public Observable<User> getUsers(UserReq userReq){
        Observable<User> userObservable = mUserService.requestLogin(userReq)
                .map(new HttpResultFunc<User>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
            return userObservable;
    }
}
