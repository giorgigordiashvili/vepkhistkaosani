package ge.example.vefkhistkaosani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vefkhistkaosani.R;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class Fullname extends AppCompatActivity {
    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullname);
        //splash_animation = AnimationUtils.loadAnimation(this,R.anim.splash_animation);
        EditText editText = findViewById(R.id.full_name);
        editText.requestFocus();
        //HOOKS


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
        EditText mFullname = (EditText) findViewById(R.id.full_name);
        Button mButton = (Button) findViewById(R.id.button_submit_fullname);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mButton.setEnabled(false);
                Login.full_name = mFullname.getText().toString();
                if( Login.full_name.length() < 3){
                    Toast.makeText(Fullname.this, "გთხოვთ შეიყვანოთ სრული სახელი", Toast.LENGTH_SHORT).show();
                }else{
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
                                    mButton.setEnabled(true);
                                    // Display the response string.
                                    System.out.println(response);


                                    JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();

                                    if(jsonObject.get("result").getAsString().equals("1")) {
                                        Login.code = jsonObject.get("ertjeradi").getAsString();
                                        Intent intent = new Intent(Fullname.this, Code.class);
                                        startActivity(intent);
                                        finish();
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





            }
        }); //FINISH
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