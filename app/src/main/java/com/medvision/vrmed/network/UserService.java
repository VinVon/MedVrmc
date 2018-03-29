package com.medvision.vrmed.network;

import com.medvision.vrmed.bean.PatientNophoneInfo;
import com.medvision.vrmed.bean.User;
import com.medvision.vrmed.bean.req.BaseReq;
import com.medvision.vrmed.bean.req.UserReq;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 接口类
 * Created by raytine on 2018/1/2.
 */

public interface UserService{
    //登陆
    @POST("appControllerSm/login")
    Observable<HttpResult<User>> requestLogin(@Body UserReq userReq);

    //关键字检索患者
    @POST("appControllerSm/getPatientByKeywordCase")
    Observable<HttpResult<PatientNophoneInfo>> requestPatientByPhone(@Body BaseReq userReq);
}
