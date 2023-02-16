package com.whatsnew.app.gbversion.latest.gbtheme.status;

import android.os.Environment;
import java.io.File;


public class GBV_Commonclass {
    public static String APP_DIR = null;
    public static final String DIR_NAME = "StatusDownloaderNew";
    public static final String WHATSAPP_BUSINESS_PATH;
    public static final String WHATSAPP_START_DIR = "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses";
    public static final String WHATSAPP_START_DIR_OLD = "WhatsApp%2FMedia%2F.Statuses";
    public static final String WHATSAPP_STATUS_PATH;
    public static final String WHATSAPP_STATUS_PATH_OLD = Environment.getExternalStorageDirectory() + File.separator + "WhatsApp/Media/.Statuses";

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append("/Android/media/com.whatsapp/WhatsApp/Media/.Statuses");
        WHATSAPP_STATUS_PATH = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        sb2.append(Environment.getExternalStorageDirectory());
        sb2.append("/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/.Statuses");
        WHATSAPP_BUSINESS_PATH = sb2.toString();
    }
}
