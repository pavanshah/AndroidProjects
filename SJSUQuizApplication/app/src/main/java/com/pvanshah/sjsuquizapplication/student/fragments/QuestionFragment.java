package com.pvanshah.sjsuquizapplication.student.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.pvanshah.sjsuquizapplication.R;
import com.pvanshah.sjsuquizapplication.student.activities.QuizActivity;
import com.pvanshah.sjsuquizapplication.student.model.Question;
import com.pvanshah.sjsuquizapplication.student.model.Response;
import com.pvanshah.sjsuquizapplication.student.util.Numerics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by avinash on 7/17/17.
 */

public class QuestionFragment extends Fragment {

    public static final String QUESTION = "question";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Question question = (Question) getArguments().getSerializable(QUESTION);
        TextView questionTxt = view.findViewById(R.id.question);
        questionTxt.setText(question.getQuestion());
        RadioGroup optionsGroup = view.findViewById(R.id.options_group);
        List<String> options = new ArrayList<>();
        options.add(question.getOptions().getA());
        options.add(question.getOptions().getB());
        options.add(question.getOptions().getC());
        options.add(question.getOptions().getD());
        final int len = options.size();
        final RadioButton[] rb = new RadioButton[len];
        for (int i = 0; i < len; i++) {
            rb[i] = new RadioButton(getActivity());
            setStyleAndAddToGroup(optionsGroup, rb[i], options.get(i));
            rb[i].setChecked(false);
        }
        optionsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                for (int j = 0; j < len; j++) {
                    if (rb[j].getId() == i) {
                        List<Response> responses = ((QuizActivity) getActivity()).responseList;
                        if (responses.size() > 0) {
                            for (int k = 0; k < responses.size(); k++) {
                                if (responses.get(k).getQuestionNumber().equals(question.getQuestionNumber())) {
                                    responses.get(k).setAttempted(getCharForNumber(j+1));
                                    return;
                                }
                            }
                            Response response = new Response();
                            response.setQuestionNumber(question.getQuestionNumber());
                            response.setCorrect(question.getAnswer());
                            response.setAttempted(getCharForNumber(j+1));
                            ((QuizActivity) getActivity()).responseList.add(response);
                        } else {
                            Response response = new Response();
                            response.setQuestionNumber(question.getQuestionNumber());
                            response.setCorrect(question.getAnswer());
                            response.setAttempted(getCharForNumber(j+1));
                            ((QuizActivity) getActivity()).responseList.add(response);
                        }
                    }
                }
            }
        });
    }

    private String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
    }

    private void setStyleAndAddToGroup(RadioGroup optionsGroup, RadioButton rb, String text) {
        optionsGroup.addView(rb);
        rb.setText(text);
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(getActivity(), null);
        params.setMargins(Numerics.TEN, Numerics.TEN, Numerics.TEN, Numerics.TEN);
        rb.setLayoutParams(params);
        rb.setTextAppearance(getActivity(), android.R.style.TextAppearance_Medium);
        rb.setPadding(0, 0, 0, 0);
        rb.setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
    }
}
