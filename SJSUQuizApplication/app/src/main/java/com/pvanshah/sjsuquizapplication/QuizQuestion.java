package com.pvanshah.sjsuquizapplication;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

/**
 * Created by Pavan Shah on 7/31/2017.
 */

@IgnoreExtraProperties
public class QuizQuestion {

    String quizID;
    int questionNumber;
    String question;
    HashMap<String, String> options;
    String answer;

    public QuizQuestion(String quizID, int questionNumber, String question, HashMap<String, String> options, String answer) {
        this.quizID = quizID;
        this.questionNumber = questionNumber;
        this.question = question;
        this.options = options;
        this.answer = answer;
    }

    public String getQuizID() {
        return quizID;
    }

    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public HashMap<String, String> getOptions() {
        return options;
    }

    public void setOptions(HashMap<String, String> options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
