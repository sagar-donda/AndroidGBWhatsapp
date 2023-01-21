package com.whatsnew.app.gbversion.latest.gbtheme.shake_Detector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class GBV_ShakeBroadCastReceiver extends BroadcastReceiver {
    private GBV_ShakeCallback callback;

    public GBV_ShakeBroadCastReceiver(GBV_ShakeCallback callback) {
        this.callback = callback;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction().equals("private.shake.detector")) {
            this.callback.onShake();
        }
    }
}
