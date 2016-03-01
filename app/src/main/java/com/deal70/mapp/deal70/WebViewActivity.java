package com.deal70.mapp.deal70;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.deal70.mapp.deal70.util.ConnectionUtil;

public class WebViewActivity extends Activity {

    private WebView webView;
    private ProgressBar progressBar;
    private WebViewActivity _this = null;

    // Connection util
    private ConnectionUtil connectionUtil = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        // _this
        _this = this;

        // check for the Internet connectivity
        // creating connection detector class instance
        connectionUtil = new ConnectionUtil(getApplicationContext());

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(5);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new ChromeWebViewClient());

        webView.loadUrl("http://deal70.com");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setProgress(0);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // hide your progress bar
                progressBar.setProgress(100);
                //progressBar.hide();
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                checkInternet();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url != null && url.startsWith("whatsapp://")) {
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;

                } else if(url != null && url.startsWith("mailto:")) {
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;

                } else {
                    return false;
                }
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        checkInternet();
    }

    private class ChromeWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            WebViewActivity.this.progressBar.setProgress(newProgress);
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        //display in long period of time
                        Toast.makeText(getApplicationContext(), "No more back pages found. Keep browsing the deals...",
                                Toast.LENGTH_LONG).show();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    private void checkInternet() {
        if(connectionUtil.isConnectingToInternet()) {
            return;
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(_this);

        alertDialogBuilder.setTitle("Internet Settings");
        alertDialogBuilder.setMessage("This Application requires Internet." +
                " Do you want to configure the Internet Settings?");

        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // go to a new activity of the app
                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            }
        });

        // set negative button: No message
        alertDialogBuilder.setNeutralButton("Refresh", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // cancel the alert box and put a Toast to the user
                dialog.cancel();
                webView.loadUrl("http://deal70.com");
            }
        });

        // set neutral button: Exit the app message
        alertDialogBuilder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // exit the app and go to the HOME
                Intent intent = new Intent(getApplicationContext(), SpalshScreenActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }

}
