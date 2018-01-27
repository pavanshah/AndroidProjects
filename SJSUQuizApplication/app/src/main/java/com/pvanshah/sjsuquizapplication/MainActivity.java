package com.pvanshah.sjsuquizapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import com.pvanshah.sjsuquizapplication.firebaseutils.FirebaseConfiguration;
import com.pvanshah.sjsuquizapplication.student.activities.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //firebase configured
        FirebaseConfiguration firebaseConfiguration = new FirebaseConfiguration();
        firebaseConfiguration.configureFirebase();

        //Landing page
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {

                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                MainActivity.this.finish(); //to remove mainactivity from activity stack of back button
                startActivity(intent);

            }
        }.start();

    }
}
