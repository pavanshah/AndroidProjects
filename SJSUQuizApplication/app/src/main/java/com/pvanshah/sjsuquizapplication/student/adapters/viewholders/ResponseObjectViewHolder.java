package com.pvanshah.sjsuquizapplication.student.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pvanshah.sjsuquizapplication.R;

/**
 * Created by avinash on 9/17/17.
 */

public class ResponseObjectViewHolder extends RecyclerView.ViewHolder{

    private TextView titleTxt;
    private TextView scoreTxt;
    private View itemView;

    public ResponseObjectViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        titleTxt = (TextView) itemView.findViewById(R.id.quiz_name);
        scoreTxt = (TextView) itemView.findViewById(R.id.quiz_score);
    }

    public View getRootView() {
        return itemView;
    }

    public TextView getTitleTxt() {
        return titleTxt;
    }
    public TextView getScoreTxt() { return scoreTxt; }
}
