package com.whatsnew.app.gbversion.latest.gbtheme.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.whatsnew.app.gbversion.latest.gbtheme.R;

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
                startActivity(new Intent(GBV_ExitActivity.this, GBV_MainActivity.class));
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}