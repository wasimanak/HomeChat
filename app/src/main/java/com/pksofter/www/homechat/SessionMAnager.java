package com.pksofter.www.homechat;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class SessionMAnager {
    Context context;
    SharedPreferences sharedPreferences;
    public SessionMAnager(Context context) {
        this.context = context;
    }

    public Map<String, Object> getInterset() {

        Map<String, Object> temp=new HashMap<>();
        for (int i=0;i<5;i++){
           // editor.putString("interest"+i,interset.get(i+"").toString());
            temp.put(i+"",sharedPreferences.getString("interest"+i,""));
        }
        return temp;
    }

    public void setInterset(Map<String, Object> interset) {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        for (int i=0;i<interset.size();i++){
            editor.putString("interest"+i,interset.get(i+"").toString());
        }

        editor.commit();
    }

    public void setAge(String age) {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("age",age);
        editor.commit();
    }

    public void setDateofBirth(String dateofBirth) {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("dateofBirth",dateofBirth);
        editor.commit();
    }

    public void setEmail(String email) {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("email",email);
        editor.commit();
    }

    public void setFcmToken(String fcmToken) {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("fcmToken",fcmToken);
        editor.commit();
    }

    public void setIntrestedCategory(String intrestedCategory) {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("intrestedCategory",intrestedCategory);
        editor.commit();
    }



    public void setLat(String lat) {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("lat",lat);

        editor.commit();
    }

    public void setLng(String lng) {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("lng",lng);

        editor.commit();
    }

    public void setPassword(String password) {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("password",password);

        editor.commit();
    }

    public void setPhone(String phone) {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("phone",phone);

        editor.commit();
    }

    public void setUserid(String userid) {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("userid",userid);

        editor.commit();
    }

    public void setUsername(String username) {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("username",username);

        editor.commit();
    }

    public String getUsertype() {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        return sharedPreferences.getString("address","");
    }

    public void setusertype(String address) {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("address",address);

        editor.commit();
    }

    public String getImage() {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        return sharedPreferences.getString("image","");
    }

    public void setImage(String image) {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("image",image);

        editor.commit();
    }

    public boolean isIs_active() {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        return sharedPreferences.getBoolean("is_active",false);

    }

    public void setIs_active(boolean is_active) {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("is_active",is_active);

        editor.commit();
    }

    public String getAge() {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        return sharedPreferences.getString("age","");
    }

    public String getDateofBirth() {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        return sharedPreferences.getString("dateofBirth","");

    }

    public String getEmail() {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        return sharedPreferences.getString("email","");

    }

    public String getFcmToken() {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        return sharedPreferences.getString("fcmToken","");

    }

    public String getIntrestedCategory() {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        return sharedPreferences.getString("intrestedCategory","");

    }



    public String getLat() {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        return sharedPreferences.getString("lat","");
    }

    public String getLng() {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        return sharedPreferences.getString("lng","");
    }

    public String getPassword() {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        return sharedPreferences.getString("password","");
    }

    public String getPhone() {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        return sharedPreferences.getString("phone","");
    }

    public String getUserid() {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        return sharedPreferences.getString("userid","");
    }

    public String getUsername() {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        return sharedPreferences.getString("username","");
    }

    public boolean isLogin() {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        return sharedPreferences.getBoolean("login",false);
    }

    public void setLogin(boolean login) {
        sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("login",login);

        editor.commit();
    }

    public void clearSession(){
      sharedPreferences=context.getSharedPreferences("session", Context.MODE_PRIVATE);
      SharedPreferences.Editor editor=sharedPreferences.edit();
      editor.putString("age","");
      editor.putString("dateofBirth","");
      editor.putString("email","");
      editor.putString("fcmToken","");
      editor.putString("intrestedCategory","");
      editor.putBoolean("is_active",false);
      editor.putString("image","");
      editor.putString("address","");
      editor.putString("username","");
      editor.putString("userid","");
      editor.putString("phone","");
      editor.putString("password","");
      editor.putString("lng","");
      editor.putString("lat","");
        editor.putBoolean("login",false);
      editor.commit();



  }
}
