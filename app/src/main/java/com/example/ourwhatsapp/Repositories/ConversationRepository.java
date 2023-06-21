package com.example.ourwhatsapp.Repositories;

import android.content.Context;

import com.example.ourwhatsapp.Database.AppDatabase;
import com.example.ourwhatsapp.Database.DatabaseDAOs.MessagesDao;
import com.example.ourwhatsapp.Database.DatabaseDAOs.UserDao;


public class ConversationRepository {
    private UserDao userDao;
    private MessagesDao messagesDao;

    public ConversationRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        userDao = db.userDao();
        messagesDao = db.messageDao();
    }
}