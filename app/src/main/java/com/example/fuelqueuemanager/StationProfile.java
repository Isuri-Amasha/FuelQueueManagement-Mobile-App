package com.example.fuelqueuemanager;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import DBhelper.dbhelper;
import Model.Owner;
import Model.Station;

public class StationProfile extends AppCompatActivity {


    Button editStation;
    EditText stationsName,stationsAddress,stationsEmail,stationsPassword;
    dbhelper mydb;
    TextView stationsid;
    String BaseURL = "https://fuelmanagementsystem.azurewebsites.net/";
    List<Station> stationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_station_profile);

        //getting the values from the previous page
        Intent result = getIntent();
        String username = result.getStringExtra("user");
        System.out.println("Retrieved User Name Is"+username);

        editStation = (Button) findViewById(R.id.editStation);
        stationsName = (EditText) findViewById(R.id.stationsName);
        stationsAddress = (EditText) findViewById(R.id.stationsAddress);
        stationsEmail = (EditText) findViewById(R.id.stationsEmail);
        stationsid = (TextView) findViewById(R.id.stationsid);


        mydb = new dbhelper(this);


        getProfile(username);



        //on click method for the editstation button
        editStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitForm(view);

            }
        });
    }
    public void getProfile(String username) {

        String getUrl = "https://fuelmanagementsystem.azurewebsites.net/FuelStation/GetStationByUsername?username=" + username;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                getUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                        System.out.println("----------------response get user by id------------------"+response);

                        //Retrieve each response object and add it to the ArrayList

                        try {

                            Owner owner = new Owner();
                            System.out.println("Successfully Retrieved at station owner profile page getprofile()");

                            //getting the details
                            String sid = response.getString("id");
                            String uName = response.getString("username");
                            String uVNo = response.getString("stationName");
                            String fType = response.getString("address");

                            //setting details
                            stationsid.setText(sid);
                            stationsEmail.setText(uName);
                            stationsName.setText(uVNo);
                            stationsAddress.setText(fType);


                            System.out.println("Retrieved USerName IS"+stationsName);


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

    //submit form method to edit details
    public void submitForm(View view){


        String sId = stationsid.getText().toString();
        String sName = stationsName.getText().toString();
        String sAddress = stationsAddress.getText().toString();
        String sEmail = stationsEmail.getText().toString();

        //if there is any empty fields
        if(sName.equals("") || sAddress.equals("") || sEmail.equals("") ){

            //display an error message
            Toast.makeText(getApplicationContext(), "Please Don't Leave Any Fields Empty", Toast.LENGTH_SHORT).show();
        }
        else {

                    String postURL = BaseURL+"FuelStation/UpdateStation";
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    JSONObject postData = new JSONObject();

                    try{

                        postData.put("stationName",sName);
                        postData.put("address",sAddress);
                        postData.put("username",sEmail);
                        postData.put("id", sId);

                        System.out.println("Updating data"+postData);

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.PUT, postURL, postData, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    System.out.println("----------------response------------------"+response);
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO: Handle error
                                    error.printStackTrace();
                                    System.out.println("response"+error.toString());

                                }
                            });


                    //add to the request queue
                    requestQueue.add(jsonObjectRequest);
            //display message
            Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_LONG).show();


        }

    }


}