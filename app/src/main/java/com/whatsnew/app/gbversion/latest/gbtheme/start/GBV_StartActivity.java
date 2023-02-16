package com.whatsnew.app.gbversion.latest.gbtheme.start;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Ad_class;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Constant;
import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.status.GBV_Commonclass;
import com.whatsnew.app.gbversion.latest.gbtheme.status.GBV_SharePrefHelperclass;

import java.io.File;
import java.util.ArrayList;

public class GBV_StartActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    boolean isPermissionGranted;
    String[] permissions = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gbv_start);
        Ad_class.refreshAd((FrameLayout) findViewById(R.id.native_frame), this);
        checkPermissions();
        GBV_Commonclass.APP_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator + GBV_Commonclass.DIR_NAME;
        StringBuilder sb = new StringBuilder();
        sb.append("onCreate: ");
        sb.append(GBV_Commonclass.APP_DIR);
        Log.d(TAG, sb.toString());
        findViewById(R.id.nxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GBV_SharePrefHelperclass.getInstance(GBV_StartActivity.this, GBV_SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).getBooleanValue(GBV_SharePrefHelperclass.Keys.USER_KYC)) {
                    if (Constant.AD_STATUS == "true") {
                        Ad_class.adCounter++;
                        Ad_class.showInterAd(GBV_StartActivity.this, new Ad_class.onLisoner() {
                            @Override
                            public void click() {
                                startActivity(new Intent(GBV_StartActivity.this, GBV_MainActivity.class));
                            }
                        });
                    } else {
                        startActivity(new Intent(GBV_StartActivity.this, GBV_MainActivity.class));
                    }
                } else {
                    GBV_StartActivity.this.getFolderPermission();
                }
            }
        });
    }

    public void getFolderPermission() {
        File file = new File(GBV_Commonclass.WHATSAPP_STATUS_PATH);
        File file2 = new File(GBV_Commonclass.WHATSAPP_STATUS_PATH_OLD);
        if (file.exists()) {
            execute(GBV_Commonclass.WHATSAPP_START_DIR);
        } else if (file2.exists()) {
            execute(GBV_Commonclass.WHATSAPP_START_DIR_OLD);
        } else {
            Toast.makeText(this, "abc", 0).show();
        }
    }

    private void execute(String str) {
        StorageManager storageManager = (StorageManager) getSystemService("storage");
        if (Build.VERSION.SDK_INT >= 29) {
            Intent createOpenDocumentTreeIntent = storageManager.getPrimaryStorageVolume().createOpenDocumentTreeIntent();
            String uri = ((Uri) createOpenDocumentTreeIntent.getParcelableExtra("android.provider.extra.INITIAL_URI")).toString();
            Log.d("TAG", "INITIAL_URI scheme: " + uri);
            String replace = uri.replace("/root/", "/document/");
            Uri parse = Uri.parse(replace + "%3A" + str);
            createOpenDocumentTreeIntent.putExtra("android.provider.extra.INITIAL_URI", parse);
            Log.d("TAG", "uri: " + parse.toString());
            try {
                startActivityForResult(createOpenDocumentTreeIntent, 6);
            } catch (ActivityNotFoundException unused) {
            }
        } else {
            startActivity(new Intent(this, GBV_MainActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        int i3 = -1;
        if (i2 == -1 && i == 6 && intent != null) {
            Uri data = intent.getData();
            if (data.getPath().endsWith(".Statuses")) {
                int flags = intent.getFlags() & 3;
                getContentResolver().takePersistableUriPermission(data, flags);
                if (flags > 0) {
                    GBV_SharePrefHelperclass.getInstance(this, GBV_SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(GBV_SharePrefHelperclass.Keys.USER_DETAILS, data.toString());
                    GBV_SharePrefHelperclass.getInstance(this, GBV_SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(GBV_SharePrefHelperclass.Keys.USER_KYC, true);
                    startActivity(new Intent(this, GBV_MainActivity.class));
                } else {
                    Toast.makeText(this, (int) R.string.please_allow_function, 0).show();
                    GBV_SharePrefHelperclass.getInstance(this, GBV_SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(GBV_SharePrefHelperclass.Keys.USER_DETAILS, "");
                    GBV_SharePrefHelperclass.getInstance(this, GBV_SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(GBV_SharePrefHelperclass.Keys.USER_KYC, false);
                }
            } else {
                Toast.makeText(this, (int) R.string.wrong_path, 0).show();
                Log.d("TAGmycheck", "onActivityResult: wrong path dialog");
                GBV_SharePrefHelperclass.getInstance(this, GBV_SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(GBV_SharePrefHelperclass.Keys.USER_DETAILS, "");
                GBV_SharePrefHelperclass.getInstance(this, GBV_SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(GBV_SharePrefHelperclass.Keys.USER_KYC, false);
            }
            i3 = -1;
        }
    }

    private void checkPermissions() {
        ArrayList arrayList = new ArrayList();
        String[] strArr = this.permissions;
        for (String str : strArr) {
            if (ContextCompat.checkSelfPermission(this, str) != 0) {
                arrayList.add(str);
            }
        }
        if (!arrayList.isEmpty()) {
            this.isPermissionGranted = false;
            Log.d("permission", "" + arrayList.toString());
            ActivityCompat.requestPermissions(this, (String[]) arrayList.toArray(new String[arrayList.size()]), 10);
            return;
        }
        this.isPermissionGranted = true;
    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 10) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                this.isPermissionGranted = false;
            } else {
                this.isPermissionGranted = true;
            }
        }
    }
    @Override
    public void onBackPressed() {
        if (Constant.AD_STATUS == "true") {
            Ad_class.adCounter++;
            Ad_class.showInterAd(this, new Ad_class.onLisoner() {
                @Override
                public void click() {
                    startActivity(new Intent(GBV_StartActivity.this, GBV_ExitActivity.class));
                }
            });
        } else {
            startActivity(new Intent(GBV_StartActivity.this, GBV_ExitActivity.class));
        }
    }
}