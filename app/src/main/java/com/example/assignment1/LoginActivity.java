package com.example.assignment1;

import static com.android.volley.toolbox.Volley.newRequestQueue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    public Button button;
    EditText email,pass;

    FirebaseAuth mAuth;
    List<User> itemList1;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView textView = findViewById(R.id.textsign);
        TextView textView2 = findViewById(R.id.textforgot);
        mAuth=FirebaseAuth.getInstance();
        Button login = findViewById(R.id.btn);
         email= findViewById(R.id.email);
         pass= findViewById(R.id.pass);
         itemList1=new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(LoginActivity.this, signup.class);
                startActivity(intent);
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to TargetActivity
                Intent intent = new Intent(LoginActivity.this, forgotpass.class);
                startActivity(intent);
            }
        });


      /*  Button button = findViewById(R.id.btn);

        // Set an OnClickListener for the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to NextActivity
                Intent intent = new Intent(LoginActivity.this, dashboard.class);
                startActivity(intent);
            }
        });*/


         login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* mAuth.signInWithEmailAndPassword(
                                email.getText().toString(),
                                pass.getText().toString()
                        )
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(LoginActivity.this,"Sign In Successful",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, dashboard.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this,"Sign In Failed",Toast.LENGTH_LONG).show();

                            }
                        });*/

                String useremail = email.getText().toString();
                String userpass = pass.getText().toString();

                // Check if email and password are not empty
                if (!TextUtils.isEmpty(useremail) && !TextUtils.isEmpty(userpass)) {
                    // Execute the fetch users task
                    new FetchUsersTask(useremail, userpass).execute();
                } else {
                    Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    private class FetchUsersTask extends AsyncTask<String, Void, Void> {
        private final String useremail;
        private final String userpass;

        public FetchUsersTask(String useremail, String userpass) {
            this.useremail = useremail;
            this.userpass = userpass;
        }

        @Override
        protected Void doInBackground(String... params) {
            String url = "http://192.168.100.19/assignment/getuser.php"; // Replace with the actual URL
            Log.d("Request", "URL: " + url);

            StringRequest request = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("CHECKINGGGGGGG", "response: " + response);

                            // Parse the JSON response and update your user list
                            List<User> userList = parseUsersFromJson(response);

                            // Check if the entered email and password match any user in the list
                            boolean isLoginSuccessful = false;
                            User currentUser=null;
                            for (User user : userList) {
                                if (user.getEmail().equals(useremail) && user.getPass().equals(userpass)) {
                                    currentUser   = user;
                                    isLoginSuccessful = true;
                                    break;
                                }
                            }

                            // Proceed to login if successful, otherwise show a toast
                            if (isLoginSuccessful) {
                                Toast.makeText(LoginActivity.this, "Sign In Successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, dashboard.class);
                                intent.putExtra("id", currentUser.getId());
                                intent.putExtra("name", currentUser.getName());
                                intent.putExtra("purl", currentUser.getDisplaypic());
                                intent.putExtra("curl", currentUser.getCoverpic());
                                intent.putParcelableArrayListExtra("userList", (ArrayList<? extends Parcelable>) new ArrayList<>(userList));

                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Sign In Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("ERROR", "Error getting user data", error);
                            // Handle error response
                        }
                    });

            // Add the request to the RequestQueue
            requestQueue.add(request);
            return null;
        }

        // Parse the JSON response into a list of User objects

    }


    private List<User> parseUsersFromJson(String jsonString) {
        List<User> itemList = new ArrayList<>();

        try {
            // Remove any non-JSON content at the beginning
            int startIndex = jsonString.indexOf('{');
            if (startIndex >= 0) {
                String jsonSubstring = jsonString.substring(startIndex);
                JSONObject jsonObject = new JSONObject(jsonSubstring);

                int status = jsonObject.getInt("Status");
                if (status == 1 && jsonObject.has("Users")) {
                    JSONArray itemsArray = jsonObject.getJSONArray("Users");
                    for (int i = 0; i < itemsArray.length(); i++) {
                        JSONObject itemJson = itemsArray.getJSONObject(i);
                        String id = itemJson.getString("id");
                        String name = itemJson.getString("name");
                        String email = itemJson.getString("email");
                        String contact = itemJson.getString("contact");
                        String purl = itemJson.getString("profileurl");
                        String curl = itemJson.getString("coverurl");

                        String pass= itemJson.getString("pass");


                        User newItem = new User(name, email, contact,id);
                        newItem.setPass(pass);
                        newItem.setCoverpic(curl);
                        newItem.setDisplaypic(purl);
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
}