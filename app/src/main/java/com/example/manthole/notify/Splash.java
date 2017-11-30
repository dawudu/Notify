package com.example.manthole.notify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {
    Animation animFadeIn, ZoomIn, Zoomin2, animBounceIn;
    private ImageView notifyImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        notifyImage = (ImageView) findViewById(R.id.notifyimage);

        BounceInMethod();
    }

    public void BounceInMethod() {

        animBounceIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce_animation);
        notifyImage.startAnimation(animBounceIn);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), Login.class));

            }
        }, 3000);

    }
}