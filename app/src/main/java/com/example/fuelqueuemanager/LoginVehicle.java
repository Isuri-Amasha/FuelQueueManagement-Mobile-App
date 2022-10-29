package com.example.fuelqueuemanager;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import DBhelper.dbhelper;
import Model.Owner;

public class LoginVehicle extends AppCompatActivity {

    Button login;
    EditText username, password;
    dbhelper mydb;
//    TextView oId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_vehicle);

        login = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
//        oId = (TextView) findViewById(R.id.oId);

        mydb = new dbhelper(this);

        //login button onclick method
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //assign values
                String userName = username.getText().toString();
                String Password = password.getText().toString();

                //if any fields are empty
                if (userName.equals("") || Password.equals("")) {

                    //display an error toast message
                    Toast.makeText(getApplicationContext(), "Please Enter Credentials", Toast.LENGTH_LONG).show();

                } else {

                    //check for the credentials
                    Boolean credResult = mydb.checkCredentialsVehicle(userName, Password);


                    //if credentials match
                    if (credResult == true) {

;
                        System.out.println("Username" + userName);

                        //calling the function get vehicle Owner id
                        getVOwnerId(userName);
                        Intent intent;
                        intent = new Intent(LoginVehicle.this, VehicleOwnerMain .class);
                        intent.putExtra("user",userName);
                        startActivity(intent);


                    } else {

                        //display password does not match message
                        Toast.makeText(getApplicationContext(), "Your Password Does Not Match", Toast.LENGTH_LONG).show();

                    }

                }

            }
        });

    }

    public void getVOwnerId(String userName) {

        String getUrl = "https://fuelmanagementsystem.azurewebsites.net/VehicleOwner/GetVehicleOwnerByUsername?username=" + userName;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                getUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                        System.out.println("----------------response get user by id------------------" + response);

                        try {

                            Owner owner = new Owner();
                            System.out.println("Successfully Retrieved at vehicle owner profile page getprofile()");

                            //getting the details
                            String id = response.getString("id");

                            System.out.println("Vehicle Owner Id is " + id);


                            //move to the vehicle owner main landing page
                            Intent intent;
                            intent = new Intent(LoginVehicle.this, VehicleOwnerMain.class);
                            intent.putExtra("user", userName);
                            intent.putExtra("owneId", id);


                            System.out.println("Sending User Name Is" + userName);
                            System.out.println("Sending Owner Id Is" + id);
                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage());
                    }
                }

        );

        //add to the queue
        requestQueue.add(jsonObjectRequest);




    }
}