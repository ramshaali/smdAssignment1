package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);




        RecyclerView recyclerView = findViewById(R.id.rv1);
        RecyclerView recyclerView2 = findViewById(R.id.rv2);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);



        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView2.setLayoutManager(layoutManager);


        List<itemcard> itemList = new ArrayList<>();
        itemList.add(new itemcard(R.drawable.square2, "Item 1", "$10/hr", 1000, "7th Mar"));
        itemList.add(new itemcard(R.drawable.square2, "Item 2", "$10/hr", 750, "6th Mar"));
        // Add more items as needed

        adapter = new recycleadapter(itemList);
        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(adapter);

    }

    private recycleadapter adapter;
}
