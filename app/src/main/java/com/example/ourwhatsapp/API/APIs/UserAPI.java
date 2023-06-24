package com.example.ourwhatsapp.API.APIs;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.ourwhatsapp.API.Entities.CreateUserReq;
import com.example.ourwhatsapp.API.Entities.GetTokenReq;
import com.example.ourwhatsapp.API.WebServiceAPI;
import com.example.ourwhatsapp.Database.DatabaseDAOs.UserDao;
import com.example.ourwhatsapp.Utils;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserAPI {
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;
    private UserDao userDao;
    private Context context;

    public UserAPI(UserDao userDao, Context context) {
        this.userDao = userDao;

        this.retrofit = new Retrofit.Builder()
                .baseUrl(Utils.getURL(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.webServiceAPI = this.retrofit.create(WebServiceAPI.class);
        this.context = context;
    }

    public void tryLogin(String username, String password, MutableLiveData<String> res) {
        Call<ResponseBody> call = webServiceAPI.getToken(new GetTokenReq(username, password));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    try {
                        res.postValue(responseBody.string());
                    } catch (Exception exception) {
                        res.postValue(null);
                    }
                } else {
                    res.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                res.postValue(null);
            }
        });
    }

    public void tryRegister(String username, String password, String displayName, String profilePic, MutableLiveData<Integer> res) {
        Call<Void> call = webServiceAPI.createUser(new CreateUserReq(username, password, displayName, profilePic));

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                res.postValue(response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                res.postValue(500);
            }
        });
    }

    public void getCurrentUser(String username, String token, MutableLiveData<Integer> res) {

    }
}