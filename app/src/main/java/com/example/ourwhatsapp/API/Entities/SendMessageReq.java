package com.example.ourwhatsapp.API.Entities;

public class SendMessageReq {
    private String msg;

    public SendMessageReq(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
