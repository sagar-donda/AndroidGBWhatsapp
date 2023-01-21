package com.whatsnew.app.gbversion.latest.gbtheme.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.GBV_DebouncedOnClickListener;
import com.whatsnew.app.gbversion.latest.gbtheme.GBV_Utils;
import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.blankmes.GBV_BlankMesActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.chating.GBV_ChattingActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.cleaner.GBV_WACleanMainActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.directChat.GBV_ChatDirect;
import com.whatsnew.app.gbversion.latest.gbtheme.emojiText.GBV_Texttoemoji;
import com.whatsnew.app.gbversion.latest.gbtheme.fackChat.GBV_MainFackChat;
import com.whatsnew.app.gbversion.latest.gbtheme.reply.GBV_ReplyActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.GBV_StatusMainActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.shakeShortcut.GBV_ShakeMain;
import com.whatsnew.app.gbversion.latest.gbtheme.shake_Detector.GBV_ShakeCallback;
import com.whatsnew.app.gbversion.latest.gbtheme.shake_Detector.GBV_ShakeDetector;
import com.whatsnew.app.gbversion.latest.gbtheme.shake_Detector.GBV_ShakeOptions;
import com.whatsnew.app.gbversion.latest.gbtheme.textRepeater.GBV_MainTextRepeater;

public class GBV_FirstMAinActivity extends AppCompatActivity {

    ImageView linearWPCleaner;
    ImageView linearWPDirectChat;
    ImageView linearWPEmojis;
    ImageView linearWPFakeChat;
    ImageView linearWPAppGallery;
    ImageView linearWPAppShortcut;
    ImageView linearWPTextRepeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_first_main);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        linearWPAppShortcut = findViewById(R.id.shortcut);
        linearWPAppGallery = findViewById(R.id.Gallery);
        linearWPCleaner = findViewById(R.id.cleaner);
        linearWPDirectChat = findViewById(R.id.directChat);
        linearWPEmojis = findViewById(R.id.Textemoji);
        linearWPTextRepeter = findViewById(R.id.RepeatText);
        linearWPFakeChat = findViewById(R.id.FackChat);

        findViewById(R.id.reply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GBV_FirstMAinActivity.this, GBV_ReplyActivity.class));
            }
        });
        findViewById(R.id.chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GBV_FirstMAinActivity.this, GBV_ChattingActivity.class));
            }
        });
        findViewById(R.id.blankm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GBV_FirstMAinActivity.this, GBV_BlankMesActivity.class));
            }
        });
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GBV_FirstMAinActivity.this, GBV_SecondMainActivity.class));
            }
        });
        GBV_DebouncedOnClickListener GBVDebouncedOnClickListener = new GBV_DebouncedOnClickListener(1500) {
            @Override
            public void onDebouncedClick(View v) {
                switch (v.getId()) {
                    case R.id.shortcut:
                        startActivity(new Intent(GBV_FirstMAinActivity.this, GBV_ShakeMain.class));
                        break;
                    case R.id.Gallery:
                        startActivity(new Intent(GBV_FirstMAinActivity.this, GBV_StatusMainActivity.class));
                        break;
                    case R.id.directChat:
                        startActivity(new Intent(GBV_FirstMAinActivity.this, GBV_ChatDirect.class));
                        break;
                    case R.id.FackChat:
                        startActivity(new Intent(GBV_FirstMAinActivity.this, GBV_MainFackChat.class));
                        break;
                    case R.id.cleaner:
                        startActivity(new Intent(GBV_FirstMAinActivity.this, GBV_WACleanMainActivity.class));
                        break;
                    case R.id.RepeatText:
                        startActivity(new Intent(GBV_FirstMAinActivity.this, GBV_MainTextRepeater.class));
                        break;
                    case R.id.Textemoji:
                        startActivity(new Intent(GBV_FirstMAinActivity.this, GBV_Texttoemoji.class));
                        break;
                    default:
                        break;
                }
            }
        };

        linearWPAppShortcut.setOnClickListener(GBVDebouncedOnClickListener);
        linearWPAppGallery.setOnClickListener(GBVDebouncedOnClickListener);
        linearWPCleaner.setOnClickListener(GBVDebouncedOnClickListener);
        linearWPDirectChat.setOnClickListener(GBVDebouncedOnClickListener);
        linearWPEmojis.setOnClickListener(GBVDebouncedOnClickListener);
        linearWPTextRepeter.setOnClickListener(GBVDebouncedOnClickListener);
        linearWPFakeChat.setOnClickListener(GBVDebouncedOnClickListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            new GBV_ShakeDetector(new GBV_ShakeOptions().background(Boolean.TRUE).interval(1000)
                    .shakeCount(2).sensibility(2.0f)).start(this, new GBVShakeDetectListner());
        }
    }

    private class GBVShakeDetectListner implements GBV_ShakeCallback {
        public void onShake() {
            if (GBV_Utils.appInstalledOrNot(GBV_FirstMAinActivity.this, "com.whatsapp")) {
                startActivity(getPackageManager().getLaunchIntentForPackage("com.whatsapp"));
            } else {
                Toast.makeText(GBV_FirstMAinActivity.this, "Please install whatsapp to use this feature.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}