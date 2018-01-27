package com.pvanshah.sjsuquizapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.pvanshah.sjsuquizapplication.firebaseutils.FirebaseConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

public class CreateQuiz extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    private static int numberOfPages = 5;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static final JSONArray finalQuizArray = new JSONArray();
    private static String quizTitle;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private static ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

   /*     Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        Intent intent = getIntent();
        quizTitle = intent.getStringExtra("quizTitle");
        int noOfQuestions = intent.getIntExtra("noOfQuestions", 5);

        numberOfPages = noOfQuestions;
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.fragment_create_quiz, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));



            //All declarations
            final Spinner dropdown = (Spinner) rootView.findViewById(R.id.spinner1);
            String[] items = new String[]{"", "A", "B", "C", "D"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
            dropdown.setAdapter(adapter);

            final Button addSubmitQuestion = (Button) rootView.findViewById(R.id.addSubmit);

            final EditText questionET = (EditText) rootView.findViewById(R.id.question);

            final EditText optionAET = (EditText) rootView.findViewById(R.id.optionA);

            final EditText optionBET = (EditText) rootView.findViewById(R.id.optionB);

            final EditText optionCET = (EditText) rootView.findViewById(R.id.optionC);

            final EditText optionDET = (EditText) rootView.findViewById(R.id.optionD);

            if(getArguments().getInt(ARG_SECTION_NUMBER) == numberOfPages)
            {
                addSubmitQuestion.setText("Add New Quiz");
            }
            else
            {
                addSubmitQuestion.setText("Add Question");
            }

            addSubmitQuestion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String question = questionET.getText().toString();
                    String optionA = optionAET.getText().toString();
                    String optionB = optionBET.getText().toString();
                    String optionC = optionCET.getText().toString();
                    String optionD = optionDET.getText().toString();
                    String correctOption = (String) dropdown.getSelectedItem();

                    if(!question.equals("") && !optionA.equals("") && !optionB.equals("") && !optionC.equals("") && !optionD.equals("")  && !correctOption.equals(""))
                    {
                        if(getArguments().getInt(ARG_SECTION_NUMBER) == numberOfPages)
                        {
                            addNewQuestion(question, optionA, optionB, optionC, optionD, correctOption);
                            publishQuiz();
                        }
                        else
                        {
                            addNewQuestion(question, optionA, optionB, optionC, optionD, correctOption);
                            mViewPager.setCurrentItem(getArguments().getInt(ARG_SECTION_NUMBER), true);
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "Complete all the fields", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            return rootView;
        }

        public void addNewQuestion(String question, String optionA, String optionB, String optionC, String optionD, String correctOption){

            JSONObject finalJSON = new JSONObject();
            JSONObject answerJSON = new JSONObject();

            try
            {
                answerJSON.put("A", optionA);
                answerJSON.put("B", optionB);
                answerJSON.put("C", optionC);
                answerJSON.put("D", optionD);
                finalJSON.put("Question", question);
                finalJSON.put("Options", answerJSON);
                finalJSON.put("CorrectOption", correctOption);

                finalQuizArray.put(finalJSON);

                for(int i = 0 ; i < finalQuizArray.length(); i++)
                {
                    try {
                        Log.d("Quiz", "New question "+finalQuizArray.get(i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Toast.makeText(getActivity(), "Question Added", Toast.LENGTH_SHORT).show();
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }


        public void publishQuiz()
        {
            //New Quiz Created
            DatabaseReference quizRoot = FirebaseConfiguration.getQuizData();

            String quizId = uniqueIdGenerator();
            String quizStatus = "Unpublished";

            QuizDetails quizDetails = new QuizDetails(quizId, quizTitle, quizStatus);

            /*HashMap<String, String> QuizObject = new HashMap<String, String>();

            QuizObject.put("quizID", quizId);
            QuizObject.put("quizTitle", quizTitle);
            QuizObject.put("quizStatus", "unpublished");*/

            quizRoot.child(quizId).setValue(quizDetails);

            //---------------------------------------------------


            for(int i = 0 ; i < finalQuizArray.length(); i++)
            {
                try
                {
                    JSONObject currentQuestion = finalQuizArray.getJSONObject(i);

                    String question = currentQuestion.getString("Question");
                    JSONObject options = currentQuestion.getJSONObject("Options");
                    String optionA = options.getString("A");
                    String optionB = options.getString("B");
                    String optionC = options.getString("C");
                    String optionD = options.getString("D");
                    String correctAnswer = currentQuestion.getString("CorrectOption");

                    //String quizID, String questionNumber, String question, String[] options, String answer
                    HashMap<String, String> optionsObject = new HashMap();
                    optionsObject.put("A", optionA);
                    optionsObject.put("B", optionB);
                    optionsObject.put("C", optionC);
                    optionsObject.put("D", optionD);

                    QuizQuestion quizQuestion = new QuizQuestion(quizId, i+1, question, optionsObject, correctAnswer);

                    Log.d("Quiz", "question "+question);
                    Log.d("Quiz", "correctAnswer "+correctAnswer);

                    DatabaseReference questionsRoot = FirebaseConfiguration.getQuestionsData();
                    questionsRoot.push().setValue(quizQuestion);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            Toast.makeText(getActivity(), "Quiz Published", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), ProfessorHomeActivity.class);
            startActivity(intent);
        }


        public String uniqueIdGenerator()
        {
            String uniqueID = "";
            Date timeStamp = new Date();

            //6 digit unique id generated using 4 digits random number, 2 digits of second
            //ensures uniqueness in every request
            uniqueID = uniqueID + (int) Math.floor(Math.random()*9000) + 1000;
            uniqueID = uniqueID+timeStamp.getSeconds();

            return uniqueID;
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return numberOfPages;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
