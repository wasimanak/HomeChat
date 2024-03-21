package com.pksofter.www.homechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ConverSationsActivity extends AppCompatActivity {
    List<ConverstaionsModel> chat_list;
    RecyclerView rv_converstation;
    ConversationsAdapter messageAdapter;
    DatabaseReference reference;
    SessionMAnager sessionMAnager;
    public void init(){
        rv_converstation=findViewById(R.id.rv_converstations);
        chat_list=new ArrayList<>();
        sessionMAnager=new SessionMAnager(this);
        readMessage();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conver_sations);
        init();
    }

    private void readMessage(){
        chat_list = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("conversations").child(sessionMAnager.getUserid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chat_list.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

//                    ChatModel chat = snapshot.getValue(ChatModel.class);
//                    assert chat != null;

                        chat_list.add(new ConverstaionsModel(snapshot.child("id").getValue(String.class)
                                , sessionMAnager.getUserid()
                                , snapshot.child("time").getValue(String.class)
                                , snapshot.child("lastmsg").getValue(String.class)
                                , snapshot.child("name").getValue(String.class)
                                , snapshot.child("image").getValue(String.class)));


                    }

                    messageAdapter = new ConversationsAdapter(ConverSationsActivity.this, chat_list);
                    rv_converstation.setLayoutManager(new LinearLayoutManager(ConverSationsActivity.this));

                    rv_converstation.setAdapter(messageAdapter);

                    messageAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("DB Error in readMessage", "onCancelled: Error -> "+ databaseError.toString());
            }
        });


    }


}