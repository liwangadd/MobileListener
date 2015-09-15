package com.nicolas.mobilelistener.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nicolas.mobilelistener.R;
import com.nicolas.mobilelistener.application.ListenerApplication;
import com.nicolas.mobilelistener.bean.AllQuestion;
import com.nicolas.mobilelistener.bean.AllTest;
import com.nicolas.mobilelistener.bean.Question;
import com.nicolas.mobilelistener.bean.StuIdHolder;
import com.nicolas.mobilelistener.service.QuestionService;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Nikolas on 2015/9/14.
 */
public class AnsActivity extends Activity implements Callback<AllQuestion>, View.OnClickListener, MediaPlayer.OnPreparedListener {

    private TextView titleView;
    private View nextView;
    private TextView queTopicView;
    private TextView ansAView;
    private TextView ansBView;
    private TextView ansCView;
    private TextView ansDView;
    private ImageView playView;
    private int selectedColr = Color.parseColor("#0288D1");

    private RestAdapter restAdapter;
    private QuestionService questionService;
    private List<Question> allQues;
    private int currentIndex = 0;
    private String currentAns = "";

    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ans_activity);

        initView();

        initData();
    }

    private void initData() {
        restAdapter = ((ListenerApplication) getApplication()).getAdapter();
        questionService = restAdapter.create(QuestionService.class);

        //初始化音频播放设置
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(this);

        Intent intent = getIntent();
        String testId = intent.getStringExtra("test_id");
        String testTopic = intent.getStringExtra("test_topic");
        titleView.setText(testTopic);
        questionService.getQueById(testId, this);
    }

    private void initView() {
        titleView = (TextView) findViewById(R.id.txtLogin);
        nextView = findViewById(R.id.next_que);
        queTopicView = (TextView) findViewById(R.id.que_topic);
        ansAView = (TextView) findViewById(R.id.que_ans_1);
        ansBView = (TextView) findViewById(R.id.que_ans_2);
        ansCView = (TextView) findViewById(R.id.que_ans_3);
        ansDView = (TextView) findViewById(R.id.que_ans_4);
//        playView = (ImageView) findViewById(R.id.btnPlay);

        nextView.setOnClickListener(this);
        ansAView.setOnClickListener(this);
        ansBView.setOnClickListener(this);
        ansCView.setOnClickListener(this);
        ansDView.setOnClickListener(this);
//        playView.setOnClickListener(this);
    }

    @Override
    public void success(AllQuestion questions, Response response) {
        Logger.d(questions.getResult().toString());
        if (questions.getMessage()) {
            allQues = questions.getResult();
            updateQue(currentIndex);
        } else {
            Toast.makeText(this, "题目获取失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(this, "网络连接异常", Toast.LENGTH_SHORT).show();
    }

    private void updateQue(int pos) {
        Question currentQue = allQues.get(pos);
        queTopicView.setText(currentQue.getQue_topic());
        ansAView.setText(currentQue.getAns_a());
        ansBView.setText(currentQue.getAns_b());
        ansCView.setText(currentQue.getAns_c());
        ansDView.setText(currentQue.getAns_d());
        try {
            mMediaPlayer.setDataSource("http://192.168.31.201/WebListener/records/" + currentQue.getPath());
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_que:
                if ("".equals(currentAns)) {
                    Toast.makeText(this, "请选择答案", Toast.LENGTH_SHORT).show();
                    return;
                }
                clearColor();
                questionService.checkQueAns(allQues.get(currentIndex).getQue_id(), currentAns, StuIdHolder.userId, new CheckAnsCallBack());
                break;
            case R.id.que_ans_1:
                resetColor(ansAView);
                currentAns = "A";
                break;
            case R.id.que_ans_2:
                resetColor(ansBView);
                currentAns = "B";
                break;
            case R.id.que_ans_3:
                resetColor(ansCView);
                currentAns = "C";
                break;
            case R.id.que_ans_4:
                resetColor(ansDView);
                currentAns = "D";
                break;
//            case R.id.btnPlay:
//                break;
        }
    }

    private void resetColor(View v) {
        clearColor();
        v.setBackgroundColor(selectedColr);
    }

    private void clearColor() {
        ansAView.setBackgroundColor(Color.TRANSPARENT);
        ansBView.setBackgroundColor(Color.TRANSPARENT);
        ansCView.setBackgroundColor(Color.TRANSPARENT);
        ansDView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    public void handler() {
        mMediaPlayer.reset();
        updateQue(++currentIndex);
    }

    class CheckAnsCallBack implements Callback<List<String>> {
        @Override
        public void failure(RetrofitError error) {
            Logger.d(error.getMessage());
            AnsActivity.this.handler();
        }

        @Override
        public void success(List<String> strings, Response response) {
            AnsActivity.this.handler();
        }
    }
}
