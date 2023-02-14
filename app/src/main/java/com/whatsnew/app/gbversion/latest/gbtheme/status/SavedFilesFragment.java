package com.whatsnew.app.gbversion.latest.gbtheme.status;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.whatsnew.app.gbversion.latest.gbtheme.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class SavedFilesFragment extends Fragment {
    private SavedFilesAdapterclass adapter;
    private File appDirFile;
    private ArrayList<WhatsappStatusModelclass> dataHolder = new ArrayList<>();
    private RecyclerView rvSavedFiles;

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.activity_saved_files_resouce, viewGroup, false);
        this.rvSavedFiles = (RecyclerView) inflate.findViewById(R.id.rv_saved_files);
        this.rvSavedFiles.setHasFixedSize(true);
        this.rvSavedFiles.setLayoutManager(new GridLayoutManager(getContext(), 2));
        this.appDirFile = new File(Commonclass.APP_DIR);
        Log.d("TAGappDir", "onCreate: " + this.appDirFile.getAbsolutePath());
        if (this.appDirFile.exists()) {
            executeAppDirectory(this.appDirFile);
        } else {
            Toast.makeText(getContext(), (int) R.string.no_file_found, 0).show();

        }

        return inflate;
    }

    private void executeAppDirectory(File file) {
        File[] listFiles = file.listFiles();
        Log.d("TAGappDir", "list: " + file.listFiles());
        this.dataHolder.clear();
        if (listFiles == null || listFiles.length <= 0) {
            Log.d("checkStatusw", "no files found");
            Toast.makeText(getContext(), (int) R.string.no_file_found, 0).show();
            return;
        }
        Arrays.sort(listFiles);
        for (File file2 : listFiles) {
            this.dataHolder.add(new WhatsappStatusModelclass(Uri.parse(new File(file2.getPath()).toString()).toString(), file2.getName(), file2.getAbsolutePath()));
        }
        if (this.dataHolder.size() > 0) {
            SavedFilesAdapterclass savedFilesAdapterclass = new SavedFilesAdapterclass(this.dataHolder, getContext());
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
}
