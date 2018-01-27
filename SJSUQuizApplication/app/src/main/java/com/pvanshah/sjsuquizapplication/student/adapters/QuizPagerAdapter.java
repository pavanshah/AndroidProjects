package com.pvanshah.sjsuquizapplication.student.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pvanshah.sjsuquizapplication.student.fragments.QuestionFragment;
import com.pvanshah.sjsuquizapplication.student.model.Question;

import java.util.List;

/**
 * Created by avinash on 7/17/17.
 */

public class QuizPagerAdapter extends FragmentPagerAdapter {

    private final List<Question> questions;

    public QuizPagerAdapter(FragmentManager fm, List<Question> questions) {
        super(fm);
        this.questions = questions;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(QuestionFragment.QUESTION, questions.get(position));
        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return questions.size();
    }
}
