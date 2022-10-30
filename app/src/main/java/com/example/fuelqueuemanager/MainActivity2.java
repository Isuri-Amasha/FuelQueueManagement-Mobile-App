package com.example.fuelqueuemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import DBhelper.dbhelper;

//main activity2
public class MainActivity2 extends AppCompatActivity {

    Button login;
    EditText username, password;
    dbhelper mydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        login = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        mydb = new dbhelper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = username.getText().toString();
                String Password = password.getText().toString();

                if(userName.equals("") || Password.equals("")){

                    Toast.makeText(getApplicationContext(), "Please Enter Credentials", Toast.LENGTH_LONG).show();

                }
                else {

                    Boolean credResult = mydb.checkCredentials(userName,Password);

                    if (credResult == true){

                        String UserRole = String.valueOf(mydb.getRole(userName));
                        String user = mydb.getUsername(userName);


                        Intent intent;
                        if (UserRole == "0"){

                            intent = new Intent(MainActivity2.this, VehicleOwnerProfile.class);
                            startActivity(intent);

                        }
                        else {

                            intent = new Intent(MainActivity2.this, StationProfile .class);
                            startActivity(intent);

                        }


                    }
                    else {

                        Toast.makeText(getApplicationContext(), "our Password Does Not Match", Toast.LENGTH_LONG).show();

                    }

                }


//                Toast.makeText(getApplicationContext(), "Successfully Logged In", Toast.LENGTH_LONG).show();
//
//                Intent intent = new Intent(MainActivity2.this, StationProfile.class);
//                startActivity(intent);
            }
        });
    }
}