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

import org.json.JSONObject;

import DBhelper.dbhelper;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//register station
public class RegisterStation extends AppCompatActivity {

    Button regStationButton;
    EditText regStationName,regStationAddress,regStationEmail,regStationPassword,regStationRePassword;
    dbhelper mydb;
    String role;
    TextView textView2;
    String BaseURL = "https://fuelmanagementsystem.azurewebsites.net/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_station);

        regStationButton = (Button) findViewById(R.id.regStationButton);
        regStationName = (EditText) findViewById(R.id.regStationName);
        regStationAddress = (EditText) findViewById(R.id.regStationAddress);
        regStationEmail = (EditText) findViewById(R.id.regStationEmail);
        regStationPassword = (EditText) findViewById(R.id.regStationPassword);
        regStationRePassword = (EditText) findViewById(R.id.regStationRePassword);
        textView2 = (TextView) findViewById(R.id.textView2);


        //spannable string
        String text = "Already have an account? Login";

        SpannableString ss = new SpannableString(text);

        //spannable string onclick method on word 'Login'
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {

                //move to the Login Page
                Intent intent = new Intent(RegisterStation.this,Login.class);
                startActivity(intent);

            }

        };

        ss.setSpan(clickableSpan,25,30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView2.setText(ss);
        textView2.setMovementMethod(LinkMovementMethod.getInstance());

        mydb = new dbhelper(this);

        //register button on click method
        regStationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //calling method to submit the form
                submitForm(view);

            }
        });
    }

    //submit registration form method
    public void submitForm(View view){

        //Assign values
        String username = regStationName.getText().toString();
        String stationAddress = regStationAddress.getText().toString();
        String stationEmail = regStationEmail.getText().toString();
        String password = regStationPassword.getText().toString();
        String repassword = regStationRePassword.getText().toString();


        //if any input fields are empty
        if(username.equals("") || stationAddress.equals("") || stationEmail.equals("") || password.equals("") || repassword.equals("")){

            //display an error toast message
            Toast.makeText(getApplicationContext(), "Please Fill All the Fields", Toast.LENGTH_SHORT).show();
        }
        else {

            //if password matches the re-entered password
            if(password.equals(repassword)){

                //call the to check for existing users
                Boolean userCheckResult = mydb.checkUser(stationEmail);

                //if there are no existing users
                if(userCheckResult == false){

                    //insert the data
                    String postURL = BaseURL+"/FuelStation/AddNewStation";
                    RequestQueue requestQueue = Volley.newRequestQueue(this);
                    JSONObject postData = new JSONObject();

                    try{

                        postData.put("stationName",username);
                        postData.put("address",stationAddress);
                        postData.put("username",stationEmail);


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

                    //register the user
                    Boolean regResult = mydb.insertData(stationEmail,password,role);
                    //add to the requestQueue
                    requestQueue.add(jsonObjectRequest);

                    //If users is successfully registered
                    if( regResult == true){

                        //display a success toast
                        Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                        //move to the Login Page
                        Intent intent = new Intent(RegisterStation.this, Login.class);
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

//
            }
            else {
                //display password does not match
                Toast.makeText(getApplicationContext(), "Your Password Does Not Match", Toast.LENGTH_SHORT).show();

            }


        }


    }


}