package com.example.ourwhatsapp.Activities.Messages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.ourwhatsapp.R;
import com.example.ourwhatsapp.Repositories.ChatRepository;
import com.example.ourwhatsapp.Utils;
import com.example.ourwhatsapp.ViewModels.ChatViewModel;
import com.example.ourwhatsapp.databinding.ActivityChatBinding;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ChatViewModel viewModel;
    private ActivityChatBinding binding;
    private MessageAdapter messageAdapter;
    private List<Message> messages;
    private ChatRepository chatRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        messages = new ArrayList<>();

        chatRepository = new ChatRepository(getApplicationContext(), getIntent().getStringExtra("id"));

        viewModel.getProfilePicture().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String profilePicture) {
                try {
                    Utils.displayBase64Image(profilePicture, binding.userImageProfileImage);
                } catch (Exception ex) {

                }
            }
        });

        viewModel.getDisplayName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String displayName) {
                binding.userTextUserName.setText(displayName);
            }
        });

        viewModel.getChat().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> newMessages) {
                messages.clear();
                messages.addAll(newMessages);
                messageAdapter.notifyDataSetChanged();
            }
        });


        Intent activityIntent = getIntent();

        if (activityIntent != null) {
            String userName = activityIntent.getStringExtra("userName");
            String profilePicture = activityIntent.getStringExtra("profilePicture");

            viewModel.getDisplayName().setValue(userName);
            viewModel.getProfilePicture().setValue(profilePicture);

            messageAdapter = new MessageAdapter(this, R.layout.custom_messages_item, messages);
            binding.listView.setAdapter(messageAdapter);
        }

        binding.returnBtn.setOnClickListener(view -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();

        chatRepository.loadMessages(viewModel.getChat());
    }
}
