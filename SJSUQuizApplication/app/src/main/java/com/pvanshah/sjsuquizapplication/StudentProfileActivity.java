package com.pvanshah.sjsuquizapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.pvanshah.sjsuquizapplication.firebaseutils.FirebaseConfiguration;

import java.util.ArrayList;

public class StudentProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        getSupportActionBar().setTitle(R.string.student);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("studentEmail");
        String name = intent.getStringExtra("studentName");

        TextView studentName = (TextView) findViewById(R.id.studentName);
        studentName.setText(name);

        final LinearLayout studentProfile = (LinearLayout) findViewById(R.id.studentProfile);
        final TextView noSubmissions = (TextView) findViewById(R.id.noSubmissions);
        noSubmissions.setVisibility(View.INVISIBLE);

        final ListView resultList = (ListView) findViewById(R.id.quizResultList);

        final ArrayList<QuizResultDetails> resultDetails = new ArrayList<QuizResultDetails>();
        final ResultDataAdapter resultDataAdapter = new ResultDataAdapter(getApplicationContext(), resultDetails);

        resultList.setAdapter(resultDataAdapter);

        Log.d("pavan2", "email "+email);

        //Results data
        final DatabaseReference resultReference = FirebaseConfiguration.getResultRef();
        resultReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("pavan2", "data received "+dataSnapshot);

                resultDetails.clear();

                for(DataSnapshot child : dataSnapshot.getChildren())
                {
                    QuizResultDetails data = child.getValue(QuizResultDetails.class);
                    QuizResultDetails childData = new QuizResultDetails();

                    if(data.getEmail().equalsIgnoreCase(email))
                    {
                        childData.setQuizName(data.getQuizName());
                        childData.setTotal(data.getTotal() + "/" + data.getMax());
                        resultDetails.add(childData);
                    }
                }

                if(resultDetails.size()>0) {
                    resultDataAdapter.datasetchanged(resultDetails);
                }
                else
                {
                    noSubmissions.setVisibility(View.VISIBLE);
                    studentProfile.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
