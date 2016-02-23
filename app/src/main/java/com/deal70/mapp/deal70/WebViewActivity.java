package com.deal70.mapp.deal70;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {

    private WebView webView;
    private ProgressDialog progressBar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading Deal70...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setIndeterminate(true);
        progressBar.setProgress(0);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new ChromeWebViewClient());
        webView.loadUrl("http://deal70.com");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // show you progress bar
                progressBar.setProgress(0);
                progressBar.show();

                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // hide your progress bar
                progressBar.setProgress(100);
                progressBar.hide();
                super.onPageFinished(view, url);
            }
        });
    }

    private class ChromeWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            WebViewActivity.this.progressBar.setProgress(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }

}
