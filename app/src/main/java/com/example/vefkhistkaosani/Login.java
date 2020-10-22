package com.example.vefkhistkaosani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

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


public class Login extends AppCompatActivity {
    public static String code = "123123";
    public static String mobile = "597147514";
    public static String full_name = "გიორგი გორდიაშვილი";
    public static String paketi = "სტანდარტული";
    public static String user_id = "1233";
    public static String logged = null;
    private static int SPLASH_SCREEN = 1500;
    Animation splash_animation;
    ImageView image;
    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        EditText editText = findViewById(R.id.phone_number);
        editText.requestFocus();

        Button mButton = (Button) findViewById(R.id.button1);
        EditText mEdit = (EditText) findViewById(R.id.phone_number);
//START
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(Login.this);
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

                                if(jsonObject.get("result").getAsString().equals("2")){
                                    code = jsonObject.get("ertjeradi").getAsString();

                                    full_name = jsonObject.get("sruli_saxeli").getAsString();
                                    user_id = jsonObject.get("userid").getAsString();
                                    Intent intent = new Intent(Login.this,Code.class);
                                    startActivity(intent);
                                } else if (jsonObject.get("result").getAsString().equals("1")){
                                    //tu ar arsebobs user
                                    //jer kodi ar aris
                                    user_id = jsonObject.get("userid").getAsString();
                                    Intent intent = new Intent(Login.this,Fullname.class);
                                    startActivity(intent);
                                } else {
                                    //
                                }

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
                        mobile = mEdit.getText().toString();
                        params.put("mobile", mobile);

                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);

            }
        }); //FINISH
        //splash_animation = AnimationUtils.loadAnimation(this,R.anim.splash_animation);

        //HOOKS
        image = findViewById(R.id.logo_splash);

        //image.setAnimation(splash_animation);
        // Hook up the VideoView to our UI.

        videoBG = (VideoView) findViewById(R.id.videoView);

        // Build your video Uri
        Uri uri = Uri.parse("android.resource://" // First start with this,
                + getPackageName() // then retrieve your package name,
                + "/" // add a slash,
                + R.raw.back); // and then finally add your video resource. Make sure it is stored
        // in the raw folder.

        // Set the new Uri to our VideoView
        videoBG.setVideoURI(uri);
        // Start the VideoView
        videoBG.start();

        // Set an OnPreparedListener for our VideoView. For more information about VideoViews,
        // check out the Android Docs: https://developer.android.com/reference/android/widget/VideoView.html
        videoBG.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer = mediaPlayer;
                // We want our video to play over and over so we set looping to true.
                mMediaPlayer.setLooping(true);
                // We then seek to the current posistion if it has been set and play the video.
                if (mCurrentVideoPosition != 0) {
                    mMediaPlayer.seekTo(mCurrentVideoPosition);
                    mMediaPlayer.start();
                }
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Capture the current video position and pause the video.
        mCurrentVideoPosition = mMediaPlayer.getCurrentPosition();
        videoBG.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Restart the video when resuming the Activity
        videoBG.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // When the Activity is destroyed, release our MediaPlayer and set it to null.
        mMediaPlayer.release();
        mMediaPlayer = null;
    }
}