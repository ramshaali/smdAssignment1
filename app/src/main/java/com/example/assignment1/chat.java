package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class chat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ImageView imageView = findViewById(R.id.home);
        ImageView imageView2 = findViewById(R.id.prof);
        ImageView imageView3 = findViewById(R.id.search);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String userId = currentUser.getUid();
        if (currentUser != null) {

            usersRef.child(userId).child("status").setValue("online");
        }else {
            // Update user status to "offline" when they log out
            usersRef.child(userId).child("status").setValue("offline");
        }
        TextView userStatusTextView = findViewById(R.id.userStatus);





        String userIdToRetrieve = "userIdToRetrieve";
        DatabaseReference userStatusRef = FirebaseDatabase.getInstance().getReference().child("users").child(userIdToRetrieve).child("status");
        userStatusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String status = dataSnapshot.getValue(String.class);
                    String userId = dataSnapshot.getKey();
                    String statususer = dataSnapshot.child("status").getValue(String.class);
                    if (status.equals("online")) {
                        userStatusTextView.setText("Online");
                        userStatusTextView.setTextColor(Color.parseColor("#00FF00"));
                    } else {
                        userStatusTextView.setText("Offline");
                        userStatusTextView.setTextColor(Color.parseColor("#d3d3d3"));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle potential errors
            }
        });








        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(chat.this, dashboard.class);
                startActivity(intent);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(chat.this, profile.class);
                startActivity(intent);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(chat.this, search.class);
                startActivity(intent);
            }
        });

        ImageView imageView4= findViewById(R.id.go);

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(chat.this, chat_open.class);
                startActivity(intent);
            }
        });

    }
}