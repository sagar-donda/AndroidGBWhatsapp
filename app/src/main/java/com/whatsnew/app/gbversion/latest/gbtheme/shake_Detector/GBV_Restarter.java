package com.whatsnew.app.gbversion.latest.gbtheme.shake_Detector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class GBV_Restarter extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("--shake_service--", "Service restarted");

        WorkManager workManager = WorkManager.getInstance(context);
        OneTimeWorkRequest startServiceRequest = new OneTimeWorkRequest.Builder(GBV_MyWorker.class).build();
        workManager.enqueue(startServiceRequest);

    }
}
