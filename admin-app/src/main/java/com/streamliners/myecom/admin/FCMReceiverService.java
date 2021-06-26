package com.streamliners.myecom.admin;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

public class FCMReceiverService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        createNotificationChannel();

        //Extract notification & notify()
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        notify(notification.getTitle(), notification.getBody());

        workingWithData(remoteMessage.getData());
    }

    private void workingWithData(Map<String, String> data) {
        String orderId = data.get("orderId");

        //...do something with this orderId

        Log.e("MeraTag", "workingWithData : orderId = " + orderId + "!");
    }

    private void notify(String title, String msg) {
        //Handle notification click
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Build notification
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this,"new-orders")
                        .setSmallIcon(R.drawable.ic_order)
                        .setContentTitle(title)
                        .setContentText(msg)
                        .setColor(getResources().getColor(R.color.teal_200))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(msg));

        //Notify
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(new Random().nextInt(10000), builder.build());
    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            //Notification Channel info
            CharSequence name = "New order Notifications";
            String description = "This is the main channel for notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            //create channel
            NotificationChannel mChannel = new NotificationChannel("new-orders", name, importance);
            mChannel.setDescription(description);

            //configure sound & vibration
            mChannel.setVibrationPattern(new long[]{0, 500, 1000});
            AudioAttributes.Builder attrs = new AudioAttributes.Builder();
            attrs.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION);
            attrs.setUsage(AudioAttributes.USAGE_ALARM);
            mChannel.setSound(Settings.System.DEFAULT_NOTIFICATION_URI, attrs.build());

            //create!
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(
                    Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(mChannel);
        }
    }
}
