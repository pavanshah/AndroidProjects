package com.example.pavan.wallpaperapplication;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);


        ImageView image1 = (ImageView) findViewById(R.id.image1);

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
                int image1 = R.drawable.w1;
                intent.putExtra("KEY", image1);
                intent.setType(Intent.ACTION_DEFAULT);
                startActivity(intent);
            }
        });



        ImageView image2 = (ImageView) findViewById(R.id.image2);

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
                int image2 = R.drawable.w2;
                intent.putExtra("KEY", image2);
                intent.setType(Intent.ACTION_DEFAULT);
                startActivity(intent);
            }
        });



        ImageView image3 = (ImageView) findViewById(R.id.image3);

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
                int image3 = R.drawable.w3;
                intent.putExtra("KEY", image3);
                intent.setType(Intent.ACTION_DEFAULT);
                startActivity(intent);
            }
        });



        ImageView image4 = (ImageView) findViewById(R.id.image4);

        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
                int image4 = R.drawable.w4;
                intent.putExtra("KEY", image4);
                intent.setType(Intent.ACTION_DEFAULT);
                startActivity(intent);
            }
        });



        ImageView image5 = (ImageView) findViewById(R.id.image5);

        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
                int image5 = R.drawable.w5;
                intent.putExtra("KEY", image5);
                intent.setType(Intent.ACTION_DEFAULT);
                startActivity(intent);
            }
        });



        ImageView image6 = (ImageView) findViewById(R.id.image6);

        image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
                int image6 = R.drawable.w6;
                intent.putExtra("KEY", image6);
                intent.setType(Intent.ACTION_DEFAULT);
                startActivity(intent);
            }
        });



        ImageView image7 = (ImageView) findViewById(R.id.image7);

        image7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
                int image7 = R.drawable.w7;
                intent.putExtra("KEY", image7);
                intent.setType(Intent.ACTION_DEFAULT);
                startActivity(intent);
            }
        });



        ImageView image8 = (ImageView) findViewById(R.id.image8);

        image8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
                int image8 = R.drawable.w8;
                intent.putExtra("KEY", image8);
                intent.setType(Intent.ACTION_DEFAULT);
                startActivity(intent);
            }
        });



        ImageView image9 = (ImageView) findViewById(R.id.image9);

        image9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
                int image9 = R.drawable.w9;
                intent.putExtra("KEY", image9);
                intent.setType(Intent.ACTION_DEFAULT);
                startActivity(intent);
            }
        });



        ImageView image10 = (ImageView) findViewById(R.id.image10);

        image10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity1.this, MainActivity2.class);
                int image10 = R.drawable.w10;
                intent.putExtra("KEY", image10);
                intent.setType(Intent.ACTION_DEFAULT);
                startActivity(intent);
            }
        });


        Button google = (Button) findViewById(R.id.google);
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity1.this, MainActivity3.class);
                intent.setType(Intent.ACTION_DEFAULT);
                startActivity(intent);
            }
        });


    }
}
