package com.example.assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Requests extends AppCompatActivity {
    String   itemName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);


        RecyclerView recyclerView = findViewById(R.id.rv);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseDatabase.getInstance().getReference("requests")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<request> requestList = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            request requestt = snapshot.getValue(request.class);
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String currentUserId = currentUser.getUid();
                            FirebaseAuth mAuth = FirebaseAuth.getInstance();
                            String requserId = requestt.getRequesterId(); // Replace this with the actual user ID
                            FirebaseDatabase.getInstance().getReference("items").child(requestt.getItemId()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {

                                        itemName = dataSnapshot.child("name").getValue(String.class);
                                      requestt.setItemId(itemName);
                                        Log.d("TAG", "onDataChange: " + dataSnapshot.toString());
                                        Log.d("TAG", "Item name: " + itemName);
                                        Log.d("TAG", "Item name after setting: " + requestt.getItemId());
                                        if (requestt != null && requestt.getOwnerId().equals(currentUserId)) {
                                            Log.d("TAG", "Item name: " + itemName);

                                            requestList.add(requestt);
                                            requestadap adapter = new requestadap(requestList);
                                            recyclerView.setAdapter(adapter);
                                        }

                                    } else {
                                        Toast.makeText(Requests.this,"name not found",Toast.LENGTH_LONG).show();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Handle any errors
                                }
                            });


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle error
                    }
                });


        ImageView back= findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(Requests.this, dashboard.class);
                startActivity(intent);
            }
        });




    }
}