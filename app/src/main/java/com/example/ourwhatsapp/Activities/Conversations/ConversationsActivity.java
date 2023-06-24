package com.example.ourwhatsapp.Activities.Conversations;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ourwhatsapp.Activities.Messages.ChatActivity;
import com.example.ourwhatsapp.Activities.Settings.SettingsActivity;
import com.example.ourwhatsapp.Database.AppDatabase;
import com.example.ourwhatsapp.Repositories.ConversationRepository;
import com.example.ourwhatsapp.ViewModels.ConversationsViewModel;
import com.example.ourwhatsapp.databinding.ActivityListBinding;

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

        binding.listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            new AlertDialog.Builder(ConversationsActivity.this)
                    .setTitle("Delete chat")
                    .setMessage("Are you sure you want to delete this chat?")

                    .setPositiveButton(android.R.string.yes, (dialog, which) ->
                            conversationRepository.deleteChat(conversations.get(i).getChatID(), viewModel.getUsers()))
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return true;
        });

        binding.addBtn.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Create new chat - enter username");

            // Set up the input
            final EditText input = new EditText(this);
            // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String newUsername = input.getText().toString();
                    if (newUsername.equals(AppDatabase.getUsername())) {
                        Toast.makeText(getApplicationContext(), "You can not create chat with yourself!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (newUsername.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Username can not be empty!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (Conversation user : conversations) {
                        if (user.getUserName().equals(newUsername)) {
                            Toast.makeText(getApplicationContext(), "Chat already exists!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    conversationRepository.createChat(newUsername, viewModel.getUsers());
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
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
