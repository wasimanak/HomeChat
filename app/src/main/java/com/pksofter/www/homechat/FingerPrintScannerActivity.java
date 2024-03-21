package com.pksofter.www.homechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;

import android.content.Intent;
import android.os.Bundle;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FingerPrintScannerActivity extends AppCompatActivity {

    SessionMAnager sessionMAnager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_print_scanner);
        sessionMAnager=new SessionMAnager(this);
        if (!sessionMAnager.isLogin()){
            startActivity(new Intent(FingerPrintScannerActivity.this, LoginActivity.class));
            finish();
        }

        Executor executor = Executors.newSingleThreadExecutor();
        final BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    // user clicked negative button
                    //Toast.makeText(activity, "Operation Cancelled By User!", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(activity, "Unknown Error!", Toast.LENGTH_SHORT).show();
                    // Called when an unrecoverable error has been encountered and the operation is complete.
                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                    if (sessionMAnager.getUsertype().equals("admin")) {
                        startActivity(new Intent(FingerPrintScannerActivity.this, MainActivity.class));
                        finish();
                        //Toast.makeText(activity, "Login Successful!", Toast.LENGTH_SHORT).show();
                    } else {
                        startActivity(new Intent(FingerPrintScannerActivity.this, ConverSationsActivity.class));
                        finish();
                    }

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                //Called when a biometric is valid but not recognized.
                //Toast.makeText(activity, "Fingerprint not recognized!", Toast.LENGTH_SHORT).show();
            }
        });

        final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login")
                .setSubtitle("Login to your account!")
                .setDescription("Place your finger on the device home button to verify your identity")
                .setNegativeButtonText("CANCEL")
                .build();
        biometricPrompt.authenticate(promptInfo);

    }
}