package com.example.fuelqueuemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import DBhelper.dbhelper;

public class FuelAvailability extends AppCompatActivity {

    EditText fuelType,edtAmount;
    Button arriveBtn,finishBtn;
    dbhelper mydb;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_availability);

        fuelType = (EditText) findViewById(R.id.fuelType);
        edtAmount = (EditText) findViewById(R.id.edtAmount);
        arriveBtn = (Button) findViewById(R.id.arriveBtn);
        finishBtn = (Button) findViewById(R.id.finishBtn);


        mydb = new dbhelper(this);

        //getting the fuel type
        Intent result = getIntent();
        Integer btnCheck = result.getIntExtra("btn",0);
        String userName = result.getStringExtra("user");

//        //assign the fuel type
//        if(btnCheck == 1){
//
//            fuelType.setText("Diesel");
//
//        }else{
//
//            fuelType.setText("Petrol");
//        }

        //on click event for the arrive button
        arriveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //calling the submitdata method
                submitData(view);


            }
        });

        //on click event for the finish button
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //calling the submitdata method
                changeStatus();


            }
        });


    }

    //method to submit the data
    public void submitData(View view){

        String amount = edtAmount.getText().toString();
        String fuelStatus = "arrived";


        //if empty fields
        if(amount.equals("") || fuelStatus.equals("") ){

            //display error message
            Toast.makeText(getApplicationContext(), "Please Fill All the Fields", Toast.LENGTH_SHORT).show();
        }
        else {

                    String postURL = "https://fuelmanagementsystem.azurewebsites.net/FuelStation/updateStatus";
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    JSONObject postData = new JSONObject();

                    try{

                        postData.put("fuelAmount",amount);
                        postData.put("fuelStatus",fuelStatus);

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.POST, postURL, postData, new Response.Listener<JSONObject>() {

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
                //mpve to the station owner main page
//                    Intent intent = new Intent(Petrol.this, StationOwnerMain.class);
//                    startActivity(intent);


                }

            }

            //method to change the fuel status
            public void changeStatus(){

                String amount = "0";
                String fuelStatus = "finish";

                //if empty fields
                if(amount.equals("") || fuelStatus.equals("") ){

                    //display error message
                    Toast.makeText(getApplicationContext(), "Please Fill All the Fields", Toast.LENGTH_SHORT).show();
                }
                else {

                    String postURL = "https://fuelmanagementsystem.azurewebsites.net/FuelStation/updateStatus";
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    JSONObject postData = new JSONObject();

                    try{

                        postData.put("fuelAmount",amount);
                        postData.put("fuelStatus",fuelStatus);

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.POST, postURL, postData, new Response.Listener<JSONObject>() {

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
                    //mpve to the station owner main page
                    Intent intent = new Intent(FuelAvailability.this, StationOwnerMain.class);
                    startActivity(intent);


                }

            }


}