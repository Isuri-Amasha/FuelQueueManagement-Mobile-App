package com.example.fuelqueuemanager;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Model.Owner;
import Model.Station;

//vehicle owner main
public class VehicleOwnerMain extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Station> stationList;
    List<Owner> ownerList;
    Button viewUserProfile;
    EditText vehicleOwnerName,vehicleNo,fuelType;

    String getURL = "https://fuelmanagementsystem.azurewebsites.net/FuelStation/GetAllStations";
    Adapter adapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_owner_main);

        viewUserProfile = (Button) findViewById(R.id.viewUserProfile);

        recyclerView = findViewById(R.id.stationRecyclerView);
        stationList = new ArrayList<>();

        //getting the values from the previous screen
        Intent result = getIntent();
        //getting the username to send to the user details page
        String username = result.getStringExtra("user");
        //getting the owner id to send to the station details page
        String ownerid = result.getStringExtra("owneId");

        System.out.println("Retrieved User Name Is" + username);
        System.out.println("Retrieved Owner Id Is" + ownerid);

        //view user profile button on click method
        viewUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(VehicleOwnerMain.this, VehicleOwnerProfile.class);
                intent.putExtra("user",username);
                startActivity(intent);
            }
        });

        //calling method to display the station details
        extractStation(ownerid);

    }

    //method to display the station details
    private void extractStation(String ownerId){

        String oUsername = ownerId;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                getURL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(TAG, response.toString());

                        //Retrieve each response object and add it to the ArrayList
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                System.out.println("Successfully Retrieved");
                                JSONObject stationObject = response.getJSONObject(i);

                                Station station = new Station();

                                //getting the details
                                station.setStationName(stationObject.getString("stationName"));
                                station.setAddress(stationObject.getString("address"));
                                station.setStationUsername(stationObject.getString("username"));
                                station.setId(stationObject.getString("id"));
                                station.setoUsername(oUsername);
                                //Adding it to the array
                                stationList.add(station);


//                                String stationName = station.getStationName();
//
//                                System.out.println(product.getProductName());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        adapter = new Adapter(getApplicationContext(),stationList);
                        recyclerView.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage());
                    }
                }

        );

        // Add the request to the RequestQueue
//        ApiService.getInstance(mContext).addToRequestQueue(jsonArrayRequest);
        //add to the requestQueue
        requestQueue.add(jsonArrayRequest);

    }


}