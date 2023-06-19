package com.example.ourwhatsapp.ViewModals;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ourwhatsapp.Activities.Messages.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatViewModal extends ViewModel {
    private MutableLiveData<ImageView> profilePicture;
    private MutableLiveData<TextView> displayName;
    private MutableLiveData<Button> retButton;
    private MutableLiveData<List<Message>> chat;

    public MutableLiveData<ImageView> getProfilePicture() {
        if (profilePicture == null) {
            profilePicture = new MutableLiveData<>();
        }
        return profilePicture;
    }

    public MutableLiveData<TextView> getDisplayName() {
        if (displayName == null) {
            displayName = new MutableLiveData<>();
        }
        return displayName;
    }

    public MutableLiveData<Button> getRetButton() {
        if (retButton == null) {
            retButton = new MutableLiveData<>();
        }
        return retButton;
    }

    public MutableLiveData<List<Message>> getChat() {
        if (chat == null) {
            chat = new MutableLiveData<>();
            chat.setValue(new ArrayList<>());
        }
        return chat;
    }
}
