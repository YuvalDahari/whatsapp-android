package com.example.ourwhatsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.ourwhatsapp.Activities.Messages.ListActivity;
import com.example.ourwhatsapp.Activities.Register.RegisterActivity;
import com.example.ourwhatsapp.Activities.Settings.MiniSettingsActivity;
import com.example.ourwhatsapp.ViewModals.SettingsViewModal;

public class MainActivity extends AppCompatActivity {

    Button showListButton;
    Button registerPage;
    Button settingButton;
    Button settingPage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showListButton = findViewById(R.id.show_list_button);

        showListButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent);
        });

        registerPage = findViewById(R.id.navToRegister);

        registerPage.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        });

        settingButton = findViewById(R.id.settingsBtn);

        settingButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MiniSettingsActivity.class);
            startActivity(intent);
        });

        settingPage = findViewById(R.id.navToMiniSettings);

        settingPage.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MiniSettingsActivity.class);
            startActivity(intent);
        });
    }
}
