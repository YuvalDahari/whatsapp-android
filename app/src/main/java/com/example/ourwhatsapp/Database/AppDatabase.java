package com.example.ourwhatsapp.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.ourwhatsapp.Database.DatabaseDAOs.UserDao;
import com.example.ourwhatsapp.Database.DatabaseDAOs.MessagesDao;
import com.example.ourwhatsapp.Database.DatabaseDAOs.SettingsDao;

import com.example.ourwhatsapp.Database.Entities.User;
import com.example.ourwhatsapp.Database.Entities.Messages;
import com.example.ourwhatsapp.Database.Entities.Settings;

@Database(entities = {User.class, Messages.class, Settings.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract MessagesDao messageDao();
    public abstract SettingsDao settingsDao();
}