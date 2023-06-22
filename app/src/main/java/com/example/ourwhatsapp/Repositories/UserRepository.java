package com.example.ourwhatsapp.Repositories;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.ourwhatsapp.API.APIs.UserAPI;
import com.example.ourwhatsapp.Database.AppDatabase;
import com.example.ourwhatsapp.Database.DatabaseDAOs.MessagesDao;
import com.example.ourwhatsapp.Database.DatabaseDAOs.SettingsDao;
import com.example.ourwhatsapp.Database.DatabaseDAOs.UserDao;
import com.example.ourwhatsapp.Database.Entities.Settings;


public class UserRepository {
    private UserDao userDao;
    private SettingsDao settingsDao;
    private UserAPI userAPI;

    public UserRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        userDao = db.userDao();
        settingsDao = db.settingsDao();
        userAPI = new UserAPI(userDao, context);
    }

    public MutableLiveData<String> tryLogin(String username, String password) {
        MutableLiveData<String> data = new MutableLiveData<>();
        new Thread(() -> {
            userAPI.tryLogin(username, password, data);
        }).start();
        return data;
    }

    public MutableLiveData<Integer> tryRegister(String username, String password, String displayName, String profilePic) {
        MutableLiveData<Integer> data = new MutableLiveData<>();
        new Thread(() -> {
            userAPI.tryRegister(username, password, displayName, profilePic, data);
        }).start();
        return data;
    }

    public void saveToken(String token, MutableLiveData<Integer> data, Context context) {
        new Thread(() -> {
            settingsDao.insert(new Settings("token", token));
            data.postValue(1);
        }).start();
    }
}
