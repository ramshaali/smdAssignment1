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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class dashboard extends AppCompatActivity {
    RecyclerView recyclerView1;
    RecyclerView recyclerView2;

    List<itemcard> itemList1;
    FirebaseAuth mAuth;
    List<itemcard> itemList2;

    recycleadapter adapter1;
    recycleadapter adapter2;
    String ownerId;
    private static final int ITEM_DETAIL_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ImageView imageView = findViewById(R.id.chat);
        ImageView imageView2 = findViewById(R.id.prof);
        ImageView imageView3 = findViewById(R.id.search);
        ImageView imageView4 = findViewById(R.id.plus);
        TextView logout = findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
      ownerId = currentUser.getUid();



        logout.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(dashboard.this, LoginActivity.class));


        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(dashboard.this, chat.class);
                startActivity(intent);
            }
        });


        ImageView req= findViewById(R.id.req);
        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(dashboard.this, Requests.class);
                startActivity(intent);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(dashboard.this, profile.class);
                intent.putParcelableArrayListExtra("itemList", new ArrayList<>(itemList1));
                startActivity(intent);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(dashboard.this, search.class);
                // Pass the list of itemcards to the SearchResultsActivity
                intent.putParcelableArrayListExtra("itemList", new ArrayList<>(itemList1));

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
        String imageUrl = "android.resource://com.example.assignment1/" + R.drawable.camera;

        itemList1.add(new itemcard(imageUrl, "Camera", "$10", 1000, "7th Mar", ownerId));
        itemList1.add(new itemcard(imageUrl, "Chair", "$10", 750, "6th Mar", ownerId));

         itemList2 = new ArrayList<>();
        //itemList2.add(new itemcard(bitmap, "Item 3", "$12/hr", 1200, "8th Mar"));
        //itemList2.add(new itemcard(bitmap, "Item 4", "$11/hr", 800, "9th Mar"));

        List<itemcard> itemList3 = new ArrayList<>();
        //itemList3.add(new itemcard(bitmap, "Item 5", "$15/hr", 1500, "10th Mar"));
        //itemList3.add(new itemcard(bitmap, "Item 6", "$9/hr", 600, "11th Mar"));

         adapter1 = new recycleadapter(itemList1, this);
        recyclerView1.setAdapter(adapter1);

       //  adapter2 = new recycleadapter(itemList2, this);
       // recyclerView2.setAdapter(adapter2);

        //recycleadapter adapter3 = new recycleadapter(itemList3, this);
       // recyclerView3.setAdapter(adapter3);



    }


    @Override
    protected void onStart( ){
        super.onStart();

        FirebaseUser user=mAuth.getCurrentUser();
        if(user==null){
            startActivity(new Intent(dashboard.this, LoginActivity.class));
        }

    }
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1 && resultCode==RESULT_OK &&data!=null){

            String Url=data.getStringExtra("img");
            String name = data.getStringExtra("name");
            String price = data.getStringExtra("price");
            int views = data.getIntExtra("views", 0);
            String date = data.getStringExtra("date");


            itemcard newItem = new itemcard(Url, name, price, views, date,ownerId);


           itemList1.add(newItem);
            itemList2.add(newItem);


            recyclerView1.getAdapter().notifyDataSetChanged();
            //recyclerView2.getAdapter().notifyDataSetChanged();
        }else   if (requestCode == ITEM_DETAIL_REQUEST && resultCode == RESULT_OK) {
            int positionToDelete = data.getIntExtra("positionToDelete", -1);

            if (positionToDelete != -1) {
                // Remove the item from the dataset
                itemList1.remove(positionToDelete);
                itemList2.remove(positionToDelete);

                // Notify the adapter that the dataset has changed
                adapter1.notifyItemRemoved(positionToDelete);
                //adapter2.notifyItemRemoved(positionToDelete);
            }
        }
    }


}
