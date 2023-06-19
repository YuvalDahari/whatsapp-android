package com.example.ourwhatsapp.Activities.Settings;

import com.example.ourwhatsapp.Utils;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ourwhatsapp.R;
import com.example.ourwhatsapp.ViewModels.SettingsViewModel;
import com.example.ourwhatsapp.databinding.ActivitySettingsBinding;



public class SettingsActivity extends AppCompatActivity {
    private SettingsViewModel viewModel;
    private ActivitySettingsBinding binding;

    Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel.getProfilePicture().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String profilePicture){
                Utils.displayBase64Image(profilePicture, binding.photoImageView);
            }
        });

        viewModel.getDisplayName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String displayName){
                binding.displayName.setText(displayName);
            }
        });

        viewModel.getServerURL().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String serverURL){
                binding.serverPort.setText(serverURL);
            }
        });

        viewModel.getTheme().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer theme) {
                if (theme == null) {
                    binding.themeSpinner.setSelection(1);
                } else {
                    binding.themeSpinner.setSelection(theme);
                }
            }
        });

        viewModel.getLanguage().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer language) {
                if (language == null) {
                    binding.languageSpinner.setSelection(1);
                } else {
                    binding.languageSpinner.setSelection(language);
                }
            }
        });


        exitButton = findViewById(R.id.exitBtn);
        exitButton.setOnClickListener(view -> finish());
    }

}

