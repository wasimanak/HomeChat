package com.pksofter.www.homechat;

public class ChatModel {
    String receiverid,senderid,time,message;

    public ChatModel() {
    }

    public ChatModel(String receiverid, String senderid, String time, String message) {
        this.receiverid = receiverid;
        this.senderid = senderid;
        this.time = time;
        this.message = message;
    }

    public String getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
