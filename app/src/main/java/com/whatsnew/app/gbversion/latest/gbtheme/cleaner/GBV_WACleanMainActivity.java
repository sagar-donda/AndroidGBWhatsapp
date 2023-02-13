package com.whatsnew.app.gbversion.latest.gbtheme.cleaner;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.service.GBV_BaseActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

import java.io.File;
import java.lang.reflect.Method;

public class GBV_WACleanMainActivity extends GBV_BaseActivity {
    LinearLayout FooterMain;
    ImageView btnStart;
    ObjectAnimator objAnimator;
    private Handler cleanerHandler = new cleanHandlerMethod();
    private GBV_FreshDownloadView freshDownloads;
    private TextView textDownloader;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_main_whatsappcleaner);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ShowHelp();
        btnStart = findViewById(R.id.startClean);
        FooterMain = findViewById(R.id.footermain);
        textDownloader = findViewById(R.id.rl_footer);
        freshDownloads = findViewById(R.id.pitt);
        btnStart.setOnClickListener(new btnStartListner(this));
    }

    private void ShowHelp() {
        final Dialog dialog = new Dialog(this);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dialog.requestWindowFeature(1);
        View inflate = layoutInflater.inflate(R.layout.gbv_whatsapp_cleaner_info_dialog, null);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        ImageView imageView = inflate.findViewById(R.id.btncancel);
        dialog.setContentView(inflate);
        dialog.show();
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void getImageDetails() {
        new getUserImage().execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @SuppressLint("HandlerLeak")
    private class cleanHandlerMethod extends Handler {

        public void handleMessage(Message message) {
            freshDownloads.upDateProgress((Integer) message.obj);
            if (message.what == 11) {
                freshDownloads.showDownloadError();
            }
        }
    }

    private class btnStartListner implements OnClickListener {
        GBV_WACleanMainActivity objClean;

        btnStartListner(GBV_WACleanMainActivity whatsappCleaner) {
            objClean = whatsappCleaner;
        }

        public void onClick(View view) {
            FooterMain.setVisibility(View.INVISIBLE);
            freshDownloads.setVisibility(View.VISIBLE);
            ((ImageView) findViewById(R.id.ivClean)).setVisibility(View.GONE);
            btnStart.setVisibility(View.INVISIBLE);
            if (!freshDownloads.using()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i <= 100; i++) {
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Message obtain = Message.obtain();
                            obtain.obj = i;
                            cleanerHandler.sendMessage(obtain);
                        }
                    }
                }).start();
                getImageDetails();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnStart.setVisibility(View.VISIBLE);
                        freshDownloads.setVisibility(View.GONE);
                        ((ImageView) findViewById(R.id.ivClean)).setVisibility(View.VISIBLE);
                        freshDownloads.reset();
                    }
                }, 8000);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        FooterMain.setVisibility(View.VISIBLE);
                        try {
                            objAnimator = ObjectAnimator.ofFloat(textDownloader, "Alpha", 0.0f, 1.0f);
                            String str = String.valueOf(GBV_CleanMain.objCleanMain / 1048576.0f);
                            textDownloader.setText("Your Whatsapp is boosted \n\t\t\tStorage Saved " + new Double(str).toString().substring(0, str.indexOf(46) + 3) + " MB");
                            objAnimator.setDuration(5000L);
                            objAnimator.start();
                            GBV_CleanMain.objCleanMain = 0.0f;
                        } catch (Exception e) {
                            textDownloader.setText("Your Whatsapp is boosted \n\t\t\tStorage Saved 0 MB");
                            objAnimator.setDuration(5000L);
                            objAnimator.start();
                            GBV_CleanMain.objCleanMain = 0.0f;
                        }
                    }
                }, 4000);
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class getUserImage extends AsyncTask<Void, Void, Void> {
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Void doInBackground(Void... voids) {
            int i = 0;
            try {
                GBV_CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/Databases/Sent"));
                GBV_CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/WhatsApp Audio/Sent"));
                GBV_CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/GBV_Calls/Sent"));
                GBV_CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/WhatsApp Documents/Sent"));
                GBV_CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/WhatsApp Animated Gifs/Sent"));
                GBV_CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/WhatsApp Images/Sent"));
                GBV_CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Profile Pictures/Sent"));
                GBV_CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/WhatsApp Profile Photos/Sent"));
                GBV_CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/WhatsApp Video/Sent"));
                GBV_CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/WhatsApp Profile/Sent"));
                GBV_CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/WhatsApp Voice Notes/Sent"));
                GBV_CleanMain.methodCleanMain(new File(Environment.getExternalStorageDirectory(), "WhatsApp/Media/GBV_Wallpaper/Sent"));
                PackageManager packageManager = getPackageManager();
                Method[] declaredMethods = packageManager.getClass().getDeclaredMethods();
                int length = declaredMethods.length;
                while (i < length) {
                    Method method = declaredMethods[i];
                    if (method.getName().equals("freeStorage")) {
                        try {
                            method.invoke(packageManager, 0L, null);
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        i++;
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return null;
        }

        public void onPostExecute(Void voids) {
        }
    }
}
