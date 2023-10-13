package com.example.assignment1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class dashboard extends AppCompatActivity {
    RecyclerView recyclerView1;
    RecyclerView recyclerView2;

    List<itemcard> itemList1;
    List<itemcard> itemList2;

    recycleadapter adapter1;
    recycleadapter adapter2;
    private static final int ITEM_DETAIL_REQUEST = 1;
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
                startActivityForResult(intent,1);
            }
        });
         recyclerView1 = findViewById(R.id.rv1);
       recyclerView2 = findViewById(R.id.rv2);
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
       // Drawable drawable=getResources().getDrawable(R.drawable.baseline_arrow_forward_24);
        //BitmapDrawable bitdrawable=(BitmapDrawable) drawable;
        //Bitmap bitmap=bitdrawable.getBitmap();
         itemList1 = new ArrayList<>();
        //itemList1.add(new itemcard(bitmap, "Item 1", "$10/hr", 1000, "7th Mar"));
        //itemList1.add(new itemcard(bitmap, "Item 2", "$10/hr", 750, "6th Mar"));

         itemList2 = new ArrayList<>();
        //itemList2.add(new itemcard(bitmap, "Item 3", "$12/hr", 1200, "8th Mar"));
        //itemList2.add(new itemcard(bitmap, "Item 4", "$11/hr", 800, "9th Mar"));

        List<itemcard> itemList3 = new ArrayList<>();
        //itemList3.add(new itemcard(bitmap, "Item 5", "$15/hr", 1500, "10th Mar"));
        //itemList3.add(new itemcard(bitmap, "Item 6", "$9/hr", 600, "11th Mar"));

         adapter1 = new recycleadapter(itemList1);
        recyclerView1.setAdapter(adapter1);

         adapter2 = new recycleadapter(itemList2);
        recyclerView2.setAdapter(adapter2);

        recycleadapter adapter3 = new recycleadapter(itemList3);
        recyclerView3.setAdapter(adapter3);



    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1 && resultCode==RESULT_OK &&data!=null){
            byte[] byteArray = getIntent().getByteArrayExtra("img");
            Bitmap imgbitmap= BitmapFactory.decodeByteArray(byteArray,0, byteArray.length);
            // Extract the item details from the data received from 'enteritem' activity

            String name = data.getStringExtra("name");
            String price = data.getStringExtra("price");
            int views = data.getIntExtra("views", 0);
            String date = data.getStringExtra("date");


            itemcard newItem = new itemcard(imgbitmap, name, price, views, date);


           itemList1.add(newItem);
            itemList2.add(newItem);


            recyclerView1.getAdapter().notifyDataSetChanged();
            recyclerView2.getAdapter().notifyDataSetChanged();
        }else   if (requestCode == ITEM_DETAIL_REQUEST && resultCode == RESULT_OK) {
            int positionToDelete = data.getIntExtra("positionToDelete", -1);

            if (positionToDelete != -1) {
                // Remove the item from the dataset
                itemList1.remove(positionToDelete);
                itemList2.remove(positionToDelete);

                // Notify the adapter that the dataset has changed
                adapter1.notifyItemRemoved(positionToDelete);
                adapter2.notifyItemRemoved(positionToDelete);
            }
        }
    }
}
