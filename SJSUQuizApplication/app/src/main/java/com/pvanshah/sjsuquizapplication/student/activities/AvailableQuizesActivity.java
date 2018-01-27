package com.pvanshah.sjsuquizapplication.student.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.pvanshah.sjsuquizapplication.R;
import com.pvanshah.sjsuquizapplication.firebaseutils.FirebaseConfiguration;
import com.pvanshah.sjsuquizapplication.student.adapters.QuizListAdapter;
import com.pvanshah.sjsuquizapplication.student.base.AbstractBaseAdapter;
import com.pvanshah.sjsuquizapplication.student.base.BaseAppCompatActivity;
import com.pvanshah.sjsuquizapplication.student.model.Quiz;
import com.pvanshah.sjsuquizapplication.student.model.ResponseObject;
import com.pvanshah.sjsuquizapplication.student.network.NetworkStateChangeListener;
import com.pvanshah.sjsuquizapplication.student.network.NetworkStateListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by avinash on 7/17/17.
 */

public class AvailableQuizesActivity extends BaseAppCompatActivity implements NetworkStateChangeListener {

    private boolean isDataFetched;

    @Bind(R.id.lst_quiz)
    ListView quizzes;
    private AbstractBaseAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_quiz));
        NetworkStateListener.registerNetworkState(this);
        FirebaseConfiguration firebaseConfiguration = new FirebaseConfiguration();
        firebaseConfiguration.configureFirebase();
        callQuizzesService();
    }

    private void callQuizzesService() {
        DatabaseReference quizRoot = FirebaseConfiguration.getQuizData();

        quizRoot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Quiz> response = collectQuizes((Map<String, Object>) dataSnapshot.getValue());
                if (response != null && response.size() > 0) {
                    setDataToList(response);
                } else {
                    Toast.makeText(AvailableQuizesActivity.this, R.string.no_quizzes, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private List<Quiz> collectQuizes(Map<String, Object> quizzes) {
        List<Quiz> quizList = new ArrayList<>();
        Quiz quiz;
        //iterate through each quiz
        if(quizzes == null)
        {
            return null;
        }
        else
        {
            for (Map.Entry<String, Object> entry : quizzes.entrySet()) {

                //Get quiz map
                Map singleQuiz = (Map) entry.getValue();
                if((singleQuiz.get("quizStatus")).equals("Published")) {
                    quiz = new Quiz();
                    quiz.setQuizId((String) singleQuiz.get("quizID"));
                    quiz.setQuizTitle((String) singleQuiz.get("quizTitle"));
                    quizList.add(quiz);
                }
            }
            return quizList;
        }
    }

    private void setDataToList(final List quizList) {
        isDataFetched = true;
        adapter = new QuizListAdapter(this).addItems(quizList);
        quizzes.setAdapter(adapter);
        quizzes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                DatabaseReference resultRef = FirebaseConfiguration.getResultRef();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                resultRef.orderByChild("email")
                        .equalTo(firebaseAuth.getCurrentUser().getEmail())
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() != null) {
                                    List<ResponseObject> responseObjects = collectResponseObjects((Map<String, Object>) dataSnapshot.getValue(), ((Quiz) quizList.get(position)).getQuizId());

                                    if (responseObjects != null && responseObjects.size() > 0) {
                                        int score = 0;
                                        for (int i = 0; i < responseObjects.size(); i++) {
                                            int scored = Integer.parseInt(responseObjects.get(i).getTotal());
                                            if (scored > score) {
                                                score = scored;
                                            }
                                        }
                                        MaterialDialog dialog = new MaterialDialog.Builder(AvailableQuizesActivity.this)
                                                .title(R.string.score)
                                                .content(getResources().getString(R.string.submitted) + " " +score + "\n" + getResources().getString(R.string.take_again))
                                                .positiveText(R.string.yes)
                                                .negativeText(R.string.no)
                                                .contentColor(Color.GRAY)
                                                .backgroundColorRes(android.R.color.white)
                                                .positiveColorRes(R.color.colorAccent)
                                                .negativeColorRes(R.color.colorAccent)
                                                .autoDismiss(false)
                                                .onPositive(new MaterialDialog.SingleButtonCallback() {

                                                    @Override
                                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                        startQuiz(((Quiz) quizList.get(position)).getQuizId(), ((Quiz) quizList.get(position)).getQuizTitle());
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
                                        startQuiz(((Quiz) quizList.get(position)).getQuizId(), ((Quiz) quizList.get(position)).getQuizTitle());
                                    }
                                } else {
                                    startQuiz(((Quiz) quizList.get(position)).getQuizId(), ((Quiz) quizList.get(position)).getQuizTitle());
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
            }
        });
    }

    private List<ResponseObject> collectResponseObjects(Map<String, Object> dataSnapshotValue, String id) {
        List<ResponseObject> responseObjectList = new ArrayList<>();
        ResponseObject responseObject;
        for (Map.Entry<String, Object> entry : dataSnapshotValue.entrySet()) {
            Map singleObject = (Map) entry.getValue();
            String json = new Gson().toJson(singleObject);
            responseObject = new Gson().fromJson(json, ResponseObject.class);
            if (id.equals(responseObject.getQuizID())) {
                responseObjectList.add(responseObject);
            }
        }
        return responseObjectList;
    }

    private void startQuiz(String id, String title) {
        Intent intent = new Intent(AvailableQuizesActivity.this, QuizActivity.class);
        intent.putExtra(QuizActivity.ID, id);
        intent.putExtra(QuizActivity.TITLE, title);
        startActivity(intent);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_available_quizes;
    }

    @Override
    public void onNetworkStateChanged(int networkState) {
        if (networkState == NetworkStateListener.NETWORK_CONNECTED) {
            if (!isDataFetched) {
                callQuizzesService();
            }
        } else if (networkState == NetworkStateListener.NO_NETWORK_CONNECTIVITY) {
            // disconnected
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkStateListener.unregisterNetworkState(this);
    }

    @Override
    protected void onPause() {
        NetworkStateListener.unregisterNetworkState(this);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.student_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.account:
                startActivity(new Intent(AvailableQuizesActivity.this, AccountActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
