package com.nicolas.mobilelistener.service;

import com.nicolas.mobilelistener.bean.AllQuestion;
import com.nicolas.mobilelistener.bean.Question;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by Nikolas on 2015/9/15.
 */
public interface QuestionService {

    @FormUrlEncoded
    @POST("/queById")
    public void getQueById(@Field("test_id")String testId, Callback<AllQuestion> callback);

}
