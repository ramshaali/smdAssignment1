package com.example.assignment1;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<Model> ls;
    String dpurl;
    private static final int VIEW_TYPE_TEXT = 1;
    private static final int VIEW_TYPE_IMAGE = 2;
    private static final int VIEW_TYPE_AUDIO = 3;
    RequestQueue requestQueue ;


    public MyAdapter(Context context, List<Model> ls) {
        this.context=context;
        this.ls=ls;
        this.requestQueue = Volley.newRequestQueue(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View row= LayoutInflater.from(context).inflate(R.layout.row,parent,false);
//        return new MyViewHolder(row);

        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == VIEW_TYPE_TEXT) {
            View itemView = inflater.inflate(R.layout.row, parent, false);
            return new TextMessageViewHolder(itemView);
        } else if (viewType == VIEW_TYPE_IMAGE) {
            View itemView = inflater.inflate(R.layout.image, parent, false);
            return new ImageMessageViewHolder(itemView);
        }
        else if (viewType == VIEW_TYPE_AUDIO) {
            View itemView = inflater.inflate(R.layout.audio, parent, false);
            return new AudioMessageViewHolder(itemView);
        }

        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Model message = ls.get(position);

        if (holder instanceof TextMessageViewHolder) {
            TextMessageViewHolder textHolder = (TextMessageViewHolder) holder;
            textHolder.msg.setText(message.getMsg());
            textHolder.timestamp.setText(message.getTimestamp());
            // Load profile image if available
            if (message.getDp() != null && !message.getDp().isEmpty()) {
                Picasso.get().load(message.getDp()).into(textHolder.dp);
            }
            //============ EDIT FUNCTIONALITY =============
            textHolder.edit_msg.setTag(position); // Set the position as a tag to the edit button
            textHolder.edit_msg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag(); // Retrieve the position from the tag
                    // Handle message editing for the clicked message
                    openEditMessageDialog(message);
                }

                private void openEditMessageDialog(Model message) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Edit Message");

                    final EditText editMessageInput = new EditText(context);
                    editMessageInput.setText(message.getMsg());
                    builder.setView(editMessageInput);

                    builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String editedMessage = editMessageInput.getText().toString();
                            //This will update the UI
                            String msg_timestamp = message.getTimestamp();
                            Log.d("Message Timestamp", "Message Timestamp: " + msg_timestamp);
                            message.setMsg(editedMessage);
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                            String timestamp = sdf.format(new Date());
                            message.setTimestamp(timestamp); //setting New Timestamp
                            // Checking If Edit ALlowed

                            Date messageTime = null;
                            try {
                                messageTime = sdf.parse(msg_timestamp);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            // Get the timestamp in minutes
                            long messageTimestampMinutes = messageTime.getHours() * 60 + messageTime.getMinutes();
                            Log.d("Message Timestamp minutes", "Message Timestamp: " + messageTimestampMinutes);

                            // Get the current time in minutes
                            Calendar calendar = Calendar.getInstance();
                            int currentMinutes = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
                            // Calculate the time difference in minutes
                            long timeDifference = currentMinutes - messageTimestampMinutes;
                            Log.d("Time Difference", "Time Difference: " + timeDifference);
                            // Check if the time difference is less than 5 minutes
                            if ( timeDifference <= 5)
                            {
                                // Update the message in the database and notify the user
                                textHolder.msg.setText(editedMessage);
                                textHolder.timestamp.setText(timestamp);
                                // identifier for the message
                                String messageId = ls.get(position).getMessageId();
                                // Update the message in the SQL database
                                updateMessageInDatabase(message);
                                notifyItemChanged(position);
                                Toast.makeText(context, "Message edited successfully", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(context, "Message cannot be edited after 5 minutes", Toast.LENGTH_SHORT).show();
                            }
                        }

                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }

            });
        } else if (holder instanceof ImageMessageViewHolder) {
            Log.d("INSIDE ImageViewHolder", "Image Message");
            ImageMessageViewHolder imageHolder = (ImageMessageViewHolder) holder;
            // Load the image into the ImageView
            String imageUrl = message.getMsg();
            Log.d("Image URL", "URL: " + imageUrl);
//            Picasso.get().load(Uri.parse(imageUrl)).into(imageHolder.img_msg);
            Picasso.get().load(imageUrl).into(imageHolder.img_msg);

            imageHolder.timestamp.setText(message.getTimestamp());
            if (message.getDp() != null && !message.getDp().isEmpty()) {
                Picasso.get().load(message.getDp()).into(imageHolder.dp);
            }
        }
        else if (holder instanceof AudioMessageViewHolder) {
            Log.d("INSIDE AudioViewHolder", "Audio Message");
            // Bind audio message data to the AudioMessageViewHolder
            AudioMessageViewHolder audioHolder = (AudioMessageViewHolder) holder;
            // Set the audio message data here.
            audioHolder.bindData(message.getMsg()); // Set the audio URL
            audioHolder.timestamp.setText(message.getTimestamp());
            // Add logic for setting the sender's profile image (dp) here if needed.
            if (message.getDp() != null && !message.getDp().isEmpty()) {
                Picasso.get().load(message.getDp()).into(audioHolder.dp);}
        }
        // Long press listener for delete functionality
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Retrieve the message and position
                Model message = ls.get(position);
                // Call the method to show the delete confirmation dialog
                deleteMessageFromDatabase(message, position);
                return true;
            }
            private void deleteMessageFromDatabase(Model message, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Message");
                builder.setMessage("Are you sure you want to delete this message?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String deleteUrl = "http://192.168.10.8/assignment3/deletemsg.php";

                        StringRequest request = new StringRequest(Request.Method.POST, deleteUrl,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("Checking Response", response);
                                        // Remove the message from the list and notify the adapter
                                        ls.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, getItemCount());
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // Handle the error response (optional)
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("msg_id", message.getMessageId());
                                return params;
                            }
                        };
                        // Add the request to the RequestQueue
                        requestQueue.add(request);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }
    @Override
    public int getItemViewType(int position) {
        Model message = ls.get(position);
        // Return the appropriate view type based on the message type
//        if (message != null && message.getMessageType() != null) {
//            return message.getMessageType().equals("IMAGE") ? VIEW_TYPE_IMAGE : VIEW_TYPE_TEXT;
//        }
        if (message != null) {
            switch (message.getMessageType()) {
                case "TEXT":
                    return VIEW_TYPE_TEXT;
                case "IMAGE":
                    return VIEW_TYPE_IMAGE;
                case "AUDIO":
                    return VIEW_TYPE_AUDIO;
            }
        }
        return VIEW_TYPE_TEXT; // Return a default view type in case of null or missing data
    }


    private class TextMessageViewHolder extends RecyclerView.ViewHolder {
        TextView msg,timestamp;
        CircleImageView dp;
        ImageView edit_msg;
        public TextMessageViewHolder(View itemView) {
            super(itemView);
            msg=itemView.findViewById(R.id.msg);
            timestamp=itemView.findViewById(R.id.timestamp);
            dp=itemView.findViewById(R.id.dp);
            edit_msg =itemView.findViewById(R.id.edit_msg);
        }
    }

    private class ImageMessageViewHolder extends RecyclerView.ViewHolder {
        ImageView img_msg;
        TextView timestamp;
        CircleImageView dp;

        public ImageMessageViewHolder(View itemView) {
            super(itemView);
            img_msg = itemView.findViewById(R.id.img_msg);
            timestamp = itemView.findViewById(R.id.timestamp);
            dp = itemView.findViewById(R.id.dp);
        }
    }

    private class AudioMessageViewHolder extends RecyclerView.ViewHolder {
        TextView timestamp;
        CircleImageView dp;
        private MediaPlayer mediaPlayer;
        private String audioUrl;

        public AudioMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            timestamp = itemView.findViewById(R.id.timestamp);
            dp = itemView.findViewById(R.id.dp);
            ImageView playButton = itemView.findViewById(R.id.play_button);
            playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("INSIDE AudioViewHolder ADAPTERRRR", "Audio Message");
                    if (mediaPlayer == null) {
                        startAudioPlayback();
                        playButton.setImageResource(R.drawable.pause);
                    } else {
                        stopAudioPlayback();
                        playButton.setImageResource(R.drawable.play);
                    }
                }
            });
        }
        private void startAudioPlayback() {
            if (audioUrl != null) {
                try {
                    Log.d("AudioURL CHECKING AudioViewHolder", audioUrl);
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(audioUrl);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("AudioMessageViewHolder", "audioUrl is null");
            }
        }
        private void stopAudioPlayback() {
            if (mediaPlayer != null) {
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }

        public void bindData(String audioUrl) {
            this.audioUrl = audioUrl;
        }
    }


    private void updateMessageInDatabase(Model message) {
        String updateUrl = "http://192.168.10.8/assignment3/updatemsg.php";

        StringRequest request = new StringRequest(Request.Method.POST, updateUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Checking Response", response);
                        // Handle the response as needed
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error response (optional)
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("msg_id", message.getMessageId());
                params.put("msg", message.getMsg());
                // Add more parameters if needed
                return params;
            }
        };

        // Add the request to the RequestQueue
        requestQueue.add(request);
    }
}
