package com.whatsnew.app.gbversion.latest.gbtheme.status;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

import java.io.File;

/* loaded from: classes.dex */
public class SingleMediaScannerclass implements MediaScannerConnection.MediaScannerConnectionClient {
    private final File mFile;
    private final MediaScannerConnection mMs;

    public SingleMediaScannerclass(Context context, File file) {
        this.mFile = file;
        MediaScannerConnection mediaScannerConnection = new MediaScannerConnection(context, this);
        this.mMs = mediaScannerConnection;
        mediaScannerConnection.connect();
    }

    @Override // android.media.MediaScannerConnection.MediaScannerConnectionClient
    public void onMediaScannerConnected() {
        this.mMs.scanFile(this.mFile.getAbsolutePath(), null);
    }

    @Override // android.media.MediaScannerConnection.OnScanCompletedListener
    public void onScanCompleted(String str, Uri uri) {
        this.mMs.disconnect();
    }
}
