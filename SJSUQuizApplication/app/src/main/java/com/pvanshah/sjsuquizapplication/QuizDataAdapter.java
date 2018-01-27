package com.pvanshah.sjsuquizapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.pvanshah.sjsuquizapplication.firebaseutils.FirebaseConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pavan Shah on 8/1/2017.
 */

public class QuizDataAdapter extends BaseAdapter {

    private static LayoutInflater inflater=null;
    ArrayList<QuizDetails> resultList = new ArrayList<>();
    Context context;

    public QuizDataAdapter(Context context) {
        this.context = context;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public QuizDataAdapter(Context context,  ArrayList<QuizDetails> arrayList) {
        this.context=context;
        resultList=arrayList;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        TextView quizTitle;
        TextView quizID;
        Button publish;
        Button checkResults;
    }

    public void datasetchanged(ArrayList<QuizDetails> arrayList) {
        resultList = arrayList;
        Log.d("Quiz", "Data set changed");
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final QuizDataAdapter.Holder holder = new QuizDataAdapter.Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.quiz_list, null);
        final QuizDetails quizDetails = resultList.get(position);

        Log.d("Quiz", "Quiz "+quizDetails);

        holder.quizTitle =(TextView) rowView.findViewById(R.id.quizTitle);
        Log.d("Quiz", "title "+quizDetails.getQuizTitle());
        holder.quizTitle.setText(quizDetails.getQuizTitle());

        holder.quizID =(TextView) rowView.findViewById(R.id.quizID);
        holder.quizID.setText(quizDetails.getQuizID());
        Log.d("Quiz", "id "+quizDetails.getQuizID());

        holder.publish = (Button) rowView.findViewById(R.id.publishQuiz);
        holder.checkResults = (Button) rowView.findViewById(R.id.checkResults);

        Log.d("Quiz", "here "+quizDetails.getQuizStatus());

        if(quizDetails.getQuizStatus().equalsIgnoreCase("Published"))
        {
            holder.publish.setVisibility(View.INVISIBLE);
            holder.checkResults.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.publish.setVisibility(View.VISIBLE);
            holder.checkResults.setVisibility(View.INVISIBLE);
        }

        holder.publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Quiz Published "+quizDetails.getQuizTitle(), Toast.LENGTH_SHORT).show();
                /*User user = new User(userID, userName, userMail, userActive);
                Map<String, Object> postValues = user.toMap();
                myRef.child(userID).updateChildren(postValues);*/

                //New Quiz Created
                DatabaseReference quizRoot = FirebaseConfiguration.getQuizData();

                String quizId = quizDetails.getQuizID();
                String quizTitle = quizDetails.getQuizTitle();
                String quizStatus = "Published";

                QuizDetails quizDetails = new QuizDetails(quizId, quizTitle, quizStatus);
                Map<String, Object> postValues = quizDetails.toMap();
                quizRoot.child(quizId).updateChildren(postValues);

            }
        });

        holder.checkResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent(QuizDataAdapter.this, QuizResultActivity.class);
                Intent intent=new Intent(context,QuizResultActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("quizName", holder.quizTitle.getText());
                intent.putExtra("quizId", holder.quizID.getText());
                context.startActivity(intent);
            }
        });

        return rowView;
    }
}
