package com.whatsnew.app.gbversion.latest.gbtheme.shake_Detector;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class GBV_MyWorker extends Worker {

    private final Context context;

    public GBV_MyWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("--shake_service--", "doWork called for: " + this.getId());
        Log.d("--shake_service--", "--shake_service--" + GBV_ShakeServiceNew.isServiceRunning);
            Log.d("--shake_service--", "starting service from doWork");
            Intent intent = new Intent(this.context, GBV_ShakeServiceNew.class);
            ContextCompat.startForegroundService(context, intent);
        return Result.success();
    }

    @Override
    public void onStopped() {
        Log.d("--shake_service--", "onStopped called for: " + this.getId());
        super.onStopped();
    }
}
