package com.example.assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        Intent intent = getIntent();
        int pricePerHour = intent.getIntExtra("INT_KEY", 0);
        String oid = intent.getStringExtra("ownerid");
        String iid = intent.getStringExtra("id");


        EditText hoursuser= findViewById(R.id.hours);
        String hours = hoursuser.getText().toString();
        int convertedInput = 0;

        try {
            convertedInput = Integer.parseInt(hours);

        } catch (NumberFormatException e) {

        }
        int totalCost = pricePerHour * convertedInput;
        String priceString = "$" + totalCost;


        TextView price = findViewById(R.id.price);
        price.setText(priceString);
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();


        DatabaseReference itemsRef = rootRef.child("requests");


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String requestId = currentUser.getUid();


        request newreq= new request(iid,requestId,oid);

        Button btn= findViewById(R.id.rentit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            // Create the "items" node and push the new item
                            String itemId = itemsRef.push().getKey();
                            itemsRef.child(itemId).setValue(newreq);
                        } else {
                            // The "items" node exists, push the new item
                            String itemId = itemsRef.push().getKey();

                            itemsRef.child(itemId).setValue(newreq);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });



    }
}