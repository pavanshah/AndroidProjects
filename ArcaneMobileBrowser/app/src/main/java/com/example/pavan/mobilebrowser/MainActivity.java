package com.example.pavan.mobilebrowser;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.SearchManager;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    EditText url;
    ImageView nextpage;
    String loadurl;
    ProgressBar progressBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextpage = (ImageView) findViewById(R.id.nextpage);
        url = (EditText) findViewById(R.id.url);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);   //to remove edittext focus


        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                processURL();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("KEY", loadurl);
                startActivity(intent);
            }
        });




    }

    private void processURL()
    {
        loadurl = url.getText().toString();

        if (loadurl.matches("https://www.*.*"))     //check formatting
        {}

        else
        {
           if (loadurl.contains(" "))
           {
               loadurl.replaceAll(" ", "+");
           }

            loadurl = "https://www.google.co.in/search?q="+loadurl;
        }

    }

}
