package com.whatsnew.app.gbversion.latest.gbtheme.status;

import android.content.Context;


public class GBV_SharePrefHelperclass {
    private static Context context;
    private static GBV_SharePrefHelperclass me;
    private static String sharedPrefName;

    
    public enum PrefFiles {
        USER_DETAILS_PREF
    }

    private GBV_SharePrefHelperclass() {
    }

    public static GBV_SharePrefHelperclass getInstance(Context context2, PrefFiles prefFiles) {
        if (me == null) {
            me = new GBV_SharePrefHelperclass();
        }
        context = context2;
        sharedPrefName = prefFiles.name();
        return me;
    }

    public String getValue(String str) {
        return context.getSharedPreferences(sharedPrefName, 0).getString(str, null);
    }

    public boolean getBooleanValue(String str) {
        return context.getSharedPreferences(sharedPrefName, 0).getBoolean(str, false);
    }

    public GBV_SharePrefHelperclass add(String str, String str2) {
        try {
            context.getSharedPreferences(sharedPrefName, 0).edit().putString(str, str2).apply();
            return me;
        } catch (Exception e) {
            throw e;
        }
    }

    public GBV_SharePrefHelperclass add(String str, boolean z) {
        try {
            context.getSharedPreferences(sharedPrefName, 0).edit().putBoolean(str, z).apply();
            return me;
        } catch (Exception e) {
            throw e;
        }
    }

    public GBV_SharePrefHelperclass remove(String str) {
        try {
            context.getSharedPreferences(sharedPrefName, 0).edit().remove(str).apply();
            return me;
        } catch (Exception e) {
            throw e;
        }
    }

    
    public final class Keys {
        public static final String LAST_REFRESHED_TIME = "last_refreshed_time";
        public static final String USER_DETAILS = "user_details";
        public static final String USER_DETAILS_BUSINESS = "user_details_business";
        public static final String USER_KYC = "user_kyc_status";
        public static final String USER_KYC_BUSINESS = "user_kyc_business";

        public Keys() {
        }
    }
}
