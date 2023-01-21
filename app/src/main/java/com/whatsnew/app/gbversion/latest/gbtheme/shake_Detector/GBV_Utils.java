package com.whatsnew.app.gbversion.latest.gbtheme.shake_Detector;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.core.app.NotificationCompat;

public class GBV_Utils {

    public static String THEME_PREFS = "THEME_PREFS";
    public static int progress = 100;

    public static void setStaticVariables(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(THEME_PREFS, 0);

        progress = sharedPreferences.getInt(NotificationCompat.CATEGORY_PROGRESS, 10);
    }

}
