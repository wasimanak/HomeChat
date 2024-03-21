package com.pksofter.www.homechat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextView btn_register;
    EditText et_email, et_password;
    TextView btn_login, btn_forget;
    DatabaseReference reference;
    SessionMAnager sessionMAnager;
    DatabaseReference referenceUpdateToken;
    KProgressHUD kProgressHUD;
    String token;

    String age, dateofBirth, email, fcmToken, usertype, intrestedCategory, lat, lng, password, image, phone, userid, username;
    boolean is_active;
    Map<String, Object> userInterests;
    public void init() {
        initProgressDialog();
        btn_login = findViewById(R.id.btn_login);
        et_email = findViewById(R.id.et_email);
        btn_register = findViewById(R.id.btn_register);
        et_password = findViewById(R.id.et_password);
        reference = FirebaseDatabase.getInstance().getReference("users");
        sessionMAnager = new SessionMAnager(this);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });

    }
    public void validation() {
        if (et_password.getText().toString().trim().equals("")) {
            et_password.setError("enter password");
        } else if (et_email.getText().toString().trim().equals("")) {

            et_email.setError("enter email");
        } else {


                LoginUser();

        }
    }

    public void LoginUser() {
        Query query = reference.orderByChild("email").equalTo(et_email.getText().toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        username = data.child("userName").getValue(String.class);
                        email = data.child("email").getValue(String.class);
                        password = data.child("password").getValue(String.class);
                        fcmToken = data.child("fcmToken").getValue(String.class);
                        userid = data.child("userId").getValue(String.class);
                        usertype = data.child("usertype").getValue(String.class);
//                        userInterests = data.child("interests").getValue(Map.class);
//                        Map<String, Object> map = (Map<String, Object>) data.child("interests").getValue();;



                    }

                    if (password.equals(et_password.getText().toString())) {

                        sessionMAnager.setUserid(userid);
                        sessionMAnager.setUsername(username);
                        sessionMAnager.setEmail(email);
                        sessionMAnager.setPassword(password);

                        sessionMAnager.setFcmToken(fcmToken);
                        sessionMAnager.setusertype(usertype);
//                        sessionMAnager.setInterset(userInterests);
                        sessionMAnager.setLogin(true);
//                        referenceUpdateToken = FirebaseDatabase.getInstance().getReference("users").child(userid);
                        UpdateToken updateToken = new UpdateToken();
                        updateToken.execute();
                        if (sessionMAnager.getUsertype().equals("admin")){
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();

                        }else {
                            startActivity(new Intent(LoginActivity.this, ConverSationsActivity.class));
                            finish();
                        }

                        finish();

                    } else {

                        Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();

                    }

                } else {

                    Toast.makeText(LoginActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

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


                        }
                    }
                });

        return token;
    }
    public class UpdateToken extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            getToken();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            referenceUpdateToken = FirebaseDatabase.getInstance().getReference("users").child(userid);
            referenceUpdateToken.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("fcmToken", token);
                        sessionMAnager.setFcmToken(token);
                        referenceUpdateToken.updateChildren(map, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Log.d("String", "Token update Info");
                                Log.d("String", "" + error);

                            }
                        });

                    }

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    kProgressHUD.dismiss();
                    Toast.makeText(LoginActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void initProgressDialog() {
        kProgressHUD = KProgressHUD.create(LoginActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(4)
                .setBackgroundColor(getResources().getColor(R.color.kprogresshud_default_color))
                .setDimAmount(0.5f);
    }

    private void scheduleDismiss() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                kProgressHUD.dismiss();
            }
        }, 100);
    }

}