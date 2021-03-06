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
import java.util.Map;

/**
 * Created by pavan on 9/19/2017.
 */

public class QuizResultDataAdapter extends BaseAdapter {

    private static LayoutInflater inflater=null;
    ArrayList<QuizResultDetails> resultList = new ArrayList<>();
    Context context;

    public QuizResultDataAdapter(Context context) {
        this.context = context;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public QuizResultDataAdapter(Context context,  ArrayList<QuizResultDetails> arrayList) {
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
        TextView studentName;
        TextView quizResult;
    }

    public void datasetchanged(ArrayList<QuizResultDetails> arrayList) {
        resultList = arrayList;
        Log.d("Quiz", "Data set changed");
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final QuizResultDataAdapter.Holder holder = new QuizResultDataAdapter.Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.quizresult_list, null);
        holder.studentName = (TextView) rowView.findViewById(R.id.studentName);
        holder.quizResult = (TextView) rowView.findViewById(R.id.quizResult);

        final QuizResultDetails quizResultDetails = resultList.get(position);

        holder.studentName.setText(quizResultDetails.getName());
        holder.quizResult.setText(quizResultDetails.getTotal());
        return rowView;
    }

}
