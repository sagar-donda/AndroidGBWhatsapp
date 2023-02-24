package com.whatsnew.app.gbversion.latest.gbtheme.status;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.potyvideo.slider.library.SliderLayout;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Ad_class;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Constant;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import org.apache.commons.io.FileUtils;


public class GBV_ViewFullImageActivityclass extends AppCompatActivity {
    private ImageView fabRepost;
    private ImageView fabSave;
    private ImageView fabShare;
    private ImageView fullIv;
    GBV_WhatsappStatusModelclass imageModel;
    private LinearLayout linearLayoutImage;
    private ImageView progressBar;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.gbv_activity_view_full_image_resource);


        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Ad_class.all_banner(GBV_ViewFullImageActivityclass.this, (LinearLayout) findViewById(R.id.adView));
        initView();
        if (getIntent().hasExtra("file")) {
            GBV_WhatsappStatusModelclass whatsappStatusModelclass = (GBV_WhatsappStatusModelclass) getIntent().getParcelableExtra("file");
            this.imageModel = whatsappStatusModelclass;
            if (whatsappStatusModelclass != null) {
                showImage(whatsappStatusModelclass.getUri());
            }
            if (new File(GBV_Commonclass.APP_DIR + File.separator + this.imageModel.getName()).exists()) {
                this.linearLayoutImage.setVisibility(0);
                this.fabSave.setVisibility(8);
            }
            this.fabSave.setOnClickListener(new View.OnClickListener() {
                @Override 
                public void onClick(View view) {
                    if (GBV_ViewFullImageActivityclass.this.imageModel != null) {
                        try {
                            GBV_ViewFullImageActivityclass viewFullImageActivityclass = GBV_ViewFullImageActivityclass.this;
                            String path = GBV_PathUtilclass.getPath(viewFullImageActivityclass, Uri.parse(viewFullImageActivityclass.imageModel.getUri()));
                            Log.d("TAGpath", "onClick: " + path);
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(GBV_ViewFullImageActivityclass.this.getContentResolver(), Uri.parse(GBV_ViewFullImageActivityclass.this.imageModel.getUri()));
                            GBV_ViewFullImageActivityclass viewFullImageActivityclass2 = GBV_ViewFullImageActivityclass.this;
                            viewFullImageActivityclass2.saveImage(bitmap, System.currentTimeMillis() + ".png");
                        } catch (Exception unused) {
                        }
                    } else {
                        Toast.makeText(GBV_ViewFullImageActivityclass.this, (int) R.string.unable_to_save_image, 0).show();
                    }
                }
            });
            this.fabShare.setOnClickListener(new View.OnClickListener() {
                @Override 
                public void onClick(View view) {
                    GBV_ViewFullImageActivityclass viewFullImageActivityclass = GBV_ViewFullImageActivityclass.this;
                    viewFullImageActivityclass.shareImage(viewFullImageActivityclass.imageModel.getPath());
                }
            });
            this.fabRepost.setOnClickListener(new View.OnClickListener() {
                @Override 
                public void onClick(View view) {
                    GBV_ViewFullImageActivityclass viewFullImageActivityclass = GBV_ViewFullImageActivityclass.this;
                    viewFullImageActivityclass.shareImageToWhatsapp(viewFullImageActivityclass.imageModel.getPath());
                }
            });
            return;
        }
        Toast.makeText(this, (int) R.string.error_while_show_image, 0).show();
    }

   
    public void shareImage(String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        Uri parse = Uri.parse(str);
        intent.setType("image/png");
        intent.putExtra("android.intent.extra.STREAM", parse);
        startActivity(Intent.createChooser(intent, "Share image using"));
    }

   
    public void shareImageToWhatsapp(String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        Uri parse = Uri.parse(str);
        intent.setType("image/png");
        intent.setPackage("com.whatsapp");
        intent.putExtra("android.intent.extra.STREAM", parse);
        startActivity(Intent.createChooser(intent, "Share image using"));
    }

    private void showImage(String str) {
        this.fullIv.setImageURI(Uri.parse(str));
        this.progressBar.setVisibility(4);
    }

    private void SaveImage(Bitmap bitmap) {
        String file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File file2 = new File(file + "/saved_images");
        file2.mkdirs();
        int nextInt = new Random().nextInt(10000);
        File file3 = new File(file2, "Image-" + nextInt + ".jpg");
        if (file3.exists()) {
            file3.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file3);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaScannerConnection.scanFile(this, new String[]{file3.toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String str, Uri uri) {
                Log.i("ExternalStorage", "Scanned " + str + ":");
                StringBuilder sb = new StringBuilder();
                sb.append("-> uri=");
                sb.append(uri);
                Log.i("ExternalStorage", sb.toString());
            }
        });
    }

    public void saveImage(Bitmap bitmap, String str) throws IOException {
        OutputStream outputStream;
        if (Build.VERSION.SDK_INT >= 29) {
            ContentResolver contentResolver = getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put("_display_name", str);
            contentValues.put("mime_type", "image/png");
            contentValues.put("relative_path", "DCIM/StatusDownloaderNew");
            outputStream = contentResolver.openOutputStream(contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues));
        } else {
            String str2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + GBV_Commonclass.DIR_NAME;
            File file = new File(str2);
            if (!file.exists()) {
                file.mkdir();
            }
            File file2 = new File(str2, str);
            outputStream = new FileOutputStream(file2);
            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            intent.setData(Uri.fromFile(new File(file2.getPath())));
            sendBroadcast(intent);
        }
        Log.d("TAGsave", "saveImage: " + bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream));
        Toast.makeText(this, (int) R.string.image_save, 0).show();
        this.fabSave.setEnabled(false);
        outputStream.flush();
        outputStream.close();
    }

    private void finalCopyFunction(Context context, String str, String str2) {
        File file = new File(str2);
        if (file.exists()) {
            Log.d("TAGCHECKER", "finalCopyFunction: " + file.getAbsolutePath());
        } else {
            Log.d("TAGCHECKER", "finalCopyFunction: not exist");
            Log.d("TAGCHECKER", "path" + str2);
        }
        File file2 = new File(GBV_Commonclass.APP_DIR);
        if (isAppDirectoryExist(file2)) {
            File file3 = new File(file2 + File.separator + str);
            try {
                FileUtils.copyFile(file, file3);
                file3.setLastModified(System.currentTimeMillis());
                new GBV_SingleMediaScannerclass(context, file2);
                Toast.makeText(this, (int) R.string.image_saved, 0).show();
            } catch (IOException e) {
                Log.d("TAGxception", "finalCopyFunction: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, (int) R.string.unable_to_create, 0).show();
        }
    }

    private boolean isAppDirectoryExist(File file) {
        if (!file.exists()) {
            file.mkdirs();
            if (!file.mkdirs()) {
                return false;
            }
        }
        return true;
    }

    private void initView() {
        this.fullIv = (ImageView) findViewById(R.id.full_iv);
        this.progressBar = (ImageView) findViewById(R.id.progress_bar);
        this.linearLayoutImage = (LinearLayout) findViewById(R.id.linearfullimage);
        this.fabRepost = (ImageView) findViewById(R.id.fab_repost);
        this.fabShare = (ImageView) findViewById(R.id.fab_share);
        this.fabSave = (ImageView) findViewById(R.id.fab_save);
    }

    @Override
    public void onBackPressed() {
        if (Constant.AD_STATUS == "true") {
            Ad_class.adCounter++;
            Ad_class.showInterAd(this, new Ad_class.onLisoner() {
                @Override
                public void click() {
                    GBV_ViewFullImageActivityclass.super.onBackPressed();
                }
            });
        } else {
            super.onBackPressed();
        }
    }
}
