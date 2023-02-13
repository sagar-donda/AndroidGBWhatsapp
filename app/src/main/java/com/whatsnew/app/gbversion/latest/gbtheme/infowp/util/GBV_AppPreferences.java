package com.whatsnew.app.gbversion.latest.gbtheme.infowp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class GBV_AppPreferences {
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    public GBV_AppPreferences(Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.sharedPreferences = defaultSharedPreferences;
        this.editor = defaultSharedPreferences.edit();
    }
}
