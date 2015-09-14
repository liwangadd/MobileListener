package com.nicolas.mobilelistener.service;

import com.nicolas.mobilelistener.bean.IsSuccess;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Nikolas on 2015/9/14.
 */
public interface AccountService {

    @FormUrlEncoded
    @POST("/doReg")
    public void register(@Field("username")String username, @Field("password")String password, @Field("grade")int grade,
                         @Field("class")int clazz, @Field("real_name")String realName, @Field("stu_num")String stuNum,
                         Callback<IsSuccess> callback);

    @FormUrlEncoded
    @POST("/doLogin")
    public void login(@Field("stu_num")String username, @Field("password")String password, Callback<IsSuccess>callback);

}
