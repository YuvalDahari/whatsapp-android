package com.example.ourwhatsapp.Repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.ourwhatsapp.API.APIs.ChatAPI;
import com.example.ourwhatsapp.Activities.Conversations.Conversation;
import com.example.ourwhatsapp.Activities.Messages.Message;
import com.example.ourwhatsapp.Database.AppDatabase;
import com.example.ourwhatsapp.Database.DatabaseDAOs.MessagesDao;
import com.example.ourwhatsapp.Database.DatabaseDAOs.UserDao;
import com.example.ourwhatsapp.Database.Entities.Messages;
import com.example.ourwhatsapp.Database.Entities.User;

import java.util.ArrayList;
import java.util.List;


public class ChatRepository {
    private UserDao userDao;
    private MessagesDao messagesDao;
    private Context context;
    private String token;
    private ChatAPI chatAPI;
    private String chatID;

    public ChatRepository(Context context, String chatID) {
        AppDatabase db = AppDatabase.getInstance(context);
        this.context = context;
        userDao = db.userDao();
        messagesDao = db.messageDao();
        token = AppDatabase.getToken();
        chatAPI = new ChatAPI(messagesDao, userDao, context);
        this.chatID = chatID;
    }

    public void loadMessages(MutableLiveData<List<Message>> messages) {
        new Thread(() -> {
            // load from ROOM
            List<Messages> roomMessages = messagesDao.getChatsMessages(chatID);
            List<Message> newMessages = new ArrayList<>();
            for (Messages roomMessage : roomMessages) {
                newMessages.add(new Message(
                        roomMessage.getContent(), roomMessage.getContent(),
                        roomMessage.getSender().equals(AppDatabase.getUsername()) ? Message.MessageType.SENT : Message.MessageType.RECEIVED
                ));
            }
            messages.postValue(newMessages);
            // update from API
            chatAPI.getMessages(AppDatabase.getToken(), chatID, messages);
        }).start();
    }
}
