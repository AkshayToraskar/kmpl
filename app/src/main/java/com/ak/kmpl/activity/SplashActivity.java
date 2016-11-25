package com.ak.kmpl.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ak.kmpl.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


public class SplashActivity extends AppCompatActivity {

    public static int SPLASH_TIMEOUT = 2000;

    ImageView ivSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ivSplash=(ImageView)findViewById(R.id.ivSplash);

       // Glide.with(this).load(R.drawable.carsplash1).asGif().into(ivSplash);

        Glide.with(this).load(R.drawable.carsplash1).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivSplash);

        /*GifView gifView1 = (GifView) findViewById(R.id.gif1);


       // gifView1.pause();
        gifView1.setGifResource(R.drawable.carsplash1);
        gifView1.getGifResource();
        gifView1.play();*/

       // gifView1.setMovieTime(time);
        //gifView1.getMovie();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, WelcomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        }, SPLASH_TIMEOUT);
    }
}
