package com.example.vefkhistkaosani;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NoInternet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        Button mButton = (Button) findViewById(R.id.try_again);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(NoInternet.this, MainActivity.class);
                startActivity(intent);
                finish();
            }});


    }
}