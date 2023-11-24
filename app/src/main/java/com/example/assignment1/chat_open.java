package com.example.assignment1;

import static com.android.volley.Request.Method.POST;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.media.tv.TvContract;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sinch.android.rtc.SinchClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import com.sinch.android.rtc.calling.CallController;
import com.sinch.android.rtc.calling.CallControllerListener;
import com.sinch.android.rtc.calling.CallListener;
import com.sinch.android.rtc.calling.MediaConstraints;
import com.sinch.android.rtc.internal.natives.jni.Call;


public class chat_open extends AppCompatActivity {

    private static final int READ_STORAGE_PERMISSION_REQUEST_CODE = 1;

    String count;
    RecyclerView rv;
    MyAdapter adapter;
    sqlitehelper dbHelper ;
    List<Model> ls;
    EditText reply;
    TextView send;
    ImageView imgButton, voice_note_button, callButton , camera, videocall;
    String currentPhotoPath;
    private SinchClient sinchClient = null;

    private static final int GALLERY_REQUEST_CODE = 200; // Request code for image picker
    private static final int CAMERA_REQUEST_CODE = 505; // Request code for camera

    private static final int VOICE_RECORD_REQUEST_CODE = 201; // Request code for voice recording
    private MediaRecorder mediaRecorder;
    private String voiceNoteFileName;

//    String dpurl = "https://tinyurl.com/4xz7w35b";
    String dpurl = "https://tinyurl.com/5cat47p5";
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_open);
        dbHelper = new sqlitehelper(this);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION_REQUEST_CODE);
        }

        //Permission for reading external storage
        if (!checkPermissionForReadExternalStorage()) {
            try {
                requestReadExternalStoragePermission();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        // SCREENSHOT DETECTION
        ScreenshotDetectionContentObserver contentObserver = new ScreenshotDetectionContentObserver(new Handler(), chat_open.this);
        getContentResolver().registerContentObserver(Uri.parse("content://media/external_primary/images/media"), true, contentObserver);

        rv = findViewById(R.id.rv);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(chat_open.this);
        ls = new ArrayList<>();
        adapter = new MyAdapter(chat_open.this, ls);
        rv.setAdapter(adapter);
        rv.setLayoutManager(lm);

        reply = findViewById(R.id.reply1);
        send = findViewById(R.id.send);
        imgButton = findViewById(R.id.img);
        voice_note_button = findViewById(R.id.voice_note);
        camera = findViewById(R.id.open_camera);
        callButton = findViewById(R.id.callbutton);
        videocall = findViewById(R.id.video_button);
        // Initialize Volley RequestQueue
        requestQueue = Volley.newRequestQueue(this);
        // Reloading Previous Messages
        String getMessagesUrl = "http://192.168.10.8/assignment3/getmsg.php";
        //new GetMessagesTask().execute(getMessagesUrl);
        uploaddata();
        //Sending NEW MSGS
        String url = "http://192.168.10.8/assignment3/insertmsg.php";

        //Text Message
        send.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                String msg = reply.getText().toString();
                if (!msg.isEmpty()) {
                    // Generate the message ID using the count variable
                    count = String.valueOf(System.currentTimeMillis());
                    String msg_id = "message_" + count;
                    Model model = new Model(msg, dpurl, getCurrentTimestamp(), "text");
                    model.setMessageId(msg_id);
                    ls.add(model);
                    adapter.notifyDataSetChanged();
                    reply.setText("");
                    // Call AsyncTask to send the message to the server
                    new SendMessageTask().execute(model);
                }
            }
        });

        //IMAGE SEND
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });

        //CAMERA IMAGE CAPTURE
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        });

        //VOICE NOTE
        voice_note_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaRecorder == null) {
                    startRecording();
                    voice_note_button.setImageResource(R.drawable.pause);
                } else {
                    stopRecording();
                    voice_note_button.setImageResource(R.drawable.voice);
                }
            }
            private void startRecording() {
                Log.d("STARTTTTTTTTTTTTTTTTT", "startRecording: ");
                // Check for audio recording permission at runtime
                if (ContextCompat.checkSelfPermission(chat_open.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(chat_open.this, new String[]{Manifest.permission.RECORD_AUDIO}, VOICE_RECORD_REQUEST_CODE);
                    return;
                }
                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                // Use a timestamp to create a unique filename
                voiceNoteFileName = "voice_" + System.currentTimeMillis() + ".3gp";
                mediaRecorder.setOutputFile(getFilesDir() + File.separator + voiceNoteFileName);


//                mediaRecorder.setOutputFile(voiceNoteFileName);
                try {
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            private void stopRecording() {
                Log.d("STOPPPPPPPPPPPPPPPPPPP", "stopRecording: ");
                if (mediaRecorder != null) {
//                  mediaRecorder.stop();
                    mediaRecorder.reset();
                    mediaRecorder.release();
                    mediaRecorder = null;
                    // Upload the recorded voice note to Firebase
                    if (voiceNoteFileName != null) {
                        // Create a new Model object with messageText, timestamp, and DP URL
                        String audioFileUrl = getFilesDir() + File.separator + voiceNoteFileName;
                        Log.d("AUDIOOOOOOOOOOOOOOOOO", "stopRecording: " + audioFileUrl);
                        Model voice_note_msg = new Model(audioFileUrl, dpurl, getCurrentTimestamp(), "AUDIO");
                        ls.add(voice_note_msg);
                        adapter.notifyDataSetChanged();

                        // Call AsyncTask to send the message to the server
                        new SendMessageTask().execute(voice_note_msg);
                    }
                }
            }
        });

        //Call Using Sinch
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(chat_open.this, "Call Triggered", Toast.LENGTH_SHORT).show();
                // Code to initiate the call
                try {
                    initiateCall();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            private void initiateCall() throws IOException {
                Toast.makeText(chat_open.this, "Initiating Call", Toast.LENGTH_SHORT).show();
                // Add SinchClient listener to handle events
//                sinchClient.start();

//                Log.d("sinch Client ", "sinchclient VALLLL :  "+ sinchClient);
                if (sinchClient == null) {
//                  Ensure that the SinchClient has been initialized properly
                    sinchClient = SinchClient.builder()
                            .context(chat_open.this)
                            .userId("ONL2310_a1SbKk")
                            .applicationKey("c65d962e-0c84-4ffc-8171-f7e8eb1a65e1")
                            .environmentHost("sandbox.sinch.com")
//                           .environmentHost("clientapi.sinch.com")
                            .build();
                    Toast.makeText(chat_open.this, "sinchClient" + sinchClient, Toast.LENGTH_SHORT).show();
                }
                sinchClient.addSinchClientListener(new SinchClientListener());

                if (!sinchClient.isStarted()) {
                    // Start the SinchClient if it's not already started
//                    sinchClient.start();
                }

                String caller_id = "+447520662012"; // Test number for your product

//                CallClient callClient = (CallClient) sinchClient.getCallController();
//                Call call = callClient.createOutgoingCall(caller_id);

                CallController callController = sinchClient.getCallController();
//                Call call = (Call) callController.callUser(caller_id, new MediaConstraints(false));

//              CallController to act on the incoming call
//              As calls come into device CallControllerListener.onIncomingCall() will be executed.
                callController.addCallControllerListener(new CallControllerListener() {
                    @Override
                    public void onIncomingCall(@NonNull CallController callController, @NonNull com.sinch.android.rtc.calling.Call call) {
                        call.addCallListener(new CallListener() {
                            @Override
                            public void onCallProgressing(@NonNull com.sinch.android.rtc.calling.Call call) {
                                Toast.makeText(chat_open.this, "Calling . . .", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCallEstablished(@NonNull com.sinch.android.rtc.calling.Call call) {
                                Toast.makeText(chat_open.this, "Call Established", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCallEnded(@NonNull com.sinch.android.rtc.calling.Call call) {
                                Toast.makeText(chat_open.this, "Call Ended", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

        //Video Call Using Sinch
        videocall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(chat_open.this, "Video Call Triggered", Toast.LENGTH_SHORT).show();
                // Code to initiate the call
                try {
                    initiateCall();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            private void initiateCall() throws IOException {
                Toast.makeText(chat_open.this, "Initiating Video Call", Toast.LENGTH_SHORT).show();
                // Add SinchClient listener to handle events
//                sinchClient.start();

                if (sinchClient == null) {
//                  Ensure that the SinchClient has been initialized properly
                    sinchClient = SinchClient.builder()
                            .context(chat_open.this)
                            .userId("+923215197176")
                            .applicationKey("c65d962e-0c84-4ffc-8171-f7e8eb1a65e1")
                            .environmentHost("clientapi.sinch.com")
//                           .environmentHost("clientapi.sinch.com")
                            .build();
                    Toast.makeText(chat_open.this, "sinchClient" + sinchClient, Toast.LENGTH_SHORT).show();
                }
                sinchClient.addSinchClientListener(new SinchClientListener());
//                // Start the SinchClient

//                if (!sinchClient.isStarted()) {
//                    // Start the SinchClient if it's not already started
//                    sinchClient.start();
//                }

                String caller_id = "+447520662012"; // Test number for your product

//                CallClient callClient = (CallClient) sinchClient.getCallController();
//                Call call = callClient.createOutgoingCall(caller_id);

                CallController callController = sinchClient.getCallController();
//                Call call = (Call) callController.callUser(caller_id, new MediaConstraints(true));

//              CallController to act on the incoming call
//              As calls come into device CallControllerListener.onIncomingCall() will be executed.
                callController.addCallControllerListener(new CallControllerListener() {
                    @Override
                    public void onIncomingCall(@NonNull CallController callController, @NonNull com.sinch.android.rtc.calling.Call call) {
                        call.addCallListener(new CallListener() {
                            @Override
                            public void onCallProgressing(@NonNull com.sinch.android.rtc.calling.Call call) {
                                Toast.makeText(chat_open.this, "Calling . . .", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCallEstablished(@NonNull com.sinch.android.rtc.calling.Call call) {
                                Toast.makeText(chat_open.this, "Call Established", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCallEnded(@NonNull com.sinch.android.rtc.calling.Call call) {
                                Toast.makeText(chat_open.this, "Call Ended", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//            img_msg = findViewById(R.id.img_msg);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            String imageUrl = selectedImageUri.toString();
//                img_msg.setImageURI(selectedImageUri);
            // Create a new Model object with the image URI, timestamp, and DP URL
            Model imageMessage = new Model(imageUrl, dpurl, getCurrentTimestamp(), "IMAGE");
//                Model message = new Model(selectedImageUri.toString(), dpurl, getCurrentTimestamp(), "IMAGE");
            ls.add(imageMessage);
            adapter.notifyDataSetChanged();
            // Call AsyncTask to send the message to the server
            new SendMessageTask().execute(imageMessage);
        }
        else  if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            // Convert the Bitmap to a Uri
            Uri imageUri = getImageUri(getApplicationContext(), imageBitmap);
            if (imageUri != null) {
                String imageUrl = imageUri.toString();
                // Create a new Model object with the image URI, timestamp, and DP URL
                Model imageMessage = new Model(imageUrl, dpurl, getCurrentTimestamp(), "IMAGE");
                // Push the new image message to the Firebase Realtime Database
                ls.add(imageMessage);
                adapter.notifyDataSetChanged();
                new SendMessageTask().execute(imageMessage);
            }
        }
    }

    private Uri getImageUri(Context applicationContext, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    private class SendMessageTask extends AsyncTask<Model, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Model... params) {
            // The URL of your PHP script on the server
            String myurl = "http://192.168.10.8/assignment3/insertmsg.php";

            try {
                String postData = "msg=" + URLEncoder.encode(params[0].getMsg(), "UTF-8") +
                        "&dpurl=" + URLEncoder.encode(params[0].getDp(), "UTF-8") +
                        "&timestamp=" + URLEncoder.encode(params[0].getTimestamp(), "UTF-8") +
                        "&msg_type=" + URLEncoder.encode(params[0].getMessageType(), "UTF-8");

                // Create a URL object
                URL url = new URL(myurl);
                // Create an HTTP connection
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
                    Log.d("HELLOOOOOOO", "postData: " + postData);

                } catch (Exception e) {
                    Log.d("ERRRRRRRRRRORRRR WRITINGG", "doInBackground: " + e.getMessage());
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
                        Log.d("LINEEEEE", "line: " + line);
                        response.append(line);
                    }
                    bufferedReader.close();
                } else {
                    // Handle error cases
                    return "Error sending message. Response code: " + responseCode;
                }
                // Return the response from the server
                Log.d("CHECKINGGGGGGG", "response: " + response.toString());

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
            Toast.makeText(chat_open.this, "Yayyy! Message Sent", Toast.LENGTH_SHORT).show();
        }
    }

    private String getCurrentTimestamp() {
        // Helper method to get the current timestamp as a string
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(new Date());
    }

    private class GetMessagesTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            String url = params[0];
            Log.d("URL ===", "url: " + url);

            StringRequest request = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("CHECKINGGGGGGG", "response: " + response);
                            // Parse the JSON response and update your chat UI
                            List<Model> storedMessages = parseMessagesFromJson(response);
                            ls.addAll(storedMessages);
                            dbHelper.deleteallitems();
                            dbHelper.insertallitems(storedMessages);
                            adapter.notifyDataSetChanged();
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

    private List<Model> parseMessagesFromJson(String jsonString) {
        List<Model> messages = new ArrayList<>();

        try {
            // Find the index of the first '{' character
            int startIndex = jsonString.indexOf('{');
            if (startIndex >= 0) {
                String jsonSubstring = jsonString.substring(startIndex);
                JSONObject jsonObject = new JSONObject(jsonSubstring);
                if (jsonObject.has("Messages")) {
                    JSONArray messagesArray = jsonObject.getJSONArray("Messages");
                    for (int i = 0; i < messagesArray.length(); i++) {
                        JSONObject jsonMessage = messagesArray.getJSONObject(i);
                        String msgId = jsonMessage.getString("msg_id");
                        String msg = jsonMessage.getString("msg");
                        String dpurl = jsonMessage.getString("dpurl");
                        String timestamp = jsonMessage.getString("timestamp");
                        String msgType = jsonMessage.getString("msg_type");
                        Model message = new Model(msg, dpurl, timestamp, msgType);
                        Log.d("Messageeeeeee", "message: " + message.msg);
                        message.setMessageId(msgId);
                        messages.add(message);
                    }
                } else {
                    Log.e("ERROR", "No 'Messages' key found in the JSON response");
                }
            } else {
                Log.e("ERROR", "No JSON data found in the response");
            }
        } catch (JSONException e) {
            Log.e("ERROR", "Error parsing JSON", e);
        }
        return messages;
    }

    public boolean checkPermissionForReadExternalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }
    public void requestReadExternalStoragePermission() throws Exception {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("REQ CODEE", "onRequestPermissionsResult: " + requestCode);
        if (requestCode == READ_STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, perform the required operation
            } else {
                // Permission denied, handle accordingly
            }
        }
    }


    private void uploaddata() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            String getMessagesUrl = "http://192.168.10.8/assignment3/getmsg.php";
            new GetMessagesTask().execute(getMessagesUrl);
            Log.d("Request", "helloooooofetch");




        } else {


            List<Model> itemList = dbHelper.getAllMessages();
            if (ls == null) {
                ls = new ArrayList<>();
            } else {
                ls.clear();
            }
            ls.addAll(itemList);
            Log.d("Request", "helloooooosqlite" + itemList);
            if (adapter == null) {

                adapter = new MyAdapter(chat_open.this, ls) ;
            }


            adapter.notifyDataSetChanged();


        }

    }

}
