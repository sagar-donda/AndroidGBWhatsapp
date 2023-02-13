package com.whatsnew.app.gbversion.latest.gbtheme.blankmes;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.whatsnew.app.gbversion.latest.gbtheme.R;

public class GBV_BlankMesActivity extends AppCompatActivity {
    ImageView btn_send;
    CheckBox chk_applink;
    EditText edt_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_blank_mes);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_send = findViewById(R.id.btn_send);
        chk_applink = findViewById(R.id.chk_applink);
        edt_number = findViewById(R.id.edt_number);

        this.btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int number = Integer.parseInt(GBV_BlankMesActivity.this.edt_number.getText().toString());
                    String message = "";
                    for (int i = 0; i < number; i++) {
                        message = message + " ";
                    }
                    boolean check = GBV_BlankMesActivity.this.chk_applink.isChecked();
                    if (check) {
                        String packageName = GBV_BlankMesActivity.this.getPackageName();
                        message = message + "\n Install this app to create blank message: https://play.google.com/store/apps/details?id=" + packageName;
                    }
                    GBV_BlankMesActivity.this.shareWhatsApp(message);
                } catch (Exception e) {
                    Toast.makeText(GBV_BlankMesActivity.this, "Can't send message", 0).show();
                }
            }
        });

    }

    public void shareWhatsApp(String message) {
        PackageManager pm = getPackageManager();
        try {
            Intent waIntent = new Intent("android.intent.action.SEND");
            waIntent.setType("text/plain");
            pm.getPackageInfo("com.whatsapp", 0);
            waIntent.setPackage("com.whatsapp");
            waIntent.putExtra("android.intent.extra.TEXT", message);
            startActivity(Intent.createChooser(waIntent, "Share with"));
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", 1).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}