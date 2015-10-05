package com.nicolas.mobilelistener.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.nicolas.mobilelistener.bean.StuIdHolder;

/**
 * Created by Nikolas on 2015/9/14.
 */
public class ListenerApplication extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        StuIdHolder.userId = preferences.getString("stuId", "");

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule())
                .build();
    }

    public ApplicationComponent component(){
        return component;
    }

}
