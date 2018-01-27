package com.pvanshah.sjsuquizapplication;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by pavan on 9/20/2017.
 */

@IgnoreExtraProperties
public class QuizMinMaxDetails {

        private String quizName;
        private String quizID;
        private String max;
        private String min;
        private String avg;
        private String totalScore;
        private String numberOfStudentsAttempted;

        public QuizMinMaxDetails(){

        }


    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getQuizID() {
        return quizID;
    }

    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getNumberOfStudentsAttempted() {
        return numberOfStudentsAttempted;
    }

    public void setNumberOfStudentsAttempted(String numberOfStudentsAttempted) {
        this.numberOfStudentsAttempted = numberOfStudentsAttempted;
    }
}
