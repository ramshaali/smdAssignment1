package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class searchresults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresults);


       ImageView button = findViewById(R.id.backButton);

        // Set an OnClickListener for the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to NextActivity
                Intent intent = new Intent(searchresults.this, dashboard.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView1 = findViewById(R.id.rv);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);

        recyclerView1.setLayoutManager(layoutManager1);



        // Retrieve the filtered item list from the Intent
        ArrayList<itemcard> filteredItems = getIntent().<itemcard>getParcelableArrayListExtra("filteredItems");

        // Set up the RecyclerView with a custom adapter

        recycleadapter adapter1 = new recycleadapter(filteredItems, this);
        recyclerView1.setAdapter(adapter1);


    }
}