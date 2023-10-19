package com.example.assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

        String ownerId= getIntent().getStringExtra("ownerid");
        String itemid= getIntent().getStringExtra("id");


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
                Intent intent = new Intent(itemdet.this, report.class);
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
                Intent intent = new Intent(itemdet.this, RentActivity.class);
                TextView price= findViewById(R.id.price);
                String userInput = price.getText().toString();
                int convertedInput = 0;

                try {
                     convertedInput = Integer.parseInt(userInput);

                } catch (NumberFormatException e) {

                }
                intent.putExtra("img", imageUrl);
                intent.putExtra("ownerid", ownerId);
                intent.putExtra("id",itemid);
                intent.putExtra("INT_KEY", convertedInput);
                startActivity(intent);
            }
        });
        String curr_id= FirebaseAuth.getInstance().getCurrentUser().getUid();

        if(curr_id.equals(ownerId)){
            del.setVisibility(View.VISIBLE);
        }else{
            del.setVisibility(View.GONE);
        }

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              FirebaseDatabase database= FirebaseDatabase.getInstance();
                DatabaseReference ref=database.getReference("items");
                DatabaseReference deleteitem=ref.child(itemid);

                deleteitem.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(itemdet.this,"Item Deleted",Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(itemdet.this,"Fail",Toast.LENGTH_LONG).show();

                        }
                    }
                });

                finish();
            }
        });






    }

}