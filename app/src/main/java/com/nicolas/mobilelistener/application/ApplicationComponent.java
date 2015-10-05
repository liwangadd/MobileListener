package com.nicolas.mobilelistener.application;

import com.nicolas.mobilelistener.activity.AllQueActivity;
import com.nicolas.mobilelistener.activity.AnsActivity;
import com.nicolas.mobilelistener.activity.LoginActivity;
import com.nicolas.mobilelistener.activity.MusicActivity;
import com.nicolas.mobilelistener.activity.RegisterActivity;
import com.nicolas.mobilelistener.bean.AllQuestion;
import com.nicolas.mobilelistener.service.AccountService;
import com.nicolas.mobilelistener.service.OperatorService;
import com.nicolas.mobilelistener.service.QuestionService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by liwang on 15-10-5.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MusicActivity activity);

    void inject(AllQueActivity activity);

    void inject(AnsActivity activity);

    void inject(RegisterActivity activity);

    void inject(LoginActivity activity);

    AccountService accountService();

    OperatorService operatorService();

    QuestionService questionService();

}
