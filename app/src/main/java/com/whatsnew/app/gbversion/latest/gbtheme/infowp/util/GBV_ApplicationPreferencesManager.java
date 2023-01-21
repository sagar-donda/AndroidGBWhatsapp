package com.whatsnew.app.gbversion.latest.gbtheme.infowp.util;

import android.app.Application;


public class GBV_ApplicationPreferencesManager extends Application {
    private static GBV_AppPreferences appPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        appPreferences = new GBV_AppPreferences(this);
    }

    public static GBV_AppPreferences getAppPreferences() {
        return appPreferences;
    }
}
