package com.example.fuelqueuemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

//splash screen
public class SplashScreen extends AppCompatActivity {

    Animation topAnim,bottomAnim;
    ImageView splash;
    TextView topic;
    TextView statement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);


        //Hooks
        splash = findViewById(R.id.splash);
        topic = findViewById(R.id.topic);
        statement = findViewById(R.id.statement);

        splash.setAnimation(topAnim);
        topic.setAnimation(bottomAnim);
        statement.setAnimation(bottomAnim);


    }

}