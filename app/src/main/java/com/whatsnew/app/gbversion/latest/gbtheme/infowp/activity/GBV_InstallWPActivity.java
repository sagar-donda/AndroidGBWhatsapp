package com.whatsnew.app.gbversion.latest.gbtheme.infowp.activity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;


import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.GBV_Utils;
import com.whatsnew.app.gbversion.latest.gbtheme.infowp.util.GBV_AppPackageNameUtils;
import com.whatsnew.app.gbversion.latest.gbtheme.infowp.util.GBV_NotificationReceiver;

import java.util.Calendar;

public class GBV_InstallWPActivity extends AppCompatActivity {
    private ImageView goToGooglePlay;
    private TextView installedVersion;
    private boolean isBackButtonPressed = false;
    private TextView latestVersion,instal;
    private ProgressBar progressWheel;
    private RelativeLayout swipeRefreshLayout;

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.gbv_activity_info_wpactivity);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        registerReceiver(new GBV_NotificationReceiver(), new IntentFilter("DISPLAY_NOTIFICATION"));
        Intent intent = new Intent(this, GBV_NotificationReceiver.class);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NotificationManager.class);
        if (Build.VERSION.SDK_INT >= 26) {
            notificationManager.createNotificationChannel(new NotificationChannel("UpdateLatestVersionNotification", "NotificationChannel", 3));
        }
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 0, intent,  PendingIntent.FLAG_MUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM);
        Calendar instance = Calendar.getInstance();
        instance.set(11, 12);
        instance.set(12, 0);
        instance.set(13, 0);
        alarmManager.setRepeating(0, instance.getTimeInMillis(), 86400000L, broadcast);
        this.installedVersion = (TextView) findViewById(R.id.app_installed_version);
        this.latestVersion = (TextView) findViewById(R.id.app_latest_version);
        this.progressWheel = (ProgressBar) findViewById(R.id.progress_wheel);
        this.swipeRefreshLayout = (RelativeLayout) findViewById(R.id.swipeContainer);
        this.goToGooglePlay = (ImageView) findViewById(R.id.go_to_google_play);
        this.instal = (TextView) findViewById(R.id.instal);

        this.goToGooglePlay.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                GBV_InstallWPActivity installWPActivity = GBV_InstallWPActivity.this;
                GBV_AppPackageNameUtils.goToPlaystore(installWPActivity, installWPActivity.getString(R.string.app_playstore_url));
            }
        });
        new GBV_AppPackageNameUtils.LatestAppVersion(this, this.latestVersion, getString(R.string.app_latest_version_url), this.progressWheel).execute(new Void[0]);
        checkInstalledAppVersion();

        if (GBV_Utils.appInstalledOrNot(GBV_InstallWPActivity.this, "com.whatsapp")) {
            instal.setText("Yes");
        } else {
            instal.setText("NA");
        }
    }

    public void checkInstalledAppVersion() {
        if (GBV_AppPackageNameUtils.isAppInstalled(GBV_InstallWPActivity.this, getString(R.string.app_package_name)).booleanValue()) {
            installedVersion.setText(GBV_AppPackageNameUtils.getInstalledAppVersion(GBV_InstallWPActivity.this, getString(R.string.app_package_name)));
        } else {
            installedVersion.setText(R.string.app_not_installed);
        }    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
