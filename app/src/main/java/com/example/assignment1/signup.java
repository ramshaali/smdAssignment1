package com.example.assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {
    public Button signup;
    EditText email,pass;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signup = findViewById(R.id.btn);
        email= findViewById(R.id.email);
        pass= findViewById(R.id.pass);
        mAuth=FirebaseAuth.getInstance();

        String[] type= new String[]{"Pakistan", "UAE", "Canada", "India"};
        ArrayAdapter<String> adapter= new ArrayAdapter<>(
                this,
              R.layout.dropdowncon,
              type
        );
        TextView textView = findViewById(R.id.textsign);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(signup.this, LoginActivity.class);
                startActivity(intent);
            }
        });

       /* Button button = findViewById(R.id.btn);

        // Set an OnClickListener for the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to NextActivity
                Intent intent = new Intent(signup.this, entercode.class);
                startActivity(intent);
            }
        });*/

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(
                                email.getText().toString(),
                                pass.getText().toString()
                        )
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(signup.this,"Sign up Successful",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(signup.this, LoginActivity.class);
                                startActivity(intent);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(signup.this,"Sign up Failed",Toast.LENGTH_LONG).show();

                            }
                        });
            }
        });


    }
}