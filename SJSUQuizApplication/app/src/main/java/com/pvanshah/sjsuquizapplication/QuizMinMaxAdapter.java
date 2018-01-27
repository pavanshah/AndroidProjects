package com.pvanshah.sjsuquizapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pavan on 9/20/2017.
 */

public class QuizMinMaxAdapter extends BaseAdapter {

    private static LayoutInflater inflater=null;
    ArrayList<QuizMinMaxDetails> resultList = new ArrayList<>();
    Context context;

    public QuizMinMaxAdapter(Context context) {
        this.context = context;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public QuizMinMaxAdapter(Context context,  ArrayList<QuizMinMaxDetails> arrayList) {
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
        TextView quizName;
        TextView quizMin;
        TextView quizMax;
        TextView quizAvg;
    }

    public void datasetchanged(ArrayList<QuizMinMaxDetails> arrayList) {
        resultList = arrayList;
        Log.d("Quiz", "Data set changed");
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final QuizMinMaxAdapter.Holder holder = new QuizMinMaxAdapter.Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.quizminmax_list, null);
        holder.quizName = (TextView) rowView.findViewById(R.id.quizName);
        holder.quizMin = (TextView) rowView.findViewById(R.id.quizMin);
        holder.quizMax = (TextView) rowView.findViewById(R.id.quizMax);

        final QuizMinMaxDetails quizResultDetails = resultList.get(position);

        holder.quizName.setText(quizResultDetails.getQuizName());
        holder.quizMax.setText(quizResultDetails.getMax());
        holder.quizMin.setText(quizResultDetails.getMin());
       // holder.quizAvg.setText(quizResultDetails.getAvg());

        return rowView;
    }

}
