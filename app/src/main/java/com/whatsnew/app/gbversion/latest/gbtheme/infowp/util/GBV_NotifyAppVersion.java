package com.whatsnew.app.gbversion.latest.gbtheme.infowp.util;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.infowp.activity.GBV_InstallWPActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class GBV_NotifyAppVersion extends AsyncTask<Void, Void, String> {
    public static String latestVersion;
    private GBV_AppPreferences appPreferences = GBV_ApplicationPreferencesManager.getAppPreferences();
    private Context context;
    private Intent intent;
    String title;

  
    public String doInBackground(Void... voidArr) {
        return null;
    }

    public GBV_NotifyAppVersion(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

  
    public void onPostExecute(String str) {
        String str2;
        super.onPostExecute((String) str);
        new LatestAppVersion(this.context.getString(R.string.app_latest_version_url)).execute(new Void[0]);
        Context context = this.context;
        if (GBV_AppPackageNameUtils.isAppInstalled(context, context.getString(R.string.app_package_name)).booleanValue()) {
            Context context2 = this.context;
            str2 = GBV_AppPackageNameUtils.getInstalledAppVersion(context2, context2.getString(R.string.app_package_name));
        } else {
            str2 = "";
        }
        try {
            String str3 = latestVersion;
            if (str3 == null || str3 == "") {
                str3 = "new version";
            }
            latestVersion = str3;
            PrintStream printStream = System.out;
            printStream.println("versionDetails>>>>" + str2 + "<<>>" + latestVersion + "<<");
            if (!str2.equals(latestVersion.trim())) {
                @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) String format = String.format(this.context.getResources().getString(R.string.app_name), latestVersion);
                this.title = format;
                this.intent.putExtra("title", format);
                NotificationManagerCompat.from(this.context).notify(0, new NotificationCompat.Builder(this.context, "UpdateLatestVersionNotification").setContentIntent(PendingIntent.getActivity(this.context, 0, new Intent(this.context, GBV_InstallWPActivity.class), PendingIntent.FLAG_UPDATE_CURRENT)).setSmallIcon(R.mipmap.ic_launcher).setLargeIcon(BitmapFactory.decodeResource(this.context.getResources(), R.mipmap.ic_launcher)).setContentTitle(this.title).setPriority(1).setAutoCancel(true).setChannelId("UpdateLatestVersionNotification").build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public static class LatestAppVersion extends AsyncTask<Void, Void, String> {
        private String latestVersionSourceURL;

      
        public String doInBackground(Void... voidArr) {
            return null;
        }

        public LatestAppVersion(String str) {
            this.latestVersionSourceURL = str;
        }

        @Override 
        protected void onPreExecute() {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
            super.onPreExecute();
            String str = "2.21.17.12";
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(this.latestVersionSourceURL).openConnection();
                httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    } else if (readLine.contains("<p class=version")) {
                        str = readLine.replaceAll("<p class=version>", "").replaceAll("</p>", "");
                    }
                }
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
                GBV_NotifyAppVersion.latestVersion = "N/A";
            }
            GBV_NotifyAppVersion.latestVersion = str;
        }

      
        public void onPostExecute(String str) {
            super.onPostExecute((String) str);
        }
    }
}
