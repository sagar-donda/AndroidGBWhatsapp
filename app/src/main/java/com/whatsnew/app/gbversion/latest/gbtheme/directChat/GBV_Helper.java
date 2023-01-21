package com.whatsnew.app.gbversion.latest.gbtheme.directChat;

import android.content.Context;
import android.telephony.TelephonyManager;

public class GBV_Helper {

    public static String getCurrentLocale(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getNetworkCountryIso();
        }
        return null;
    }
}
