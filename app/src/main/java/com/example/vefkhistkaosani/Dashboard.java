package com.example.vefkhistkaosani;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Dashboard extends AppCompatActivity {
    Button settingsOpen;
    private AppBarConfiguration mAppBarConfiguration;
    NavigationView mNavigationView;
    View mHeaderView;

    TextView textViewUsername;
    TextView textViewEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        //FINSH
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // NavigationView
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        // NavigationView Header
        mHeaderView =  mNavigationView.getHeaderView(0);

        // View
        textViewUsername = (TextView) mHeaderView.findViewById(R.id.fullname_drawer);
        textViewEmail= (TextView) mHeaderView.findViewById(R.id.package_drawer);
        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        // Set username & email
        textViewUsername.setText(sp.getString("fullname", null));
        textViewEmail.setText(sp.getString("package", null));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_view);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        //BOTTOMSHEET
        settingsOpen = findViewById(R.id.open_settings);
        settingsOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsFragment bottomSheet = new SettingsFragment();
                bottomSheet.show(getSupportFragmentManager(), "TAG");




            }
        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_vefx, R.id.nav_apor, R.id.nav_sarc,  R.id.nav_eseebi, R.id.nav_sanishni, R.id.nav_shinaarsi, R.id.nav_lexikoni)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);


        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(bottomNav,navController);

    }

    public void rame(){

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {





        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);

    }


    public void changeCheck(){
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_view);

            bottomNav.setItemIconTintList(ColorStateList.valueOf(Color.parseColor("#848484")));
            bottomNav.setItemTextColor(ColorStateList.valueOf(Color.parseColor("#848484")));


    }
    public void changeView(String text){


        VefxFragment fragment = new VefxFragment();

        ((VefxFragment) fragment).scrollToThat(text);



    }

    public void logOut(){
        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        // Set username & email
        sp.edit().putString("USERID",null).apply();
        sp.edit().putString("fullname",null).apply();
        sp.edit().putString("package",null).apply();
        Login.logged = null;
        Login.user_id = null;
        Intent intent = new Intent(Dashboard.this,MainActivity.class);
        startActivity(intent);
        finish();


    }



}
