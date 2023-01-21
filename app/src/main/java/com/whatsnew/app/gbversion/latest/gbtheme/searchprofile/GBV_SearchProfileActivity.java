package com.whatsnew.app.gbversion.latest.gbtheme.searchprofile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.hbb20.CountryCodePicker;
import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.GBV_Utils;
import com.whatsnew.app.gbversion.latest.gbtheme.directChat.GBV_Helper;

public class GBV_SearchProfileActivity extends AppCompatActivity {
    private EditText number;
    ImageView SendMessage;
    CountryCodePicker CcP;
    private SharedPreferences preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_search_profile);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        this.number = findViewById(R.id.input_text);
        this.CcP = findViewById(R.id.ccp);
        this.SendMessage = findViewById(R.id.go);
        this.SendMessage.setOnClickListener(new GBV_SearchProfileActivity.btnSendMessageListner());
        this.preference = PreferenceManager.getDefaultSharedPreferences(this);
        this.CcP.setCountryForNameCode(GBV_Helper.getCurrentLocale(this));
        this.CcP.setOnCountryChangeListener(new GBV_SearchProfileActivity.btnCcpListner());
        if (getIntent().getStringExtra("number") != null) {
            this.number.setText(getIntent().getStringExtra("number"));
        }

    }

    private class btnSendMessageListner implements View.OnClickListener {
        public void onClick(View v) {
            if (GBV_Utils.appInstalledOrNot(GBV_SearchProfileActivity.this, "com.whatsapp")) {
                String number = GBV_SearchProfileActivity.this.number.getText().toString();
                String mainNumber = GBV_SearchProfileActivity.this.CcP.getSelectedCountryCode() + number;
                if (number.length() == 0) {
                    Toast.makeText(GBV_SearchProfileActivity.this, R.string.message_number_empty, Toast.LENGTH_SHORT).show();
                } else if (number.length() < 7) {
                    Toast.makeText(GBV_SearchProfileActivity.this, R.string.message_number_error, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        PackageManager packageManager = GBV_SearchProfileActivity.this.getPackageManager();
                        Intent intent = new Intent("android.intent.action.VIEW");
                        try {
                            String str3 = "https://api.whatsapp.com/send?phone=" + mainNumber ;
                            intent.setPackage("com.whatsapp");
                            intent.setData(Uri.parse(str3));
                            if (intent.resolveActivity(packageManager) != null) {
                                GBV_SearchProfileActivity.this.startActivity(intent);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e2) {
                        Toast.makeText(GBV_SearchProfileActivity.this, "Error/n" + e2.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(GBV_SearchProfileActivity.this, "Please install whatsapp to use this feature.", Toast.LENGTH_SHORT).show();
            }        }
    }

    private class btnCcpListner implements CountryCodePicker.OnCountryChangeListener {
        public void onCountrySelected() {
            GBV_SearchProfileActivity.this.CcP.setCountryPreference(GBV_SearchProfileActivity.this.CcP.getSelectedCountryNameCode());
            GBV_SearchProfileActivity.this.preference.edit().putString("last_locale", GBV_SearchProfileActivity.this.CcP.getSelectedCountryCode()).apply();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}