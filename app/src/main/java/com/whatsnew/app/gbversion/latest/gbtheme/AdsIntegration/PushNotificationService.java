package com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.start.GBV_SplashActivity;

import java.util.Map;

public class PushNotificationService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> params = remoteMessage.getData();
        if (params.size() > 0) {
            sendNotification(params.get("title"), params.get("message"));
            broadcastNewNotification();
            return;
        }
        sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
    }

    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(this, GBV_SplashActivity.class);
        intent.addFlags(67108864);
        intent.putExtra("Push Notification", title);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 1073741824);
        NotificationManager notificationManager = (NotificationManager) getSystemService("notification");
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel mChannel = new NotificationChannel(getResources().getString(R.string.app_name), getResources().getString(R.string.app_name), 4);
            mChannel.enableLights(true);
            mChannel.setLockscreenVisibility(1);
            notificationManager.createNotificationChannel(mChannel);
        }
        notificationManager.notify(1, new NotificationCompat.Builder(this, getResources().getString(R.string.app_name)).setAutoCancel(true).setSmallIcon(R.mipmap.ic_launcher).setColor(getResources().getColor(R.color.black)).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)).setDefaults(-1).setPriority(1).setContentTitle(title).setContentText(messageBody).setChannelId(getResources().getString(R.string.app_name)).setFullScreenIntent(pendingIntent, true).build());
    }

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
    }

    private void broadcastNewNotification() {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("new_notification"));
    }

}