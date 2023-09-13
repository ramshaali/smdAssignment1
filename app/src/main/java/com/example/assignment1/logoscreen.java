package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class logoscreen extends AppCompatActivity {
    private static final long time = 3000;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logoscreen);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Create an Intent to navigate to the login activity
                Intent intent = new Intent(logoscreen.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, time);


    }
}