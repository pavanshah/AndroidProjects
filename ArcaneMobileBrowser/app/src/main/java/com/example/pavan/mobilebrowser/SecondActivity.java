package com.example.pavan.mobilebrowser;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    //declaration
    String myurl, myurl1;
    EditText url1;
    WebView page;
    ImageView search1;
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //object references
        search1 = (ImageView) findViewById(R.id.search1);
        url1 = (EditText) findViewById(R.id.url1);
        page = (WebView) findViewById(R.id.webpage1);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        //Intent from main activity with URL
        Intent intent = getIntent();
        myurl = intent.getStringExtra("KEY");

        //Load URL
        url1.setText(myurl);
        page.loadUrl(myurl);
        page.clearCache(true);
        page.clearHistory();
        page.clearFormData();

        //Web settings
        WebSettings webSettings = page.getSettings();
        //webSettings.getCacheMode();
        //webSettings.getJavaScriptEnabled();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);   //to remove edittext focus


        page.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });


        search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processURL();
                url1.setText(myurl1);
                page.loadUrl(myurl1);
            }
        });

    }

    private void processURL() {
            myurl1 = url1.getText().toString();

            if (myurl1.matches("https://www.*.*"))     //check formatting
            {
            }

            else
            {
                if (myurl1.contains(" "))
                {
                    myurl1.replaceAll(" ", "+");
                }

                myurl1 = "https://www.google.co.in/search?q="+myurl1;
            }

    }


    @Override
    public void onBackPressed() {

        if(page.canGoBack())
        {
            page.goBack();
        }
        else
        {
            super.onBackPressed();
        }
    }

}
