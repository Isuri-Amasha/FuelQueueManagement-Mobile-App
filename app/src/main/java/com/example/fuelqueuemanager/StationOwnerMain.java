package com.example.fuelqueuemanager;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Model.Station;

public class StationOwnerMain extends AppCompatActivity {

    Button petrol_arrive_btn,petrol_finish_btn,diesel_arrive_btn,diesel_finish_btn,viewUserProfile;
    TextView petrolVehicles,dieselVehicles;
    String BaseURL = "https://fuelmanagementsystem.azurewebsites.net/";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_station_owner_main);

        petrol_arrive_btn = (Button) findViewById(R.id.petrol_arrive_btn);
        petrol_finish_btn = (Button) findViewById(R.id.petrol_finish_btn);
        diesel_arrive_btn = (Button) findViewById(R.id.diesel_arrive_btn);
        diesel_finish_btn = (Button) findViewById(R.id.diesel_finish_btn);
        viewUserProfile = (Button) findViewById(R.id.viewUserProfile);

        //getting the value of role from the Login screen
        Intent result = getIntent();
        String username = result.getStringExtra("user");
        System.out.println("Username retrieved by the station main page is"+username);


        //on click method for view user profile button
        viewUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //move to the station profile
                Intent intent = new Intent(StationOwnerMain.this, StationProfile.class);
                intent.putExtra("user",username);
                startActivity(intent);
            }
        });


//        on click method for petrol button
        petrol_arrive_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fuelType = "Petrol";
                Boolean fuelArrived = true;
                Boolean fuelFinished = false;
                changeFuelStatus(username,fuelType,fuelArrived,fuelFinished);
            }
        });

        //        on click method for petrol button
        petrol_finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fuelType = "Petrol";
                Boolean fuelArrived = false;
                Boolean fuelFinished = true;
                changeFuelStatus(username,fuelType,fuelArrived,fuelFinished);

            }
        });
        //        on click method for petrol button
        diesel_arrive_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fuelType = "Desel";
                Boolean fuelArrived = true;
                Boolean fuelFinished = false;
                changeFuelStatus(username,fuelType,fuelArrived,fuelFinished);

            }
        });
        //        on click method for petrol button
        diesel_finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fuelType = "Desel";
                Boolean fuelArrived = false;
                Boolean fuelFinished = true;
                changeFuelStatus(username,fuelType,fuelArrived,fuelFinished);

            }
        });



    }

    //Method to change the fuel status
    public void changeFuelStatus(String username,String fuelType,Boolean fuelArrived, Boolean fuelFinished){


        //if any input fields are empty
        if( username.equals("") || fuelType.equals("") || fuelArrived.equals("") || fuelFinished.equals("")){

            //display an error toast message
            Toast.makeText(getApplicationContext(), "Please Fill All the Fields", Toast.LENGTH_SHORT).show();
        }
        else {


                    //insert the data
                    String postURL = BaseURL+"/FuelStation/UpdateFuelStatus";
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    JSONObject postData = new JSONObject();

                    try{

                        postData.put("stationName",username);
                        postData.put("fuelType",fuelType);
                        postData.put("fuelArrived",fuelArrived);
                        postData.put("fuelFinished",fuelFinished);

                        System.out.println("Fuel Status"+postData);


                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.PUT, postURL, postData, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    System.out.println("----------------response------------------"+response);
                                    //display a success toast
                                    Toast.makeText(getApplicationContext(), "Fuel Status Successfully Updated", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(StationOwnerMain.this,StationDetails.class);
//                                    startActivity(intent);

                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO: Handle error
                                    error.printStackTrace();
                                    System.out.println("response"+error.toString());

                                }
                            });


                    requestQueue.add(jsonObjectRequest);

                }

            }




}