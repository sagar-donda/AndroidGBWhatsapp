package com.whatsnew.app.gbversion.latest.gbtheme.status;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.whatsnew.app.gbversion.latest.gbtheme.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes.dex */
public class SavedFilesActivityclass extends AppCompatActivity {
    private SavedFilesAdapterclass adapter;
    private File appDirFile;
    private ArrayList<WhatsappStatusModelclass> dataHolder = new ArrayList<>();
    private RecyclerView rvSavedFiles;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_saved_files_resouce);
        initView();
        this.rvSavedFiles.setHasFixedSize(true);
        this.rvSavedFiles.setLayoutManager(new GridLayoutManager(this, 2));
        this.appDirFile = new File(Commonclass.APP_DIR);
        Log.d("TAGappDir", "onCreate: " + this.appDirFile.getAbsolutePath());
        if (this.appDirFile.exists()) {
            executeAppDirectory(this.appDirFile);
        } else {
            Toast.makeText(this, (int) R.string.no_file_found, 0).show();
        }
    }

    private void executeAppDirectory(File file) {
        File[] listFiles = file.listFiles();
        Log.d("TAGappDir", "list: " + file.listFiles());
        this.dataHolder.clear();
        if (listFiles == null || listFiles.length <= 0) {
            Log.d("checkStatusw", "no files found");
            Toast.makeText(this, (int) R.string.no_file_found, 0).show();
            return;
        }
        Arrays.sort(listFiles);
        for (File file2 : listFiles) {
            this.dataHolder.add(new WhatsappStatusModelclass(Uri.parse(new File(file2.getPath()).toString()).toString(), file2.getName(), file2.getAbsolutePath()));
        }
        if (this.dataHolder.size() > 0) {
            SavedFilesAdapterclass savedFilesAdapterclass = new SavedFilesAdapterclass(this.dataHolder, this);
            this.adapter = savedFilesAdapterclass;
            this.rvSavedFiles.setAdapter(savedFilesAdapterclass);
            this.adapter.notifyDataSetChanged();
        }
    }

    private void loadImageFromStorage(String str, String str2) {
        try {
            BitmapFactory.decodeStream(new FileInputStream(new File(str, str2)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        this.rvSavedFiles = (RecyclerView) findViewById(R.id.rv_saved_files);
    }
}
