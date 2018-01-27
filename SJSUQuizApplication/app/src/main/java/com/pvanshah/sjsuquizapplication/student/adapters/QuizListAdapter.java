package com.pvanshah.sjsuquizapplication.student.adapters;

import android.content.Context;
import android.view.View;

import com.pvanshah.sjsuquizapplication.R;
import com.pvanshah.sjsuquizapplication.student.adapters.viewholders.QuizViewHolder;
import com.pvanshah.sjsuquizapplication.student.base.AbstractBaseAdapter;
import com.pvanshah.sjsuquizapplication.student.model.Quiz;

/**
 * Created by avinash on 7/17/17.
 */

public class QuizListAdapter extends AbstractBaseAdapter<Quiz, QuizViewHolder> {

    private final Context context;

    public QuizListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_quiz;
    }

    @Override
    public QuizViewHolder newViewHolder(View view) {
        return new QuizViewHolder(view);
    }

    @Override
    public void bindView(QuizViewHolder holder, Quiz item) {
        holder.getTitleTxt().setText(item.getQuizTitle());
    }
}
