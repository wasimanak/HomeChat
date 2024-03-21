package com.pksofter.www.homechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    RecyclerView rv_users;
    UsersAdapter usersAdapter;
    List<UserModel> userModelList;
    KProgressHUD kProgressHUD;

    public void initProgressDialog() {
        kProgressHUD = KProgressHUD.create(MainActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(4)
                .setBackgroundColor(getResources().getColor(R.color.kprogresshud_default_color))
                .setDimAmount(0.5f);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_users=findViewById(R.id.rv_users);
        userModelList=new ArrayList<>();
        initProgressDialog();
        kProgressHUD.show();
        getData();
    }

    public void getData() {

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                     /*   if (new SessionMAnager(MainActivity.this).getUserid().equals(data.child("userId").getValue(String.class)))
                        {

                        }else {*/
                            userModelList.add(new UserModel( data.child("userName").getValue(String.class),
                                    data.child("email").getValue(String.class),
                                    data.child("userId").getValue(String.class),
                                    data.child("fcmToken").getValue(String.class),
                                    data.child("usertype").getValue(String.class)));
                            usersAdapter=new UsersAdapter(userModelList,MainActivity.this);
                            rv_users.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            rv_users.setAdapter(usersAdapter);
                        }


                /*        username = data.child("userName").getValue(String.class);
                        email = data.child("email").getValue(String.class);
                        password = data.child("password").getValue(String.class);
                        fcmToken = data.child("fcmToken").getValue(String.class);
                        userid = data.child("userId").getValue(String.class);
                        usertype = data.child("usertype").getValue(String.class);*/

//                        userInterests = data.child("interests").getValue(Map.class);
//                        Map<String, Object> map = (Map<String, Object>) data.child("interests").getValue();;



//                    }


                } else {

                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        kProgressHUD.dismiss();
    }


}