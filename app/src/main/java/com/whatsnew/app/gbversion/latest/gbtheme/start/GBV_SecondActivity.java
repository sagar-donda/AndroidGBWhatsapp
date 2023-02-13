package com.whatsnew.app.gbversion.latest.gbtheme.start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.whatsnew.app.gbversion.latest.gbtheme.R;

public class GBV_SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_second);

        findViewById(R.id.nxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GBV_SecondActivity.this, GBV_ThirdActivity.class));
            }
        });
        findViewById(R.id.skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GBV_SecondActivity.this, GBV_MainActivity.class));
            }
        });
    }
}