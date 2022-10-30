package com.example.fuelqueuemanager;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import Model.Station;

//station details
public class StationDetails extends AppCompatActivity {

    TextView stationName,stationAddress,waitingTime,petrolAvailability,dieselAvailability,petrolVehiclesNum,dieselVehiclesNum,fueledVehicleLabel;
    Button checkin_btn_p,checkout_btn_p,checkin_btn_d,checkout_btn_d;
    EditText edtStationName,edtStationAddress,edtAvgTime,edtTvehicles;
    List<Station> stationList;
    Dialog dialogP;

    String BaseURL = "https://fuelmanagementsystem.azurewebsites.net/";

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_details);

        //getting the value of role from the recyclerview
        Intent result = getIntent();
        String stationUsername = result.getStringExtra("stationName");
        String ownerUsername = result.getStringExtra("user");
        String sId = result.getStringExtra("stationId");
        System.out.println("Name of the station user is"+stationUsername);
        System.out.println("Name of the Owner Id is"+ownerUsername);
        System.out.println("Station Id is"+sId);

        stationName = findViewById(R.id.stationName);
        stationAddress = findViewById(R.id.stationAddress);
        waitingTime = findViewById(R.id.waitingTime);
        petrolAvailability = findViewById(R.id.petrolAvailability);
        dieselAvailability = findViewById(R.id.dieselAvailability);
        petrolVehiclesNum = findViewById(R.id.petrolVehiclesNum);
        fueledVehicleLabel = findViewById(R.id.fueledVehicleLabel);
        edtTvehicles = findViewById(R.id.edtTvehicles);
        dieselVehiclesNum = findViewById(R.id.dieselVehiclesNum);
        checkin_btn_p = findViewById(R.id.checkin_btn_p);
        checkin_btn_d = findViewById(R.id.checkin_btn_d);
        checkout_btn_p = findViewById(R.id.checkout_btn_p);
        checkout_btn_d = findViewById(R.id.checkout_btn_d);
        edtStationName = findViewById(R.id.edtStationName);
        edtStationAddress = findViewById(R.id.edtStationAddress);
        edtAvgTime = findViewById(R.id.edtAvgTime);

        getAvailabilityDetails(stationUsername);
        getStationDetails(stationUsername);

        dialogP = new Dialog(StationDetails.this);

        dialogP.setContentView(R.layout.layout_dialog_p);
        dialogP.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_background));
        dialogP.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //Add animations to the dialog box
        dialogP.setCancelable(false);
        dialogP.getWindow().getAttributes().windowAnimations = R.style.animation_dialog;

        Button yesP = dialogP.findViewById(R.id.btnYesP);
        Button NoP = dialogP.findViewById(R.id.btnNoP);



        yesP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fType = "Petrol";
                checkOut(sId,ownerUsername,fType);
                dialogP.dismiss();
            }
        });

        NoP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String fType = "Petrol";
                checkOut(sId,ownerUsername,fType);
                dialogP.dismiss();
            }
        });

        checkin_btn_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fType = "Petrol";
                checkIn(sId,ownerUsername,fType);

            }
        });

        checkout_btn_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fType = "Petrol";
                    dialogP.show();
            }
        });

        checkin_btn_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fType = "Diesel";
               checkIn(sId,ownerUsername,fType);

            }
        });

        checkout_btn_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogP.show();
            }
        });

    }

    //method to get the availability details
    public void getAvailabilityDetails(String stationUsername){

        String getUrl = "https://fuelmanagementsystem.azurewebsites.net/FuelStation/GetAvailabillityByUserName?username="+stationUsername;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                getUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                        System.out.println("----------------response get stationNAme by id------------------"+response);

                            try {

                                System.out.println("Successfully Retrieved");


                                Station station = new Station();

                                //getting the details
                                String id = response.getString("id");
                                Double avgTime = response.getDouble("avarageTimeInQueue");
                                String tVehicles = response.getString("totalNumberOfVehicalsGotFuel");
                                Boolean pAvailable = response.getBoolean("isPetrolAvailable");
                                Boolean dAvailable = response.getBoolean("isDeselAvailable");
                                String pVehicle = response.getString("numberOfPetrolVehicalsInQueue");
                                String dVehicle = response.getString("numberOfDeselVehicalsInQueue");

                                String avtime = String.valueOf(avgTime);
                                String sId = id;

                                //setting details
                                edtAvgTime.setText(avtime+"hrs");
                                edtTvehicles.setText(tVehicles);

                                if (pAvailable == true) {
                                    petrolAvailability.setText("Yes");
                                }else{
                                    petrolAvailability.setText("No");
                                }

                                if (dAvailable == true) {
                                    dieselAvailability.setText("Yes");
                                }else{
                                    dieselAvailability.setText("No");
                                }

                                petrolVehiclesNum.setText(pVehicle);
                                dieselVehiclesNum.setText(dVehicle);


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
    //method to get the station details
    public void getStationDetails(String stationUsername){

        String getUrl = "https://fuelmanagementsystem.azurewebsites.net/FuelStation/GetStationByUserName?username="+stationUsername;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                getUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                        System.out.println("----------------response get stationNAme by id------------------"+response);

                        //Retrieve each response object and add it to the ArrayList

                        try {

                            System.out.println("Successfully Retrieved");


                            Station station = new Station();

                            //getting the details
                            String sName = response.getString("stationName");
                            String sAddress = response.getString("address");

                            //setting details
                            edtStationName.setText(sName);
                            edtStationAddress.setText(sAddress);


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

    //method to change checking status
    public void checkIn(String sId,String ownerUsername,String fType){

            String stationId = sId;
            String vOwnerId = ownerUsername;
            String fuelType = fType;


            //insert the data
            String postURL = "https://fuelmanagementsystem.azurewebsites.net/Queue/CheckIn";
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject postData = new JSONObject();

            try{
                postData.put("StationId",stationId);
                postData.put("vehicalOwnerId",vOwnerId);
                postData.put("FuelType",fuelType);

                System.out.println("Checkin details are"+postData);


            }catch(Exception e){
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, postURL, postData, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println("----------------response------------------"+response);

                            //display success message
                            Toast.makeText(getApplicationContext(), "Successfully Checked In To A Queue", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            error.printStackTrace();
                            System.out.println("response"+error.toString());

                        }
                    });


            //add to the requestQueue
            requestQueue.add(jsonObjectRequest);


    }

    //method to change checkout status
    public void checkOut(String sId,String ownerUsername,String fType){

        String stationId = sId;
        String vOwnerId = ownerUsername;
        String fuelType = fType;


        //insert the data
        String postURL = "https://fuelmanagementsystem.azurewebsites.net/Queue/CheckOut";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject postData = new JSONObject();

        try{
            postData.put("StationId",stationId);
            postData.put("vehicalOwnerId",vOwnerId);
            postData.put("FuelType",fuelType);

            System.out.println("Checkin details are"+postData);


        }catch(Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, postURL, postData, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("----------------response------------------"+response);

                        //display success message
                        Toast.makeText(getApplicationContext(), "Successfully Checked Out Of A Queue", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        error.printStackTrace();
                        System.out.println("response"+error.toString());

                    }
                });


        //add to the requestQueue
        requestQueue.add(jsonObjectRequest);

    }

}