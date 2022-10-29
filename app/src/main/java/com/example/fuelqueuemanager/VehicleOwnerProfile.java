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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.BatchUpdateException;
import java.util.List;

import Model.Owner;
import Model.Station;

public class VehicleOwnerProfile extends AppCompatActivity {

    Button editVehicleOwner;
    EditText vehicleOwnerName, vehicleNo, fuelTy;
    TextView oId;
    List<Station> stationList;
    List<Owner> ownerList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTheme(R.style.Theme_FuelQueueManager);
        setContentView(R.layout.activity_vehicle_owner_profile);

        //getting the value of role from the main screen
        Intent result = getIntent();
        String username = result.getStringExtra("user");
        System.out.println("Retrieved User Name Is"+username);

        editVehicleOwner = (Button) findViewById(R.id.editVehicleOwner);
        vehicleOwnerName = (EditText) findViewById(R.id.vehicleOwnerName);
        vehicleNo = (EditText) findViewById(R.id.vehicleNo);
        fuelTy = (EditText) findViewById(R.id.fuelTy);

        System.out.println("I don't know what to do now"+fuelTy);
        oId = (TextView) findViewById(R.id.oId);

        //calling the get profile method
        getProfile(username);


        editVehicleOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //calling the edit profile method
                editProfile(username);


            }
        });

    }

    //method to display the profile details
    public void getProfile(String username) {

        String getUrl = "https://fuelmanagementsystem.azurewebsites.net/VehicleOwner/GetVehicleOwnerByUsername?username=" +username;
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

                            try {

                                Owner owner = new Owner();
                                System.out.println("Successfully Retrieved at vehicle owner profile page getprofile()");

                                //getting the details
                                String id = response.getString("id");
                                String uName = response.getString("ownerName");
                                String uVNo = response.getString("vehicalNo");
                                String fType = response.getString("fuelType");

                                System.out.println("Vehicle Owner Id is "+id);

                                //setting details
                                oId.setText(id);
                                vehicleOwnerName.setText(uName);
                                vehicleNo.setText(uVNo);
                                fuelTy.setText(fType);


                                System.out.println("Retrieved USerName IS"+fType);


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


    //method to edit user profile
    public void editProfile(String username) {

        //assign the values
        String ownerId = oId.getText().toString();
        String userName = username;
        String Name = vehicleOwnerName.getText().toString();
        String vNo = vehicleNo.getText().toString();
        String fuelT = fuelTy.getText().toString();

        System.out.println("Displaying fuel type is"+fuelTy);
        System.out.println("Retrieveing fuel type is"+fuelT);


        //if any input fields are empty
        if (Name.equals("") || vNo.equals("") || fuelT.equals("")) {

            //display an error toast message
            Toast.makeText(getApplicationContext(), "Please Fill All the Fields", Toast.LENGTH_SHORT).show();

        } else {

            //insert the data
            String postURL = "https://fuelmanagementsystem.azurewebsites.net/VehicleOwner/UpdateVehicalOwner";
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject postData = new JSONObject();

            try {


                postData.put("username",userName);
                postData.put("ownerName", Name);
                postData.put("id", ownerId);
                postData.put("vehicalNo", vNo);
                postData.put("fuelType", fuelT);



                System.out.println("Editing data set is"+postData);

            } catch (Exception e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.PUT, postURL, postData, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println("----------------response------------------" + response);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            error.printStackTrace();
                            System.out.println("response" + error.toString());

                        }
                    });


            //add to the requestQueue
            requestQueue.add(jsonObjectRequest);

            //display a success toast
            Toast.makeText(getApplicationContext(), "Successfully Updated the Vehicle Owner Profile", Toast.LENGTH_SHORT).show();


        }
    }
}