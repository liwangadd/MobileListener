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

import com.nicolas.mobilelistener.R;
import com.nicolas.mobilelistener.bean.Question;
import com.nicolas.mobilelistener.bean.StuIdHolder;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by liwang on 15-9-17.
 */
public class RandListenActivity extends Activity implements View.OnClickListener, MediaPlayer.OnPreparedListener {

    private TextView titleView;
    private TextView queTopicView;
    private TextView ansAView;
    private TextView ansBView;
    private TextView ansCView;
    private TextView ansDView;
    private ImageView playView;
    private ImageView rightA, rightB, rightC, rightD;
    private ImageView rightView;
    private View uploadView;
    private View flag1View, flag2View, flag3View, flag4View;

    private int selectedColr = Color.parseColor("#0288D1");
    private ArrayList<Question> allQues;
    private int position;
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rand_listener_activity);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        if (mMediaPlayer == null) {
            //初始化音频播放设置
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnPreparedListener(this);

            Intent intent = getIntent();
            allQues = (ArrayList<Question>) intent.getSerializableExtra("all_que");
            position = intent.getIntExtra("position", 0);
            updateQue();
        } else {
            mMediaPlayer.start();
        }
    }

    private void initView() {
        titleView = (TextView) findViewById(R.id.testtitle);
        queTopicView = (TextView) findViewById(R.id.que_topic);
        ansAView = (TextView) findViewById(R.id.que_ans_1);
        ansBView = (TextView) findViewById(R.id.que_ans_2);
        ansCView = (TextView) findViewById(R.id.que_ans_3);
        ansDView = (TextView) findViewById(R.id.que_ans_4);
        rightA = (ImageView) findViewById(R.id.right_1);
        rightB = (ImageView) findViewById(R.id.right_2);
        rightC = (ImageView) findViewById(R.id.right_3);
        rightD = (ImageView) findViewById(R.id.right_4);
        uploadView = findViewById(R.id.upload_ans);
        flag1View = findViewById(R.id.flag_1);
        flag2View = findViewById(R.id.flag_2);
        flag3View = findViewById(R.id.flag_3);
        flag4View = findViewById(R.id.flag_4);
        playView = (ImageView) findViewById(R.id.btnPlay);

        flag1View.setOnClickListener(this);
        flag2View.setOnClickListener(this);
        flag3View.setOnClickListener(this);
        flag4View.setOnClickListener(this);
        uploadView.setOnClickListener(this);
        playView.setOnClickListener(this);
    }

    private void updateQue() {
        titleView.setText(allQues.get(position).getQue_topic());
        queTopicView.setText(allQues.get(position).getQue_topic());
        ansAView.setText(allQues.get(position).getAns_a());
        ansBView.setText(allQues.get(position).getAns_b());
        ansCView.setText(allQues.get(position).getAns_c());
        ansDView.setText(allQues.get(position).getAns_d());
        try {
            mMediaPlayer.setDataSource(StuIdHolder.recordBase + allQues.get(position).getPath());
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload_ans:
                showRightAns();
                break;
            case R.id.flag_1:
                resetColor(flag1View);
                break;
            case R.id.flag_2:
                resetColor(flag2View);
                break;
            case R.id.flag_3:
                resetColor(flag3View);
                break;
            case R.id.flag_4:
                resetColor(flag4View);
                break;
            case R.id.btnPlay:
                break;
        }
    }

    private void showRightAns() {
        String rightAns = allQues.get(position).getAns_right();
        if ("A".equals(rightAns)) {
            rightView = rightA;
        } else if ("B".equals(rightAns)) {
            rightView = rightB;
        } else if ("C".equals(rightAns)) {
            rightView = rightC;
        } else {
            rightView = rightD;
        }
        rightView.setImageResource(R.drawable.homework_right);
    }

    private void resetColor(View v) {
        clearColor();
        v.setBackgroundColor(selectedColr);
    }

    private void clearColor() {
        flag1View.setBackgroundColor(Color.TRANSPARENT);
        flag2View.setBackgroundColor(Color.TRANSPARENT);
        flag3View.setBackgroundColor(Color.TRANSPARENT);
        flag4View.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.stop();
        mMediaPlayer.release();
    }
}
