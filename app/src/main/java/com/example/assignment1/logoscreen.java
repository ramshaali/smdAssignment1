package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class logoscreen extends AppCompatActivity {
    private static final long time = 3000;
    FirebaseAuth mAuth;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logoscreen);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(logoscreen.this, dashboard.class);
            startActivity(intent);
            finish();
        }
        else
        {
            // Use a Handler to post a delayed action to navigate to the login screen
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Create an Intent to navigate to the login activity
                    Intent intent = new Intent(logoscreen.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Finish the splash activity so the user can't go back to it
                }
            }, time);
        }








    }

   /* @Override
    protected void onStart( ){
        super.onStart();

        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null){
            startActivity(new Intent(logoscreen.this, dashboard.class));
        }

    }*/
}