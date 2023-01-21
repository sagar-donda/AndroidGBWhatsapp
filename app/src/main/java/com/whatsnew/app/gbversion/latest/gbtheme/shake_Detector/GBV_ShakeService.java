package com.whatsnew.app.gbversion.latest.gbtheme.shake_Detector;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.start.GBV_MainActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GBV_ShakeService extends Service {

    private GBV_appPreferences appPreferences;
    private Sensor sensor;
    private SensorManager sensorManager;
    private GBV_ShakeListener shakeListener;
    private GBV_ShakeOptions shakeOptions;

    public void onCreate() {
        this.appPreferences = new GBV_appPreferences(getBaseContext());

        Log.d("--shake_service--", "onCreate: ");
        super.onCreate();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startForegroundService();
        else
            startForeground(1, new Notification());
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        this.shakeOptions = new GBV_ShakeOptions().background(this.appPreferences.getBoolean("BACKGROUND",
                Boolean.TRUE)).sensibility(this.appPreferences.getFloat("SENSIBILITY", 1.2f))
                .shakeCount(this.appPreferences.getInt("SHAKE_COUNT", 1))
                .interval(this.appPreferences.getInt("SHAKE_INTERVAL", 2000));

        startShakeService(getBaseContext());

        startTimer();

        if (this.shakeOptions.isBackground()) {
            Log.d("--shake_service--", "isBackground: ");
            return START_STICKY;
        }

        Log.d("--shake_service--", "onStartCommand: ");
        return START_NOT_STICKY;
    }

    public void startShakeService(Context context) {
        this.shakeListener = new GBV_ShakeListener(this.shakeOptions, context);
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> sensors = this.sensorManager.getSensorList(1);
        if (sensors.size() > 0) {
            this.sensor = sensors.get(0);
            this.sensorManager.registerListener(this.shakeListener, this.sensor, 1);
        }
        Log.d("--shake_service--", "startShakeService: ");
    }

    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public int counter = 0;

    private Timer timer;
    private TimerTask timerTask;

    public void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            public void run() {
                Log.d("--shake_service--", "=========  " + (counter++));
            }
        };
        timer.schedule(timerTask, 1000, 1000);
    }

    public void onDestroy() {
        super.onDestroy();

        stoptimertask();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, GBV_Restarter.class);
        this.sendBroadcast(broadcastIntent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        Log.d("--shake_service--", "onTaskRemoved");
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        Log.d("--shake_service--", "startShakeService: ");
        return null;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void startForegroundService() {
        Intent tabIntent = new Intent(this, GBV_MainActivity.class);
        tabIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent tabPendingIntent = PendingIntent.getActivity(this, 0, tabIntent, PendingIntent.FLAG_MUTABLE);

        Intent closeIntent = new Intent(this, GBV_ShakeServiceNew.class);
        closeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        closeIntent.putExtra("destroyCode", 666); // this is the important line //
        PendingIntent closePendingIntent = PendingIntent.getService(this, 0, closeIntent, PendingIntent.FLAG_MUTABLE);

        String NOTIFICATION_CHANNEL_ID = "whatstools.gbstatus.statussaver.tools";
        String channelName = "Shake Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_HIGH);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(getText(R.string.app_name))
                .setContentText("Please shake your mobile to open whatsapp.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(tabPendingIntent)
                .addAction(R.drawable.logo, null, closePendingIntent)
                .build();
        startForeground(2, notification);
    }
}