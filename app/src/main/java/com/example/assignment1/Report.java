package com.example.assignment1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class report extends AppCompatActivity {
    private EditText report_edit_txt;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // ++++++++++++++++ BACK BUTTON ++++++++++++++++++++

        ImageView closebuttonview = findViewById(R.id.back_button);
        closebuttonview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //        REPORT STORAGE

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reports = database.getReference("reports");

        report_edit_txt = findViewById(R.id.report);
        submitButton = findViewById(R.id.post);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text from the report EditText
                String reportText = report_edit_txt.getText().toString();

                // Push the new report to the Firebase Realtime Database
                reports.push().setValue(reportText);

                // Show a toast message
                Toast.makeText(report.this, "Report generated and saved to Firebase", Toast.LENGTH_SHORT).show();

                report_edit_txt.setText(""); // Clear the EditText after Reportinh


            }
        });


    }
}