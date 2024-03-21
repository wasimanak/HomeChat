package com.pksofter.www.homechat;

public class ConverstaionsModel {
    String receiverId,senderId,time,lastsms,receiverName,image;

    public ConverstaionsModel(String receiverId, String senderId, String time, String lastsms, String receiverName, String image) {
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.time = time;
        this.lastsms = lastsms;
        this.receiverName=receiverName;
        this.image=image;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLastsms() {
        return lastsms;
    }

    public void setLastsms(String lastsms) {
        this.lastsms = lastsms;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;

    }
}
