package com.whatsnew.app.gbversion.latest.gbtheme.chating;

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

import com.whatsnew.app.gbversion.latest.gbtheme.GBV_Utils;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

public class GBV_ChattingActivity extends AppCompatActivity {
    EditText message;
    ImageView SendMessage;
    private SharedPreferences preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_chatting);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        this.message = findViewById(R.id.msg);
        this.SendMessage = findViewById(R.id.go);
        this.SendMessage.setOnClickListener(new GBV_ChattingActivity.btnSendMessageListner());
        this.preference = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class btnSendMessageListner implements View.OnClickListener {
        public void onClick(View v) {
            if (GBV_Utils.appInstalledOrNot(GBV_ChattingActivity.this, "com.whatsapp")) {
                String messege = GBV_ChattingActivity.this.message.getText().toString();
                if (messege.length() == 0) {
                    Toast.makeText(GBV_ChattingActivity.this, "Please enter message", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        PackageManager packageManager = GBV_ChattingActivity.this.getPackageManager();
                        Intent intent = new Intent("android.intent.action.VIEW");
                        try {
                            String str3 = "https://api.whatsapp.com/send?phone=" + "&text=" + messege;
                            intent.setPackage("com.whatsapp");
                            intent.setData(Uri.parse(str3));
                            if (intent.resolveActivity(packageManager) != null) {
                                GBV_ChattingActivity.this.startActivity(intent);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e2) {
                        Toast.makeText(GBV_ChattingActivity.this, "Error/n" + e2.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(GBV_ChattingActivity.this, "Please install whatsapp to use this feature.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}