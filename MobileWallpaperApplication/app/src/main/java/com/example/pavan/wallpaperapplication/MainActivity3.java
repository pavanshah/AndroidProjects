package com.example.pavan.wallpaperapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        WebView wv = (WebView) findViewById(R.id.webview);
        wv.loadUrl("https://www.google.co.in/search?q=mobile+hd+wallpapers&tbm=isch&tbo=u&source=univ&sa=X&ved=0ahUKEwjOgsniwbbNAhVKN48KHRbrC-cQsAQIHQ&biw=1366&bih=667");
        wv.setWebViewClient(new WebViewClient());
    }
}
