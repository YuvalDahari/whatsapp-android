package com.example.ourwhatsapp.Database.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Settings {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String key;
    private String value;

    public Settings(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
