package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        ImageView imageView = findViewById(R.id.home);
        ImageView imageView2 = findViewById(R.id.chat);
        ImageView imageView3 = findViewById(R.id.search);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(profile.this, dashboard.class);
                startActivity(intent);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(profile.this, chat.class);
                startActivity(intent);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(profile.this, search.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.rv1);
        RecyclerView recyclerView2 = findViewById(R.id.rv2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);



        layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);



        layoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView2.setLayoutManager(layoutManager2);


        List<itemcard> itemList = new ArrayList<>();
        itemList.add(new itemcard(R.drawable.square2, "Item 1", "$10/hr", 1000, "7th Mar"));
        itemList.add(new itemcard(R.drawable.square2, "Item 2", "$10/hr", 750, "6th Mar"));

        List<itemcard> itemList3 = new ArrayList<>();
        itemList3.add(new itemcard(R.drawable.square2, "Item 5", "$15/hr", 1500, "10th Mar"));
        itemList3.add(new itemcard(R.drawable.square2, "Item 6", "$9/hr", 600, "11th Mar"));

        recycleadapter adapter2 = new recycleadapter(itemList3);
        recyclerView2.setAdapter(adapter2);
        adapter = new recycleadapter(itemList);
        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(adapter);

    }

    private recycleadapter adapter;
}
