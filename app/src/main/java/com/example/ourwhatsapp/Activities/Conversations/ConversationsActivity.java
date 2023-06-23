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
import com.example.ourwhatsapp.Repositories.ConversationRepository;
import com.example.ourwhatsapp.ViewModels.ConversationsViewModel;
import com.example.ourwhatsapp.databinding.ActivityListBinding;
import com.example.ourwhatsapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class ConversationsActivity extends AppCompatActivity {
    private ConversationsViewModel viewModel;
    private ActivityListBinding binding;
    private ConversationsAdapter adapter;
    private ArrayList<Conversation> conversations;
    private ConversationRepository conversationRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        conversationRepository = new ConversationRepository(getApplicationContext());

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

        adapter = new ConversationsAdapter(getApplicationContext(), conversations);

        binding.listView.setAdapter(adapter);
        binding.listView.setClickable(true);

        binding.listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);

            Conversation currentConversation = conversations.get(i);

            intent.putExtra("userName", currentConversation.getUserName());
            intent.putExtra("profilePicture", currentConversation.getProfilePicture());
            intent.putExtra("lastMassage", currentConversation.getLastMassage());
            intent.putExtra("time", currentConversation.getLastMassageSendingTime());
            intent.putExtra("id", currentConversation.getChatID());

            startActivity(intent);
        });

        binding.addBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AddNewChatActivity.class);
            startActivity(intent);
        });

        binding.settingsBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        conversationRepository.loadConversations(viewModel.getUsers());
    }
}
