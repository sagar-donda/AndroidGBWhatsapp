package com.whatsnew.app.gbversion.latest.gbtheme.shakeShortcut;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;


import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Ad_class;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.GBV_BaseActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.GBV_Utils;
import com.whatsnew.app.gbversion.latest.gbtheme.shake_Detector.GBV_MyWorker;
import com.whatsnew.app.gbversion.latest.gbtheme.shake_Detector.GBV_Restarter;
import com.whatsnew.app.gbversion.latest.gbtheme.shake_Detector.GBV_ShakeCallback;
import com.whatsnew.app.gbversion.latest.gbtheme.shake_Detector.GBV_ShakeDetector;
import com.whatsnew.app.gbversion.latest.gbtheme.shake_Detector.GBV_ShakeOptions;
import com.whatsnew.app.gbversion.latest.gbtheme.shake_Detector.GBV_ShakeServiceNew;

public class GBV_ShakeMain extends GBV_BaseActivity {
    ImageView ShakeDemo;
    ImageView ShakeButton;
    private GBV_ShakeDetector shakeDetector;
    public static boolean ShakeCheck = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
               setContentView(R.layout.gbv_activity_main_shake);
        Ad_class.all_banner(GBV_ShakeMain.this, (LinearLayout) findViewById(R.id.adView));
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("--shake_service--", "Build Version Greater than or equal to M: " + Build.VERSION_CODES.M);
            checkDrawOverlayPermission();
        } else {
            Log.d("--shake_service--", "OS Version Less than M");
        }

        this.ShakeDemo = findViewById(R.id.shake);
        this.ShakeButton = findViewById(R.id.btnShake);
        this.ShakeButton.setOnClickListener(new btnShakeListner());

        if (GBV_ShakeMain.ShakeCheck) {
            PackageManager pm = getPackageManager();

            ComponentName componentShakeService = new ComponentName(GBV_ShakeMain.this, GBV_ShakeServiceNew.class);
            pm.setComponentEnabledSetting(componentShakeService, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

            ComponentName componentShakeReceiver = new ComponentName(GBV_ShakeMain.this, GBV_ShakeReceiver.class);
            pm.setComponentEnabledSetting(componentShakeReceiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

            ComponentName componentRestart = new ComponentName(GBV_ShakeMain.this, GBV_Restarter.class);
            pm.setComponentEnabledSetting(componentRestart, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

            startServiceViaWorker();

            this.shakeDetector = new GBV_ShakeDetector(new GBV_ShakeOptions().background(Boolean.TRUE).interval(1000)
                    .shakeCount(2).sensibility(2.0f)).start(this, new GBVShakeDetectListner());
        }
    }

    private class btnShakeListner implements OnClickListener {
        public void onClick(View view) {
            if (GBV_ShakeMain.ShakeCheck) {
                Log.d("--service--", "onClick: " + "Service Stop");
                GBV_ShakeMain.ShakeCheck = false;
                ShakeButton.setImageResource(R.drawable.switch_off);
                shakeDetector.stopShakeDetector(getBaseContext());
                stopService(new Intent(GBV_ShakeMain.this, GBV_ShakeServiceNew.class));
                cancelNotification(GBV_ShakeMain.this);
                Toast.makeText(GBV_ShakeMain.this, "Shake Mobile Service Stopped", Toast.LENGTH_SHORT).show();

                PackageManager pm = getPackageManager();

                ComponentName componentShakeService = new ComponentName(GBV_ShakeMain.this, GBV_ShakeServiceNew.class);
                pm.setComponentEnabledSetting(componentShakeService, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

                ComponentName componentShakeReceiver = new ComponentName(GBV_ShakeMain.this, GBV_ShakeReceiver.class);
                pm.setComponentEnabledSetting(componentShakeReceiver, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

                ComponentName componentRestart = new ComponentName(GBV_ShakeMain.this, GBV_Restarter.class);
                pm.setComponentEnabledSetting(componentRestart, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            } else {
                Log.d("--service--", "onClick: " + "Service Start");
                GBV_ShakeMain.ShakeCheck = true;
                ShakeButton.setImageResource(R.drawable.switch_on);

                PackageManager pm = getPackageManager();

                ComponentName componentShakeService = new ComponentName(GBV_ShakeMain.this, GBV_ShakeServiceNew.class);
                pm.setComponentEnabledSetting(componentShakeService, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

                ComponentName componentShakeReceiver = new ComponentName(GBV_ShakeMain.this, GBV_ShakeReceiver.class);
                pm.setComponentEnabledSetting(componentShakeReceiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

                ComponentName componentRestart = new ComponentName(GBV_ShakeMain.this, GBV_Restarter.class);
                pm.setComponentEnabledSetting(componentRestart, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

                shakeDetector = new GBV_ShakeDetector(new GBV_ShakeOptions().background(Boolean.TRUE).interval(1000)
                        .shakeCount(2).sensibility(2.0f)).start(GBV_ShakeMain.this, new GBVShakeDetectListner());

                shakeDetector.startShakeService(getBaseContext());

                startServiceViaWorker();

                Toast.makeText(GBV_ShakeMain.this, "Shake Mobile Service Started", Toast.LENGTH_SHORT).show();
            }        }
    }

    public void cancelNotification(Context context) {
        NotificationManager notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notifManager.cancelAll();
    }

    private class GBVShakeDetectListner implements GBV_ShakeCallback {
        public void onShake() {
            Log.d("--shake_service--", "onShake: " + "GBVShakeDetectListner");
            if (GBV_Utils.appInstalledOrNot(GBV_ShakeMain.this, "com.whatsapp")) {
                startActivity(getPackageManager().getLaunchIntentForPackage("com.whatsapp"));
            } else {
                Toast.makeText(GBV_ShakeMain.this, "Please install whatsapp to use this feature.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.d("--shake_service--", "isMyServiceRunning: " + "ififiif");
                return true;
            } else {
                Log.d("--shake_service--", "isMyServiceRunning: " + "elelel");
            }
        }
        return false;
    }

    public void startServiceViaWorker() {
        WorkManager workManager = WorkManager.getInstance(GBV_ShakeMain.this);
        OneTimeWorkRequest startServiceRequest = new OneTimeWorkRequest.Builder(GBV_MyWorker.class).build();
        workManager.enqueue(startServiceRequest);
    }

    public final static int REQUEST_CODE = 1023;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkDrawOverlayPermission() {
        Log.v("--shake_service--", "Package Name: " + getApplicationContext().getPackageName());

        if (!Settings.canDrawOverlays(GBV_ShakeMain.this)) {
            Log.v("--shake_service--", "Requesting Permission" + Settings.canDrawOverlays(GBV_ShakeMain.this));
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getApplicationContext().getPackageName()));
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            Log.v("--shake_service--", "We already have permission for it.");
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("--shake_service--", "OnActivity Result.");
        if (requestCode == REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (ShakeCheck) {
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("restartservice");
            broadcastIntent.setClass(this, GBV_Restarter.class);
            this.sendBroadcast(broadcastIntent);
            setResult(RESULT_OK, broadcastIntent);
        }

        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isMyServiceRunning(GBV_ShakeServiceNew.class)) {
            Log.d("--shake_service--", "onResume: " + "isServiceRunning: ");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (isMyServiceRunning(GBV_ShakeServiceNew.class)) {
            Log.d("--shake_service--", "onPause: " + "isServiceRunning: ");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
