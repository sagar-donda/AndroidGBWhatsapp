package com.whatsnew.app.gbversion.latest.gbtheme.shake_Detector;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
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

public class GBV_ShakeServiceNew extends Service {

    public int counter = 0;

    private GBV_appPreferences appPreferences;
    private GBV_ShakeOptions shakeOptions;
    public static boolean isServiceRunning;

    public void onCreate() {
        this.appPreferences = new GBV_appPreferences(getBaseContext());

        Log.d("--shake_service--", "onCreate: ");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("--shake_service--", "onStartCommand called");

        this.shakeOptions = new GBV_ShakeOptions().background(this.appPreferences.getBoolean("BACKGROUND",
                Boolean.TRUE)).sensibility(this.appPreferences.getFloat("SENSIBILITY", 1.2f))
                .shakeCount(this.appPreferences.getInt("SHAKE_COUNT", 1))
                .interval(this.appPreferences.getInt("SHAKE_INTERVAL", 2000));

        isServiceRunning = true;

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startForegroundService();
        else
            startForeground(1, new Notification());

        startShakeService(getBaseContext());

        if (this.shakeOptions.isBackground()) {
            Log.d("--shake_service--", "isBackground: ");
            startTimer();
            return START_STICKY;
        }

        Log.d("--shake_service--", "onStartCommand: ");
        return START_NOT_STICKY;
    }

    public void startShakeService(Context context) {
        GBV_ShakeListener shakeListener = new GBV_ShakeListener(this.shakeOptions, context);
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(1);
        if (sensors.size() > 0) {
            Sensor sensor = sensors.get(0);
            sensorManager.registerListener(shakeListener, sensor, 1);
        }
        Log.d("--shake_service--", "startShakeService: ");
    }

    @Override
    public void onDestroy() {
        Log.d("--shake_service--", "onDestroy called");

        stoptimertask();

        isServiceRunning = false;
        stopForeground(true);

        super.onDestroy();
    }

    private Timer timer;

    public void startTimer() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Log.d("--shake_service--", "=========  " + (counter++));
            }
        };
        timer.schedule(timerTask, 1000, 1000);
    }

    public void stoptimertask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
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
        closeIntent.putExtra("destroyCode", 666);
        PendingIntent closePendingIntent = PendingIntent.getService(this, 0, closeIntent, PendingIntent.FLAG_MUTABLE);

        String appName = getString(R.string.app_name);
        String CHANNEL_ID = "whatstools.gbstatus.statussaver.tools";
        NotificationChannel serviceChannel = new NotificationChannel(
                CHANNEL_ID,
                appName,
                NotificationManager.IMPORTANCE_DEFAULT
        );
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(serviceChannel);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getText(R.string.app_name))
                .setContentText("Please shake your mobile to open whatsapp.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setContentIntent(tabPendingIntent)
                .addAction(R.mipmap.ic_launcher, null, closePendingIntent)
                .build();
        startForeground(2, notification);
    }
}