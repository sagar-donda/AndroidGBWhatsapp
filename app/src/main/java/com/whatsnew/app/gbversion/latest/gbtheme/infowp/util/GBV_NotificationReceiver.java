package com.whatsnew.app.gbversion.latest.gbtheme.infowp.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class GBV_NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        new GBV_NotifyAppVersion(context, intent).execute(new Void[0]);
    }
}
