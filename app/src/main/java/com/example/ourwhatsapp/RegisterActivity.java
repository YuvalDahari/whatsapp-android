package com.example.ourwhatsapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check the screen orientation
        int orientation = getResources().getConfiguration().orientation;

        // Set the content view based on the orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_register_horizontal);
        } else {
            setContentView(R.layout.activity_register);
        }
        loginButton = findViewById(R.id.navToLogin);

        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(intent);
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Check the new screen orientation
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (isFinishing()) {
                return;
            }
            setContentView(R.layout.activity_register_horizontal);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (isFinishing()) {
                return;
            }
            setContentView(R.layout.activity_main);
        }
    }
}
