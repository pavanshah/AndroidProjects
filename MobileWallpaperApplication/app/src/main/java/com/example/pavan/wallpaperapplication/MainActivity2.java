package com.example.pavan.wallpaperapplication;

import android.app.WallpaperManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        setTitle("Preview");


        Intent intent = getIntent();
        final int path = intent.getIntExtra("KEY", 0);


        ImageView iv = (ImageView) findViewById(R.id.wallpaper);
        iv.setImageResource(path);


        Button finalbutton = (Button) findViewById(R.id.mainbutton);
        finalbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
                try {
                    myWallpaperManager.setResource(path);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(),"Wallpaper changed",Toast.LENGTH_LONG).show();

            }
        });

    }
}
