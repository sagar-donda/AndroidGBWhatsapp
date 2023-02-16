package com.whatsnew.app.gbversion.latest.gbtheme.status;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Ad_class;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Constant;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;


public class GBV_WhatsappImagesAdapterclass extends RecyclerView.Adapter<GBV_WhatsappImagesAdapterclass.myViewHolder> {
    ArrayList<GBV_WhatsappStatusModelclass> dataholder;
    Snackbar snackbar;
    Context context;

    public GBV_WhatsappImagesAdapterclass(ArrayList<GBV_WhatsappStatusModelclass> arrayList, Context context) {
        this.dataholder = arrayList;
        this.context = context;
    }

    @Override 
    public myViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new myViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gbv_whatsapp_images_row_resource, viewGroup, false));
    }

    public void onBindViewHolder(myViewHolder myviewholder, int i) {
        final GBV_WhatsappStatusModelclass whatsappStatusModelclass = this.dataholder.get(i);
        Picasso.get().load(whatsappStatusModelclass.getUri()).into(myviewholder.imageView);
        myviewholder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                if (Constant.AD_STATUS == "true") {
                    Ad_class.adCounter++;
                    Ad_class.showInterAd((Activity) context, new Ad_class.onLisoner() {
                        @Override
                        public void click() {
                            AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                            Intent intent = new Intent(appCompatActivity.getApplicationContext(), GBV_ViewFullImageActivityclass.class);
                            Bundle bundle = new Bundle();
                            whatsappStatusModelclass.setPath(null);
                            try {
                                whatsappStatusModelclass.setPath(GBV_PathUtilclass.getPath(view.getContext(), Uri.parse(whatsappStatusModelclass.getUri())));
                                Log.d("TAGpathch", "onClick: " + whatsappStatusModelclass.getPath());
                                bundle.putParcelable("file", whatsappStatusModelclass);
                                intent.putExtras(bundle);
                                appCompatActivity.startActivity(intent);
                            } catch (URISyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                    Intent intent = new Intent(appCompatActivity.getApplicationContext(), GBV_ViewFullImageActivityclass.class);
                    Bundle bundle = new Bundle();
                    whatsappStatusModelclass.setPath(null);
                    try {
                        whatsappStatusModelclass.setPath(GBV_PathUtilclass.getPath(view.getContext(), Uri.parse(whatsappStatusModelclass.getUri())));
                        Log.d("TAGpathch", "onClick: " + whatsappStatusModelclass.getPath());
                        bundle.putParcelable("file", whatsappStatusModelclass);
                        intent.putExtras(bundle);
                        appCompatActivity.startActivity(intent);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        myviewholder.download.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                try {
                    String path = GBV_PathUtilclass.getPath(view.getContext(), Uri.parse(whatsappStatusModelclass.getUri()));
                    Log.d("TAGpath", "onClick: " + path);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(view.getContext().getContentResolver(), Uri.parse(whatsappStatusModelclass.getUri()));
                    GBV_WhatsappImagesAdapterclass whatsappImagesAdapterclass = GBV_WhatsappImagesAdapterclass.this;
                    whatsappImagesAdapterclass.saveImage(bitmap, whatsappStatusModelclass.getName() + ".png", view.getContext(), view);
                } catch (Exception unused) {
                }
            }
        });
    }

   
    public void saveImage(Bitmap bitmap, String str, Context context, View view) throws IOException {
        OutputStream outputStream;
        if (Build.VERSION.SDK_INT >= 29) {
            ContentResolver contentResolver = context.getContentResolver();
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
            context.sendBroadcast(intent);
        }
        boolean compress = bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        Log.d("TAGsave", "saveImage: " + compress);
        if (compress) {
            showSnackbar(view, "Image Saved!");
        } else {
            showSnackbar(view, "Error while saving image!");
        }
        outputStream.flush();
        outputStream.close();
    }

    private void shareImageToWhatsapp(String str, Context context) {
        Intent intent = new Intent("android.intent.action.SEND");
        Uri parse = Uri.parse(str);
        intent.setType("image/png");
        intent.setPackage("com.whatsapp");
        intent.putExtra("android.intent.extra.STREAM", parse);
        context.startActivity(Intent.createChooser(intent, "Share image using"));
    }

    @Override 
    public int getItemCount() {
        return this.dataholder.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        ImageView download;
        ImageView imageView;

        public myViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.whatsapp_status_image);
            this.download = (ImageView) view.findViewById(R.id.status_download);
        }
    }

    private void showSnackbar(View view, String str) {
        Snackbar make = Snackbar.make(view, str, -1);
        this.snackbar = make;
        make.show();
    }

    private void hideSnackbar() {
        this.snackbar.dismiss();
    }
}
