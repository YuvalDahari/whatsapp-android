package com.example.ourwhatsapp.ViewModals;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModal extends ViewModel {
    private MutableLiveData<String> profilePicture;
    private MutableLiveData<String> displayName;
    private MutableLiveData<String> serverURL;
    private MutableLiveData<Integer> theme;
    private MutableLiveData<Integer> language;

    public MutableLiveData<String> getProfilePicture() {
        if (profilePicture == null) {
            profilePicture = new MutableLiveData<>();
        }
        return profilePicture;
    }

    public MutableLiveData<String> getDisplayName() {
        if (displayName == null) {
            displayName = new MutableLiveData<>();
        }
        return displayName;
    }

    public MutableLiveData<String> getServerURL() {
        if (serverURL == null) {
            serverURL = new MutableLiveData<>();
        }
        return serverURL;
    }

    public MutableLiveData<Integer> getTheme() {
        if (theme == null) {
            theme = new MutableLiveData<>();
        }
        return theme;
    }

    public MutableLiveData<Integer> getLanguage() {
        if (language == null) {
            language = new MutableLiveData<>();
        }
        return language;
    }
}
