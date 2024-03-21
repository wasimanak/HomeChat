package com.pksofter.www.homechat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MyService extends FirebaseMessagingService {

    Context context;
    String tokenUpdated="";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "from: " + remoteMessage.getFrom());
        context= getApplicationContext();
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

                    sendMessageNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("body"), remoteMessage.getData().get("time"),remoteMessage.getData().get("senderid"),remoteMessage.getData().get("senderImage"),"", context);

/*  //          Toast.makeText(this, remoteMessage.getData().get("title"), Toast.LENGTH_SHORT).show();
            if (*//* Check if data needs to be processed by long running job *//* true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.

            } else {
                // Handle message within 10 seconds

            }*/

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    /*private void sendNotification(String title, String messageBody, String notificationType , Context context) {


        Intent intent = new Intent(context, OpenNotification.class);
        intent.putExtra("notificationType",notificationType);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 *//* Request code *//*, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = "fcm_default_channel";//getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.drawable.splash3)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.user_male_default))
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        notificationManager.notify(0 *//* ID of notification *//*, notificationBuilder.build());
    }*/

    private void sendMessageNotification(String title, String messageBody, String time, String senderId, String senderImage, String notificationType , Context context) {

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.conversations_card);


        Intent intent = new Intent(context, ChatingActivity.class);
        intent.putExtra("receiverid",senderId);
        intent.putExtra("name",title);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = "fcm_default_channel";//getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setSmallIcon(R.drawable.ic_baseline_mail_outline_24)
                        .setAutoCancel(true)
                        .setTicker("custom")
                        .setSound(defaultSoundUri)
                        .setPriority(Notification.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent);

        contentView.setImageViewResource(R.id.image, R.drawable.user_male_default);
        contentView.setTextViewText(R.id.tv_name, title);
        contentView.setTextViewText(R.id.tv_lastmsg, messageBody);
        contentView.setTextViewText(R.id.tv_lastmsg, time);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
       /* if (Helper.isAppRunning(context,"com.pksofter.eattogetheruser")){

            try {
                MediaPlayer mediaPlayer=new MediaPlayer();
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }else {
                    mediaPlayer=MediaPlayer.create(context,R.raw.notification);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {*/
        notificationManager.notify(0/* ID of notification */, notificationBuilder.build());
//    }
    }

    @SuppressLint("WrongThread")
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        tokenUpdated=s;
        if (tokenUpdated.equals("")){
            Toast.makeText(context, "Token update problem", Toast.LENGTH_SHORT).show();
        }else {
            UpdateToken updateToken=new UpdateToken();
            updateToken.execute();
        }




    }

    public class UpdateToken extends AsyncTask<Void, Void, Void> {
        DatabaseReference referenceUpdateToken;
        SessionMAnager sessionMAnager=new SessionMAnager(context);
        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            referenceUpdateToken = FirebaseDatabase.getInstance().getReference("users").child(sessionMAnager.getUserid());
            referenceUpdateToken.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("fcmToken", tokenUpdated);
                        sessionMAnager.setFcmToken(tokenUpdated);
                        referenceUpdateToken.updateChildren(map, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Log.d("String","Token update Info");
                                Log.d("String",""+error);

                            }
                        });

                    }

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
