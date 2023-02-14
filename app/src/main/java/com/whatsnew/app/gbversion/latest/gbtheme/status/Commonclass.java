package com.whatsnew.app.gbversion.latest.gbtheme.status;

import android.os.Environment;

import java.io.File;

/* loaded from: classes.dex */
public class Commonclass {
    public static String APP_DIR = null;
    public static final String DIR_NAME = "StatusDownloaderNew";
    public static final String WHATSAPP_BUSINESS_PATH;
    public static final String WHATSAPP_START_DIR =  "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses";
    public static final String WHATSAPP_START_DIR_BUSINESS = "Android%2Fmedia%2Fcom.whatsapp.w4b%2FWhatsApp Business%2FMedia%2F.Statuses";
    public static final String WHATSAPP_START_DIR_BUSINESS_OLD = "WhatsApp Business%2FMedia%2F.Statuses";
    public static final String WHATSAPP_START_DIR_OLD = "WhatsApp%2FMedia%2F.Statuses";
    public static final String WHATSAPP_STATUS_PATH;
    public static final File STATUS_DIRECTORY = new File(Environment.getExternalStorageDirectory() + File.separator + "WhatsApp/Media/.Statuses");
    public static final File STATUS_DIRECTORY_NEW = new File(Environment.getExternalStorageDirectory() + File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses");
    public static final File GB_DIRECTORY = new File(Environment.getExternalStorageDirectory() + File.separator + "GBWhatsApp/Media/.Statuses");
    public static final File BUSINESS_DIRECTORY = new File(Environment.getExternalStorageDirectory() + File.separator + "WhatsApp Business/Media/.Statuses");
    public static final File BUSINESS_DIRECTORY_NEW = new File(Environment.getExternalStorageDirectory() + File.separator + "Android/media/com.whatsapp.w4b/WhatsApp Business/Media/.Statuses");
    public static final String WHATSAPP_STATUS_PATH_OLD = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/.Statuses";
    public static final String WHATSAPP_BUSINESS_PATH_OLD = Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Business/Media/.Statuses";

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp/Media/.Statuses");
        WHATSAPP_STATUS_PATH = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory().getAbsolutePath() + "/WhatsApp Business/Media/.Statuses");
        WHATSAPP_BUSINESS_PATH = sb2.toString();
    }
}
