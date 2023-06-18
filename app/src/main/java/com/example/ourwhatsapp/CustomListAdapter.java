package com.example.ourwhatsapp;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ourwhatsapp.databinding.ActivitySettingsBinding;
import com.example.ourwhatsapp.databinding.CustomListItemBinding;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<User> {
    private final LayoutInflater inflater;

    public CustomListAdapter(Context ctx, ArrayList<User> userArrayList) {
        super(ctx, R.layout.custom_list_item, userArrayList);

        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User user = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_list_item, parent, false);
        }

        Utils.displayBase64Image(user.getProfilePicture(), convertView.findViewById(R.id.profile_image));
        ((TextView)convertView.findViewById(R.id.user_name)).setText(user.getUserName());
        ((TextView)convertView.findViewById(R.id.last_massage)).setText(user.getLastMassage());
        ((TextView)convertView.findViewById(R.id.time)).setText(user.getLastMassageSendingTime());

        return convertView;
    }
}
