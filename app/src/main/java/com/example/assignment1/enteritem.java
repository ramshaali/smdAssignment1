package com.example.assignment1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class enteritem extends AppCompatActivity {
    private final int code_gallery=1000;
    Bitmap bitmap;
    ImageView imageView1;
    String imageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enteritem);

         imageView1 = findViewById(R.id.cam);
        ImageView imageView2 = findViewById(R.id.cam2);
        Button b = findViewById(R.id.btn);

        TextView tv =findViewById(R.id.gallery);
        TextView vid = findViewById(R.id.video);
        vid.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("video/*");
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
               // Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, code_gallery);


            }
        });

       /* imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(enteritem.this, photo.class);
                startActivity(intent);
            }
        });*/

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
                EditText desc=findViewById(R.id.desc);
                EditText  itemprice=findViewById(R.id.rate);

                String name = itemname.getText().toString();
                String price = itemprice.getText().toString();
                String descc=desc.getText().toString();
                int views = 1;
                String date = getCurrentDate();

                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();


                DatabaseReference itemsRef = rootRef.child("items");


                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String ownerId = currentUser.getUid();
                Uri uri = Uri.parse(imageUrl);
                String imagePath = getImagePathFromUri(uri);
                String  count = String.valueOf(System.currentTimeMillis()) ;
                String itemId= count+name;
                itemcard newItem = new itemcard(imageUrl, name, price, views, date, ownerId,descc);
                newItem.setid(itemId);

                new postitemTask().execute(newItem);



              /*  itemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            // Create the "items" node and push the new item
                            String itemId = itemsRef.push().getKey();
                            newItem.setid(itemId);
                            itemsRef.child(itemId).setValue(newItem);
                            sendPostRequest(itemId, name, imageUrl, price, date, Integer.toString(views), ownerId, descc);
                        } else {
                            // The "items" node exists, push the new item
                            String itemId = itemsRef.push().getKey();
                            newItem.setid(itemId);

                            itemsRef.child(itemId).setValue(newItem);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                }); */





                Intent resultIntent = new Intent();
                //resultIntent.putExtra("img", bytearray);
                resultIntent.putExtra("img", imageUrl);

                resultIntent.putExtra("name", name);
                resultIntent.putExtra("price", price);
                resultIntent.putExtra("views", views);
                resultIntent.putExtra("date", date);
                resultIntent.putExtra("desc", descc);


                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });


    }
    private class postitemTask extends AsyncTask<itemcard, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(itemcard... params) {
            // The URL of your PHP script on the server
            String url = "http://192.168.100.19/assignment/insert.php";

            try {
                String postData = "id=" + URLEncoder.encode(params[0].getid(), "UTF-8") +
                        "&name=" + URLEncoder.encode(params[0].getName(), "UTF-8") +
                        "&imgurl=" + URLEncoder.encode(params[0].getImg(), "UTF-8") +
                        "&price=" + URLEncoder.encode(params[0].getPrice(), "UTF-8") +
                        "&date=" + URLEncoder.encode(params[0].getDate(), "UTF-8") +
                        "&views=" + URLEncoder.encode(String.valueOf(params[0].getViews()), "UTF-8") +
                        "&ownerid=" + URLEncoder.encode(params[0].getOwnerid(), "UTF-8") +
                        "&description=" + URLEncoder.encode(params[0].getDesc(), "UTF-8");

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

        }
    }


    private String getCurrentDate() {
        // Create a date object
        Date currentDate = new Date();

        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // Format the date and store it as a string
        return dateFormat.format(currentDate);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == code_gallery && data != null) {
            Uri selectedImage = data.getData();

            imageUrl = selectedImage.toString();
            imageView1.setImageURI(selectedImage);
        }
    }


    public String getImagePathFromUri(Uri uri) {
        String path = null;
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);
            cursor.close();
        }
        return path;
    }


}