package com.example.pavanshah.gameoflifetrial;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        final LandingActivity landingActivity = this;

        Button newGame = (Button) findViewById(R.id.newGame);
        Button gameRules = (Button) findViewById(R.id.gameRules);
        Button quitGame = (Button) findViewById(R.id.quitGame);


        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        gameRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();
                alertDialog.setTitle("Game Rules");
                alertDialog.setMessage(
                        "Conway's game of life is a cellular evolution game.\n" +
                        "It creates certain cellular patterns based on different rules.\n" +
                        "It works on following rules -\n" +
                        "1] Any live cell with fewer than two live neighbours dies, as if caused by underpopulation.\n" +
                        "2] Any live cell with two or three live neighbours lives on to the next generation.\n" +
                        "3] Any live cell with more than three live neighbours dies, as if by overpopulation.\n" +
                        "4] Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.\n"+
                                "Have Fun"
                );

                alertDialog.show();

            }
        });

        quitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    landingActivity.finishAffinity();
            }
        });

        /*
        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(LandingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }.start();
        */
    }
}
