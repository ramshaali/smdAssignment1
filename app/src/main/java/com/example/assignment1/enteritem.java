package com.example.assignment1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class enteritem extends AppCompatActivity {
    private final int code_gallery=1000;
    ImageView imageView1;
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
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, code_gallery);


            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(enteritem.this, photo.class);
                startActivity(intent);
            }
        });

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
                BitmapDrawable drawable=(BitmapDrawable) imageView1.getDrawable();
                Bitmap bitmap=drawable.getBitmap();

                ByteArrayOutputStream stream= new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytearray=stream.toByteArray();
                // Get user input from EditText fields
                // Get user input and create an Intent to send back the item details
                // Replace with the actual image resource
                String name = itemname.getText().toString(); // Replace with the actual item name
                String price = "Item Price"; // Replace with the actual item price
                int views = 0; // Replace with the actual number of views
                String date = "Item Date"; // Replace with the actual item date

                Intent resultIntent = new Intent();
                resultIntent.putExtra("img", bytearray);
                resultIntent.putExtra("name", name);
                resultIntent.putExtra("price", price);
                resultIntent.putExtra("views", views);
                resultIntent.putExtra("date", date);

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

    }

    @Override
    protected  void onActivityResult(int requestCode,int resultCode, @Nullable Intent data  ) {


        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (resultCode==code_gallery){
                imageView1.setImageURI(data.getData());
            }
        }
    }

}