package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class itemdet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdet);


        List<Integer> img = new ArrayList<>();
        img.add(R.drawable.square2);
        img.add(R.drawable.square2);



        ViewPager2 data = findViewById(R.id.slide);
       imageslideradap slideAdapter = new imageslideradap(img);
       data.setAdapter(slideAdapter);

        TextView textView = findViewById(R.id.rep);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(itemdet.this, Report.class);
                startActivity(intent);
            }
        });


    }
}