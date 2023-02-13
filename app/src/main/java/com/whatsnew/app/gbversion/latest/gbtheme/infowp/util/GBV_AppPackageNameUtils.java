package com.whatsnew.app.gbversion.latest.gbtheme.infowp.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class GBV_AppPackageNameUtils {
    public static String getInstalledAppVersion(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfo(str, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Boolean isAppInstalled(Context context, String str) {
        try {
            context.getPackageManager().getPackageInfo(str, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    
    public static class LatestAppVersion extends AsyncTask<Void, Void, String> {
        private final Context context;
        private TextView latestVersion;
        private String latestVersionSourceURL;
        private ProgressBar progressWheel;

      
        public String doInBackground(Void... voidArr) {
            return "";
        }

        public LatestAppVersion(Context context, TextView textView, String str, ProgressBar progressBar) {
            this.latestVersion = textView;
            this.latestVersionSourceURL = str;
            this.progressWheel = progressBar;
            this.context = context;
        }

        @Override 
        protected void onPreExecute() {
            super.onPreExecute();
            this.latestVersion.setVisibility(8);
            this.progressWheel.setVisibility(0);
        }

      
        public void onPostExecute(String str) {
            super.onPostExecute((String) str);
            this.latestVersion.setVisibility(0);
            this.progressWheel.setVisibility(8);
            String str2 = "2.21.17.00";
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(this.latestVersionSourceURL).openConnection();
                httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    } else if (readLine.contains("<p class=version")) {
                        str2 = readLine.replaceAll("<p class=version>", "").replaceAll("</p>", "");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                this.latestVersion.setText("N/A");
            }
            PrintStream printStream = System.out;
            printStream.println("versionReleaseDate>" + str2 + " " + this.latestVersionSourceURL);
            this.latestVersion.setText(str2);
        }
    }

    public static void goToPlaystore(Context context, String str) {
        try {
            ((AppCompatActivity) context).startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

}
