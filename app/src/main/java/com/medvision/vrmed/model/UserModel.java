package com.medvision.vrmed.model;

import com.cs.networklibrary.http.HttpResultFunc;
import com.medvision.vrmed.base.BaseModel;
import com.medvision.vrmed.bean.User;
import com.medvision.vrmed.bean.req.UserReq;

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
