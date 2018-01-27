package com.pvanshah.sjsuquizapplication.student.adapters;

import android.content.Context;
import android.view.View;

import com.pvanshah.sjsuquizapplication.R;
import com.pvanshah.sjsuquizapplication.student.adapters.viewholders.ResponseObjectViewHolder;
import com.pvanshah.sjsuquizapplication.student.base.AbstractBaseAdapter;
import com.pvanshah.sjsuquizapplication.student.model.ResponseObject;

/**
 * Created by avinash on 9/17/17.
 */

public class ScoreListAdapter extends AbstractBaseAdapter<ResponseObject, ResponseObjectViewHolder> {

    private final Context context;

    public ScoreListAdapter(Context context) {
        super(context);
        this.context = context;
    }
    @Override
    public int getLayoutId() {
        return R.layout.item_scores;
    }

    @Override
    public ResponseObjectViewHolder newViewHolder(View view) {
        return new ResponseObjectViewHolder(view);
    }

    @Override
    public void bindView(ResponseObjectViewHolder holder, ResponseObject item) {
        holder.getTitleTxt().setText(item.getQuizName());
        holder.getScoreTxt().setText(item.getTotal()+"/"+item.getMax());
    }
}
