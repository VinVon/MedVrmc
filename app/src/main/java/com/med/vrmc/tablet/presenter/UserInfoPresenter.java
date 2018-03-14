package com.med.vrmc.tablet.presenter;

import com.med.vrmc.tablet.base.BasePresenter;
import com.med.vrmc.tablet.bean.User;
import com.med.vrmc.tablet.bean.req.UserReq;
import com.med.vrmc.tablet.imp.UserInfoView;
import com.med.vrmc.tablet.model.UserModel;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by raytine on 2018/1/2.
 */

public class UserInfoPresenter extends BasePresenter<UserInfoView,UserModel> {
    // 是直接 new 还是？一个 Presneter 对应多个 Model 怎么解决？ new 很正常 ，尽量分离（六大基本原则）
    // 一般情况下是 1 个 Presneter 对应一个 Model
    // 如果说你的项目有这种一对多的情况（待会参考我写的 1 个 View 对应多个 Presneter 的解决方案）
    // 写一个 一对一的情况

    // 解绑 View 加了这个之后越来越复杂，代码写起来越来越多？怎么办？
    // 问题是，1. 很多代码是公用反复的，attach detach 每个 Presenter 都要有
    //         2. Activity -> View 的 attach detach 每个 View 层也要有

    public void getUsers(UserReq userReq) {
        // RxJava  + OkHttp + Retrofit
        // 网络引擎 + OkHttp
        // 显示正在加载中
        getView().onLoading();
        // 耗时 3s
        getModel().getUsers(userReq).subscribe(new Subscriber<User>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().onError();
            }

            @Override
            public void onNext(User user) {
                // 成功
                getView().onSucceed(user);
            }
        });

    }
}
