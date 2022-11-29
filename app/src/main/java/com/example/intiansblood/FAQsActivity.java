package com.example.intiansblood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.widget.ImageButton;

public class FAQsActivity extends AppCompatActivity {
    WebView browser;
    Toolbar toolbar04;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);
        toolbar04=findViewById(R.id.toolbar04);
        imageButton=findViewById(R.id.iB01);

        browser=(WebView)findViewById(R.id.wV01);
        browser.setWebViewClient(new WebViewClient());
        browser.loadUrl("https://mysejahtera.moh.gov.my/en/blood-donation/faq");
        WebSettings webSettings=browser.getSettings();
        webSettings.setJavaScriptEnabled(true);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==android.R.id.home){finish();}
        return super.onOptionsItemSelected(item);
    }

    public class browser extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if (browser.canGoBack()){
            browser.goBack();
        }else{
        super.onBackPressed();}
    }
}