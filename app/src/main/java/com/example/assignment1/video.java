package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class video extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videocall);


        Button button = findViewById(R.id.photo);

        // Set an OnClickListener for the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to NextActivity
                Intent intent = new Intent(video.this, photo.class);
                startActivity(intent);
            }
        });

        Button button2 = findViewById(R.id.butex);

        // Set an OnClickListener for the button
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to NextActivity
                Intent intent = new Intent(video.this, enteritem.class);
                startActivity(intent);
            }
        });

    }
}