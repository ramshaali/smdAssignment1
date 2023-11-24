package com.example.assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class signup extends AppCompatActivity {
    public Button signup;
    EditText email,pass, name;
    String itemId;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signup = findViewById(R.id.btn);
        email= findViewById(R.id.email);
        pass= findViewById(R.id.pass);
        name=findViewById(R.id.name);
        String contact="0000000000";
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

                Intent intent = new Intent(signup.this, EditProfile.class);
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
             /*   mAuth.createUserWithEmailAndPassword(
                                email.getText().toString(),
                                pass.getText().toString()
                        )
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(signup.this,"Sign up Successful",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(signup.this, profile.class);
                                startActivity(intent);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(signup.this,"Sign up Failed",Toast.LENGTH_LONG).show();

                            }
                        });*/
                String emaill=email.getText().toString();
                String passs= pass.getText().toString();
                String namee =name.getText().toString();
                String  count = String.valueOf(System.currentTimeMillis()) ;
                itemId= count+emaill;
                User newItem = new User( namee, emaill, contact, itemId);
                String imageUrl = "android.resource://com.example.assignment1/" + R.drawable.profile;
                newItem.setCoverpic(imageUrl);
                newItem.setDisplaypic(imageUrl);
                newItem.setPass(passs);

                new postitemTask().execute(newItem);



            }
        });


    }

    private class postitemTask extends AsyncTask<User, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(User... params) {
            // The URL of your PHP script on the server
            String url = "http://192.168.100.19/assignment/insertuser.php";

            try {
                String postData = "id=" + URLEncoder.encode(params[0].getId(), "UTF-8") +
                        "&name=" + URLEncoder.encode(params[0].getName(), "UTF-8") +
                        "&email=" + URLEncoder.encode(params[0].getEmail(), "UTF-8") +
                        "&contact=" + URLEncoder.encode(params[0].getContact(), "UTF-8") +
                        "&profileurl=" + URLEncoder.encode(params[0].getDisplaypic(), "UTF-8") +
                        "&coverurl=" + URLEncoder.encode(params[0].getCoverpic(), "UTF-8")+
                        "&pass=" + URLEncoder.encode(params[0].getPass(), "UTF-8");


                // Create a URL object
                URL urll = new URL(url);
                // Create an HTTP connection
                HttpURLConnection connection = (HttpURLConnection) urll.openConnection();
                connection.setRequestMethod("POST"); // Specify the request method as POST
                connection.setConnectTimeout(10000); // Set connection timeout
                connection.setReadTimeout(10000);    // Set read timeout
                connection.setDoOutput(true); // Enable output (body data)
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.connect();

                // Write data to the HTTP connection
                try {
                    OutputStream os = connection.getOutputStream();
                    // Use OutputStreamWriter to write data to the OutputStream
                    OutputStreamWriter osw = new OutputStreamWriter(os);
                    osw.write(postData);
                    osw.flush();
                    os.close();
                    osw.close();
                    Log.d("Ramsha", "datasent: " + postData);

                } catch (Exception e) {
                    Log.d("error", "doInBackground: " + e.getMessage());
                    throw new RuntimeException(e);
                }
                // Get the response from the server
                int responseCode = connection.getResponseCode();
                // Read the response from the server
                StringBuilder response = new StringBuilder();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        Log.d("line", "line: " + line);
                        response.append(line);
                    }
                    bufferedReader.close();
                } else {
                    // Handle error cases
                    return "Error sending message. Response code: " + responseCode;
                }
                // Return the response from the server
                Log.d("resss", "response: " + response.toString());

                return response.toString();


            } catch (ProtocolException e) {
                throw new RuntimeException(e);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        protected void onPostExecute(String result) {
            String expectedResponse = "Connection Established Successfully{\"Status\":1,\"id\":0,\"Message\":\"Data inserted successfully\"}";
            Log.d("resssult", "result " +result);
            if (result != null && result.equals(expectedResponse)) {
                Toast.makeText(signup.this, "Signup Successful", Toast.LENGTH_LONG).show();


                Intent intent = new Intent(signup.this, profile.class);
                intent.putExtra("curruserid", itemId);
                startActivity(intent);
            } else {
                Toast.makeText(signup.this, "Signup Failed", Toast.LENGTH_LONG).show();
            }
        }
    }
}