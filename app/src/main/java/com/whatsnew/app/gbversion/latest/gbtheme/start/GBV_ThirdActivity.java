package com.whatsnew.app.gbversion.latest.gbtheme.start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.whatsnew.app.gbversion.latest.gbtheme.R;

public class GBV_ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_third);

        findViewById(R.id.nxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GBV_ThirdActivity.this, GBV_DashBoardActivity.class));
            }
        });

    }
}