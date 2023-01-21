package com.whatsnew.app.gbversion.latest.gbtheme.fackChat;

import static com.whatsnew.app.gbversion.latest.gbtheme.fackChat.GBV_Wallpaper.selectedImageUri;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.service.GBV_BaseActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

import java.io.IOException;

public class GBV_Calls extends GBV_BaseActivity {
    LinearLayout msg;
    LinearLayout mute;
    LinearLayout speaker;
    private Handler customHandler = new Handler();
    ImageView endCall;
    TextView Name;
    ImageView profileImages;
    String profilename;
    private long startTime = 0;
    TextView times;
    long timeInMilliseconds = 0;
    long timeSwapBuff = 0;
    private Runnable updateTimerThread = new updateTimeThreadListner();
    long updatedTime = 0;

    private class btnSpeakerListner implements OnClickListener {
        public void onClick(View v) {
        }
    }

    private class btnMsgListner implements OnClickListener {
        public void onClick(View v) {
        }
    }

    private class btnMuteListner implements OnClickListener {
        public void onClick(View v) {
        }
    }

    private class btnEndCallListner implements OnClickListener {
        public void onClick(View view) {
            GBV_Calls GBVCalls = GBV_Calls.this;
            GBVCalls.timeSwapBuff += timeInMilliseconds;
            customHandler.removeCallbacks(updateTimerThread);
            finish();}
    }

    private class updateTimeThreadListner implements Runnable {
        @SuppressLint("DefaultLocale")
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs %= 60;
            times.setText("" + String.format("%02d", mins) + " : " + String.format("%02d", secs));
            customHandler.postDelayed(this, 0);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_calls);

        Name = findViewById(R.id.txtname);
        times = findViewById(R.id.txtTime);
        profileImages = findViewById(R.id.rlProfile);
        endCall = findViewById(R.id.imEndCall);
        speaker = findViewById(R.id.speaker);
        msg = findViewById(R.id.msg);
        mute = findViewById(R.id.mute);
        speaker.setOnClickListener(new btnSpeakerListner());
        msg.setOnClickListener(new btnMsgListner());
        mute.setOnClickListener(new btnMuteListner());

        if (getIntent().getExtras().getInt("ID") == 1) {
            profilename = getIntent().getExtras().getString("NAME");
            if (selectedImageUri != null) {
                Bitmap mBitmap = null;
                try {
                    mBitmap = Media.getBitmap(getContentResolver(), selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                profileImages.setBackground(new BitmapDrawable(getResources(), mBitmap));
                Log.e("TAG", "onCreate:profile "+profileImages);
            } else {
                profileImages.setBackground(getResources().getDrawable(R.drawable.ic_user));
            }
        } else {
            profilename = getIntent().getExtras().getString("NAME");
            if (GBV_UserChat.callImages != null) {
                profileImages.setBackground(new BitmapDrawable(getResources(), GBV_UserChat.callImages));
            } else {
                profileImages.setBackground(getResources().getDrawable(R.drawable.ic_user));
            }
        }
        Name.setText(profilename);
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);
        endCall.setOnClickListener(new btnEndCallListner());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
