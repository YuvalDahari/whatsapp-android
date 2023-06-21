package com.example.ourwhatsapp.API.APIs;

import com.example.ourwhatsapp.API.WebServiceAPI;
import com.example.ourwhatsapp.Database.DatabaseDAOs.MessagesDao;
import com.example.ourwhatsapp.Database.DatabaseDAOs.UserDao;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    private MessagesDao messagesDao;
    private UserDao userDao;

    public ChatAPI(MessagesDao messagesDao, UserDao userDao) {
        this.messagesDao = messagesDao;
        this.userDao = userDao;

        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.0.2:12345/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.webServiceAPI = this.retrofit.create(WebServiceAPI.class);
    }
}