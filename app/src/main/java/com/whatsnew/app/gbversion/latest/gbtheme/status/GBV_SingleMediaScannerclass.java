package com.whatsnew.app.gbversion.latest.gbtheme.status;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import java.io.File;


public class GBV_SingleMediaScannerclass implements MediaScannerConnection.MediaScannerConnectionClient {
    private final File mFile;
    private final MediaScannerConnection mMs;

    public GBV_SingleMediaScannerclass(Context context, File file) {
        this.mFile = file;
        MediaScannerConnection mediaScannerConnection = new MediaScannerConnection(context, this);
        this.mMs = mediaScannerConnection;
        mediaScannerConnection.connect();
    }

    @Override
    public void onMediaScannerConnected() {
        this.mMs.scanFile(this.mFile.getAbsolutePath(), null);
    }

    @Override
    public void onScanCompleted(String str, Uri uri) {
        this.mMs.disconnect();
    }
}
