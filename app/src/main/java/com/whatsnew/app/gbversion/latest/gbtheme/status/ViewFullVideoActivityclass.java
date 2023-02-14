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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.whatsnew.app.gbversion.latest.gbtheme.BuildConfig;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/* loaded from: classes.dex */
public class ViewFullVideoActivityclass extends AppCompatActivity {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private Button fabRepostVideo;
    private Button fabSaveVideo;
    private Button fabShareVideo;
    private LinearLayout linearLayoutVideo;
    private MediaController mediaController;
    private WhatsappStatusModelclass model;
    private int position = 0;
    private VideoView videoFull;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_view_full_video_resource);
        initView();
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if (getIntent().hasExtra("file")) {
            WhatsappStatusModelclass whatsappStatusModelclass = (WhatsappStatusModelclass) getIntent().getParcelableExtra("file");
            this.model = whatsappStatusModelclass;
            if (whatsappStatusModelclass != null) {
                this.videoFull.setVideoURI(Uri.parse(whatsappStatusModelclass.getUri()));
                MediaController mediaController = new MediaController(this) { // from class: com.saver.image.video.business.wp.ViewFullVideoActivityclass.1
                    @Override // android.widget.MediaController, android.view.ViewGroup, android.view.View
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
                this.videoFull.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.saver.image.video.business.wp.ViewFullVideoActivityclass.2
                    @Override // android.media.MediaPlayer.OnPreparedListener
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        if (ViewFullVideoActivityclass.this.position != 0) {
                            ViewFullVideoActivityclass.this.videoFull.seekTo(0);
                            ViewFullVideoActivityclass.this.videoFull.pause();
                        } else {
                            ViewFullVideoActivityclass.this.videoFull.start();
                        }
                        ViewFullVideoActivityclass.this.mediaController.show(5000);
                        ViewFullVideoActivityclass.this.videoFull.requestFocus();
                    }
                });
                if (new File(Commonclass.APP_DIR + File.separator + this.model.getName()).exists()) {
                    this.linearLayoutVideo.setVisibility(8);
                }
                this.fabSaveVideo.setOnClickListener(new View.OnClickListener() { // from class: com.saver.image.video.business.wp.ViewFullVideoActivityclass.3
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        ViewFullVideoActivityclass viewFullVideoActivityclass = ViewFullVideoActivityclass.this;
                        viewFullVideoActivityclass.saveVideoQ(Uri.parse(viewFullVideoActivityclass.model.getUri()));
                    }
                });
                this.fabShareVideo.setOnClickListener(new View.OnClickListener() { // from class: com.saver.image.video.business.wp.ViewFullVideoActivityclass.4
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        ViewFullVideoActivityclass viewFullVideoActivityclass = ViewFullVideoActivityclass.this;
                        viewFullVideoActivityclass.shareVideo(viewFullVideoActivityclass.model.getUri());
                    }
                });
                this.fabRepostVideo.setOnClickListener(new View.OnClickListener() { // from class: com.saver.image.video.business.wp.ViewFullVideoActivityclass.5
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        ViewFullVideoActivityclass viewFullVideoActivityclass = ViewFullVideoActivityclass.this;
                        viewFullVideoActivityclass.shareVideoToWhatsapp(viewFullVideoActivityclass.model.getUri());
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveVideoQ(Uri uri) {
        String str = BuildConfig.APPLICATION_ID + System.currentTimeMillis() + ".mp4";
        if (Build.VERSION.SDK_INT >= 29) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("relative_path", "DCIM/StatusDownloaderNew");
            contentValues.put(SQLHelperClassclass.TITLE, str);
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
                        new File(Commonclass.APP_DIR + str);
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
                File file2 = new File(Commonclass.APP_DIR);
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

    /* JADX INFO: Access modifiers changed from: private */
    public void shareVideo(String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        Uri parse = Uri.parse(str);
        intent.setType("video/mp4");
        intent.putExtra("android.intent.extra.STREAM", parse);
        startActivity(Intent.createChooser(intent, "Share image using"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void shareVideoToWhatsapp(String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        Uri parse = Uri.parse(str);
        intent.setType("video/mp4");
        intent.setPackage("com.whatsapp");
        intent.putExtra("android.intent.extra.STREAM", parse);
        startActivity(Intent.createChooser(intent, "Share video using"));
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAGvideo", "onDesrtro: if");
        this.position = 0;
    }

    private void initView() {
        this.videoFull = (VideoView) findViewById(R.id.video_full);
        this.linearLayoutVideo = (LinearLayout) findViewById(R.id.linearfullvideo);
        this.fabRepostVideo = (Button) findViewById(R.id.fab_repost_video);
        this.fabShareVideo = (Button) findViewById(R.id.fab_share_video);
        this.fabSaveVideo = (Button) findViewById(R.id.fab_save_video);
    }
}
