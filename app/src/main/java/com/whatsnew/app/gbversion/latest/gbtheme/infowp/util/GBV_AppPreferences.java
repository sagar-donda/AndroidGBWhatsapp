package com.whatsnew.app.gbversion.latest.gbtheme.infowp.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;


public class GBV_AppPreferences {
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private final String KeyAutoDownload = "prefAutoDownload";
    private final String KeyShowAppUpdates = "prefShowAppUpdates";
    private final String KeyEnableNotifications = "prefEnableNotifications";
    private final String KeySoundNotification = "prefSoundNotification";
    private final String KeyHoursNotification = "prefHoursNotification";

    public GBV_AppPreferences(Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.sharedPreferences = defaultSharedPreferences;
        this.editor = defaultSharedPreferences.edit();
    }

    public void resetAppPreferences() {
        this.editor.clear();
        this.editor.commit();
    }

    public Boolean getAutoDownload() {
        return Boolean.valueOf(this.sharedPreferences.getBoolean("prefAutoDownload", false));
    }

    public Boolean getShowAppUpdates() {
        return Boolean.valueOf(this.sharedPreferences.getBoolean("prefShowAppUpdates", true));
    }

    public void setShowAppUpdate(Boolean bool) {
        this.editor.putBoolean("prefShowAppUpdates", bool.booleanValue());
        this.editor.commit();
    }

    public Boolean getEnableNotifications() {
        return Boolean.valueOf(this.sharedPreferences.getBoolean("prefEnableNotifications", true));
    }

    public Uri getSoundNotification() {
        return Uri.parse(this.sharedPreferences.getString("prefSoundNotification", RingtoneManager.getDefaultUri(2).toString()));
    }

    public void setSoundNotification(Uri uri) {
        this.editor.putString("prefSoundNotification", uri.toString());
        this.editor.commit();
    }

    public Integer getHoursNotification() {
        return Integer.valueOf(Integer.parseInt(this.sharedPreferences.getString("prefHoursNotification", "12")));
    }
}
