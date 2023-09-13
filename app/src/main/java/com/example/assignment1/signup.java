package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class signup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        String[] type= new String[]{"Pakistan", "UAE", "Canada", "India"};
        ArrayAdapter<String> adapter= new ArrayAdapter<>(
                this,
              R.layout.dropdowncon,
              type
        );
        TextView textView = findViewById(R.id.textsign);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(signup.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        Button button = findViewById(R.id.btn);

        // Set an OnClickListener for the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to NextActivity
                Intent intent = new Intent(signup.this, entercode.class);
                startActivity(intent);
            }
        });
    }
}