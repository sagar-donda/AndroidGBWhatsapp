package com.whatsnew.app.gbversion.latest.gbtheme.shakeShortcut;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.whatsnew.app.gbversion.latest.gbtheme.GBV_Utils;


public class GBV_ShakeReceiver extends BroadcastReceiver {

    @SuppressLint("WrongConstant")
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction().equals("shake.detector")) {
            Log.d("--shake_service--", ">>>>>> my receiver <<<<<");

            try {
                if (GBV_Utils.appInstalledOrNot(context, "com.whatsapp")) {
                    Log.d("--shake_service--", "whatsapp installed: " + "com.whatsapp");
                    context.startActivity(context.getPackageManager().getLaunchIntentForPackage("com.whatsapp"));
                } else {
                    Log.d("--shake_service--", "whatsapp not installed: " + "com.whatsapp");
                    Toast.makeText(context, "Please install whatsapp to use this feature.", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("--shake_service--", "onReceive: " + "error: " + e.getMessage());
            }
        }
    }
}
