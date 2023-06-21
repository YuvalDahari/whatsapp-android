package com.example.ourwhatsapp.Repositories;

import android.content.Context;

import com.example.ourwhatsapp.Database.AppDatabase;
import com.example.ourwhatsapp.Database.DatabaseDAOs.SettingsDao;


public class SettingRepository {
    private SettingsDao settingsDao;

    public SettingRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        settingsDao = db.settingsDao();
    }
}