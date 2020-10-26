package com.example.vefkhistkaosani;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 1500;
    Animation splash_animation;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);

        System.out.println();

        //Animations
        Login.logged = sp.getString("USERID", null);


        //sp.edit().putString("USERID",null).apply();

        //HOOKS
        image = findViewById(R.id.logo_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = null;

                if (Login.logged != null){
                    if (!DetectConnection.checkInternetConnection(getBaseContext())) {
                       
                        intent = new Intent(MainActivity.this, NoInternet.class);
                        finish();
                    } else {
                        intent = new Intent(MainActivity.this, Dashboard.class);
                        finish();
                    }

                }else {
                     intent = new Intent(MainActivity.this, Login.class);
                    finish();
                }
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(image, "splash_logo");


               /* if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                    startActivity(intent, options.toBundle());
                }*/
                startActivity(intent);
            }
        },SPLASH_SCREEN);
        }



}

