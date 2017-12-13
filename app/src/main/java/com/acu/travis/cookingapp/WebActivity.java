package com.acu.travis.cookingapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {

    public String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        String url = getIntent().getStringExtra("href");
        query = getIntent().getStringExtra("query");
        Log.d("Web Results", "Got query: " + query);

        // Setup webview
        WebView webView = (WebView) findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        final Activity activity = this;
        webView.setWebViewClient(new WebViewClient() );
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        sendResult();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                sendResult();
                break;
        }
        return true;
    }

    public void sendResult() {
        // Setup result to be passed back
        Intent intent = new Intent();
        intent.putExtra("query", query);
        setResult(RESULT_OK, intent);
        finish();
    }
}
