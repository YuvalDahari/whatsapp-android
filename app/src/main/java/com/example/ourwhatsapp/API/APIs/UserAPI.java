package com.example.ourwhatsapp.API.APIs;

import com.example.ourwhatsapp.API.WebServiceAPI;
import com.example.ourwhatsapp.Database.DatabaseDAOs.UserDao;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    private UserDao userDao;

    public UserAPI(UserDao userDao) {
        this.userDao = userDao;

        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.0.2:12345/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.webServiceAPI = this.retrofit.create(WebServiceAPI.class);
    }
}