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

public class dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ImageView imageView = findViewById(R.id.chat);
        ImageView imageView2 = findViewById(R.id.prof);
        ImageView imageView3 = findViewById(R.id.search);
        ImageView imageView4 = findViewById(R.id.plus);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(dashboard.this, chat.class);
                startActivity(intent);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(dashboard.this, profile.class);
                startActivity(intent);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(dashboard.this, search.class);
                startActivity(intent);
            }
        });


        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(dashboard.this, enteritem.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView1 = findViewById(R.id.rv1);
        RecyclerView recyclerView2 = findViewById(R.id.rv2);
        RecyclerView recyclerView3 = findViewById(R.id.rv3);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView1.setLayoutManager(layoutManager1);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView2.setLayoutManager(layoutManager2);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this);
        layoutManager3.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView3.setLayoutManager(layoutManager3);

        List<itemcard> itemList1 = new ArrayList<>();
        itemList1.add(new itemcard(R.drawable.square2, "Item 1", "$10/hr", 1000, "7th Mar"));
        itemList1.add(new itemcard(R.drawable.square2, "Item 2", "$10/hr", 750, "6th Mar"));

        List<itemcard> itemList2 = new ArrayList<>();
        itemList2.add(new itemcard(R.drawable.square2, "Item 3", "$12/hr", 1200, "8th Mar"));
        itemList2.add(new itemcard(R.drawable.square2, "Item 4", "$11/hr", 800, "9th Mar"));

        List<itemcard> itemList3 = new ArrayList<>();
        itemList3.add(new itemcard(R.drawable.square2, "Item 5", "$15/hr", 1500, "10th Mar"));
        itemList3.add(new itemcard(R.drawable.square2, "Item 6", "$9/hr", 600, "11th Mar"));

        recycleadapter adapter1 = new recycleadapter(itemList1);
        recyclerView1.setAdapter(adapter1);

        recycleadapter adapter2 = new recycleadapter(itemList2);
        recyclerView2.setAdapter(adapter2);

        recycleadapter adapter3 = new recycleadapter(itemList3);
        recyclerView3.setAdapter(adapter3);



    }
}
