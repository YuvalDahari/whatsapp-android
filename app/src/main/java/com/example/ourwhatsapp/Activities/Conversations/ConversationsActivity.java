package com.example.ourwhatsapp.Activities.Conversations;

import static com.example.ourwhatsapp.Utils.imageToBase64;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ourwhatsapp.Activities.Messages.AddNewChatActivity;
import com.example.ourwhatsapp.Activities.Messages.ChatActivity;
import com.example.ourwhatsapp.R;
import com.example.ourwhatsapp.Activities.Settings.SettingsActivity;
import com.example.ourwhatsapp.ViewModels.ConversationsViewModel;

import java.util.ArrayList;
import java.util.List;

public class ConversationsActivity extends AppCompatActivity {
    private ConversationsViewModel viewModel;

    final private Conversation conversation1 = new Conversation("Yuval Dahari", imageToBase64(this, R.drawable.yuval),
            "Great JOB!", "12:00");

    final private Conversation conversation2 = new Conversation("Eliyahu Houri", imageToBase64(this, R.drawable.eliyahu),
            "I have to study", "00:30");

    final private Conversation conversation3 = new Conversation("Yehonatan Calinsky", imageToBase64(this, R.drawable.yonatan),
            "Why the hell did you start?", "03:23");

    final private Conversation conversation4 = new Conversation("Gal Kaminka", imageToBase64(this, R.drawable.gal),
            "MICROSOFT!", "08:59");

    ListView listView;
    ConversationsAdapter adapter;

    private ArrayList<Conversation> conversations;

    Button addButton;

    Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        viewModel = new ViewModelProvider(this).get(ConversationsViewModel.class);

        conversations = new ArrayList<>();
        conversations.add(conversation1);
        conversations.add(conversation2);
        conversations.add(conversation3);
        conversations.add(conversation4);

        viewModel.getUsers().observe(this, new Observer<List<Conversation>>() {
            @Override
            public void onChanged(List<Conversation> newConversations) {
                conversations.clear();
                conversations.addAll(newConversations);
                adapter.notifyDataSetChanged();
            }
        });

        listView = findViewById(R.id.list_view);
        adapter = new ConversationsAdapter(getApplicationContext(), conversations);

        listView.setAdapter(adapter);
        listView.setClickable(true);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);

            intent.putExtra("userName", conversations.get(i).getUserName());
            intent.putExtra("profilePicture", conversations.get(i).getProfilePicture());
            intent.putExtra("lastMassage", conversations.get(i).getUserName());
            intent.putExtra("time", conversations.get(i).getUserName());

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