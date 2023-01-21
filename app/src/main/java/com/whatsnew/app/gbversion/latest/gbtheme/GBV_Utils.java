package com.whatsnew.app.gbversion.latest.gbtheme;

import android.content.Context;
import android.content.pm.PackageManager;

public class GBV_Utils {
    public static boolean appInstalledOrNot(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
