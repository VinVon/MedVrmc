package com.med.vrmc.tablet.base;

import com.cs.networklibrary.http.HttpMethods;
import com.med.vrmc.tablet.network.UserService;

/**
 * Created by hcDarren on 2018/1/6.
 * 基类的 Model
 */
public class BaseModel {
    public UserService mUserService;

    public BaseModel() {
        this.mUserService = HttpMethods.getInstance().getClassInstance(UserService.class);;
    }
}
