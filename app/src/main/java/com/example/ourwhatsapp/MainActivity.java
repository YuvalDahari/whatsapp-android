package com.example.ourwhatsapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.ourwhatsapp.Activities.Conversations.ConversationsActivity;
import com.example.ourwhatsapp.Activities.Register.RegisterActivity;
import com.example.ourwhatsapp.Activities.Settings.MiniSettingsActivity;
import com.example.ourwhatsapp.Repositories.UserRepository;
import com.example.ourwhatsapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userRepository = new UserRepository(getApplicationContext());

        MutableLiveData<String> token = userRepository.checkToken();
        MutableLiveData<Integer> saveTokenRes = new MutableLiveData<>();
        token.observe(this, s -> {
            if (s != null) {
                Intent intent = new Intent(getApplicationContext(), ConversationsActivity.class);
                startActivity(intent);
            }
        });

        saveTokenRes.observe(this, value -> {
            if (value == 1) {
                // Login succeeded
                Intent intent = new Intent(getApplicationContext(), ConversationsActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Something went wrong, try again!", Toast.LENGTH_LONG).show();
            }
        });

        binding.loginUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (Utils.isValidUsername(s.toString())) {
                    binding.loginUsername.setTextColor(Color.RED);
                } else {
                    binding.loginUsername.setTextColor(Color.BLACK);
                }
            }
        });

        binding.loginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (Utils.isValidPassword(s.toString())) {
                    binding.loginPassword.setTextColor(Color.RED);
                } else {
                    binding.loginPassword.setTextColor(Color.BLACK);
                }
            }
        });

        binding.showListButton.setOnClickListener(view -> {
            String username = binding.loginUsername.getText().toString();
            String password = binding.loginPassword.getText().toString();

            if (Utils.isValidUsername(username)) {
                Toast.makeText(getApplicationContext(), "Invalid username", Toast.LENGTH_LONG / 2).show();
                Toast.makeText(getApplicationContext(), "Username must be at least 6 chars, only letters and digits", Toast.LENGTH_LONG).show();
            } else if (Utils.isValidPassword(password)) {
                Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_LONG / 2).show();
                Toast.makeText(getApplicationContext(), "at least 8 chars, 1+ uppercase & lowercase letter, digits and special char", Toast.LENGTH_LONG).show();
            } else {

                MutableLiveData<String> res = userRepository.tryLogin(username, password);
                res.observe(this, s -> {
                    if (s != null) {
                        // Save token and get user data
                        userRepository.login(s, binding.loginUsername.getText().toString(), saveTokenRes);
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong username or password!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        binding.navToRegister.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        });

        binding.settingsBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MiniSettingsActivity.class);
            startActivity(intent);
        });

        binding.navToMiniSettings.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MiniSettingsActivity.class);
            startActivity(intent);
        });
    }
}
