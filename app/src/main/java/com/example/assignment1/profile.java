package com.example.assignment1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class profile extends AppCompatActivity {

    FirebaseAuth mAuth;
    String userId;
    String userName, userEmail, userContact;
    TextView name;
    ImageView profilePic, coverPic;
    private final int EDIT_PROF_CODE = 100;
    private static final int DP_REQUEST_CODE = 110;
    private static final int COVER_REQUEST_CODE = 200;
    String ownerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name= findViewById(R.id.name);
        profilePic=findViewById(R.id.img);
        coverPic= findViewById(R.id.black);
        Intent intent = getIntent();

        ownerId = intent.getStringExtra("curruserid");
        String namee= intent.getStringExtra("name");
        String purl= intent.getStringExtra("purl");
        String curl= intent.getStringExtra("curl");

        name.setText(namee);


        if (!purl.equals("")) {
            Picasso.get().load(purl).into(profilePic);
        }


        if (!curl.equals("")) {
            Picasso.get().load(curl).into(coverPic);
        }

        //ArrayList<User> userList = getIntent().getParcelableArrayListExtra("userList");



        //get data from db for recycler view
       /* mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userId = currentUser.getUid();
        FirebaseDatabase dbRef = FirebaseDatabase.getInstance();
        DatabaseReference itemsRef = dbRef.getReference("Users");

        itemsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User newUser = snapshot.getValue(User.class);

                    if(newUser.getId().equals(userId))
                    {

                        name.setText(newUser.getName());

                        String dpUrl = newUser.getDisplaypic();
                        if (!dpUrl.equals("")) {
                            Picasso.get().load(dpUrl).into(profilePic);
                        }

                        String coverUrl = newUser.getCoverpic();
                        if (!coverUrl.equals("")) {
                            Picasso.get().load(coverUrl).into(coverPic);
                        }


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });*/

        ImageView back= findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(profile.this, dashboard.class);
                startActivity(intent);
            }
        });
        ImageView changeprof= findViewById(R.id.changedp);
        ImageView changecover= findViewById(R.id.changecover);
        changeprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, DP_REQUEST_CODE);
            }
        });
        changecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, COVER_REQUEST_CODE);
            }
        });

        ImageView imageView = findViewById(R.id.home);
        ImageView imageView2 = findViewById(R.id.chat);
        ImageView imageView3 = findViewById(R.id.search);
        ImageView imageView4 = findViewById(R.id.icon);
        //List<itemcard> itemList = new ArrayList<>();

       // itemList = getIntent().getParcelableArrayListExtra("itemList");


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(profile.this, dashboard.class);
                startActivity(intent);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(profile.this, chat.class);
                startActivity(intent);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(profile.this, search.class);
                startActivity(intent);
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(profile.this, EditProfile.class);

                intent.putExtra("id", ownerId);
                startActivityForResult(intent,EDIT_PROF_CODE);
            }
        });

        //RecyclerView recyclerView = findViewById(R.id.rv1);
       // RecyclerView recyclerView2 = findViewById(R.id.rv2);

        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);



       // layoutManager.setOrientation(RecyclerView.HORIZONTAL);

        /*LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);



        layoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView2.setLayoutManager(layoutManager2);



        List<itemcard> itemList3 = new ArrayList<>();
        //itemList3.add(new itemcard(bitmap, "Item 5", "$15/hr", 1500, "10th Mar"));
        //itemList3.add(new itemcard(bitmap, "Item 6", "$9/hr", 600, "11th Mar"));

        recycleadapter adapter2 = new recycleadapter(itemList, this);
        recyclerView2.setAdapter(adapter2);
        adapter = new recycleadapter(itemList, this);
        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(adapter); */


    }




    protected  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_PROF_CODE && resultCode == RESULT_OK && data != null) {

            userName = data.getStringExtra("userName");
            userEmail = data.getStringExtra("userEmail");
            userContact = data.getStringExtra("userContact");

            name.setText(userName);


        }

        if (requestCode == DP_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri selectedImg = data.getData();
            String photoUrl = selectedImg.toString();


            Picasso.get().load(photoUrl).into(profilePic);



            String postData = null;
            try {
                postData = "id=" + URLEncoder.encode(ownerId, "UTF-8") +
                  "&profileurl=" + URLEncoder.encode(photoUrl, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            new UpdateUserTask().execute(postData);


        }

        if (requestCode == COVER_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri selectedImg = data.getData();
            String photoUrl = selectedImg.toString();

            Picasso.get().load(photoUrl).into(coverPic);



            String postData = null;
            try {
                postData = "id=" + URLEncoder.encode(ownerId, "UTF-8") +
                        "&coverurl=" + URLEncoder.encode(photoUrl, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            new UpdateUserTask().execute(postData);


        }


    }

    private class UpdateUserTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = "http://192.168.100.19/assignment/updateuser.php"; // Change to your PHP script URL

            try {
                // Extract the postData string from the parameters
                String postData = params[0];

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
                    // Handle error cases
                    return "Error sending message. Response code: " + responseCode;
                }

                // Return the response from the server
                Log.d("Ramsha", "Response: " + response.toString());
                return response.toString();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        protected void onPostExecute(String result) {
            // Handle the result if needed
        }
    }


    private recycleadapter adapter;
}
