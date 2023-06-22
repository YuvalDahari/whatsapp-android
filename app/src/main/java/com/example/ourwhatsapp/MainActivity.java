package com.example.ourwhatsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.ourwhatsapp.Activities.Conversations.ConversationsActivity;
import com.example.ourwhatsapp.Activities.Messages.ChatActivity;
import com.example.ourwhatsapp.Activities.Register.RegisterActivity;
import com.example.ourwhatsapp.Activities.Settings.MiniSettingsActivity;
import com.example.ourwhatsapp.Repositories.UserRepository;
import com.example.ourwhatsapp.databinding.ActivityChatBinding;
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
        MutableLiveData<Integer> saveTokenRes = new MutableLiveData<>();

        saveTokenRes.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer value) {
                if (value == 1) {
                    // Login succeeded
                    Intent intent = new Intent(getApplicationContext(), ConversationsActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong, try again!", Toast.LENGTH_LONG).show();
                }
            }
        });

        binding.showListButton.setOnClickListener(view -> {
            MutableLiveData<String> res = userRepository.tryLogin(binding.loginUsername.getText().toString(), binding.loginPassword.getText().toString());
            res.observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    String token = s;
                    if (token != null) {
                        // Save token and get user data
                        userRepository.saveToken(token, saveTokenRes, getApplicationContext());
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong username or password!", Toast.LENGTH_LONG).show();
                    }
                }
            });
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
