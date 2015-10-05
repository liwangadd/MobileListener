package com.nicolas.mobilelistener.service;

import com.nicolas.mobilelistener.bean.AllTest;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by Nikolas on 2015/9/14.
 */
public interface OperatorService {

    @FormUrlEncoded
    @POST("allTest")
    public Observable<AllTest> getAllTest(@Field("stu_id")String stuId);

}
