package com.example.ourwhatsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    ImageView profilePictureView;
    TextView userNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        profilePictureView = findViewById(R.id.user_image_profile_image);
        userNameView = findViewById(R.id.user_text_user_name);

        Intent activityIntent = getIntent();

        if (activityIntent != null) {
            String userName = activityIntent.getStringExtra("userName");
            int profilePicture = activityIntent.getIntExtra("profilePicture", R.drawable.blue);

            profilePictureView.setImageResource(profilePicture);
            userNameView.setText(userName);

            List<Message> messages = new ArrayList<>();
            messages.add(new Message("Hello", "10:00", Message.MessageType.SENT));
            messages.add(new Message("Hi", "10:05", Message.MessageType.RECEIVED));

            ListView listView = findViewById(R.id.list_view);
            MessageAdapter adapter = new MessageAdapter(this, R.layout.custom_messages_item, messages);
            listView.setAdapter(adapter);
        }
    }
}
