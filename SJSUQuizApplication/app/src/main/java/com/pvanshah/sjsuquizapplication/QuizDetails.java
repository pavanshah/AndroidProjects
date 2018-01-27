package com.pvanshah.sjsuquizapplication;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pavan Shah on 7/31/2017.
 */

public class QuizDetails {

    private String quizID;
    private String quizTitle;
    private  String quizStatus;

    public QuizDetails(){

    }

    public QuizDetails(String quizID, String quizTitle, String quizStatus) {
        this.quizID = quizID;
        this.quizTitle = quizTitle;
        this.quizStatus = quizStatus;
    }

    public String getQuizID() {
        return quizID;
    }

    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public String getQuizStatus() {
        return quizStatus;
    }

    public void setQuizStatus(String quizStatus) {
        this.quizStatus = quizStatus;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("quizID", quizID);
        result.put("quizTitle", quizTitle);
        result.put("quizStatus", quizStatus);
        return result;
    }
}
