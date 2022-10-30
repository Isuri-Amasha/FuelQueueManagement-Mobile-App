package com.example.fuelqueuemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

//main loading page
public class RegMenu extends AppCompatActivity {

    Animation topAnim,bottomAnim;

    ImageView splash1;
    TextView topicFuel;
    TextView statementFuel;

    Button regVehicleBtn, regStationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reg_menu);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        splash1 = findViewById(R.id.splash1);
        topicFuel = findViewById(R.id.topicFuel);
        statementFuel = findViewById(R.id.statementFuel);

        //setting the animations
        splash1.setAnimation(topAnim);
        topicFuel.setAnimation(bottomAnim);
        statementFuel.setAnimation(bottomAnim);


        regVehicleBtn = (Button) findViewById(R.id.regVehicleBtn);
        regStationBtn = (Button) findViewById(R.id.regStationBtn);


        //continue as vehicle owner button onclick method
        regVehicleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //move to the register vehicle form
                Intent intent = new Intent(RegMenu.this, RegisterVehicle.class);
                startActivity(intent);
            }
        });
        //continue as station owner button onclick method
         regStationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //move to the register station form
                Intent intent = new Intent(RegMenu.this, RegisterStation.class);
                startActivity(intent);
            }
        });
}
}