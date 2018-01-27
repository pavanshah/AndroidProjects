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
 * Created by Pavan Shah on 8/1/2017.
 */

public class ResultDataAdapter extends BaseAdapter {

    private static LayoutInflater inflater=null;
    ArrayList<QuizResultDetails> resultList = new ArrayList<>();
    Context context;

    public ResultDataAdapter(Context context, ArrayList<QuizResultDetails> arrayList) {
        this.context=context;
        resultList=arrayList;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ResultDataAdapter(Context context) {
        this.context = context;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
        TextView quizName;
        TextView quizScore;
    }

    public void datasetchanged(ArrayList<QuizResultDetails> arrayList) {
        resultList = arrayList;
        Log.d("pavan2", "Data set changed "+resultList);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ResultDataAdapter.Holder holder = new ResultDataAdapter.Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.result_list, null);
        final QuizResultDetails quizResultDetails = resultList.get(position);

        holder.quizName =(TextView) rowView.findViewById(R.id.QuizName);
        holder.quizName.setText(quizResultDetails.getQuizName());

        holder.quizScore =(TextView) rowView.findViewById(R.id.QuizScore);
        holder.quizScore.setText(quizResultDetails.getTotal());

        return rowView;
    }
}
