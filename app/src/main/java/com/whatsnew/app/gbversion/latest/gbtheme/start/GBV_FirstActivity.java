package com.whatsnew.app.gbversion.latest.gbtheme.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.service.GBV_BaseActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

public class GBV_FirstActivity extends GBV_BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_first);

        findViewById(R.id.nxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GBV_FirstActivity.this, GBV_SecondActivity.class));
            }
        });
        findViewById(R.id.skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GBV_FirstActivity.this, GBV_MainActivity.class));
            }
        });
    }
}