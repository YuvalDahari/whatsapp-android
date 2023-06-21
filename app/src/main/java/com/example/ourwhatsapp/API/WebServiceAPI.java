package com.example.ourwhatsapp.API;

import com.example.ourwhatsapp.API.Entities.Chat;
import com.example.ourwhatsapp.API.Entities.ChatData;
import com.example.ourwhatsapp.API.Entities.CreateChatReq;
import com.example.ourwhatsapp.API.Entities.CreateUserReq;
import com.example.ourwhatsapp.API.Entities.GetTokenReq;
import com.example.ourwhatsapp.API.Entities.Message;
import com.example.ourwhatsapp.API.Entities.SendMessageReq;
import com.example.ourwhatsapp.API.Entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceAPI {
    @GET("Chats")
    Call<List<Chat>> getChats(@Header("Authorization") String token);

    @POST("Chats")
    Call<Chat> createChat(@Header("Authorization") String token, @Body CreateChatReq req);

    @GET("Chats/{id}")
    Call<ChatData> getChat(@Header("Authorization") String token, @Path("id") String id);

    @DELETE("Chats/{id}")
    Call<Void> deleteChat(@Header("Authorization") String token, @Path("id") String id);

    @GET("Chats/{id}/Messages")
    Call<List<Message>> getMessages(@Header("Authorization") String token, @Path("id") String id);

    @POST("Chats/{id}/Messages")
    Call<Message> sendMessage(@Header("Authorization") String token, @Path("id") String id, @Body SendMessageReq req);

    @GET("Tokens")
    Call<String> getToken(@Body GetTokenReq req);

    @GET("Users/{username}")
    Call<User> getUser(@Header("Authorization") String token, @Path("username") String username);

    @POST("Users")
    Call<Void> createUser(@Body CreateUserReq req);
}