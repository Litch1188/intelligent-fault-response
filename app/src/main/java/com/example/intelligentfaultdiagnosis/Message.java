package com.example.intelligentfaultdiagnosis;

public class Message {
    String message;
    int type;//0为发送，1为接收
    public String getMessage() {
        return message;
    }

    public int getType() {
        return type;
    }

    public void setMessage(String message, int type) {
        this.message = message;
        this.type=type;
    }
}
