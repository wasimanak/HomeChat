package com.pksofter.www.homechat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatingActivity extends AppCompatActivity {
    String dateCurrent;
    String txt_senderId, txt_receiverId, txt_time, txt_msg;
    SessionMAnager sessionMAnager;
    TextView tv_username;
    DatabaseReference reference;
    EditText et_sms;
    List<ChatModel> chat_list;
    MessageAdapter messageAdapter;
    RecyclerView rv_msg;
    String time;
    ImageView btn_back;
    String msgText;
    String fcm;
    ImageView btn_sendsms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chating);
        btn_back = findViewById(R.id.btn_back);
        btn_sendsms = findViewById(R.id.btn_sendsms);
        et_sms = findViewById(R.id.et_sms);
        tv_username = findViewById(R.id.tv_username);
        rv_msg = findViewById(R.id.rv_msg);
        sessionMAnager = new SessionMAnager(this);
        txt_senderId = sessionMAnager.getUserid();
        txt_receiverId = getIntent().getStringExtra("receiverid");
        tv_username.setText(getIntent().getStringExtra("name"));
        readMessage(getIntent().getStringExtra("receiverid"), sessionMAnager.getUserid());

        txt_time = getDate();


btn_sendsms.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

            if (et_sms.getText().toString().trim() == "") {
                Log.d("error:", "empty msg not send ");
            } else {
                insertDataToFirebase();
            }

    }
});


    }
    private void readMessage(final String readerId, final String senderId) {
        getDate();
        chat_list = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("chats");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chat_list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    ChatModel chat = snapshot.getValue(ChatModel.class);
//                    assert chat != null;

                    if (dateCurrent.equals(snapshot.child("date").getValue(String.class))) {

                    ChatModel chat = new ChatModel(snapshot.child("receiverId").getValue(String.class), snapshot.child("senderId").getValue(String.class), snapshot.child("time").getValue(String.class), snapshot.child("sms").getValue(String.class));
                    if (chat.getReceiverid().equals(readerId) && chat.getSenderid().equals(senderId) || chat.getReceiverid().equals(senderId) && chat.getSenderid().equals(readerId)) {
                        chat_list.add(chat);
                    }
                }
                }

                messageAdapter = new MessageAdapter(ChatingActivity.this, chat_list);
                rv_msg.setLayoutManager(new LinearLayoutManager(ChatingActivity.this));
                rv_msg.setAdapter(messageAdapter);
                messageAdapter.notifyDataSetChanged();
