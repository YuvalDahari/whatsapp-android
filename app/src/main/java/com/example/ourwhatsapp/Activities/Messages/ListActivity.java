package com.example.ourwhatsapp.Activities.Messages;

import static com.example.ourwhatsapp.Utils.imageToBase64;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ourwhatsapp.CustomListAdapter;
import com.example.ourwhatsapp.R;
import com.example.ourwhatsapp.Activities.Settings.SettingsActivity;
import com.example.ourwhatsapp.User;
import com.example.ourwhatsapp.ViewModals.ConversationsViewModal;
import com.example.ourwhatsapp.ViewModals.SettingsViewModal;
import com.example.ourwhatsapp.databinding.ActivitySettingsBinding;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private ConversationsViewModal viewModel;

    final private User user1 = new User("Yuval Dahari", imageToBase64(this, R.drawable.yuval),
            "Great JOB!", "12:00");

    final private User user2 = new User("Eliyahu Houri", imageToBase64(this, R.drawable.eliyahu),
            "I have to study", "00:30");

    final private User user3 = new User("Yehonatan Calinsky", imageToBase64(this, R.drawable.yonatan),
            "Why the hell did you start?", "03:23");

    final private User user4 = new User("Gal Kaminka", imageToBase64(this, R.drawable.gal),
            "MICROSOFT!", "08:59");

    ListView listView;
    CustomListAdapter adapter;

    private ArrayList<User> users;

    Button addButton;

    Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        viewModel = new ViewModelProvider(this).get(ConversationsViewModal.class);

        users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        viewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> newUsers) {
                users.clear();
                users.addAll(newUsers);
                adapter.notifyDataSetChanged();
            }
        });

        listView = findViewById(R.id.list_view);
        adapter = new CustomListAdapter(getApplicationContext(), users);

        listView.setAdapter(adapter);
        listView.setClickable(true);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(), UserActivity.class);

            intent.putExtra("userName", users.get(i).getUserName());
            intent.putExtra("profilePicture", users.get(i).getProfilePicture());
            intent.putExtra("lastMassage", users.get(i).getUserName());
            intent.putExtra("time", users.get(i).getUserName());

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