package com.example.ourwhatsapp;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AddNewChatActivity extends AppCompatActivity {
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_chat);

        cancelButton = findViewById(R.id.cancelBtn);

        cancelButton.setOnClickListener(view -> finish());
    }
}