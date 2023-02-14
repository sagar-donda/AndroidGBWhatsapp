package com.whatsnew.app.gbversion.latest.gbtheme.savestatus;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.whatsnew.app.gbversion.latest.gbtheme.R;
public class GBV_VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gbv_video);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        final String path = getIntent().getStringExtra("video");

        VideoView videoView = findViewById(R.id.video_full);

        // Uri object to refer the
        // resource from the videoUrl
        Uri uri = Uri.parse(path);

        // sets the resource from the
        // videoUrl to the videoView
        videoView.setVideoURI(uri);

        // creating object of
        // media controller class
        MediaController mediaController = new MediaController(this);

        // sets the anchor view
        // anchor view for the videoView
        mediaController.setAnchorView(videoView);

        // sets the media player to the videoView
        mediaController.setMediaPlayer(videoView);

        // sets the media controller to the videoView
        videoView.setMediaController(mediaController);

        // starts the video
        videoView.start();

        videoView.setMediaController(new MediaController(this) {
            public boolean dispatchKeyEvent(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP)
                    ((Activity) getContext()).finish();

                return super.dispatchKeyEvent(event);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}