package com.example.third;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private BackKeyHelper backKeyHelper = new BackKeyHelper(this);
    private WebView webView;
    private Button[] buttons = new Button[4];
    private final String url = "https://guava-cse.tk/";

    @SuppressLint("SetJavaScriptEnabled")
    void loadWebview(){
        webView.setWebViewClient(new WebViewClient());

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportMultipleWindows(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDomStorageEnabled(true);

        webView.loadUrl(url);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        buttons[0] = findViewById(R.id.btn00);
        buttons[1] = findViewById(R.id.btn01);
        buttons[2] = findViewById(R.id.btn02);
        buttons[3] = findViewById(R.id.btn03);

        loadWebview();

        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "이미 웹뷰 탭 입니다.",Toast.LENGTH_SHORT).show();
            }
        });
        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Tab1Activity.class);
                finish();
                startActivity(intent);
            }
        });
        buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Tab2Activity.class);
                finish();
                startActivity(intent);
            }
        });
        buttons[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Tab3Activity.class);
                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        if(webView.getOriginalUrl().equalsIgnoreCase(url)){
            backKeyHelper.onBackPressed();
        } else if(webView.canGoBack()){
            webView.goBack();
        } else {
            backKeyHelper.onBackPressed();
        }
    }

}