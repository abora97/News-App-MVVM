package com.example.newappmvvm.activity.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.newappmvvm.R;
import com.example.newappmvvm.activity.home.NewsActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // start countdown to start news activity
        timerStart();
        //set animation to imageView
        ImageView imageView = findViewById(R.id.imageView);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        imageView.startAnimation(animation);
    }
    /*

   When Thread is preferred:

   When you're doing a heavy work like network communication,
   or decoding large bitmap files, a new thread is preferred.
   If a lot of thread is needed, maybe ExecutorService is preferred further.


   When Handler is preferred:

   When you want to update UI objects (like TextView text) from other thread,
   it is necessary that UI objects could only be updated in UI Thread.
   Also, when you just want to run some light code later
   (like the delay for 300ms) you can use Handler because it's lighter and faster.

    */
    private void timerStart() {
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, NewsActivity.class));
            finish();
        }, 3000);
    }
}
