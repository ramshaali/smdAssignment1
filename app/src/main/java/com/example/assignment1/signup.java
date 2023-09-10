package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
         AutoCompleteTextView autoCompleteTextView=findViewById(R.id.drop1);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText( signup.this, autoCompleteTextView.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}