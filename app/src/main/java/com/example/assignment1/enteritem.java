package com.example.assignment1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class enteritem extends AppCompatActivity {
    private final int code_gallery=1000;
    Bitmap bitmap;
    ImageView imageView1;
    String imageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enteritem);

         imageView1 = findViewById(R.id.cam);
        ImageView imageView2 = findViewById(R.id.cam2);
        Button b = findViewById(R.id.btn);

        TextView tv =findViewById(R.id.gallery);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, code_gallery);


            }
        });

       /* imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(enteritem.this, photo.class);
                startActivity(intent);
            }
        });*/

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(enteritem.this, video.class);
                startActivity(intent);
            }
        });

       b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(enteritem.this, dashboard.class);
                startActivity(intent);
            }
        });

        // Inside enteritem activity
        Button postItemButton = findViewById(R.id.btn);
        postItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText itemname= findViewById(R.id.name);
                EditText desc=findViewById(R.id.desc);
                EditText  itemprice=findViewById(R.id.rate);

                String name = itemname.getText().toString();
                String price = itemprice.getText().toString();
                int views = 1;
                String date = getCurrentDate();

                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();


                DatabaseReference itemsRef = rootRef.child("items");


                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String ownerId = currentUser.getUid();


                itemcard newItem = new itemcard(imageUrl, name, price, views, date, ownerId);


                itemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            // Create the "items" node and push the new item
                            String itemId = itemsRef.push().getKey();
                            newItem.setid(itemId);
                            itemsRef.child(itemId).setValue(newItem);
                        } else {
                            // The "items" node exists, push the new item
                            String itemId = itemsRef.push().getKey();
                            newItem.setid(itemId);

                            itemsRef.child(itemId).setValue(newItem);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





                Intent resultIntent = new Intent();
                //resultIntent.putExtra("img", bytearray);
                resultIntent.putExtra("img", imageUrl);

                resultIntent.putExtra("name", name);
                resultIntent.putExtra("price", price);
                resultIntent.putExtra("views", views);
                resultIntent.putExtra("date", date);

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });


    }
    private String getCurrentDate() {
        // Create a date object
        Date currentDate = new Date();

        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // Format the date and store it as a string
        return dateFormat.format(currentDate);
    }

    @Override
    protected  void onActivityResult(int requestCode,int resultCode, @Nullable Intent data  ) {


        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == code_gallery && data != null) {
            Uri selectedImage = data.getData();
            imageUrl = selectedImage.toString();
            imageView1.setImageURI(selectedImage);
        }
    }

}