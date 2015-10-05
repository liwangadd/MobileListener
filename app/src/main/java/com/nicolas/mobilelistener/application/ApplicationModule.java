package com.nicolas.mobilelistener.application;

import com.nicolas.mobilelistener.bean.StuIdHolder;
import com.nicolas.mobilelistener.service.AccountService;
import com.nicolas.mobilelistener.service.OperatorService;
import com.nicolas.mobilelistener.service.QuestionService;
import com.orhanobut.logger.Logger;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.BaseUrl;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by liwang on 15-10-5.
 */
@Module
public class ApplicationModule {

    @Provides
    @Singleton
    public OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request newRequest = request.newBuilder().addHeader("Cookie", "stuId=" + StuIdHolder.userId).build();
                return chain.proceed(newRequest);
            }
        });
        return client;
    }

    @Provides
    @Singleton
    public Retrofit getRetrofit(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.105:9000/WebListener/student/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient).addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    public AccountService getAccountService(Retrofit retrofit) {
        return retrofit.create(AccountService.class);
    }

    @Provides
    @Singleton
    public OperatorService getOperatorService(Retrofit retrofit) {
        return retrofit.create(OperatorService.class);
    }

    @Provides
    @Singleton
    public QuestionService getQuestionService(Retrofit retrofit) {
        return retrofit.create(QuestionService.class);
    }

}
