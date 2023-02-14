package com.whatsnew.app.gbversion.latest.gbtheme.status;

import android.Manifest;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.whatsnew.app.gbversion.latest.gbtheme.R;

import java.io.File;
import java.util.ArrayList;


public class MainActivitycalss extends AppCompatActivity {
    public static final int MULTIPLE_PERMISSIONS = 10;
    private static final String TAG = "MainActivity";
    private LinearLayout bussinesWhatsapp;
    boolean isPermissionGranted;
    String[] permissions = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    private LinearLayout savedFile;
    private LinearLayout whatsappStatus;

    private static final int REQUEST_PERMISSIONS = 1234;
    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final String MANAGE_EXTERNAL_STORAGE_PERMISSION = "android:manage_external_storage";

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        Commonclass.APP_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator + Commonclass.DIR_NAME;
        StringBuilder sb = new StringBuilder();
        sb.append("onCreate: ");
        sb.append(Commonclass.APP_DIR);
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).commit();
        initView();
        checkPermissions();

        Log.d(TAG, sb.toString());
        this.whatsappStatus.setOnClickListener(new View.OnClickListener() { // from class: com.saver.image.video.business.wp.MainActivitycalss.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
//                if (SharePrefHelperclass.getInstance(MainActivitycalss.this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).getBooleanValue(SharePrefHelperclass.Keys.USER_KYC)) {
                    MainActivitycalss.this.startActivity(new Intent(MainActivitycalss.this, Whatsapp_Statusclass.class));
//                }
            }
        });
        this.bussinesWhatsapp.setOnClickListener(new View.OnClickListener() { // from class: com.saver.image.video.business.wp.MainActivitycalss.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SharePrefHelperclass.getInstance(MainActivitycalss.this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).getBooleanValue(SharePrefHelperclass.Keys.USER_KYC_BUSINESS)) {
                }
            }
        });
        this.savedFile.setOnClickListener(new View.OnClickListener() { // from class: com.saver.image.video.business.wp.MainActivitycalss.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainActivitycalss.this.startActivity(new Intent(MainActivitycalss.this, SavedFilesActivityclass.class));
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getFolderPermissionBusiness() {
        File file = new File(Commonclass.WHATSAPP_BUSINESS_PATH);
        File file2 = new File(Commonclass.WHATSAPP_BUSINESS_PATH_OLD);
        if (file.exists()) {
            executeBusiness(Commonclass.WHATSAPP_START_DIR_BUSINESS);
        } else if (file2.exists()) {
            executeBusiness(Commonclass.WHATSAPP_START_DIR_BUSINESS_OLD);
        } else {
            Toast.makeText(this, (int) R.string.please_allow_function, 0).show();
        }
    }

    private void executeBusiness(String str) {
        StorageManager storageManager = (StorageManager) getSystemService("storage");
//        if (Build.VERSION.SDK_INT >= 29) {
            Intent createOpenDocumentTreeIntent = storageManager.getPrimaryStorageVolume().createOpenDocumentTreeIntent();
            String uri = ((Uri) createOpenDocumentTreeIntent.getParcelableExtra("android.provider.extra.INITIAL_URI")).toString();
            Log.d("TAG", "INITIAL_URI scheme: " + uri);
            String replace = uri.replace("/root/", "/document/");
            Uri parse = Uri.parse(replace + "%3A" + str);
            createOpenDocumentTreeIntent.putExtra("android.provider.extra.INITIAL_URI", parse);
            Log.d("TAG", "uri: " + parse.toString());
//            try {
                startActivityForResult(createOpenDocumentTreeIntent, 7);
//            } catch (ActivityNotFoundException unused) {
//            }
//        } else {
//            startActivity(new Intent(this, BussinessWhatsappclass.class));
//        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getFolderPermission() {
        File file = new File(Commonclass.WHATSAPP_STATUS_PATH);
        File file2 = new File(Commonclass.WHATSAPP_STATUS_PATH_OLD);
        if (file.exists()) {
            execute(Commonclass.WHATSAPP_START_DIR);
        } else if (file2.exists()) {
            execute(Commonclass.WHATSAPP_START_DIR_OLD);
        }
//        else {
//            Toast.makeText(this, (int) R.string.please_watch_w_msg, 0).show();
//        }
    }

    private void execute(String str) {
        StorageManager storageManager = (StorageManager) getSystemService("storage");
//        if (Build.VERSION.SDK_INT >= 29) {
            Intent createOpenDocumentTreeIntent = storageManager.getPrimaryStorageVolume().createOpenDocumentTreeIntent();
            String uri = ((Uri) createOpenDocumentTreeIntent.getParcelableExtra("android.provider.extra.INITIAL_URI")).toString();
            Log.d("TAG", "INITIAL_URI scheme: " + uri);
            String replace = uri.replace("/root/", "/document/");
            Uri parse = Uri.parse(replace + "%3A" + str);
            createOpenDocumentTreeIntent.putExtra("android.provider.extra.INITIAL_URI", parse);
            Log.d("TAG", "uri: " + parse.toString());
//            try {
                startActivityForResult(createOpenDocumentTreeIntent, 6);
//            } catch (ActivityNotFoundException unused) {
//            }
//        } else {
//            startActivity(new Intent(this, Whatsapp_Statusclass.class));
//        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        int i3 = -1;
        if (i2 == -1 && i == 6 && intent != null) {
            Uri data = intent.getData();
            if (data.getPath().endsWith(".Statuses")) {
                int flags = intent.getFlags() & 3;
                getContentResolver().takePersistableUriPermission(data, flags);
                if (flags > 0) {
                    SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_DETAILS, data.toString());
                    SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_KYC, true);
                    startActivity(new Intent(this, MainActivitycalss.class));
                } else {
                    Toast.makeText(this, (int) R.string.please_allow_function, 0).show();
                    SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_DETAILS, "");
                    SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_KYC, false);
                }
            } else {
                Toast.makeText(this, (int) R.string.wrong_path, 0).show();
                Log.d("TAGmycheck", "onActivityResult: wrong path dialog");
                SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_DETAILS, "");
                SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_KYC, false);
            }
            i3 = -1;
        }
        if (i2 == i3 && i == 7 && intent != null) {
            Uri data2 = intent.getData();
            if (data2.getPath().endsWith(".Statuses")) {
                int flags2 = intent.getFlags() & 3;
                getContentResolver().takePersistableUriPermission(data2, flags2);
                if (flags2 > 0) {
                    SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_DETAILS_BUSINESS, data2.toString());
                    SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_KYC_BUSINESS, true);
                    startActivity(new Intent(this, MainActivitycalss.class));
                    return;
                }
                Toast.makeText(this, (int) R.string.please_allow_function, 0).show();
                SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_DETAILS_BUSINESS, "");
                SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_KYC_BUSINESS, false);
                return;
            }
            Toast.makeText(this, (int) R.string.wrong_path, 0).show();
            Log.d("TAGmycheck", "onActivityResult: wrong path dialog");
            SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_DETAILS, "");
            SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_KYC, false);
        }
    }

    private void androidVersionControl() {
        if (Build.VERSION.SDK_INT >= 30) {
            try {
                Intent intent = new Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION");
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                startActivityForResult(intent, 2296);
            } catch (Exception unused) {
                Intent intent2 = new Intent();
                intent2.setAction("android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION");
                startActivityForResult(intent2, 2296);
            }
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
            execute(Commonclass.WHATSAPP_START_DIR);
            this.isPermissionGranted = false;
            Log.d("permission", "" + arrayList.toString());
            ActivityCompat.requestPermissions(this, (String[]) arrayList.toArray(new String[arrayList.size()]), 10);
            return;
        }
        this.isPermissionGranted = true;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
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

    private boolean authenticate() {
        return Build.VERSION.SDK_INT <= 30 || ActivityCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionDenied()) {
            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
            return;
        }

        Commonclass.APP_DIR = Environment.getExternalStorageDirectory().getPath() +
                File.separator + "StatusDownloader";
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
    private void initView() {
        this.whatsappStatus = (LinearLayout) findViewById(R.id.linearstatus);
        this.bussinesWhatsapp = (LinearLayout) findViewById(R.id.linearbusinessstatus);
        this.savedFile = (LinearLayout) findViewById(R.id.linearsave);
    }

}
