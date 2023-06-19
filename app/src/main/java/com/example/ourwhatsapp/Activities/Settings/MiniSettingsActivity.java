package com.example.ourwhatsapp.Activities.Settings;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ourwhatsapp.R;

public class MiniSettingsActivity extends AppCompatActivity {

    Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_settings);

        exitButton = findViewById(R.id.exitBtn);
        exitButton.setOnClickListener(view -> finish());
    }
}