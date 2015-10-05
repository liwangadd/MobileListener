package com.nicolas.mobilelistener.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nicolas.mobilelistener.R;
import com.nicolas.mobilelistener.application.ListenerApplication;
import com.nicolas.mobilelistener.bean.AllQuestion;
import com.nicolas.mobilelistener.bean.Question;
import com.nicolas.mobilelistener.service.QuestionService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by liwang on 15-9-17.
 */
public class AllQueActivity extends Activity implements AdapterView.OnItemClickListener {

    private TextView titleView;
    private View logoutView;
    private ListView dataView;

    @Inject
    QuestionService questionService;

    private String testTitle;
    private String testId;
    private ArrayList<Question> allQues = new ArrayList<>();

    private ProgressDialog loadingView;
    private QuestionAdapter questionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musiclis_activityt);
        ((ListenerApplication)getApplication()).component().inject(this);

        initView();

        initData();
    }

    private void initView() {
        titleView = (TextView) findViewById(R.id.testtitle);
        logoutView = findViewById(R.id.logout);
        dataView = (ListView) findViewById(R.id.music_listview);

        logoutView.setVisibility(View.GONE);
        loadingView = new ProgressDialog(this);
        loadingView.setMessage("正在从服务器生成题目");
        loadingView.show();

        dataView.setOnItemClickListener(this);
    }

    private void initData() {

        Intent intent = getIntent();
        testTitle = intent.getStringExtra("test_topic");
        testId = intent.getStringExtra("test_id");

        titleView.setText(testTitle);
        Observable<AllQuestion> allQueObservable = questionService.getQueById(testId);
        allQueObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Action1<AllQuestion>() {
                    @Override
                    public void call(AllQuestion questions) {
                        loadingView.dismiss();
                        if (questions.getMessage()) {
                            allQues.addAll(questions.getResult());
                            questionAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(AllQueActivity.this, "题目获取失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(AllQueActivity.this, "网络连接异常", Toast.LENGTH_SHORT).show();
                        loadingView.dismiss();
                    }
                });

        questionAdapter = new QuestionAdapter(allQues);
        dataView.setAdapter(questionAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, RandListenActivity.class);
        intent.putExtra("all_que", allQues);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    class QuestionAdapter extends BaseAdapter {

        private List<Question> questions;
        private LayoutInflater inflate;

        public QuestionAdapter(List<Question> questions) {
            this.questions = questions;
            inflate = AllQueActivity.this.getLayoutInflater();
        }

        @Override
        public int getCount() {
            return questions.size();
        }

        @Override
        public Question getItem(int position) {
            return questions.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = inflate.inflate(R.layout.musiclist_listview_item, null);
            TextView nameView = (TextView) convertView.findViewById(R.id.hwname);
            nameView.setText(getItem(position).getQue_topic());
            ImageView markView = (ImageView) convertView.findViewById(R.id.hwmark);
            markView.setVisibility(View.GONE);
            return convertView;
        }
    }
}
