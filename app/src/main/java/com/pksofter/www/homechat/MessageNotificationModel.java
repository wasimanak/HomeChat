package com.pksofter.www.homechat;

public class MessageNotificationModel {
    public String to;

    public Notification notification = new Notification();
    public Data data = new Data();


    public static class Notification {
        public String title;
        public String body;
        public String time;
        public String senderid;
        public String senderName;
        public String senderImage;
        public String fromm;

    }

    public static class Data {
        public String title;
        public String body;
        public String time;
        public String senderid;
        public String senderName;
        public String senderImage;
        public String fromm;
    }
}
