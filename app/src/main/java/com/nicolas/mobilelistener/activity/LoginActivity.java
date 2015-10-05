package com.nicolas.mobilelistener.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nicolas.mobilelistener.R;
import com.nicolas.mobilelistener.application.ListenerApplication;
import com.nicolas.mobilelistener.bean.IsSuccess;
import com.nicolas.mobilelistener.bean.StuIdHolder;
import com.nicolas.mobilelistener.service.AccountService;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Nikolas on 2015/9/14.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText userView;
    private EditText passwordView;
    private Button loginBtn;
    private View registerView;
    private static LoginActivity self;

    @Inject
    AccountService accountService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ((ListenerApplication)getApplication()).component().inject(this);

        initView();

        self = this;
    }

    private void initView() {
        userView = (EditText) findViewById(R.id.loginUser);
        passwordView = (EditText) findViewById(R.id.loginPassword);
        loginBtn = (Button) findViewById(R.id.btnLogin);
        registerView = findViewById(R.id.register);

        loginBtn.setOnClickListener(this);
        registerView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                if ("".equals(userView.getText().toString())) {
                    Toast.makeText(this, "请输入学号", Toast.LENGTH_SHORT).show();
                } else if ("".equals(passwordView.getText().toString())) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else {
                    Observable<IsSuccess> loginObservable = accountService.login(userView.getText().toString(), passwordView.getText().toString());
                    loginObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                    .subscribe(new Action1<IsSuccess>() {
                        @Override
                        public void call(IsSuccess isSuccess) {
                            if (isSuccess.getMessage().equals("true")) {
                                SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                                StuIdHolder.userId = isSuccess.getStuId();
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putBoolean("isLogined", true);
                                editor.putString("stuId", StuIdHolder.userId);
                                editor.commit();
                                startActivity(new Intent(LoginActivity.this, MusicActivity.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Logger.d(throwable.getMessage());
                            Toast.makeText(LoginActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                break;
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }

}
