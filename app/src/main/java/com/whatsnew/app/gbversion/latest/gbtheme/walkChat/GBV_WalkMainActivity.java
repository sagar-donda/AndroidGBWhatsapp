package com.whatsnew.app.gbversion.latest.gbtheme.walkChat;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.text.TextUtils.SimpleStringSplitter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.GBV_BaseActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.GBV_Utils;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

public class GBV_WalkMainActivity extends GBV_BaseActivity {
    public static boolean isWalk = true;
    ImageView BtnWalk;
    ImageView OpenWhatsApp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_main_walk);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + getPackageName()));
            Log.e("Packagename", getPackageName());
            startActivityForResult(intent, 1234);
        }
        if (!accessibilityPermission(getApplicationContext(), GBV_BasicAccessibilityService.class)) {
            startActivityForResult(new Intent("android.settings.ACCESSIBILITY_SETTINGS"), 5000);
        }
        this.BtnWalk = findViewById(R.id.btnWalk);
        this.OpenWhatsApp = findViewById(R.id.openWhatsapp);
        this.OpenWhatsApp.setOnClickListener(new btnOpenWhatsappListner());
        this.BtnWalk.setOnClickListener(new btnWalkListner());
    }

    private class btnOpenWhatsappListner implements OnClickListener {

        public void onClick(View v) {
            new AlertDialog.Builder(GBV_WalkMainActivity.this)
                    .setCancelable(true)
                    .setMessage("Do you want to open Whatsapp with this feature?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (GBV_Utils.appInstalledOrNot(GBV_WalkMainActivity.this, "com.whatsapp")) {
                                startActivity(getPackageManager().getLaunchIntentForPackage("com.whatsapp"));
                            } else {
                                Toast.makeText(GBV_WalkMainActivity.this, "Please install whatsapp to use this feature.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }
    }

    private class btnWalkListner implements OnClickListener {

        public void onClick(View v) {
            if (GBV_WalkMainActivity.isWalk) {
                GBV_WalkMainActivity.isWalk = false;
                BtnWalk.setImageResource(R.drawable.switch_off);
                return;
            }
            GBV_WalkMainActivity.isWalk = true;
            BtnWalk.setImageResource(R.drawable.switch_on);
        }
    }

    public static boolean accessibilityPermission(Context context, Class<?> cls) {
        ComponentName componentName = new ComponentName(context, cls);
        String string = Secure.getString(context.getContentResolver(), "enabled_accessibility_services");
        if (string == null) {
            return false;
        }
        SimpleStringSplitter simpleStringSplitter = new SimpleStringSplitter(':');
        simpleStringSplitter.setString(string);
        while (simpleStringSplitter.hasNext()) {
            ComponentName unflattenFromString = ComponentName.unflattenFromString(simpleStringSplitter.next());
            if (unflattenFromString != null && unflattenFromString.equals(componentName)) {
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