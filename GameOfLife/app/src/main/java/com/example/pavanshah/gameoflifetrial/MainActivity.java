package com.example.pavanshah.gameoflifetrial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MyGridView pixelGrid = (MyGridView) findViewById(R.id.myGrid);
        Button generateNext = (Button) findViewById(R.id.next);
        Button resetGrid = (Button) findViewById(R.id.reset);

        generateNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pixelGrid.generateNext();
            }
        });

        resetGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pixelGrid.resetGrid();
            }
        });

    }

}
