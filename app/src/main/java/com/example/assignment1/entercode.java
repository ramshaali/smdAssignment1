package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class entercode extends AppCompatActivity {
    private EditText[] codeEditTexts;
    private ImageView backButton;
    private StringBuilder codeBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entercode);


        codeEditTexts = new EditText[]{
                findViewById(R.id.e1),
                findViewById(R.id.e2),
                findViewById(R.id.e3),
                findViewById(R.id.e4),
                findViewById(R.id.e5)
        };


        backButton = findViewById(R.id.backspace);


        codeBuilder = new StringBuilder();


        for (int i = 1; i <= 9; i++) {
            int buttonId = getResources().getIdentifier("button" + i, "id", getPackageName());
            Button button = findViewById(buttonId);
            int finalI = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (codeBuilder.length() < codeEditTexts.length) {
                        int index = codeBuilder.length();
                        codeBuilder.append(finalI);
                        codeEditTexts[index].setText(String.valueOf(finalI));
                    }
                }
            });
        }


        Button button0 = findViewById(R.id.b0);
        button0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (codeBuilder.length() < codeEditTexts.length) {
                    int index = codeBuilder.length();
                    codeBuilder.append(0);
                    codeEditTexts[index].setText("0");
                }
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (codeBuilder.length() > 0) {
                    int lastIndex = codeBuilder.length() - 1;
                    codeBuilder.deleteCharAt(lastIndex);
                    codeEditTexts[lastIndex].setText("");
                }
            }
        });

        ImageView button = findViewById(R.id.backButton);

        // Set an OnClickListener for the button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to navigate to NextActivity
                Intent intent = new Intent(entercode.this, signup.class);
                startActivity(intent);
            }
        });
    }
}
