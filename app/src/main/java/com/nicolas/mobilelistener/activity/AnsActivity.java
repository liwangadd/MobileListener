package com.nicolas.mobilelistener.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nicolas.mobilelistener.R;
import com.nicolas.mobilelistener.application.ListenerApplication;
import com.nicolas.mobilelistener.bean.AllQuestion;
import com.nicolas.mobilelistener.bean.Question;
import com.nicolas.mobilelistener.service.QuestionService;
import com.orhanobut.logger.Logger;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Nikolas on 2015/9/14.
 */
public class AnsActivity extends Activity implements Callback<AllQuestion>, View.OnClickListener {

    private TextView titleView;
    private View nextView;
    private TextView queTopicView;
    private TextView ansAView;
    private TextView ansBView;
    private TextView ansCView;
    private TextView ansDView;
    private ImageView playView;

    private RestAdapter restAdapter;
    private QuestionService questionService;
    private List<Question> allQues;
    private int currentIndex=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ans_activity);

        initView();

        restAdapter = ((ListenerApplication) getApplication()).getAdapter();
        questionService = restAdapter.create(QuestionService.class);
        questionService.getQueById("1", this);
    }

    private void initView() {
        titleView= (TextView) findViewById(R.id.txtLogin);
        nextView=findViewById(R.id.next_que);
        queTopicView= (TextView) findViewById(R.id.que_topic);
        ansAView= (TextView) findViewById(R.id.que_ans_1);
        ansBView= (TextView) findViewById(R.id.que_ans_2);
        ansCView= (TextView) findViewById(R.id.que_ans_3);
        ansDView= (TextView) findViewById(R.id.que_ans_4);
        playView= (ImageView) findViewById(R.id.btnPlay);

        nextView.setOnClickListener(this);
        playView.setOnClickListener(this);
    }

    @Override
    public void success(AllQuestion questions, Response response) {
        Logger.d(questions.getResult().toString());
        if(questions.getMessage()){
            allQues=questions.getResult();
            updateQue(currentIndex);
        }else{
            Toast.makeText(this,"题目获取失败",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(this,"网络连接异常",Toast.LENGTH_SHORT).show();
    }

    private void updateQue(int pos){
        Question currentQue=allQues.get(pos);
        queTopicView.setText(currentQue.getQue_topic());
        ansAView.setText(currentQue.getAns_a());
        ansBView.setText(currentQue.getAns_b());
        ansCView.setText(currentQue.getAns_c());
        ansDView.setText(currentQue.getAns_d());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next_que:
                break;
            case R.id.btnPlay:
                break;
        }
    }
}
