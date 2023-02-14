package com.whatsnew.app.gbversion.latest.gbtheme.start;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.NFC;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.SET_WALLPAPER;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Ad_class;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Constant;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.GBV_BaseActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.RecyclerData;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.RetrofitAPI;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GBV_SplashActivity extends GBV_BaseActivity {

    private static final int REQUEST_PERMISSIONS = 1234;
    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final String MANAGE_EXTERNAL_STORAGE_PERMISSION = "android:manage_external_storage";
    private final Handler handler = new Handler();
    AppOpenAd appOpenAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_splash);
        if (!arePermissionDenied()) {
            getdattata();
            return;
        }
        requestPermissions();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionDenied()) {

            // If Android 11 Request for Manage File Access Permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);

                startActivityForResult(intent, REQUEST_PERMISSIONS);
                return;
            }

            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
        }
    }

    public void getdattata() {
        try {
            String adsu = "https://metalineappstudio.com/";
            Retrofit retrofit = new Retrofit.Builder().baseUrl(adsu).addConverterFactory(GsonConverterFactory.create()).build();
            RetrofitAPI loginService = retrofit.create(RetrofitAPI.class);
            Call<RecyclerData> call = loginService.getCourse("metalineapproid/testads/appdata_on.json");
            call.enqueue(new Callback<RecyclerData>() {
                @Override
                public void onResponse(Call<RecyclerData> call, Response<RecyclerData> response) {
                    if (response.body() != null) {
                        RecyclerData appAdModel = response.body();
                        Constant.AD_STATUS = appAdModel.getadStatus();
                        Constant.PRIVACY_POLICY = appAdModel.getprivacypolicy();
                        Constant.MORE_APPS = appAdModel.getmoreapps();

                        if (Constant.AD_STATUS == "true") {
                            Constant.AD_STATUS = appAdModel.getadStatus();
                            Constant.BANNER_VISIBLE = appAdModel.getshow_banner();
                            Constant.FBAD_STATUS = appAdModel.getfb_adstatus();
                            Constant.FBBANNER_VISIBLE = appAdModel.getshow_fb_banner();
                            Constant.APP_OPEN_ID = appAdModel.getappopenad1();
                            Constant.ADMOB_BANNER_ID = appAdModel.getbanner();
                            Constant.INTERSTRIAL_ID = appAdModel.getinterstitial();
                            Constant.NATIVE_ID = appAdModel.getnative1();
                            Constant.FBBANNER_ID = appAdModel.getfb_banner();
                            Constant.FBNATIVE_ID = appAdModel.getfb_native();
                            Constant.FBINTERSTRIAL_ID = appAdModel.getfb_interstitial();
                            Constant.NEXT_CLICK_COUNT = appAdModel.getfullscreenadcount();
                            Constant.PRIVACY_POLICY = appAdModel.getprivacypolicy();
                            Constant.MORE_APPS = appAdModel.getmoreapps();

                            System.out.println("throwable" + appAdModel.getadStatus());
                            System.out.println("throwable" + appAdModel.getshow_banner());
                            System.out.println("throwable" + appAdModel.getfb_adstatus());
                            System.out.println("throwable" + appAdModel.getshow_fb_banner());
                            System.out.println("throwable" + appAdModel.getappopenad1());
                            System.out.println("throwable" + appAdModel.getbanner());
                            System.out.println("throwable" + appAdModel.getinterstitial());
                            System.out.println("throwable" + appAdModel.getnative1());
                            System.out.println("throwable" + appAdModel.getfb_banner());
                            System.out.println("throwable" + appAdModel.getfb_native());
                            System.out.println("throwable" + appAdModel.getfb_interstitial());
                            System.out.println("throwable" + appAdModel.getfullscreenadcount());
                            System.out.println("throwable" + appAdModel.getprivacypolicy());
                            System.out.println("throwable" + appAdModel.getmoreapps());
                            Ad_class.loadInterAd(GBV_SplashActivity.this);
                            if (Constant.AD_STATUS == "true") {
                                appOPEN();
                            } else {
                                next();
                            }
                        } else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    next();
                                }
                            }, 5000L);

                        }
                    }
                }

                @Override
                public void onFailure(Call<RecyclerData> call, Throwable t) {
                    Toast.makeText(GBV_SplashActivity.this, "Fail to get the data..", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            next();
                        }
                    }, 5000L);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void appOPEN() {
        AdRequest request = new AdRequest.Builder().build();
        AppOpenAd.load(GBV_SplashActivity.this, Constant.APP_OPEN_ID, request, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(AppOpenAd ad) {
                appOpenAd = ad;
                appOpenAd.show(GBV_SplashActivity.this);
                appOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        appOpenAd = null;
                        next();

                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        appOpenAd = null;
                        next();
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                next();
            }
        });
    }

    private void requestPermissions() {
        Dexter.withContext(GBV_SplashActivity.this)
                .withPermissions(CAMERA,
                        READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE,
                        SET_WALLPAPER,
                        NFC)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            next();
                        }
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(GBV_SplashActivity.this);

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
    protected void onResume() {
        super.onResume();
        if (!arePermissionDenied()) {
            next();
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
            opennectscreen();
        }, 5000L);

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


    public void opennectscreen() {
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).commit();
        if (isFirstRun) {
            startActivity(new Intent(GBV_SplashActivity.this, GBV_FirstActivity.class));
            finishAffinity();
        } else {
            startActivity(new Intent(GBV_SplashActivity.this, GBV_MainActivity.class));
            finish();
        }
    }
}
