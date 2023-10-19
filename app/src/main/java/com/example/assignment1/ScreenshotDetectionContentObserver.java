package com.example.assignment1;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
        // You can perform any actions you need here, such as showing a notification
        // or sending a broadcast to your activity
        // For example:
        // sendBroadcast(new Intent("screenshot_taken"));
        Toast.makeText(context, "Screenshot taken", Toast.LENGTH_SHORT).show();
        Log.d("ScreenshotDetection", "Screenshot taken");

        sendPushNotification();


        // Check if the URI contains the screenshot
        Log.d("uri is:::", " uri issssssssssss : " + uri);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference("Screenshots").child(System.currentTimeMillis() + "screenshot.png");
//        reference.putFile(uri);


    }


    private void sendPushNotification() {
        String ONESIGNAL_APP_ID = "28ccc6da-66e0-4a95-a5ac-8ebaf785250c"; // Replace with your OneSignal App ID
        String REST_API_KEY = "NTRkMDQ5NGQtMzcyNy00NjkzLWFiNmItMTA5Y2M1ZWQ5MzVj"; // Replace with your OneSignal REST API Key

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

//        try {
//            Response response = client.newCall(request).execute();
//            if (response.isSuccessful()) {
//                Log.d("ScreenshotDetection", "Push notification sent successfully");
//            } else {
//                Log.e("ScreenshotDetection", "Error sending push notification");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("ScreenshotDetection", "Error sending push notification");
//        }
    }

//    private void showScreenshotTakenNotification() {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("Screenshot Taken")
//                .setContentText("A screenshot was taken.")
//                .setAutoCancel(true);
//
//        Notification notification = builder.build();
//
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        if (notificationManager != null) {
//            notificationManager.notify(0, notification);
//        }
//    }
}
