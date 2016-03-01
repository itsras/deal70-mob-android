package com.deal70.mapp.deal70;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SpalshScreenActivity extends Activity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

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

        // Exit Handler
        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean("EXIT", false)) {
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }

        ImageView backgroundImg = (ImageView) findViewById(R.id.iv_logo);
        backgroundImg.setBackgroundResource(R.mipmap.ic_spalsh_screen);

        startAnimations();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {
                Intent intent = new Intent(SpalshScreenActivity.this, WebViewActivity.class);
                startActivity(intent);
            }
        }, SPLASH_TIME_OUT);


    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
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

}
