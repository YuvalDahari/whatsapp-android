package com.example.ourwhatsapp.Activities.Messages;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ourwhatsapp.R;

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