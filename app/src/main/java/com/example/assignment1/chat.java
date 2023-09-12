package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class chat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ImageView imageView = findViewById(R.id.home);
        ImageView imageView2 = findViewById(R.id.prof);
        ImageView imageView3 = findViewById(R.id.search);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(chat.this, dashboard.class);
                startActivity(intent);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(chat.this, profile.class);
                startActivity(intent);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(chat.this, search.class);
                startActivity(intent);
            }
        });
    }
}