package com.example.vefkhistkaosani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class Fullname extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullname);
        EditText mFullname = (EditText) findViewById(R.id.full_name);
        Button mButton = (Button) findViewById(R.id.button_submit_fullname);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Login.full_name = mFullname.getText().toString();
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(Fullname.this);
                //this is the url where you want to send the request
                //TODO: replace with your own url to send request, as I am using my own localhost for this tutorial
                String url = "https://vefxistyaosani.ge/iOS/config/fullnameEnter.php";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the response string.
                                System.out.println(response);


                                JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();

                                if(jsonObject.get("result").getAsString().equals("1")) {
                                    Login.code = jsonObject.get("ertjeradi").getAsString();
                                    Intent intent = new Intent(Fullname.this, Code.class);
                                    startActivity(intent);
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error);
                    }
                }) {
                    //adding parameters to the request
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<>();
                        params.put("idus",Login.user_id);
                        params.put("fullname", Login.full_name);

                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);

            }
        }); //FINISH
    }
}