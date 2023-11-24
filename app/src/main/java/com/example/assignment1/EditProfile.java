package com.example.assignment1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;


public class EditProfile extends AppCompatActivity {
    EditText nameTextBox, emailTextBox, contactTextBox;
    TextView saveChangesButton;

    FirebaseAuth mauth;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        nameTextBox = findViewById(R.id.nameTextBox);
        emailTextBox = findViewById(R.id.emailTextBox);
        contactTextBox = findViewById(R.id.contactTextBox);
        saveChangesButton =  findViewById(R.id.saveChangesButton);

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameTextBox.getText().toString();
                String email = emailTextBox.getText().toString();
                String contact = contactTextBox.getText().toString();


                String imageUrl = "android.resource://com.example.assignment1/" + R.drawable.profile;



                User newItem =  new User (name, email,contact, id);


                new UpdateUserTask().execute(newItem);


                // Send data to home
                Intent intent = new Intent();
                intent.putExtra("userName", name);
                intent.putExtra("userEmail", email);
                intent.putExtra("userContact", contact);

                setResult(RESULT_OK, intent);
                finish();
            }
        });



        ImageView back = findViewById(R.id.backArrowIcon);
        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfile.this, profile.class);
                startActivity(intent);
            }
        });
    }

    private class UpdateUserTask extends AsyncTask<User, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(User... params) {
            String url = "http://192.168.100.19/assignment/updateuser.php";

            try {
                // Build the data string for POST request
                String postData = "id=" + URLEncoder.encode(params[0].getId(), "UTF-8") +
                        "&name=" + URLEncoder.encode(params[0].getName(), "UTF-8") +
                        "&email=" + URLEncoder.encode(params[0].getEmail(), "UTF-8") +
                        "&contact=" + URLEncoder.encode(params[0].getContact(), "UTF-8");

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
                    OutputStreamWriter osw = new OutputStreamWriter(os);
                    osw.write(postData);
                    osw.flush();
                    os.close();
                    osw.close();
                    Log.d("Ramsha inside ", "Data sent: " + postData);

                } catch (Exception e) {
                    Log.d("Ramsha", "Error: " + e.getMessage());
                    throw new RuntimeException(e);
                }

                // Get the response from the server
                int responseCode = connection.getResponseCode();
                // Read the response from the server
                StringBuilder response = new StringBuilder();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        Log.d("Ramsha ", "Response: " + line);
                        response.append(line);
                    }
                    bufferedReader.close();
                } else {

                    return "Error sending message. Response code: " + responseCode;
                }

                Log.d("Ramsha", "Response: " + response.toString());
                return response.toString();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        protected void onPostExecute(String result) {

        }
    }

}