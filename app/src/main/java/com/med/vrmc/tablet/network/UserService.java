package com.med.vrmc.tablet.network;

import com.med.vrmc.tablet.base.BaseView;
import com.med.vrmc.tablet.bean.PatientNophoneInfo;
import com.med.vrmc.tablet.bean.User;
import com.med.vrmc.tablet.bean.req.BaseReq;
import com.med.vrmc.tablet.bean.req.UserReq;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 接口类
 * Created by raytine on 2018/1/2.
 */

public interface UserService{
    //登陆
    @POST("appControlVrRoom/login")
    Observable<HttpResult<User>> requestLogin(@Body UserReq userReq);

    //关键字检索患者
    @POST("appControlVrRoom/getPatientByKeywordCase")
    Observable<HttpResult<PatientNophoneInfo>> requestPatientByPhone(@Body BaseReq userReq);
}
