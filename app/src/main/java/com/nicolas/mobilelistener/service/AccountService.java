package com.nicolas.mobilelistener.service;

import com.nicolas.mobilelistener.bean.IsSuccess;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by Nikolas on 2015/9/14.
 */
public interface AccountService {

    @FormUrlEncoded
    @POST("doReg")
    public Observable<IsSuccess> register(@Field("username")String username, @Field("password")String password, @Field("grade")int grade,
                         @Field("class")int clazz, @Field("real_name")String realName, @Field("stu_num")String stuNum);

    @FormUrlEncoded
    @POST("doLogin")
    public Observable<IsSuccess> login(@Field("stu_num")String username, @Field("password")String password);

}
