package com.pvanshah.sjsuquizapplication.student.model;

public class Response {
    private String questionNumber;

    private String correct;

    private String attempted;

    public String getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(String questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getAttempted() {
        return attempted;
    }

    public void setAttempted(String attempted) {
        this.attempted = attempted;
    }

    @Override
    public String toString() {
        return "ClassPojo [questionNumber = " + questionNumber + ", correct = " + correct + ", attempted = " + attempted + "]";
    }
}