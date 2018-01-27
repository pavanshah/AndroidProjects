package com.pvanshah.sjsuquizapplication.firebaseutils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by avinash on 7/02/2017.
 */

public class FirebaseConfiguration {

    private static FirebaseDatabase firebaseDatabase;
    private static DatabaseReference databaseRoot;
    private static DatabaseReference quizData;
    private static DatabaseReference studentData;
    private static DatabaseReference questionsData;
    private static DatabaseReference applicationUsers;
    private static DatabaseReference resultRef;
    private static DatabaseReference userRef;
    private static FirebaseAuth mAuth;

    public static DatabaseReference getUserRef() {
        return userRef;
    }

    public void configureFirebase(){
        //Firebase configuration with all elements
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseRoot = firebaseDatabase.getReference();
        quizData = databaseRoot.child("Quizzes");
        studentData = databaseRoot.child("users");
        questionsData = databaseRoot.child("Questions");
        applicationUsers = databaseRoot.child("ApplicationUsers");
        resultRef = databaseRoot.child("result");
        userRef = databaseRoot.child("users");
        mAuth = FirebaseAuth.getInstance();
    }

    public static DatabaseReference getStudentData() {
        return studentData;
    }

    public static void setStudentData(DatabaseReference studentData) {
        FirebaseConfiguration.studentData = studentData;
    }

    public static DatabaseReference getApplicationUsers() {
        return applicationUsers;
    }

    public static FirebaseDatabase getFirebaseDatabase() {
        return firebaseDatabase;
    }

    public static DatabaseReference getDatabaseRoot() {
        return databaseRoot;
    }

    public static DatabaseReference getQuizData() {
        return quizData;
    }

    public static DatabaseReference getQuestionsData() {
        return questionsData;
    }

    public static FirebaseAuth getmAuth() {return mAuth;}

    public static DatabaseReference getResultRef() {
        return resultRef;
    }
}
