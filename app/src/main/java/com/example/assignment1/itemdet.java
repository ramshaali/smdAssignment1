package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class itemdet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemdet);
        TextView name= findViewById(R.id.name);
        TextView price=findViewById(R.id.price);
        ImageView img= findViewById(R.id.slide);
        TextView date= findViewById(R.id.date);
        name.setText(getIntent().getStringExtra("name"));
        date.setText(getIntent().getStringExtra("date"));
        price.setText(getIntent().getStringExtra("price"));
        String imageUrl = getIntent().getStringExtra("img");
        Picasso.get().load(imageUrl).into(img);


      /*  List<Integer> img = new ArrayList<>();
        img.add(R.drawable.square2);
        img.add(R.drawable.square2);



        ViewPager2 data = findViewById(R.id.slide);
       imageslideradap slideAdapter = new imageslideradap(img);
       data.setAdapter(slideAdapter);*/

        TextView textView = findViewById(R.id.rep);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(itemdet.this, Report.class);
                startActivity(intent);
            }
        });


        Button button = findViewById(R.id.btn);
        ImageView del = findViewById(R.id.del);
        // Set an OnClickListener for the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to NextActivity
                Intent intent = new Intent(itemdet.this, dashboard.class);
                startActivity(intent);
            }
        });


        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to NextActivity
                int positionToDelete = getIntent().getIntExtra("positionToDelete", -1);

                if (positionToDelete != -1) {
                    // Create an intent to send back the deleted item position
                    Intent intent = new Intent();
                    intent.putExtra("positionToDelete", positionToDelete);
                    setResult(RESULT_OK, intent);

                    // Finish the activity to return to the previous one
                    finish();
                }
            }
        });






    }

}