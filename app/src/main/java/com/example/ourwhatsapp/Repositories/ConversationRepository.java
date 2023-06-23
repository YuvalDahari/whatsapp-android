package com.example.ourwhatsapp.Repositories;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.ourwhatsapp.API.APIs.ChatAPI;
import com.example.ourwhatsapp.Activities.Conversations.Conversation;
import com.example.ourwhatsapp.Database.AppDatabase;
import com.example.ourwhatsapp.Database.DatabaseDAOs.MessagesDao;
import com.example.ourwhatsapp.Database.DatabaseDAOs.UserDao;
import com.example.ourwhatsapp.Database.Entities.User;

import java.util.ArrayList;
import java.util.List;


public class ConversationRepository {
    private UserDao userDao;
    private MessagesDao messagesDao;
    private Context context;
    private String token;
    private ChatAPI chatAPI;

    public ConversationRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.context = context;
        userDao = db.userDao();
        messagesDao = db.messageDao();
        token = AppDatabase.getToken();
        chatAPI = new ChatAPI(messagesDao, userDao, context);
    }

    public void loadConversations(MutableLiveData<List<Conversation>> users) {
        new Thread(() -> {
            // load from ROOM
            List<User> chats = userDao.getChats();
            List<Conversation> conversations = new ArrayList<>();
            for (User chat : chats) {
                conversations.add(new Conversation(chat.getUsername(), chat.getProfilePhoto(),
                        chat.getLastMessage(), chat.getLastMassageSendingTime(), chat.getChatID()));
            }
            users.postValue(conversations);
            // update from API
            chatAPI.getConversations(AppDatabase.getToken(), users);
        }).start();
    }
}