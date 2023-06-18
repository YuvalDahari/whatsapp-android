package com.example.ourwhatsapp.ViewModals;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ourwhatsapp.User;

import java.util.ArrayList;
import java.util.List;

public class ConversationsViewModal extends ViewModel {
    private MutableLiveData<List<User>> users;

    public MutableLiveData<List<User>> getUsers() {
        if (users == null) {
            users = new MutableLiveData<>();
            users.setValue(new ArrayList<>());
        }
        return users;
    }
}
