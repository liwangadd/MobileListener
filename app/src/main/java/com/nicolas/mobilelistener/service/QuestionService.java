package com.nicolas.mobilelistener.service;

import com.nicolas.mobilelistener.bean.AllQuestion;

import java.util.Map;
import java.util.Objects;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by Nikolas on 2015/9/15.
 */
public interface QuestionService {

    @FormUrlEncoded
    @POST("queById")
    public Observable<AllQuestion> getQueById(@Field("test_id") String testId);

    @FormUrlEncoded
    @POST("ans")
    public Observable<Map<String, Object>> checkQueAns(@Field("que_id") int queId, @Field("ans") String ans, @Field("stu_id") String stuId);

    @FormUrlEncoded
    @POST("compTest")
    public Observable<Map<String, Object>> completeTest(@Field("stu_id") String stuId, @Field("test_id") String queId);

}
