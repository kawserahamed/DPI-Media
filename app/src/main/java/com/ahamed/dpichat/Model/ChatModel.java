package com.ahamed.dpichat.Model;

public class ChatModel {

    String senderId;
    String receiverId;
    String message;
    String messageId;

    public ChatModel(String senderId, String receiverId, String message, String messageId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.messageId = messageId;
    }

    public ChatModel() {
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}


