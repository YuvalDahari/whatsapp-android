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

    private Conversation conversation1;
    private Conversation conversation2;
    private Conversation conversation3;
    private Conversation conversation4;

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

            Conversation currentConversation = conversations.get(i);

            intent.putExtra("userName", currentConversation.getUserName());
            intent.putExtra("profilePicture", currentConversation.getProfilePicture());
            intent.putExtra("lastMassage", currentConversation.getLastMassage());
            intent.putExtra("time", currentConversation.getLastMassageSendingTime());

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
