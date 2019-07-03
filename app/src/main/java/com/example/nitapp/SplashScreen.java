package com.example.nitapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    private ImageView imageViewSplashImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imageViewSplashImage = findViewById(R.id.splashImage);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splashtransition);
        imageViewSplashImage.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}

