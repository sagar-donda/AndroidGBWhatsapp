package com.whatsnew.app.gbversion.latest.gbtheme.start;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.GBV_DebouncedOnClickListener;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.service.GBV_BaseActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.GBV_Utils;
import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.chating.GBV_ChattingActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.GBV_StatusMainActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.shake_Detector.GBV_ShakeCallback;
import com.whatsnew.app.gbversion.latest.gbtheme.shake_Detector.GBV_ShakeDetector;
import com.whatsnew.app.gbversion.latest.gbtheme.shake_Detector.GBV_ShakeOptions;
import com.whatsnew.app.gbversion.latest.gbtheme.walkChat.GBV_WalkMainActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.whatsWebScan.GBV_WebActivity;

import java.util.Objects;

public class GBV_MainActivity extends GBV_BaseActivity {
    private static final int REQUEST_PERMISSIONS = 1234;
    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final String MANAGE_EXTERNAL_STORAGE_PERMISSION = "android:manage_external_storage";

    private final Handler handler = new Handler();

    ImageView whatsWeb;
    ImageView linearWPCleaner;
    ImageView linearWpAppStatusSaver;
    ImageView linearWPWalk;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_main);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        whatsWeb = findViewById(R.id.whtsWeb);
        linearWPWalk = findViewById(R.id.walkchat);
        linearWPCleaner = findViewById(R.id.cleaner);
        linearWpAppStatusSaver = findViewById(R.id.StatusSaver);
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GBV_MainActivity.this, GBV_FirstMAinActivity.class));
            }
        });

        GBV_DebouncedOnClickListener GBVDebouncedOnClickListener = new GBV_DebouncedOnClickListener(1500) {
            @Override
            public void onDebouncedClick(View v) {
                switch (v.getId()) {
                    case R.id.whtsWeb:
                        startActivity(new Intent(GBV_MainActivity.this, GBV_WebActivity.class));
                        break;
                    case R.id.StatusSaver:
//                        if (!arePermissionDenied()) {
                            next();
//                            return;
//                        }
//
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionDenied()) {
//
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//
//                                startActivityForResult(intent, REQUEST_PERMISSIONS);
//                                return;
//                            }
//
//                            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
//                        }
                        break;
                    case R.id.walkchat:
                        startActivity(new Intent(GBV_MainActivity.this, GBV_WalkMainActivity.class));
                        break;
                    case R.id.chat:
                        startActivity(new Intent(GBV_MainActivity.this, GBV_ChattingActivity.class));
                        break;
                    default:
                        break;
                }
            }
        };

        whatsWeb.setOnClickListener(GBVDebouncedOnClickListener);
        linearWPWalk.setOnClickListener(GBVDebouncedOnClickListener);
        linearWpAppStatusSaver.setOnClickListener(GBVDebouncedOnClickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            new GBV_ShakeDetector(new GBV_ShakeOptions().background(Boolean.TRUE).interval(1000)
                    .shakeCount(2).sensibility(2.0f)).start(this, new GBVShakeDetectListner());
        }
    }

    private class GBVShakeDetectListner implements GBV_ShakeCallback {
        public void onShake() {
            if (GBV_Utils.appInstalledOrNot(GBV_MainActivity.this, "com.whatsapp")) {
                startActivity(getPackageManager().getLaunchIntentForPackage("com.whatsapp"));
            } else {
                Toast.makeText(GBV_MainActivity.this, "Please install whatsapp to use this feature.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    boolean checkStorageApi30() {
        AppOpsManager appOps = getApplicationContext().getSystemService(AppOpsManager.class);
        int mode = appOps.unsafeCheckOpNoThrow(
                MANAGE_EXTERNAL_STORAGE_PERMISSION,
                getApplicationContext().getApplicationInfo().uid,
                getApplicationContext().getPackageName()
        );
        return mode != AppOpsManager.MODE_ALLOWED;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS && grantResults.length > 0) {
            if (arePermissionDenied()) {
                ((ActivityManager) Objects.requireNonNull(this.getSystemService(ACTIVITY_SERVICE))).clearApplicationUserData();
                recreate();
            } else {
                next();
            }
        }
    }

    private void next() {

        handler.postDelayed(() -> {
            startActivity(new Intent(GBV_MainActivity.this, GBV_StatusMainActivity.class));
            finish();
        }, 1000);

    }

    private boolean arePermissionDenied() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return checkStorageApi30();
        }

        for (String permissions : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), permissions) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
