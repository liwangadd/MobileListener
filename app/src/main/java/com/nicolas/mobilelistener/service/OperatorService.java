package com.nicolas.mobilelistener.service;

import com.nicolas.mobilelistener.bean.AllTest;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Nikolas on 2015/9/14.
 */
public interface OperatorService {

    @GET("/allTest")
    public void getAllTest(Callback<AllTest> callback);

}
