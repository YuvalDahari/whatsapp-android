package com.example.ourwhatsapp.Activities.Register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.ourwhatsapp.MainActivity;
import com.example.ourwhatsapp.R;
import com.example.ourwhatsapp.Repositories.UserRepository;
import com.example.ourwhatsapp.Utils;
import com.example.ourwhatsapp.databinding.ActivityMainBinding;
import com.example.ourwhatsapp.databinding.ActivityRegisterBinding;

import java.io.InputStream;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private UserRepository userRepository;

    private String base64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bitmap bm=((BitmapDrawable)binding.photoImageView.getDrawable()).getBitmap();
        base64 = Utils.imagePathToBase64(bm);

        ActivityResultLauncher<Intent> imagePickerActivityResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            try {
                                final Uri imageUri = result.getData().getData();
                                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                                binding.photoImageView.setImageBitmap(selectedImage);

                                base64 = Utils.imagePathToBase64(selectedImage);
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Something went wrong, try again!", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "No image was picked!", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        userRepository = new UserRepository(getApplicationContext());

        binding.navToLogin.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(intent);
        });

        binding.photoImageView.setOnClickListener(view -> {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK);
            galleryIntent.setType("image/*");
            imagePickerActivityResult.launch(galleryIntent);
        });

        binding.showListButton.setOnClickListener(view -> {
            MutableLiveData<Integer> res = userRepository.tryRegister(binding.loginUsername.getText().toString(),
                    binding.loginPassword.getText().toString(), binding.displayName.getText().toString(), base64);
            res.observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer code) {
                    if (code == 200) {
                        finish();
                    } else if (code == 409) {
                        Toast.makeText(getApplicationContext(), "Username is already exists, try another one!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong, try again!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
