package com.whatsnew.app.gbversion.latest.gbtheme.start;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.NFC;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.SET_WALLPAPER;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.GBV_DebouncedOnClickListener;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.service.GBV_BaseActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

import java.util.List;

public class GBV_DashBoardActivity extends GBV_BaseActivity {
    ImageView btnStart;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_dashboard);
        btnStart = findViewById(R.id.btn_start_app);
        GBV_DebouncedOnClickListener GBVDebouncedOnClickListener = new GBV_DebouncedOnClickListener(1500) {
            @Override
            public void onDebouncedClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_start_app:
                        requestPermissions();
                        break;
                    case R.id.btn_share_app:
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey check out my app at: https://play.google.com/store/apps/details?id=" + getPackageName());
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                        break;
                    case R.id.btn_privacy_app:
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://sites.google.com/view/gb-version-latest/home")));
                        break;
                    case R.id.btn_rate_app:
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
                        break;
                }
            }
        };

        btnStart.setOnClickListener(GBVDebouncedOnClickListener);
        findViewById(R.id.btn_share_app).setOnClickListener(GBVDebouncedOnClickListener);
        findViewById(R.id.btn_privacy_app).setOnClickListener(GBVDebouncedOnClickListener);
        findViewById(R.id.btn_rate_app).setOnClickListener(GBVDebouncedOnClickListener);
    }

    private void requestPermissions() {
        Dexter.withContext(GBV_DashBoardActivity.this)
                .withPermissions(CAMERA,
                        READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE,
                        SET_WALLPAPER,
                        NFC)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            startActivity(new Intent(GBV_DashBoardActivity.this, GBV_MainActivity.class));
                        }
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread().check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(GBV_DashBoardActivity.this);

        builder.setTitle("Need Permissions");

        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(GBV_DashBoardActivity.this, GBV_ExitActivity.class));
    }
}
