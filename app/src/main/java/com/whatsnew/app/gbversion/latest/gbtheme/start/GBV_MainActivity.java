package com.whatsnew.app.gbversion.latest.gbtheme.start;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Ad_class;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.GBV_BaseActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.GBV_Utils;
import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.blankmes.GBV_BlankMesActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.chating.GBV_ChattingActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.cleaner.GBV_WACleanMainActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.directChat.GBV_ChatDirect;
import com.whatsnew.app.gbversion.latest.gbtheme.emojiText.GBV_Texttoemoji;
import com.whatsnew.app.gbversion.latest.gbtheme.infowp.activity.GBV_InstallWPActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.reply.GBV_ReplyActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.GBV_StatusMainActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.searchprofile.GBV_SearchProfileActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.shakeShortcut.GBV_ShakeMain;
import com.whatsnew.app.gbversion.latest.gbtheme.shake_Detector.GBV_ShakeCallback;
import com.whatsnew.app.gbversion.latest.gbtheme.shake_Detector.GBV_ShakeDetector;
import com.whatsnew.app.gbversion.latest.gbtheme.shake_Detector.GBV_ShakeOptions;
import com.whatsnew.app.gbversion.latest.gbtheme.textRepeater.GBV_MainTextRepeater;
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

    ImageView whatsWeb;
    ImageView linearWPCleaner;
    ImageView linearWpAppStatusSaver;
    ImageView linearWPWalk;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_main);
        Ad_class.refreshAd((FrameLayout) findViewById(R.id.native_frame), this);
        Ad_class.refreshAd((FrameLayout) findViewById(R.id.native_frame1), this);
        Ad_class.refreshAd((FrameLayout) findViewById(R.id.native_frame2), this);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GBV_MainActivity.this, GBV_SettingActivity.class));
            }
        });
        whatsWeb = findViewById(R.id.whtsWeb);
        linearWPWalk = findViewById(R.id.walkchat);
        linearWPCleaner = findViewById(R.id.cleaner);
        linearWpAppStatusSaver = findViewById(R.id.StatusSaver);
        findViewById(R.id.reply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GBV_MainActivity.this, GBV_ReplyActivity.class));
            }
        });
        findViewById(R.id.chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GBV_MainActivity.this, GBV_ChattingActivity.class));
            }
        });
        findViewById(R.id.blankm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GBV_MainActivity.this, GBV_BlankMesActivity.class));
            }
        });
        findViewById(R.id.searchp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GBV_MainActivity.this, GBV_SearchProfileActivity.class));
            }
        });
        findViewById(R.id.info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GBV_MainActivity.this, GBV_InstallWPActivity.class));
            }
        });
        findViewById(R.id.directChat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GBV_MainActivity.this, GBV_ChatDirect.class));
            }
        });
        findViewById(R.id.shortcut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GBV_MainActivity.this, GBV_ShakeMain.class));
            }
        });
        findViewById(R.id.whtsWeb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GBV_MainActivity.this, GBV_WebActivity.class));
            }
        });
        findViewById(R.id.walkchat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GBV_MainActivity.this, GBV_WalkMainActivity.class));
            }
        });
        findViewById(R.id.chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GBV_MainActivity.this, GBV_ChattingActivity.class));
            }
        });
        findViewById(R.id.shortcut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GBV_MainActivity.this, GBV_ShakeMain.class));
            }
        });
        findViewById(R.id.cleaner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GBV_MainActivity.this, GBV_WACleanMainActivity.class));
            }
        });
        findViewById(R.id.RepeatText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GBV_MainActivity.this, GBV_MainTextRepeater.class));
            }
        });
        findViewById(R.id.Textemoji).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GBV_MainActivity.this, GBV_Texttoemoji.class));
            }
        });
        findViewById(R.id.StatusSaver).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!arePermissionDenied()) {
                    next();
                    return;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionDenied()) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);

                        startActivityForResult(intent, REQUEST_PERMISSIONS);
                        return;
                    }

                    requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            new GBV_ShakeDetector(new GBV_ShakeOptions().background(Boolean.TRUE).interval(1000)
                    .shakeCount(2).sensibility(2.0f)).start(this, new GBVShakeDetectListner());
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
        startActivity(new Intent(GBV_MainActivity.this, GBV_StatusMainActivity.class));
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
        startActivity(new Intent(GBV_MainActivity.this, GBV_ExitActivity.class));
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
}
