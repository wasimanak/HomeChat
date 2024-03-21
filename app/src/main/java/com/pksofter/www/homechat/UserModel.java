package com.pksofter.www.homechat;

public class UserModel {
    String username,email,userid,fcmtoken,usertype;

    public UserModel(String username, String email, String userid, String fcmtoken,String usertype) {
        this.username = username;
        this.email = email;
        this.userid = userid;
        this.fcmtoken = fcmtoken;
        this.usertype=usertype;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFcmtoken() {
        return fcmtoken;
    }

    public void setFcmtoken(String fcmtoken) {
        this.fcmtoken = fcmtoken;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}
