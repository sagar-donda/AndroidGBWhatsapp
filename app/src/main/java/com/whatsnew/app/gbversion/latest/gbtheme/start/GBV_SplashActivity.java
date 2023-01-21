package com.whatsnew.app.gbversion.latest.gbtheme.start;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.service.GBV_BaseActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

public class GBV_SplashActivity extends GBV_BaseActivity {

    public void opennectscreen() {
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).commit();
        if (isFirstRun) {
            startActivity(new Intent(GBV_SplashActivity.this, GBV_FirstActivity.class));
            finishAffinity();
        } else {
            startActivity(new Intent(GBV_SplashActivity.this, GBV_DashBoardActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_splash);
        if (GBV_SplashActivity.this.isConnected()) {
            loadData();
        }
    }

    public void loadData() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                opennectscreen();
            }
        }, 2500);
    }

    public boolean isConnected() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
                return false;
            }
            return activeNetworkInfo.isConnected();
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
            return false;
        }
    }

}
