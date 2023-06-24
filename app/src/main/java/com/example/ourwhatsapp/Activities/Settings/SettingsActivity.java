package com.example.ourwhatsapp.Activities.Settings;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ourwhatsapp.Database.AppDatabase;
import com.example.ourwhatsapp.Database.Entities.Settings;
import com.example.ourwhatsapp.MainActivity;
import com.example.ourwhatsapp.Utils;
import com.example.ourwhatsapp.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;
    AppDatabase db = AppDatabase.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());

        boolean showLogout = getIntent().getBooleanExtra("SHOW_LOGOUT", false);
        binding.logoutButton.setVisibility(showLogout ? View.VISIBLE : View.GONE);

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

        // Load saved theme
        new Thread(() -> {
            Settings themeSetting = db.settingsDao().get("theme");
            String savedTheme = (themeSetting != null) ? themeSetting.getValue() : "Classic";
            runOnUiThread(() -> {
                if ("Dark".equals(savedTheme)) {
                    binding.themeSpinner.setSelection(1);
                } else {
                    binding.themeSpinner.setSelection(0);
                }
            });
        }).start();

        binding.logoutButton.setOnClickListener(view -> {
            new Thread(db::clearAllTables).start();
            // Start MainActivity after logging out
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        binding.themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedTheme = parent.getItemAtPosition(position).toString();
                // Save selected theme
                new Thread(() -> {
                    Settings themeSetting = new Settings("theme", selectedTheme);
                    db.settingsDao().insert(themeSetting);
                }).start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        binding.exitBtn.setOnClickListener(view -> {
            String url = binding.serverPort.getText().toString();

            if (Utils.isValidURL(url) && !url.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Invalid url", Toast.LENGTH_LONG / 2).show();
            } else {
                new Thread(() -> {
                    Settings urlSetting = new Settings("url", binding.serverPort.getText().toString());
                    db.settingsDao().insert(urlSetting);
                }).start();
                finish();
            }
        });
    }
}
