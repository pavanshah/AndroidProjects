package com.pvanshah.sjsuquizapplication.student.adapters.viewholders;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.widget.TextView;

import com.pvanshah.sjsuquizapplication.R;

/**
 * Created by avinash on 7/17/17.
 */

public class QuizViewHolder extends ViewHolder {

    private TextView titleTxt;
    private View itemView;

    public QuizViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        titleTxt = (TextView) itemView.findViewById(R.id.txt_title);
    }

    public View getRootView() {
        return itemView;
    }

    public TextView getTitleTxt() {
        return titleTxt;
    }
}
