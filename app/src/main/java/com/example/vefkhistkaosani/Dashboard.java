package com.example.vefkhistkaosani;


import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
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
        bottomNav.getMenu().findItem(R.id.nav_vefx).setChecked(false);
        bottomNav.getMenu().findItem(R.id.nav_sarc).setChecked(false);
        bottomNav.getMenu().findItem(R.id.nav_apor).setChecked(false);
    }
    public void changeView(String text){


    }

}
