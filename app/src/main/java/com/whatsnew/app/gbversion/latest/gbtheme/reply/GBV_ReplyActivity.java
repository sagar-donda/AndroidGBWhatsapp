package com.whatsnew.app.gbversion.latest.gbtheme.reply;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Ad_class;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Constant;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.GBV_BaseActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.chating.GBV_ChattingActivity;

public class GBV_ReplyActivity extends GBV_BaseActivity {
    LinearLayout banner_ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_reply);
        Ad_class.all_banner(GBV_ReplyActivity.this, (LinearLayout) findViewById(R.id.adView));
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        GBV_MyListData[] myListData = new GBV_MyListData[]{
                new GBV_MyListData("Hi,How are you?"),
                new GBV_MyListData("Good Morning"),
                new GBV_MyListData("Good Night"),
                new GBV_MyListData("Where are you?"),
                new GBV_MyListData("When will you reach?"),
                new GBV_MyListData("I have reached home."),
                new GBV_MyListData("Where are going?"),
                new GBV_MyListData("Have you had dinner?"),
                new GBV_MyListData("How's your day?"),
                new GBV_MyListData("Where will we meet?"),
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        GBV_MyListAdapter adapter = new GBV_MyListAdapter(myListData, GBV_ReplyActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (Constant.AD_STATUS == "true") {
            Ad_class.adCounter++;
            Ad_class.showInterAd(this, new Ad_class.onLisoner() {
                @Override
                public void click() {
                    GBV_ReplyActivity.super.onBackPressed();
                }
            });
        } else {
            super.onBackPressed();
        }
    }
}