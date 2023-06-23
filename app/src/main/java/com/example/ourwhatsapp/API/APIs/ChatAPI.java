package com.example.ourwhatsapp.API.APIs;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.ourwhatsapp.API.Entities.Chat;
import com.example.ourwhatsapp.API.WebServiceAPI;
import com.example.ourwhatsapp.Activities.Conversations.Conversation;
import com.example.ourwhatsapp.Activities.Messages.Message;
import com.example.ourwhatsapp.Database.AppDatabase;
import com.example.ourwhatsapp.Database.DatabaseDAOs.MessagesDao;
import com.example.ourwhatsapp.Database.DatabaseDAOs.UserDao;
import com.example.ourwhatsapp.Database.Entities.Messages;
import com.example.ourwhatsapp.Database.Entities.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatAPI {
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;
    private MessagesDao messagesDao;
    private UserDao userDao;
    Context context;

    public ChatAPI(MessagesDao messagesDao, UserDao userDao, Context context) {
        this.messagesDao = messagesDao;
        this.userDao = userDao;
        this.context = context;

        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:12345/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.webServiceAPI = this.retrofit.create(WebServiceAPI.class);
    }

    public void getConversations(String token, MutableLiveData<List<Conversation>> users) {
        Call<List<Chat>> getChats  = webServiceAPI.getChats(token);
        getChats.enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                if (response.isSuccessful()) {
                    List<Chat> chats = response.body();
                    List<Conversation> conversations = new ArrayList<>();
                    List<User> newUsers = new ArrayList<>();
                    for (Chat chat : chats) {
                        if (chat.getLastMessage() != null) {
                            newUsers.add(new User(chat.getId(),
                                    chat.getUser().getDisplayName(), chat.getUser().getUsername(),
                                    chat.getUser().getProfilePic(), chat.getLastMessage().getContent(),
                                    chat.getLastMessage().getCreated()));
                            conversations.add(new Conversation(chat.getUser().getUsername(),
                                    chat.getUser().getProfilePic(),
                                    chat.getLastMessage().getContent(), chat.getLastMessage().getCreated(),
                                    chat.getId()));
                        } else {
                            newUsers.add(new User(chat.getId(),
                                    chat.getUser().getDisplayName(), chat.getUser().getUsername(),
                                    chat.getUser().getProfilePic(), null,
                                    null));
                            conversations.add(new Conversation(chat.getUser().getUsername(),
                                    chat.getUser().getProfilePic(),
                                    null, null,
                                    chat.getId()));
                        }
                    }
                    users.postValue(conversations);
                    new Thread(() -> {
                        userDao.insert(newUsers);
                    }).start();
                } else {
                    Toast.makeText(context, "code:" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                Toast.makeText(context, "error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getMessages(String token, String chatID, MutableLiveData<List<Message>> messages) {
        Call<List<com.example.ourwhatsapp.API.Entities.Message>> getMessages  = webServiceAPI.getMessages(token, chatID);
        getMessages.enqueue(new Callback<List<com.example.ourwhatsapp.API.Entities.Message>>() {
            @Override
            public void onResponse(Call<List<com.example.ourwhatsapp.API.Entities.Message>> call, Response<List<com.example.ourwhatsapp.API.Entities.Message>> response) {
                if (response.isSuccessful()) {
                    List<com.example.ourwhatsapp.API.Entities.Message> apiMessages = response.body();
                    List<Message> chat = new ArrayList<>();
                    List<Messages> newMessages = new ArrayList<>();
                    for (com.example.ourwhatsapp.API.Entities.Message apiMessage : apiMessages) {
                        chat.add(new Message(apiMessage.getContent(), apiMessage.getCreated(),
                                apiMessage.getSender().getUsername().equals(AppDatabase.getUsername()) ? Message.MessageType.SENT : Message.MessageType.RECEIVED));
                        newMessages.add(new Messages(chatID, apiMessage.getContent(),
                                apiMessage.getSender().getUsername(), apiMessage.getCreated()));
                    }
                    messages.postValue(chat);
                    new Thread(() -> {
                        // update room
                        messagesDao.delete(chatID);
                        messagesDao.insert(newMessages);
                    }).start();
                } else {
                    Toast.makeText(context, "code:" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<com.example.ourwhatsapp.API.Entities.Message>> call, Throwable t) {
                Toast.makeText(context, "error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}