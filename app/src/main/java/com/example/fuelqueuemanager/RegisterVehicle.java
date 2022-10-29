package com.example.fuelqueuemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import DBhelper.dbhelper;

public class RegisterVehicle extends AppCompatActivity {

    Button regVehicleButton;
    EditText regVehicleOwnerName,regVehicleNo,regVOwnerEmail,regVOwnerPassword,regVOwnerRePassword;
    String role;
    dbhelper mydb;
    TextView textView2;
    List<String> items;
    String item;
    Spinner spinnerFuelType;
    String[] name = {"Petrol","Diesel"};
    String BaseURL = "https://fuelmanagementsystem.azurewebsites.net/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_vehicle);

        regVehicleButton = (Button) findViewById(R.id.regVehicleButton);
        regVehicleOwnerName = (EditText) findViewById(R.id.regVehicleOwnerName);
        regVehicleNo = (EditText) findViewById(R.id.regVehicleNo);
        regVOwnerEmail = (EditText) findViewById(R.id.regVOwnerEmail);
        regVOwnerPassword = (EditText) findViewById(R.id.regVOwnerPassword);
        regVOwnerRePassword = (EditText) findViewById(R.id.regVOwnerRePassword);
        textView2 = (TextView) findViewById(R.id.textView2);
        spinnerFuelType = (Spinner) findViewById(R.id.spinnerFuelType);

        items = new ArrayList<>();

        items.add("Petrol");
        items.add("Desel");

        spinnerFuelType.setAdapter(new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,items));

        spinnerFuelType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                item = items.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //spannable string
        String text = "Already have an account? Login";

        SpannableString ss = new SpannableString(text);

        //spannable string onclick method on word 'Login'
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {

                //move to the Login Page
                Intent intent = new Intent(RegisterVehicle.this,LoginVehicle.class);
                startActivity(intent);

            }

        };

        ss.setSpan(clickableSpan,25,30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView2.setText(ss);
        textView2.setMovementMethod(LinkMovementMethod.getInstance());


        mydb = new dbhelper(this);

        //on click method for register button
        regVehicleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //calling method to submit the form
                submitForm(view);

            }
        });
    }


    //method to submit the form
    public void submitForm(View view){

        //assign the values
        String username = regVehicleOwnerName.getText().toString();
        String vehicleNo = regVehicleNo.getText().toString();
        String ownerEmail = regVOwnerEmail.getText().toString();
        String password = regVOwnerPassword.getText().toString();
        String repassword = regVOwnerRePassword.getText().toString();
        String fuelType = item;

        System.out.println("Fuel Type is" +item);

        //if any input fields are empty
        if(username.equals("") || vehicleNo.equals("") || ownerEmail.equals("") || password.equals("") || repassword.equals("") || fuelType.equals("")){

            //display an error toast message
            Toast.makeText(getApplicationContext(), "Please Fill All the Fields", Toast.LENGTH_SHORT).show();

        }
        else {

            //if password matches the re-entered password
            if(password.equals(repassword)){

                //call the to check for existing users
                Boolean userCheckResult = mydb.checkUserVehicle(ownerEmail);

                //if there are no existing users
                if(userCheckResult == false){

                    //insert the data
                    String postURL = "https://fuelmanagementsystem.azurewebsites.net/VehicleOwner/AddNewVehicalOwner";
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    JSONObject postData = new JSONObject();

                    try{
                        postData.put("username",ownerEmail);
                        postData.put("ownerName",username);

                        postData.put("fuelType",fuelType);
                        postData.put("vehicalNo",vehicleNo);
                        System.out.println("Object IS"+postData);


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


                    //add to the requestQueue
                    requestQueue.add(jsonObjectRequest);
                    //register the user
                    Boolean regResult = mydb.insertDataVehicle(ownerEmail,password);

                    //If users is successfully registered
                    if( regResult == true){

                        //display a success toast
                        Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                        //move to the Login Page
                        Intent intent = new Intent(RegisterVehicle.this, LoginVehicle.class);
                        startActivity(intent);
                    }
                    else {

                        //display and error message
                        Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();

                    }

                }
                else{

                    //display user already exists
                    Toast.makeText(getApplicationContext(), "User Already Exists", Toast.LENGTH_SHORT).show();

                }


            }
            else {

                //display password does not match
                Toast.makeText(getApplicationContext(), "Your Password Does Not Match", Toast.LENGTH_SHORT).show();

            }




        }

    }

}