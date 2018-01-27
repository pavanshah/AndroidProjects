package com.pvanshah.sjsuquizapplication.student.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.eftimoff.viewpagertransformers.ForegroundToBackgroundTransformer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.pixelcan.inkpageindicator.InkPageIndicator;
import com.pvanshah.sjsuquizapplication.R;
import com.pvanshah.sjsuquizapplication.firebaseutils.FirebaseConfiguration;
import com.pvanshah.sjsuquizapplication.student.adapters.QuizPagerAdapter;
import com.pvanshah.sjsuquizapplication.student.model.Question;
import com.pvanshah.sjsuquizapplication.student.model.Response;
import com.pvanshah.sjsuquizapplication.student.model.ResponseObject;
import com.pvanshah.sjsuquizapplication.student.util.Numerics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by avinash on 7/17/17.
 */

public class QuizActivity extends AppCompatActivity {

    public static final String ID = "id";
    static final String TITLE = "title";

    @Bind(R.id.pager_quiz)
    ViewPager quizPager;

    @Bind(R.id.viewpager_indicator)
    InkPageIndicator pagerIndicator;

    public List<Response> responseList = new ArrayList();
    private String id;
    private int max = 0;

    @OnClick(R.id.prev_btn)
    void moveBack() {
        if (quizPager != null && quizPager.getCurrentItem() > 0) {
            quizPager.setCurrentItem(quizPager.getCurrentItem() - 1, true);
        }
    }

    @OnClick(R.id.next_btn)
    void moveForward() {
        if (quizPager != null && quizPager.getCurrentItem() < quizPager.getAdapter().getCount()) {
            quizPager.setCurrentItem(quizPager.getCurrentItem() + 1, true);
        }
    }

    @OnClick(R.id.submit)
    void submit() {
        if (quizPager != null && responseList != null) {
            if (quizPager.getAdapter().getCount() > 0 && responseList.size() == quizPager.getAdapter().getCount()) {
                MaterialDialog dialog = new MaterialDialog.Builder(QuizActivity.this)
                        .title(R.string.sure)
                        .content(R.string.submit_ask)
                        .positiveText(R.string.submit)
                        .negativeText(R.string.no)
                        .contentColor(Color.GRAY)
                        .backgroundColorRes(android.R.color.white)
                        .positiveColorRes(R.color.colorAccent)
                        .negativeColorRes(R.color.colorAccent)
                        .autoDismiss(false)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {

                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                callToSaveResponses(responseList);
                                dialog.dismiss();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            } else {
                MaterialDialog dialog = new MaterialDialog.Builder(QuizActivity.this)
                        .title(R.string.sure)
                        .content(R.string.attempt_all)
                        .positiveText(R.string.submit)
                        .negativeText(R.string.no)
                        .contentColor(Color.GRAY)
                        .backgroundColorRes(android.R.color.white)
                        .positiveColorRes(R.color.colorAccent)
                        .negativeColorRes(R.color.colorAccent)
                        .autoDismiss(false)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {

                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                callToSaveResponses(responseList);
                                dialog.dismiss();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        }
    }

    private void callToSaveResponses(List<Response> responseList) {
        final DatabaseReference resultRef = FirebaseConfiguration.getResultRef();
        int total = 0;
        for (Response response1 : responseList) {
            if (response1.getAttempted().equals(response1.getCorrect())) {
                total = total + 1;
            }
        }
        final ResponseObject responseObject = new ResponseObject();

        if (!TextUtils.isEmpty(id)) {
            responseObject.setQuizID(id);
        } else {
            if (getIntent() != null) {
                responseObject.setQuizID(getIntent().getStringExtra(ID));
            }
        }

        responseObject.setTotal(total + "");

        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        responseObject.setEmail(firebaseAuth.getCurrentUser().getEmail());
        responseObject.setName(firebaseAuth.getCurrentUser().getDisplayName());
        responseObject.setQuizName(getIntent().getStringExtra(TITLE));
        responseObject.setMax(max+"");
        responseObject.setResponse(responseList);

        resultRef.push().setValue(responseObject);

        MaterialDialog dialog = new MaterialDialog.Builder(QuizActivity.this)
                .title(R.string.submit)
                .content(getResources().getString(R.string.your_score) + " " + total + " " + getResources().getString(R.string.in_this_quiz))
                .negativeText(R.string.ok)
                .contentColor(Color.GRAY)
                .backgroundColorRes(android.R.color.white)
                .negativeColorRes(R.color.colorAccent)
                .autoDismiss(false)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FirebaseConfiguration firebaseConfiguration = new FirebaseConfiguration();
        firebaseConfiguration.configureFirebase();
        quizPager.setOffscreenPageLimit(Numerics.FIFTY);

        Intent intent = getIntent();
        if (intent != null) {
            startQuiz(intent);
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    private void startQuiz(Intent intent) {
        id = intent.getStringExtra(ID);
        String title = intent.getStringExtra(TITLE);
        if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(title)) {
            getQuizQuestions(id);
            getSupportActionBar().setTitle(title);
        } else {
            finish();
        }
    }

    private void getQuizQuestions(String id) {
        DatabaseReference questionsRoot = FirebaseConfiguration.getQuestionsData();

        questionsRoot.orderByChild("quizID").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Question> response = collectQuestions((Map<String, Object>) dataSnapshot.getValue());
                if (response != null && response.size() > 0) {
                    setQuestionsToPager(response);
                } else {
                    Toast.makeText(QuizActivity.this, R.string.no_questions, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private List<Question> collectQuestions(Map<String, Object> questions) {
        List<Question> questionsList = new ArrayList<>();
        Question question;
        for (Map.Entry<String, Object> entry : questions.entrySet()) {
            Map singleQuestion = (Map) entry.getValue();
            String json = new Gson().toJson(singleQuestion);
            question = new Gson().fromJson(json, Question.class);
            questionsList.add(question);
        }
        this.max = questionsList.size();
        return questionsList;
    }

    private void setQuestionsToPager(List<Question> questions) {
        final QuizPagerAdapter quizPagerAdapter = new QuizPagerAdapter(getSupportFragmentManager(), questions);
        quizPager.setAdapter(quizPagerAdapter);
        pagerIndicator.setViewPager(quizPager);
        quizPager.setPageTransformer(true, new ForegroundToBackgroundTransformer());
        quizPager.setClipToPadding(false);
        quizPager.setPadding(Numerics.TEN, 0, Numerics.TEN, 0);
        quizPager.setPageMargin(Numerics.TEN);
    }

    @Override
    public void onBackPressed() {
        showWarningDialog();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            showWarningDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showWarningDialog() {
        MaterialDialog dialog = new MaterialDialog.Builder(QuizActivity.this)
                .title(R.string.sure)
                .content(R.string.quit_quiz)
                .positiveText(R.string.quit)
                .negativeText(R.string.no)
                .contentColor(Color.GRAY)
                .backgroundColorRes(android.R.color.white)
                .positiveColorRes(R.color.colorAccent)
                .negativeColorRes(R.color.colorAccent)
                .autoDismiss(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {

                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

}
