package com.example.assignment1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.MediaStore;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;

import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ScreenshotDetectionContentObserver  extends ContentObserver {

    private Context context;

    public ScreenshotDetectionContentObserver(Handler handler, Context context) {
        super(handler);
        this.context = context;
        Log.d("ScreenshotDetection", "Screenshot CHECKING CONSTRUCTOR");

    }

    public boolean deliverSelfNotifications() {
        return super.deliverSelfNotifications();
    }

    @Override
    public void onChange(boolean selfChange) {
        onChange(selfChange, null);
    }

    public void onChange(boolean selfChange, Uri uri) {
        // This method is called when a screenshot is taken
        // sendBroadcast(new Intent("screenshot_taken"));
        Toast.makeText(context, "Screenshot taken", Toast.LENGTH_SHORT).show();
        Log.d("ScreenshotDetection", "Screenshot taken");
        try {
            sendPushNotification(uri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void sendPushNotification(Uri screenshoturi) throws IOException {

        String ONESIGNAL_APP_ID = "f8dfe0ea-6564-4f00-8598-994eb661c71a"; // Replace with your OneSignal App ID
        String REST_API_KEY = "YzM1ZmIzMDktZDFhYy00MTcyLThlMzItNDFmZTgwNWMxYjkw"; // Replace with your OneSignal REST API Key
        // Define custom data to be sent with the push notification
        JSONObject customData = new JSONObject();
        try {
            customData.put("screenshot_detected", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create the push notification payload
        JSONObject notificationPayload = new JSONObject();
        try {
            notificationPayload.put("app_id", ONESIGNAL_APP_ID);
            notificationPayload.put("contents", new JSONObject().put("en", "A screenshot was taken."));
            notificationPayload.put("included_segments", new JSONArray().put("All"));
            notificationPayload.put("data", customData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Send the push notification to OneSignal using OkHttp
        OkHttpClient client = new OkHttpClient();
        String url = "https://onesignal.com/api/v1/notifications";
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), notificationPayload.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", "Basic " + REST_API_KEY)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e("ScreenshotDetection", "Error sending push notification");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.d("ScreenshotDetection", "Push notification sent successfully");
                } else {
                    Log.e("ScreenshotDetection", "Error sending push notification");
                }
            }
        });
        new SendScreenshotTask().execute(screenshoturi);

    }
    private class SendScreenshotTask extends AsyncTask<Uri, Void, Void> {
        @Override
        protected Void doInBackground(Uri... uris) {
            // The URL of your PHP script on the server
            String myurl = "http://192.168.10.8/assignment3/screenshot.php";
            // Extract the Uri from the array
            Uri screenshotUri = uris[0];
            Log.d("ScreenshotDetection", "screenshotUri: " + screenshotUri.toString());

            String postData = null;
            try {
                postData = "img=" + URLEncoder.encode(screenshotUri.toString(), "UTF-8");
                Log.d("ScreenshotDetection", "postData: " + postData);
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
                    Log.d("ERRRRRRRRRRORRRR", "doInBackground: " + responseCode);
                    throw new RuntimeException("Response code:" + responseCode);
                }

            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            } catch (ProtocolException e) {
                throw new RuntimeException(e);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Toast.makeText(context, "Successful Insertion", Toast.LENGTH_SHORT).show();
        }
    }
}
