package com.aldenocain.chatroomapp;
public class Msg {
    private int id;
    private int senderId;
    private String messageText;
    private String timestamp;

    public Msg(int id, int senderId, String messageText, String timestamp) {
        this.id = id;
        this.senderId = senderId;
        this.messageText = messageText;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public int getSenderId() {
        return senderId;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
