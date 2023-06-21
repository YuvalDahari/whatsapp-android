package com.example.ourwhatsapp.Database.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String chatID;
    private String displayName;

    private String username;

    private String profilePhoto;

    public User(String chatID, String displayName, String username, String profilePhoto) {
        this.chatID = chatID;
        this.displayName = displayName;
        this.username = username;
        this.profilePhoto = profilePhoto;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUsername() {
        return username;
    }

    public String getChatID() {
        return chatID;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setDisplayName(String newName) {
        this.displayName = newName;
    }

    public void setProfilePhoto(String newProfilePicture) {
        this.profilePhoto = newProfilePicture;
    }

    public void setId(int id) {
        this.id = id;
    }
}
