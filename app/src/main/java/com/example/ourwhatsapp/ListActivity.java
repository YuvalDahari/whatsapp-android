package com.example.ourwhatsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    final private int[] profilePictures = {
            R.drawable.yuval, R.drawable.eliyahu, R.drawable.yonatan,
            R.drawable.gal,
    };

    final private String[] userNames = {
            "Yuval Dahari", "Eliyahu Huri", "Yonatan Calinski", "Gal Kaminka",
    };

    final private String[] lastMassages = {
            "Great JOB!", "I have to study", "Why the hell did you start?", "MICROSOFT!",
    };

    final private String[] times = {
            "12:00", "00:30", "03:23", "08:59",
    };

    ListView listView;
    CustomListAdapter adapter;

    Button addButton;

    Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ArrayList<User> users = new ArrayList<>();

        for (int i = 0; i < profilePictures.length; i++) {
            User aUser = new User(
                    userNames[i], profilePictures[i],
                    lastMassages[i], times[i]
            );

            users.add(aUser);
        }

        listView = findViewById(R.id.list_view);
        adapter = new CustomListAdapter(getApplicationContext(), users);

        listView.setAdapter(adapter);
        listView.setClickable(true);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(), UserActivity.class);

            intent.putExtra("userName", userNames[i]);
            intent.putExtra("profilePicture", profilePictures[i]);
            intent.putExtra("lastMassage", lastMassages[i]);
            intent.putExtra("time", times[i]);

            startActivity(intent);
        });

        addButton = findViewById(R.id.addBtn);

        addButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddNewChatActivity.class);
            startActivity(intent);
        });

        settingsButton = findViewById(R.id.settingsBtn);

        settingsButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        });

    }
}