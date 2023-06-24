package com.example.ourwhatsapp.Activities.Settings;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ourwhatsapp.Utils;
import com.example.ourwhatsapp.databinding.ActivityMiniSettingsBinding;

public class MiniSettingsActivity extends AppCompatActivity {

    private ActivityMiniSettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMiniSettingsBinding.inflate(getLayoutInflater());

        binding.serverPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (Utils.isValidURL(s.toString())) {
                    binding.serverPort.setTextColor(Color.RED);
                } else {
                    binding.serverPort.setTextColor(Color.BLACK);
                }
            }
        });
        setContentView(binding.getRoot());

        binding.exitBtn.setOnClickListener(view -> {
            String url = binding.serverPort.getText().toString();
            if (Utils.isValidURL(url)) {
                Toast.makeText(getApplicationContext(), "Invalid url", Toast.LENGTH_LONG / 2).show();
            } else finish();
        });
    }
}