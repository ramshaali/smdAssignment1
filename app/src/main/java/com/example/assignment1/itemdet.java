package com.example.assignment1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
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
        TextView desc=findViewById(R.id.desc);
        name.setText(getIntent().getStringExtra("name"));
        date.setText(getIntent().getStringExtra("date"));
        price.setText(getIntent().getStringExtra("price"));
        desc.setText(getIntent().getStringExtra("desc"));

        String imageUrl = getIntent().getStringExtra("img");
        Picasso.get().load(imageUrl).into(img);

        String ownerId= getIntent().getStringExtra("ownerid");
        String itemid= getIntent().getStringExtra("id");
        String curr_id= getIntent().getStringExtra("curruserid");


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
        //String curr_id= FirebaseAuth.getInstance().getCurrentUser().getUid();

        if(curr_id.equals(ownerId)){
            del.setVisibility(View.VISIBLE);
        }else{
            del.setVisibility(View.GONE);
        }

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              FirebaseDatabase database= FirebaseDatabase.getInstance();
                DeleteItemTask deleteItemTask = new DeleteItemTask();
                deleteItemTask.execute(itemid);

              /*  DatabaseReference ref=database.getReference("items");
                DatabaseReference deleteitem=ref.child(itemid);
                new DeleteItemTask().execute(itemid);

                deleteitem.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(itemdet.this,"Item Deleted",Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(itemdet.this,"Fail",Toast.LENGTH_LONG).show();

                        }
                    }
                });*/

                finish();
            }
        });


        ImageView imageView4= findViewById(R.id.chat);

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(itemdet.this, chat_open.class);
                startActivity(intent);
            }
        });


    }

  public static class DeleteItemTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String itemId = params[0];
            String deleteUrl = "http://192.168.100.19/assignment/delete.php";

            try {
                URL url = new URL(deleteUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                // Prepare the data to be sent
                String postData = "id=" + itemId;

                // Write data to the connection
                OutputStream os = connection.getOutputStream();
                os.write(postData.getBytes());
                os.flush();
                os.close();

                // Get the response from the server
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }

                    in.close();
                    return response.toString();
                } else {
                    return "Error: " + responseCode;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Handle the result after the task completes
            Log.d("DeleteItemTask", "Result: " + result);

            // You can implement further logic based on the result
            // For example, show a Toast or update UI
        }
    }


}