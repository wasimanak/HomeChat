package com.pksofter.www.homechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Hashtable;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText et_name, et_email, et_password, et_cinfirm_password, et_phone;
    TextView et_dateOfBirth, btn_continue, tv_location;
    DatabaseReference reference;
    String token="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_continue = findViewById(R.id.btn_continue);
        reference = FirebaseDatabase.getInstance().getReference("users");

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getToken();
            }
        });
    }
    public String getToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {

                            token = task.getResult();
                            validation();

                        }
                    }
                });
        /*FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {

                        if (!task.isSuccessful()) {
                            Log.w("error FCM ", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();

                        // Log and toast

//                        Toast.makeText(RegisterActivity.this, token, Toast.LENGTH_SHORT).show();
                    }
                });*/
        return token;
    }
    public void validation() {
        if (et_name.getText().toString().trim().equals("")) {
            et_name.setError("Required");
        }  else if (et_email.getText().toString().trim().equals("")) {
            et_email.setError("Required");
        } else if (et_password.getText().toString().trim().equals("")) {
            et_password.setError("Required");
        }else {

           insertDataToFirebase();


        }
    }
    private void insertDataToFirebase() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Map<String, Object> map = new Hashtable<>();
                final String key = reference.push().getKey();
                map.put("userId", key);
                map.put("userName", et_name.getText().toString());
                map.put("email", et_email.getText().toString());
                map.put("password", et_password.getText().toString());
                map.put("fcmToken", token);
                map.put("usertype", "user");
                reference.child(key).setValue(map);
                finish();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                /*kProgressHUD.dismiss();*/
                Toast.makeText(RegisterActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}