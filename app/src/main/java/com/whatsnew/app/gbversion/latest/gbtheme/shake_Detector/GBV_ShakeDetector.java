package com.whatsnew.app.gbversion.latest.gbtheme.shake_Detector;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.core.content.ContextCompat;

public class GBV_ShakeDetector {
    private GBV_appPreferences appPreferences;
    private GBV_ShakeBroadCastReceiver shakeBroadCastReceiver;
    private GBV_ShakeCallback shakeCallback;
    private GBV_ShakeOptions shakeOptions;

    public GBV_ShakeDetector(GBV_ShakeOptions shakeOptions) {
        this.shakeOptions = shakeOptions;
    }

    public GBV_ShakeDetector start(Context context, GBV_ShakeCallback shakeCallback) {
        this.shakeCallback = shakeCallback;
        this.shakeBroadCastReceiver = new GBV_ShakeBroadCastReceiver(shakeCallback);
        registerPrivateBroadCast(context);
        saveOptionsInStorage(context);
        startShakeService(context);
        return this;
    }

    public GBV_ShakeDetector start(Context context) {
        saveOptionsInStorage(context);
        startShakeService(context);
        return this;
    }

    public void stopShakeDetector(Context context) {
        Log.d("--shake_service--", "stopService called");
        if (GBV_ShakeServiceNew.isServiceRunning) {
            Intent serviceIntent = new Intent(context, GBV_ShakeServiceNew.class);
            context.stopService(serviceIntent);
        }
    }

    public void startShakeService(Context context) {
        Log.d("--shake_service--", "startService called");
        if (!GBV_ShakeServiceNew.isServiceRunning) {
            Intent serviceIntent = new Intent(context, GBV_ShakeServiceNew.class);
            ContextCompat.startForegroundService(context, serviceIntent);
        }
    }

    private void saveOptionsInStorage(Context context) {
        this.appPreferences = new GBV_appPreferences(context);
        this.appPreferences.putBoolean("BACKGROUND", this.shakeOptions.isBackground());
        this.appPreferences.putInt("SHAKE_COUNT", this.shakeOptions.getShakeCounts());
        this.appPreferences.putInt("SHAKE_INTERVAL", this.shakeOptions.getInterval());
        this.appPreferences.putFloat("SENSIBILITY", this.shakeOptions.getSensibility());
    }

    private void registerPrivateBroadCast(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("shake.detector");
        filter.addAction("private.shake.detector");
        context.registerReceiver(this.shakeBroadCastReceiver, filter);
    }
}
