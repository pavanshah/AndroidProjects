package com.pvanshah.sjsuquizapplication;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pavan Shah on 7/31/2017.
 */

@IgnoreExtraProperties
public class QuizResultDetails {

        private String name;
        private String quizName;
        private String quizID;
        private  String total;
        private String email;
        private String max;

        public QuizResultDetails(){

        }

        public QuizResultDetails(String quizTitle, String quizScore) {
            this.quizName = quizTitle;
            this.total = quizScore;
        }

        public String getQuizName() {
            return quizName;
        }

        public void setQuizName(String quizName) {
            this.quizName = quizName;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getQuizID() {
            return quizID;
        }

        public void setQuizID(String quizID) {
            this.quizID = quizID;
        }
}
