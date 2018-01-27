package com.pvanshah.sjsuquizapplication.student.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.pvanshah.sjsuquizapplication.R;
import com.pvanshah.sjsuquizapplication.firebaseutils.FirebaseConfiguration;
import com.pvanshah.sjsuquizapplication.student.adapters.ScoreListAdapter;
import com.pvanshah.sjsuquizapplication.student.base.AbstractBaseAdapter;
import com.pvanshah.sjsuquizapplication.student.base.BaseAppCompatActivity;
import com.pvanshah.sjsuquizapplication.student.model.ResponseObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by avinash on 7/19/17.
 */

public class AccountActivity extends BaseAppCompatActivity {

    @Bind(R.id.name)
    TextView nameTxt;

    @Bind(R.id.email)
    TextView emailTxt;

    @Bind(R.id.score_lst)
    ListView scoreLst;

    @OnClick(R.id.sign_out)
    void signOut() {
        AuthUI.getInstance().signOut(AccountActivity.this);
        Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        AccountActivity.this.finish();
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_account;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        nameTxt.setText(firebaseAuth.getCurrentUser().getDisplayName());
        emailTxt.setText(firebaseAuth.getCurrentUser().getEmail());
        getSupportActionBar().setTitle(R.string.account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getStudentScores(firebaseAuth.getCurrentUser().getEmail());
    }

    private void getStudentScores(String email) {
        DatabaseReference resultRef = FirebaseConfiguration.getResultRef();

        resultRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    List<ResponseObject> responseObjects = collectResponseObjects((Map<String, Object>) dataSnapshot.getValue());
                    if (responseObjects != null && responseObjects.size() > 0) {
                        AbstractBaseAdapter adapter = new ScoreListAdapter(AccountActivity.this).addItems(responseObjects);
                        scoreLst.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private List<ResponseObject> collectResponseObjects(Map<String, Object> dataSnapshotValue) {
        List<ResponseObject> responseObjectList = new ArrayList<>();
        ResponseObject responseObject;
        for (Map.Entry<String, Object> entry : dataSnapshotValue.entrySet()) {
            Map singleObject = (Map) entry.getValue();
            String json = new Gson().toJson(singleObject);
            responseObject = new Gson().fromJson(json, ResponseObject.class);
            responseObjectList.add(responseObject);
        }
        return responseObjectList;
    }
}
