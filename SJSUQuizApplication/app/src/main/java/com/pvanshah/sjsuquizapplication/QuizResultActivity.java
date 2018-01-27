package com.pvanshah.sjsuquizapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.pvanshah.sjsuquizapplication.firebaseutils.FirebaseConfiguration;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class QuizResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        Intent intent = getIntent();

        final String id = intent.getStringExtra("quizId");
        String name = intent.getStringExtra("quizName");

        TextView quizName = (TextView) findViewById(R.id.quizName);
        quizName.setText(name);

        ListView quizList = (ListView) findViewById(R.id.quizList);
        final ArrayList<QuizResultDetails> resultDetails = new ArrayList<QuizResultDetails>();
        final QuizResultDataAdapter quizResultDataAdapter = new QuizResultDataAdapter(getApplicationContext(), resultDetails);
        quizList.setAdapter(quizResultDataAdapter);

        //Results data
        final DatabaseReference resultReference = FirebaseConfiguration.getResultRef();
        resultReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("quizResultData", "data received "+dataSnapshot);

                resultDetails.clear();

                for(DataSnapshot child : dataSnapshot.getChildren())
                {
                    QuizResultDetails data = child.getValue(QuizResultDetails.class);
                    QuizResultDetails childData = new QuizResultDetails();

                    Log.d("quizResultData", "child received "+childData);

                    if(data.getQuizID().equalsIgnoreCase(id))
                    {
                        childData.setQuizName(data.getQuizName());
                        childData.setTotal(data.getTotal() + "/" + data.getMax());
                        childData.setName(data.getName());
                        resultDetails.add(childData);
                    }
                }

                if(resultDetails.size() > 0)
                {
                    quizResultDataAdapter.datasetchanged(resultDetails);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
