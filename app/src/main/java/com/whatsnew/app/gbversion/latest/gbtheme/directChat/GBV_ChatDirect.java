package com.whatsnew.app.gbversion.latest.gbtheme.directChat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.hbb20.CountryCodePicker;
import com.hbb20.CountryCodePicker.OnCountryChangeListener;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.service.GBV_BaseActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.GBV_Utils;

public class GBV_ChatDirect extends GBV_BaseActivity {
    EditText message;
    private EditText number;
    ImageView SendMessage;
    CountryCodePicker CcP;
    private SharedPreferences preference;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_main_chat_direct);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        this.message = findViewById(R.id.msg);
        this.number = findViewById(R.id.input_text);
        this.CcP = findViewById(R.id.ccp);
        this.SendMessage = findViewById(R.id.go);
        this.SendMessage.setOnClickListener(new btnSendMessageListner());
        this.preference = PreferenceManager.getDefaultSharedPreferences(this);
        this.CcP.setCountryForNameCode(GBV_Helper.getCurrentLocale(this));
        this.CcP.setOnCountryChangeListener(new btnCcpListner());
        if (getIntent().getStringExtra("number") != null) {
            this.number.setText(getIntent().getStringExtra("number"));
        }
    }

    private class btnSendMessageListner implements OnClickListener {
        public void onClick(View v) {
            if (GBV_Utils.appInstalledOrNot(GBV_ChatDirect.this, "com.whatsapp")) {
                String messege = GBV_ChatDirect.this.message.getText().toString();
                String number = GBV_ChatDirect.this.number.getText().toString();
                String mainNumber = GBV_ChatDirect.this.CcP.getSelectedCountryCode() + number;
                if (messege.length() == 0) {
                    Toast.makeText(GBV_ChatDirect.this, "Please enter message", Toast.LENGTH_SHORT).show();
                } else if (number.length() == 0) {
                    Toast.makeText(GBV_ChatDirect.this, R.string.message_number_empty, Toast.LENGTH_SHORT).show();
                } else if (number.length() < 7 || messege.length() <= 0) {
                    Toast.makeText(GBV_ChatDirect.this, R.string.message_number_error, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        PackageManager packageManager = GBV_ChatDirect.this.getPackageManager();
                        Intent intent = new Intent("android.intent.action.VIEW");
                        try {
                            String str3 = "https://api.whatsapp.com/send?phone=" + mainNumber + "&text=" + messege;
                            intent.setPackage("com.whatsapp");
                            intent.setData(Uri.parse(str3));
                            if (intent.resolveActivity(packageManager) != null) {
                                GBV_ChatDirect.this.startActivity(intent);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e2) {
                        Toast.makeText(GBV_ChatDirect.this, "Error/n" + e2.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(GBV_ChatDirect.this, "Please install whatsapp to use this feature.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class btnCcpListner implements OnCountryChangeListener {
        public void onCountrySelected() {
            GBV_ChatDirect.this.CcP.setCountryPreference(GBV_ChatDirect.this.CcP.getSelectedCountryNameCode());
            GBV_ChatDirect.this.preference.edit().putString("last_locale", GBV_ChatDirect.this.CcP.getSelectedCountryCode()).apply();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
