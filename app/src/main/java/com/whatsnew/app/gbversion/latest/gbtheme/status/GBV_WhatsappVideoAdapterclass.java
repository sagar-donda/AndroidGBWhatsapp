package com.whatsnew.app.gbversion.latest.gbtheme.status;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Ad_class;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Constant;
import com.whatsnew.app.gbversion.latest.gbtheme.BuildConfig;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;


public class GBV_WhatsappVideoAdapterclass extends RecyclerView.Adapter<GBV_WhatsappVideoAdapterclass.VieoViewHolder> {
    Context context;
    ArrayList<GBV_WhatsappStatusModelclass> dataHolder;
    Snackbar snackbar;

    public GBV_WhatsappVideoAdapterclass(ArrayList<GBV_WhatsappStatusModelclass> arrayList, Context context) {
        this.dataHolder = arrayList;
        this.context = context;
    }

    @Override 
    public VieoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new VieoViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gbv_whatsapp_video_row_resource, viewGroup, false));
    }

    public void onBindViewHolder(VieoViewHolder vieoViewHolder, int i) {
        final GBV_WhatsappStatusModelclass whatsappStatusModelclass = this.dataHolder.get(i);
        Glide.with(this.context).asBitmap().load(whatsappStatusModelclass.getUri()).into(vieoViewHolder.imageView);
        vieoViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (Constant.AD_STATUS == "true") {
                    Ad_class.adCounter++;
                    Ad_class.showInterAd((Activity) context, new Ad_class.onLisoner() {
                        @Override
                        public void click() {
                            AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                            Intent intent = new Intent(appCompatActivity.getApplicationContext(), GBV_ViewFullVideoActivityclass.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("file", whatsappStatusModelclass);
                            intent.putExtras(bundle);
                            appCompatActivity.startActivity(intent);
                        }
                    });
                } else {
                    AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                    Intent intent = new Intent(appCompatActivity.getApplicationContext(), GBV_ViewFullVideoActivityclass.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("file", whatsappStatusModelclass);
                    intent.putExtras(bundle);
                    appCompatActivity.startActivity(intent);
                }
            }
        });
        vieoViewHolder.download.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                GBV_WhatsappVideoAdapterclass.this.saveVideoQ(Uri.parse(whatsappStatusModelclass.getUri()), view.getContext(), view);
            }
        });
    }

    @Override 
    public int getItemCount() {
        return this.dataHolder.size();
    }

    
    public class VieoViewHolder extends RecyclerView.ViewHolder {
        ImageView download;
        ImageView imageView;

        public VieoViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.whatsapp_video_thumb);
            this.download = (ImageView) view.findViewById(R.id.status_download);
        }
    }

   
    public void saveVideoQ(Uri uri, Context context, View view) {
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
            Uri insert = context.getContentResolver().insert(MediaStore.Video.Media.getContentUri("external_primary"), contentValues);
            try {
                ParcelFileDescriptor openFileDescriptor = context.getContentResolver().openFileDescriptor(insert, "w");
                FileOutputStream fileOutputStream = new FileOutputStream(openFileDescriptor.getFileDescriptor());
                InputStream openInputStream = context.getContentResolver().openInputStream(uri);
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
                        context.getContentResolver().update(insert, contentValues, null, null);
                        new File(GBV_Commonclass.APP_DIR + str);
                        showSnackbar(view, "Video Saved!");
                        return;
                    }
                }
            } catch (Exception e) {
                showSnackbar(view, "Error while saving video!");
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
                            context.sendBroadcast(intent);
                            fileInputStream.close();
                            fileOutputStream2.close();
                            showSnackbar(view, "Video Saved!");
                            return;
                        }
                    }
                } else {
                    showSnackbar(view, "Error while saving video!");
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                showSnackbar(view, "Error while saving video!");
            }
        }
    }

    private void shareVideoToWhatsapp(String str, Context context) {
        Intent intent = new Intent("android.intent.action.SEND");
        Uri parse = Uri.parse(str);
        intent.setType("video/mp4");
        intent.setPackage("com.whatsapp");
        intent.putExtra("android.intent.extra.STREAM", parse);
        context.startActivity(Intent.createChooser(intent, "Share video using"));
    }

    private void showSnackbar(View view, String str) {
        Snackbar make = Snackbar.make(view, str, -1);
        this.snackbar = make;
        make.show();
    }
}
