package com.whatsnew.app.gbversion.latest.gbtheme.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Ad_class;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Constant;
import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.emojiText.GBV_Texttoemoji;

public class GBV_ExitActivity extends AppCompatActivity {
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_exit);
        findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });
        findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.AD_STATUS == "true") {
                    Ad_class.adCounter++;
                    Ad_class.showInterAd(GBV_ExitActivity.this, new Ad_class.onLisoner() {
                        @Override
                        public void click() {
                            GBV_ExitActivity.this.startActivity(new Intent(GBV_ExitActivity.this, GBV_MainActivity.class));
                        }
                    });
                } else {
                    GBV_ExitActivity.this.startActivity(new Intent(GBV_ExitActivity.this, GBV_MainActivity.class));
                }
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}