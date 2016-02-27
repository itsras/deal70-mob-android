package com.deal70.mapp.deal70;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.deal70.mapp.deal70.util.ConnectionUtil;

public class SpalshScreenActivity extends Activity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    private boolean processingNetworkSettings = false;

    // Connection util
    private ConnectionUtil connectionUtil = null;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);

        // creating connection detector class instance
        connectionUtil = new ConnectionUtil(getApplicationContext());

        ImageView backgroundImg = (ImageView) findViewById(R.id.iv_logo);
        backgroundImg.setBackgroundResource(R.mipmap.ic_logo);

        startAnimations();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Check for the Internet connection
                Boolean isInternetPresent = connectionUtil.isConnectingToInternet();

                // Start your app main activity
                if (isInternetPresent) {
                    Intent intent = new Intent(SpalshScreenActivity.this, WebViewActivity.class);
                    startActivity(intent);
                } else {
                    // show a message
                    openAlert();
                }

                // close this activity
                //finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        // check for the flags
        if(processingNetworkSettings) {
            // might returned from the Network settings
            // try to call the netork dialog again
            Boolean isInternetPresent = connectionUtil.isConnectingToInternet();

            // Start your app main activity
            if (isInternetPresent) {
                Intent intent = new Intent(SpalshScreenActivity.this, WebViewActivity.class);
                startActivity(intent);
            } else {
                // show a message
                openAlert();
            }
        } else {
            SpalshScreenActivity.this.finish();
        }
    }

    private void startAnimations() {
        // Animation 01
        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.fadein);
        anim1.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.activity_anim);
        l.clearAnimation();
        l.startAnimation(anim1);

        // Animation 02
        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.fadein);
        anim2.reset();
        ImageView iv = (ImageView) findViewById(R.id.iv_logo);
        iv.clearAnimation();
        iv.startAnimation(anim2);

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private void openAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SpalshScreenActivity.this);

        alertDialogBuilder.setTitle("Network Settings");
        alertDialogBuilder.setMessage("This Application requires Internet access. Do you want to configure the Network Settings?");

        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // go to a new activity of the app
                processingNetworkSettings = true;
                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            }
        });

        // set negative button: No message
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // cancel the alert box and put a Toast to the user
                dialog.cancel();
                Toast.makeText(getApplicationContext(), "This Application can't work without Internet",
                        Toast.LENGTH_LONG).show();
            }
        });

        // set neutral button: Exit the app message
        alertDialogBuilder.setNeutralButton("Exit Application",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                // exit the app and go to the HOME
                SpalshScreenActivity.this.finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }

}