//                rv_msg.smoothScrollToPosition(chat_list.size()-1);
                rv_msg.smoothScrollToPosition(rv_msg.getAdapter().getItemCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("DB Error in readMessage", "onCancelled: Error -> " + databaseError.toString());
            }
        });


    }

    private void insertDataToFirebase() {
        reference = FirebaseDatabase.getInstance().getReference("chats");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> map = new Hashtable<>();
                String key = reference.push().getKey();
                reference.child(key).setValue(map);
                map.put("senderId", txt_senderId);
                map.put("receiverId", txt_receiverId);
                map.put("sms", et_sms.getText().toString());
                map.put("time", time);
                map.put("date", dateCurrent);

                reference.child(key).setValue(map);

//                CreateChat();
                final DatabaseReference chatReferenceForSender = FirebaseDatabase.getInstance().getReference("conversations")
                        .child(sessionMAnager.getUserid())
                        .child(txt_receiverId);

                chatReferenceForSender.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("id", txt_receiverId);
                            map.put("lastmsg", et_sms.getText().toString());

                            map.put("time", time);
                            map.put("name", getIntent().getStringExtra("name"));
                            map.put("image", getIntent().getStringExtra("image"));
                            chatReferenceForSender.setValue(map);
                            et_sms.setText("");
                            getFcm();

                        } else {
                            Map<String, Object> map = new HashMap<>();
                            map.put("lastmsg", et_sms.getText().toString());

                            map.put("time", time);

                            chatReferenceForSender.updateChildren(map, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                    Log.d("database", "" + databaseError);
                                    et_sms.setText("");
                                    getFcm();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                final DatabaseReference chatReferenceForReceiver = FirebaseDatabase.getInstance().getReference("conversations")
                        .child(txt_receiverId)
                        .child(sessionMAnager.getUserid());

                chatReferenceForReceiver.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("id", sessionMAnager.getUserid());
                            map.put("lastmsg", et_sms.getText().toString());

                            map.put("time", time);
                            map.put("name", sessionMAnager.getUsername());
                            chatReferenceForReceiver.setValue(map);
                            et_sms.setText("");
                            getFcm();


                        } else {
                            Map<String, Object> map = new HashMap<>();
                            map.put("lastmsg", et_sms.getText().toString());

                            map.put("time", time);
                            chatReferenceForReceiver.updateChildren(map, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                                    Log.d("database", "" + databaseError);
                                    et_sms.setText("");
                                    getFcm();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(ChatingActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



    public void getFcm() {
        DatabaseReference databaseReference=reference = FirebaseDatabase.getInstance().getReference("users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (dataSnapshot.child(txt_senderId).exists() && dataSnapshot.child(txt_receiverId).exists()) {
                            if (data.child("userId").getValue(String.class).equals(txt_receiverId)) {
                                fcm=data.child("fcmToken").getValue(String.class);
                            }
                        }
//


                    }
                    sendNotification(sessionMAnager.getUsername()
                            , msgText, getDate(), txt_senderId
                            , fcm);


                } else {

                    Toast.makeText(ChatingActivity.this, "Invalid user", Toast.LENGTH_SHORT).show();
//                    new GlideToast.makeToast(ChatingActivity.this, "Invalid user ", GlideToast.LENGTHLONG, GlideToast.CUSTOMTOAST, GlideToast.BOTTOM, R.drawable.ic_close, "#FFAB00").show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChatingActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public String getDate() {

        String timeReal;
        Date date = new Date();
        String  datee[]=String.valueOf(date).split(" ");
        dateCurrent=datee[0]+" "+datee[1]+" "+datee[2];
        SimpleDateFormat dateFormatWithZone = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat dateFormatWithZone2 = new SimpleDateFormat("HH:mm", Locale.getDefault());


        String[] temp = dateFormatWithZone2.format(date).split(":");
        int t = Integer.parseInt(temp[0]);

        if (t > 12) {
            timeReal = t - 12 + ":" + temp[1] + " pm";
        } else {
            timeReal = dateFormatWithZone2.format(date) + " am";

        }

        time = timeReal;
        return timeReal;
    }
    public void sendNotification(String title, String msg, String time,String senderid,String fcm) {
        SessionMAnager sessionMAnager = new SessionMAnager(ChatingActivity.this);
        Gson gson = new Gson();
        MessageNotificationModel notificationModel = new MessageNotificationModel();
        notificationModel.notification.title = title;
        notificationModel.notification.body = msg;
        notificationModel.notification.time = time;
        notificationModel.notification.senderid = senderid;
        notificationModel.notification.fromm = "chat";
//        notificationModel.notification.messageKey = messageKey;
//        notificationModel.notification.senderUserId = mCurrentUserId;
//        notificationModel.notification.receiverUserId = mChatUserId;
        notificationModel.data.title = title;
        notificationModel.data.body = msg;
        notificationModel.data.time = time;
        notificationModel.data.senderid = senderid;
        notificationModel.data.fromm = "chat";

//        notificationModel.data.
//        notificationModel.data.messageKey = messageKey;
//        notificationModel.data.senderUserId = mCurrentUserId;
//        notificationModel.data.receiverUserId = mChatUserId;

//        notificationModel.to = fcm;//ye kia kiya he
        notificationModel.to = fcm;



//       //same par send nai huta ? jis device say karha hun usii par receive
        //dubara bhaij notification
        //token update kr register k doran// new user bnaon ?//jo existing he usi ko update kr dyusko update abi nai kar sakta new bnata hun
        // ya phir har bar login par token update karwaen ?hn thek h
        //challo abi tu new banata hun
        //hugya new login new token
        //bhaij notification
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf8"),
                gson.toJson(notificationModel));
        final Request request = new Request.Builder()
                .header("Content-Type", "application/json")
                .addHeader("Authorization", "key=AAAA-wx017g:APA91bGeYv-xB7fL6lOho1DjYT5a7mtlEQcalyulEyemDnEgnInLb5ar3BasJNvDL2_kYQESpYVAH9YghmboHKA-6Dd7NlJOorgefVjtxHUTMgFrmCsRCpMo0W9EaJW5q3esrQmlC46X")
                .url("https://fcm.googleapis.com/fcm/send")
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("okhttp fail", e.getMessage());
//                neechy response me kia ata he
//            log me
//                yaha oy log me kia ata he?
            }//lol ata hy

            //            check kr k bta  error": "NotRegistered" ye ata hy//is ki pain ko ya
//            android sy dekha kia ata he okay ruk
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("okhttp response", response.toString());
            }
        });
    }
}