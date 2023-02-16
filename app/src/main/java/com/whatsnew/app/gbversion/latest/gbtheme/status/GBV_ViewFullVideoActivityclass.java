package com.whatsnew.app.gbversion.latest.gbtheme.status;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Ad_class;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Constant;
import com.whatsnew.app.gbversion.latest.gbtheme.BuildConfig;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;


public class GBV_ViewFullVideoActivityclass extends AppCompatActivity {
    private ImageView fabRepostVideo;
    private ImageView fabSaveVideo;
    private ImageView fabShareVideo;
    private LinearLayout linearLayoutVideo;
    private MediaController mediaController;
    private GBV_WhatsappStatusModelclass model;
    private int position = 0;
    private VideoView videoFull;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.gbv_activity_view_full_video_resource);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Ad_class.all_banner(GBV_ViewFullVideoActivityclass.this, (LinearLayout) findViewById(R.id.adView));
        initView();
        if (getIntent().hasExtra("file")) {
            GBV_WhatsappStatusModelclass whatsappStatusModelclass = (GBV_WhatsappStatusModelclass) getIntent().getParcelableExtra("file");
            this.model = whatsappStatusModelclass;
            if (whatsappStatusModelclass != null) {
                this.videoFull.setVideoURI(Uri.parse(whatsappStatusModelclass.getUri()));
                MediaController mediaController = new MediaController(this) {
                    @Override 
                    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
                        if (keyEvent.getKeyCode() == 4 && keyEvent.getAction() == 1) {
                            ((Activity) getContext()).finish();
                        }
                        return super.dispatchKeyEvent(keyEvent);
                    }
                };
                this.mediaController = mediaController;
                this.videoFull.setMediaController(mediaController);
                this.mediaController.setAnchorView(this.videoFull);
                this.videoFull.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override 
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        if (GBV_ViewFullVideoActivityclass.this.position != 0) {
                            GBV_ViewFullVideoActivityclass.this.videoFull.seekTo(0);
                            GBV_ViewFullVideoActivityclass.this.videoFull.pause();
                        } else {
                            GBV_ViewFullVideoActivityclass.this.videoFull.start();
                        }
                        GBV_ViewFullVideoActivityclass.this.mediaController.show(5000);
                        GBV_ViewFullVideoActivityclass.this.videoFull.requestFocus();
                    }
                });
                if (new File(GBV_Commonclass.APP_DIR + File.separator + this.model.getName()).exists()) {
                    this.linearLayoutVideo.setVisibility(0);
                    this.fabSaveVideo.setVisibility(8);
                }
                this.fabSaveVideo.setOnClickListener(new View.OnClickListener() { 
                    @Override 
                    public void onClick(View view) {
                        GBV_ViewFullVideoActivityclass viewFullVideoActivityclass = GBV_ViewFullVideoActivityclass.this;
                        viewFullVideoActivityclass.saveVideoQ(Uri.parse(viewFullVideoActivityclass.model.getUri()));
                    }
                });
                this.fabShareVideo.setOnClickListener(new View.OnClickListener() { 
                    @Override 
                    public void onClick(View view) {
                        GBV_ViewFullVideoActivityclass viewFullVideoActivityclass = GBV_ViewFullVideoActivityclass.this;
                        viewFullVideoActivityclass.shareVideo(viewFullVideoActivityclass.model.getUri());
                    }
                });
                this.fabRepostVideo.setOnClickListener(new View.OnClickListener() { 
                    @Override 
                    public void onClick(View view) {
                        GBV_ViewFullVideoActivityclass viewFullVideoActivityclass = GBV_ViewFullVideoActivityclass.this;
                        viewFullVideoActivityclass.shareVideoToWhatsapp(viewFullVideoActivityclass.model.getUri());
                    }
                });
            }
        }
    }

   
    public void saveVideoQ(Uri uri) {
        String str = BuildConfig.APPLICATION_ID + System.currentTimeMillis() + ".mp4";
        if (Build.VERSION.SDK_INT >= 29) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("relative_path", "DCIM/StatusDownloaderNew");
            contentValues.put(GBV_SQLHelperClassclass.TITLE, str);
            contentValues.put("_display_name", str);
            contentValues.put("mime_type", "video/mp4");
            contentValues.put("date_added", Long.valueOf(System.currentTimeMillis() / 1000));
            contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
            contentValues.put("is_pending", (Integer) 1);
            Uri insert = getContentResolver().insert(MediaStore.Video.Media.getContentUri("external_primary"), contentValues);
            try {
                ParcelFileDescriptor openFileDescriptor = getContentResolver().openFileDescriptor(insert, "w");
                FileOutputStream fileOutputStream = new FileOutputStream(openFileDescriptor.getFileDescriptor());
                InputStream openInputStream = getContentResolver().openInputStream(uri);
                byte[] bArr = new byte[8192];
                while (true) {
                    int read = openInputStream.read(bArr);
                    if (read > 0) {
                        fileOutputStream.write(bArr, 0, read);
                    } else {
                        fileOutputStream.close();
                        openInputStream.close();
                        openFileDescriptor.close();
                        contentValues.clear();
                        contentValues.put("is_pending", (Integer) 0);
                        contentValues.put("is_pending", (Integer) 0);
                        getContentResolver().update(insert, contentValues, null, null);
                        new File(GBV_Commonclass.APP_DIR + str);
                        Toast.makeText(this, (int) R.string.video_saved, 0).show();
                        this.fabSaveVideo.setEnabled(false);
                        return;
                    }
                }
            } catch (Exception e) {
                Toast.makeText(this, "error: " + e.getMessage(), 0).show();
                e.printStackTrace();
            }
        } else {
            try {
                File file = new File(uri.getPath());
                String str2 = BuildConfig.APPLICATION_ID + System.currentTimeMillis() + ".mp4";
                File file2 = new File(GBV_Commonclass.APP_DIR);
                if (!file2.exists()) {
                    file2.mkdirs();
                }
                File file3 = new File(file2, str2);
                if (file.exists()) {
                    FileInputStream fileInputStream = new FileInputStream(new File(file.getPath()));
                    FileOutputStream fileOutputStream2 = new FileOutputStream(file3);
                    byte[] bArr2 = new byte[1024];
                    while (true) {
                        int read2 = fileInputStream.read(bArr2);
                        if (read2 > 0) {
                            fileOutputStream2.write(bArr2, 0, read2);
                        } else {
                            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                            intent.setData(Uri.fromFile(new File(file.getPath())));
                            sendBroadcast(intent);
                            fileInputStream.close();
                            fileOutputStream2.close();
                            Toast.makeText(this, (int) R.string.video_save, 0).show();
                            return;
                        }
                    }
                } else {
                    Toast.makeText(this, (int) R.string.video_save_fail, 0).show();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

   
    public void shareVideo(String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        Uri parse = Uri.parse(str);
        intent.setType("video/mp4");
        intent.putExtra("android.intent.extra.STREAM", parse);
        startActivity(Intent.createChooser(intent, "Share image using"));
    }

   
    public void shareVideoToWhatsapp(String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        Uri parse = Uri.parse(str);
        intent.setType("video/mp4");
        intent.setPackage("com.whatsapp");
        intent.putExtra("android.intent.extra.STREAM", parse);
        startActivity(Intent.createChooser(intent, "Share video using"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.videoFull != null) {
            Log.d("TAGvideo", "onPause: if");
            this.position = this.videoFull.getCurrentPosition();
            return;
        }
        this.position = 0;
        Log.d("TAGvideo", "onPause: else");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAGvideo", "onDesrtro: if");
        this.position = 0;
    }

    private void initView() {
        this.videoFull = (VideoView) findViewById(R.id.video_full);
        this.linearLayoutVideo = (LinearLayout) findViewById(R.id.linearfullvideo);
        this.fabRepostVideo = (ImageView) findViewById(R.id.fab_repost_video);
        this.fabShareVideo = (ImageView) findViewById(R.id.fab_share_video);
        this.fabSaveVideo = (ImageView) findViewById(R.id.fab_save_video);
    }

    @Override
    public void onBackPressed() {
        if (Constant.AD_STATUS == "true") {
            Ad_class.adCounter++;
            Ad_class.showInterAd(this, new Ad_class.onLisoner() {
                @Override
                public void click() {
                    GBV_ViewFullVideoActivityclass.super.onBackPressed();
                }
            });
        } else {
            super.onBackPressed();
        }
    }
}
