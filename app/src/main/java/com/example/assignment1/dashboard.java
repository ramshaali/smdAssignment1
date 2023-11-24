package com.example.assignment1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class dashboard extends AppCompatActivity {
    RecyclerView recyclerView1;
    RecyclerView recyclerView2;

    List<itemcard> itemList1;
    FirebaseAuth mAuth;
    List<itemcard> itemList2;
    RequestQueue requestQueue;
    recycleadapter adapter1;
    recycleadapter adapter2;
    String ownerId;
    String name, purl,curl;
    sqlitehelper2 dbHelper ;
    private static final int ITEM_DETAIL_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Intent intent = getIntent();
        ownerId = intent.getStringExtra("id");
        dbHelper = new sqlitehelper2(this);
        List<User> userList = intent.<User>getParcelableArrayListExtra("userList");
        String namee= intent.getStringExtra("name");
        String purl= intent.getStringExtra("purl");
        String curl= intent.getStringExtra("curl");


        ImageView imageView = findViewById(R.id.chat);
        ImageView imageView2 = findViewById(R.id.prof);
        ImageView imageView3 = findViewById(R.id.search);
        ImageView imageView4 = findViewById(R.id.plus);
        TextView logout = findViewById(R.id.logout);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
       // ownerId = currentUser.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("items"); // Replace with your reference
        requestQueue = Volley.newRequestQueue(this);
        uploaddata();
        /* myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itemList1.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    itemcard item = snapshot.getValue(itemcard.class);
                    Log.d("Image Path", item.getImg());
                    itemList1.add(item);
                }
                // Update your RecyclerView adapter with the itemList here
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error
            }
        });


*/





        logout.setOnClickListener(view -> {
            mAuth.signOut();
            startActivity(new Intent(dashboard.this, LoginActivity.class));


        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(dashboard.this, chat.class);
                startActivity(intent);
            }
        });


        ImageView req= findViewById(R.id.req);
        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(dashboard.this, Requests.class);
                startActivity(intent);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(dashboard.this, profile.class);
                intent.putParcelableArrayListExtra("itemList", new ArrayList<>(itemList1));
                intent.putExtra("name", namee);
                intent.putExtra("purl", purl);
                intent.putExtra("curl", curl);
                startActivity(intent);
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(dashboard.this, search.class);
                // Pass the list of itemcards to the SearchResultsActivity
                intent.putExtra("curruserid", ownerId);
                intent.putParcelableArrayListExtra("itemList", new ArrayList<>(itemList1));

                startActivity(intent);

            }
        });




        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(dashboard.this, enteritem.class);

                startActivityForResult(intent,1);
            }
        });
         recyclerView1 = findViewById(R.id.rv1);
       recyclerView2 = findViewById(R.id.rv2);
        RecyclerView recyclerView3 = findViewById(R.id.rv3);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView1.setLayoutManager(layoutManager1);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView2.setLayoutManager(layoutManager2);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this);
        layoutManager3.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView3.setLayoutManager(layoutManager3);
       // Drawable drawable=getResources().getDrawable(R.drawable.baseline_arrow_forward_24);
        //BitmapDrawable bitdrawable=(BitmapDrawable) drawable;
        //Bitmap bitmap=bitdrawable.getBitmap();
         itemList1 = new ArrayList<>();
        String imageUrl = "android.resource://com.example.assignment1/" + R.drawable.camera;

        //itemList1.add(new itemcard(imageUrl, "Camera", "$10", 1000, "7th Mar", ownerId,"Nokia Camera"));
        //itemList1.add(new itemcard(imageUrl, "Chair", "$10", 750, "6th Mar", ownerId, "Sony Camera"));

         itemList2 = new ArrayList<>();
        //itemList2.add(new itemcard(bitmap, "Item 3", "$12/hr", 1200, "8th Mar"));
        //itemList2.add(new itemcard(bitmap, "Item 4", "$11/hr", 800, "9th Mar"));

        List<itemcard> itemList3 = new ArrayList<>();
        //itemList3.add(new itemcard(bitmap, "Item 5", "$15/hr", 1500, "10th Mar"));
        //itemList3.add(new itemcard(bitmap, "Item 6", "$9/hr", 600, "11th Mar"));

         adapter1 = new recycleadapter(itemList1, this, ownerId);
        recyclerView1.setAdapter(adapter1);

       //  adapter2 = new recycleadapter(itemList2, this);
       // recyclerView2.setAdapter(adapter2);

        //recycleadapter adapter3 = new recycleadapter(itemList3, this);
       // recyclerView3.setAdapter(adapter3);



    }


   /* @Override
    protected void onStart( ){
        super.onStart();

        FirebaseUser user=mAuth.getCurrentUser();
        if(user==null){
            startActivity(new Intent(dashboard.this, LoginActivity.class));
        }

    }*/






    private class FetchItemsTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String url = "http://192.168.100.19/assignment/get.php"; // Replace with the actual URL
            Log.d("Request", "helloooooo");
            Log.d("Request", "URL: " + url);;
            Log.d("URL ===", "url: " + url);

            StringRequest request = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("CHECKINGGGGGGG", "response: " + response);
                            // Parse the JSON response and update your chat UI
                            List<itemcard> itemList = parseMessagesFromJson(response);

                            // Update the UI with the fetched items
                            if (itemList1 == null) {
                                itemList1 = new ArrayList<>();
                            } else {
                                itemList1.clear(); // Now you can safely call clear() on the initialized list
                            }
                            itemList1.addAll(itemList);
                            dbHelper.deleteallitems();
                            dbHelper.insertallitems(itemList1);
                            Log.d("Request", "helloooooo" + itemList);
                            adapter1.notifyDataSetChanged();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("ERROR", "Error getting messages", error);
                            // Handle error response
                        }
                    });

            // Add the request to the RequestQueue
            requestQueue.add(request);
            return null;
        }
    }

    private List<itemcard> parseMessagesFromJson(String jsonString) {
        List<itemcard> itemList = new ArrayList<>();

        try {
            // Remove any non-JSON content at the beginning
            int startIndex = jsonString.indexOf('{');
            if (startIndex >= 0) {
                String jsonSubstring = jsonString.substring(startIndex);
                JSONObject jsonObject = new JSONObject(jsonSubstring);

                int status = jsonObject.getInt("Status");
                if (status == 1 && jsonObject.has("Items")) {
                    JSONArray itemsArray = jsonObject.getJSONArray("Items");
                    for (int i = 0; i < itemsArray.length(); i++) {
                        JSONObject itemJson = itemsArray.getJSONObject(i);

                        // Replace these lines with your actual column names
                        String id = itemJson.getString("id");
                        String name = itemJson.getString("name");
                        String imgUrl = itemJson.getString("imgurl");
                        String price = itemJson.getString("price");
                        String date = itemJson.getString("date");
                        int views = itemJson.getInt("views");
                        String ownerid = itemJson.getString("ownerid");
                        String description = itemJson.getString("description");

                        itemcard newItem = new itemcard(imgUrl, name, price, views, date, ownerid, description);
                        newItem.setid(id);
                        itemList.add(newItem);
                        Log.d("Request", "helloooooo" + id);
                    }
                } else {
                    Log.e("ERROR", "Invalid Status or no 'Items' key found in the JSON response");
                }
            } else {
                Log.e("ERROR", "No JSON data found in the response");
            }
        } catch (JSONException e) {
            Log.e("ERROR", "Error parsing JSON", e);
        }
        return itemList;
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onresume", "onresume function: " );
        refreshItems();
    }

    private void refreshItems() {
        //new FetchItemsTask().execute();
      uploaddata() ;

    }
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1 && resultCode==RESULT_OK &&data!=null){

            String Url=data.getStringExtra("img");
            String name = data.getStringExtra("name");
            String price = data.getStringExtra("price");
            int views = data.getIntExtra("views", 0);
            String date = data.getStringExtra("date");
            String desc=data.getStringExtra("desc");


            itemcard newItem = new itemcard(Url, name, price, views, date,ownerId,desc);


           itemList1.add(newItem);
            itemList2.add(newItem);


            recyclerView1.getAdapter().notifyDataSetChanged();
            //recyclerView2.getAdapter().notifyDataSetChanged();
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void uploaddata() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            new FetchItemsTask().execute();
            Log.d("Request", "helloooooofetch");




        } else {


            List<itemcard> itemList = dbHelper.getAllItems();
            if (itemList1 == null) {
                itemList1 = new ArrayList<>();
            } else {
                itemList1.clear();
            }
            itemList1.addAll(itemList);
            Log.d("Request", "helloooooosqlite" + itemList);
            if (adapter1 == null) {

                adapter1 = new recycleadapter(itemList1, this, ownerId);
            }


            adapter1.notifyDataSetChanged();


        }

    }

}
