package com.example.fuelqueuemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import DBhelper.dbhelper;

//login station
public class LoginStation extends AppCompatActivity {

    Button login;
    EditText username, password;
    dbhelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        mydb = new dbhelper(this);

        //login button onclick method
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //assign values
                String userName = username.getText().toString();
                String Password = password.getText().toString();

                //if any fields are empty
                if(userName.equals("") || Password.equals("")){

                    //display an error toast message
                    Toast.makeText(getApplicationContext(), "Please Enter Credentials", Toast.LENGTH_LONG).show();

                }
                else {

                    //check for the credentials
                    Boolean credResult = mydb.checkCredentials(userName,Password);

                    //if credentials match
                    if (credResult == true){


                        System.out.println("Username of the Station"+userName);

                        //Move to the station Main Page
                        Intent intent;
                        intent = new Intent(LoginStation.this, StationOwnerMain .class);
                        intent.putExtra("user",userName);
                        startActivity(intent);

                    }
                    else {

                        //display password does not match message
                        Toast.makeText(getApplicationContext(), "Your Password Does Not Match", Toast.LENGTH_LONG).show();

                    }

                }


            }
        });
    }

}