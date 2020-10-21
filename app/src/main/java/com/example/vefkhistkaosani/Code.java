package com.example.vefkhistkaosani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
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
import android.webkit.CookieManager;
public class Code extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_code);

        Button resendButton = (Button) findViewById(R.id.button_resend);
        Button backButton = (Button)this.findViewById(R.id.button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        resendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(Code.this);
                //this is the url where you want to send the request
                //TODO: replace with your own url to send request, as I am using my own localhost for this tutorial
                String url = "https://vefxistyaosani.ge/iOS/config/mobileCheck.php";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the response string.
                                System.out.println(response);
                                Gson gson = new Gson();
                                JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
                                Login.code = jsonObject.get("ertjeradi").getAsString();


                                System.out.println(response);
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

                        params.put("mobile", Login.mobile);

                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);

            }
        });

        final PinEntryEditText pinEntry = (PinEntryEditText) findViewById(R.id.txt_pin_entry);
        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {
                    if (str.toString().equals(Login.code)) {
                        Intent intent = new Intent(Code.this,Dashboard.class);
                        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                        sp.edit().putString("USERID",Login.user_id).apply();
                        sp.edit().putString("fullname",Login.full_name).apply();
                        sp.edit().putString("package","სტანდარტული").apply();


                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Code.this, "კოდი არასწორია", Toast.LENGTH_SHORT).show();
                        pinEntry.setText(null);
                    }
                }
            });
        }
    }
}