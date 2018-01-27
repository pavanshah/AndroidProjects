package com.pvanshah.sjsuquizapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.pvanshah.sjsuquizapplication.firebaseutils.FirebaseConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ProfessorHomeActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private FloatingActionButton addQuiz;
    private ListView quizlist;
    private ListView studentlist;
    private ListView quizMinMax;
    QuizDataAdapter quizDataAdapter;
    StudentDataAdapter studentDataAdapter;
    private int noOfQuestions = 0;
    private LinearLayout analyticsView;
    TextView studentsEnrolled;
    TextView quizzesConducted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_home);

        Intent intent = getIntent();
        String professorName = intent.getStringExtra("ProfessorName");

        Log.d("title", "professorName "+professorName);
        getSupportActionBar().setTitle("My Class");

        //All declarations
        final ArrayList<StudentDetails> studentDetails = new ArrayList<StudentDetails>();
        studentlist=(ListView) findViewById(R.id.studentlist);
        studentlist.setVisibility(View.VISIBLE);
        studentDataAdapter = new StudentDataAdapter(getApplicationContext(), studentDetails);
        studentlist.setAdapter(studentDataAdapter);

        final ArrayList<QuizDetails> quizData = new ArrayList<QuizDetails>();
        quizlist=(ListView) findViewById(R.id.quizList);
        quizlist.setVisibility(View.INVISIBLE);
        quizDataAdapter = new QuizDataAdapter(getApplicationContext(), quizData);
        quizlist.setAdapter(quizDataAdapter);

        addQuiz = (FloatingActionButton) findViewById(R.id.addQuiz);
        addQuiz.setVisibility(View.INVISIBLE);

        analyticsView = (LinearLayout) findViewById(R.id.analytics);
        analyticsView.setVisibility(View.INVISIBLE);

        studentsEnrolled = (TextView) findViewById(R.id.studentsEnrolled);
        quizzesConducted = (TextView) findViewById(R.id.quizzesConducted);

        final ArrayList<QuizMinMaxDetails> quizMinMaxData = new ArrayList<QuizMinMaxDetails>();
        quizMinMax = (ListView) findViewById(R.id.quizMinMax);
        final QuizMinMaxAdapter quizMinMaxAdapter = new QuizMinMaxAdapter(getApplicationContext(), quizMinMaxData);
        quizMinMax.setAdapter(quizMinMaxAdapter);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Retrive all the data from firebase here

        //Results data

        final DatabaseReference resultReference = FirebaseConfiguration.getResultRef();
        final ArrayList<QuizResultDetails> resultDetails = new ArrayList<QuizResultDetails>();

        resultReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("minmax", "data received "+dataSnapshot);

                resultDetails.clear();

                for(DataSnapshot child : dataSnapshot.getChildren())
                {
                    QuizResultDetails data = child.getValue(QuizResultDetails.class);
                    QuizResultDetails childData = new QuizResultDetails();

                    childData.setQuizID(data.getQuizID());
                    childData.setQuizName(data.getQuizName());
                    childData.setMax(data.getMax());
                    childData.setTotal(data.getTotal());

                    resultDetails.add(childData);
                }

                Log.d("minmax", "resultDetails "+resultDetails);

                final ArrayList<QuizMinMaxDetails> quizMinMaxDetails = new ArrayList<QuizMinMaxDetails>();
                HashMap<String, Integer> quizMax = new HashMap<String, Integer>();
                HashMap<String, Integer> quizMin = new HashMap<String, Integer>();

                for(int i = 0 ; i < resultDetails.size() ; i++)
                {
                    int totalMarks = Integer.parseInt(resultDetails.get(i).getTotal());

                    if(quizMax.containsKey(resultDetails.get(i).getQuizName()))
                    {
                        if(quizMax.get(resultDetails.get(i).getQuizName()) < totalMarks)
                        {
                            quizMax.put(resultDetails.get(i).getQuizName(), totalMarks);
                        }
                    }
                    else
                    {
                        quizMax.put(resultDetails.get(i).getQuizName(), totalMarks);
                    }



                    if(quizMin.containsKey(resultDetails.get(i).getQuizName()))
                    {
                        if(quizMin.get(resultDetails.get(i).getQuizName()) > totalMarks)
                        {
                            quizMax.put(resultDetails.get(i).getQuizName(), totalMarks);
                        }
                    }
                    else
                    {
                        quizMin.put(resultDetails.get(i).getQuizName(), totalMarks);
                    }
                }


                Log.d("minmax", "quizMax "+quizMax);
                Log.d("minmax", "quizMin "+quizMin);


                Iterator it = quizMax.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    QuizMinMaxDetails child = new QuizMinMaxDetails();
                    child.setQuizName(pair.getKey().toString());
                    child.setMax(pair.getValue().toString());
                    child.setMin(quizMin.get(pair.getKey()).toString());
                    quizMinMaxDetails.add(child);
                }

                quizMinMaxAdapter.datasetchanged(quizMinMaxDetails);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Student Data
        final DatabaseReference studentReference = FirebaseConfiguration.getStudentData();
        studentReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("student", "data received "+dataSnapshot);
                studentDetails.clear();

                for(DataSnapshot child : dataSnapshot.getChildren())
                {
                    StudentDetails data = child.getValue(StudentDetails.class);
                    StudentDetails childData = new StudentDetails();
                    childData.setUsername(data.getUsername());
                    childData.setEmail(data.getEmail());
                    studentDetails.add(childData);
                }

                studentsEnrolled.setText(studentDetails.size()+"");
                studentDataAdapter.datasetchanged(studentDetails);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        final DatabaseReference quizReference = FirebaseConfiguration.getQuizData();
        quizReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //QuizDetails quizData = dataSnapshot.getValue(QuizDetails.class)
                quizData.clear();

                for(DataSnapshot child : dataSnapshot.getChildren())
                {
                    QuizDetails data = child.getValue(QuizDetails.class);
                    QuizDetails childData = new QuizDetails();
                    childData.setQuizID(data.getQuizID());
                    childData.setQuizTitle(data.getQuizTitle());
                    childData.setQuizStatus(data.getQuizStatus());
                    //QuizDetails childData = child.getValue(QuizDetails.class);
                    Log.d("Quiz", "data "+childData.getQuizTitle());
                    quizData.add(childData);
                }

                quizzesConducted.setText(quizData.size()+"");
                quizDataAdapter.datasetchanged(quizData);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        addQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addNewQuiz();

            }
        });

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    addQuiz.setVisibility(View.INVISIBLE);
                    quizlist.setVisibility(View.INVISIBLE);
                    analyticsView.setVisibility(View.INVISIBLE);
                    studentlist.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_dashboard:
                    addQuiz.setVisibility(View.INVISIBLE);
                    quizlist.setVisibility(View.INVISIBLE);
                    analyticsView.setVisibility(View.VISIBLE);
                    studentlist.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_notifications:
                    addQuiz.setVisibility(View.VISIBLE);
                    quizlist.setVisibility(View.VISIBLE);
                    analyticsView.setVisibility(View.INVISIBLE);
                    studentlist.setVisibility(View.INVISIBLE);
                    return true;
            }
            return false;
        }

    };


    public void addNewQuiz()
    {
        // custom dialog
        final Dialog dialog = new Dialog(ProfessorHomeActivity.this);
        dialog.setContentView(R.layout.customtoast);

        //get the spinner from the xml.
//        final Spinner dropdown = (Spinner) dialog.findViewById(R.id.spinner1);
//        //create a list of items for the spinner.
//        Integer[] items = new Integer[]{1,2, 3, 4, 5, 6, 7 , 8 , 9 ,10};
//        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
//        //There are multiple variations of this, but this is the basic variant.
//        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, items);
//        //set the spinners adapter to the previously created one.
//        dropdown.setAdapter(adapter);

        MaterialSpinner spinner = (MaterialSpinner) dialog.findViewById(R.id.spinner);
        spinner.setItems("1","2","3","4","5","6","7","8","9","10");

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                noOfQuestions = Integer.parseInt(item);
            }
        });

        final EditText quizTitle = (EditText) dialog.findViewById(R.id.quizName);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = quizTitle.getText().toString();
                Intent intent = new Intent(ProfessorHomeActivity.this, CreateQuiz.class);
                intent.putExtra("quizTitle", title);
                intent.putExtra("noOfQuestions", noOfQuestions);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
