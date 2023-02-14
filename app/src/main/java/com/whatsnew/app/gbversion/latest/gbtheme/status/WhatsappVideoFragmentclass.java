package com.whatsnew.app.gbversion.latest.gbtheme.status;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.whatsnew.app.gbversion.latest.gbtheme.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes.dex */
public class WhatsappVideoFragmentclass extends Fragment {
    private WhatsappVideoAdapterclass adapter;
    private Context context;
    private ArrayList<WhatsappStatusModelclass> dataHolder = new ArrayList<>();
    private RecyclerView recyclerView;
    private String uri;

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_whatsapp_video_resource, viewGroup, false);
        this.context = inflate.getContext();
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.rv_whatsapp_video);
        this.recyclerView = recyclerView;
        recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        if (SharePrefHelperclass.getInstance(getContext(), SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).getValue(SharePrefHelperclass.Keys.USER_DETAILS) != null) {
            this.uri = SharePrefHelperclass.getInstance(getContext(), SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).getValue(SharePrefHelperclass.Keys.USER_DETAILS);
            Log.d("TAGuri", "onCreateView: " + this.uri);
            if (this.uri != "") {
                execute();
            } else {
                Log.d("checkStatus", "no directory found");
                Toast.makeText(getActivity(), (int) R.string.no_file_found, 0).show();
            }
        } else {
            File file = new File(Commonclass.WHATSAPP_STATUS_PATH);
            File file2 = new File(Commonclass.WHATSAPP_STATUS_PATH_OLD);
            if (file.exists()) {
                executeThroughFile(file);
            } else if (file2.exists()) {
                executeThroughFile(file2);
            } else {
                Log.d("checkStatus", "no directory found");
                Toast.makeText(getActivity(), (int) R.string.no_file_found, 0).show();
            }
        }
        return inflate;
    }

    private void execute() {
        DocumentFile[] listFiles = DocumentFile.fromTreeUri(getContext(), Uri.parse(this.uri)).listFiles();
        Log.d("TAGlistfiles", "execute: " + listFiles.length);
        this.dataHolder.clear();
        if (listFiles == null || listFiles.length <= 0) {
            Log.d("checkStatusw", "no files found");
            Toast.makeText(getContext(), (int) R.string.no_file_found, 0).show();
            return;
        }
        for (DocumentFile documentFile : listFiles) {
            WhatsappStatusModelclass whatsappStatusModelclass = new WhatsappStatusModelclass(documentFile.getUri().toString(), documentFile.getName(), documentFile.getUri().getPath());
            if (whatsappStatusModelclass.getName().endsWith(".mp4")) {
                Log.d("checkStatusw", "execute: " + whatsappStatusModelclass);
                this.dataHolder.add(whatsappStatusModelclass);
            }
        }
        if (this.dataHolder.size() > 0) {
            WhatsappVideoAdapterclass whatsappVideoAdapterclass = new WhatsappVideoAdapterclass(this.dataHolder, this.context);
            this.adapter = whatsappVideoAdapterclass;
            this.recyclerView.setAdapter(whatsappVideoAdapterclass);
            this.adapter.notifyDataSetChanged();
        }
    }

    private void executeThroughFile(File file) {
        File[] listFiles = file.listFiles();
        this.dataHolder.clear();
        if (listFiles == null || listFiles.length <= 0) {
            Log.d("checkStatusw", "no files found");
            Toast.makeText(getContext(), (int) R.string.no_file_found, 0).show();
            return;
        }
        Arrays.sort(listFiles);
        for (File file2 : listFiles) {
            WhatsappStatusModelclass whatsappStatusModelclass = new WhatsappStatusModelclass(String.valueOf(Uri.fromFile(file2)), file2.getName(), file2.getAbsolutePath());
            if (whatsappStatusModelclass.getName().endsWith(".mp4")) {
                Log.d("checkStatusw", "execute: " + whatsappStatusModelclass);
                this.dataHolder.add(whatsappStatusModelclass);
            }
        }
        if (this.dataHolder.size() > 0) {
            WhatsappVideoAdapterclass whatsappVideoAdapterclass = new WhatsappVideoAdapterclass(this.dataHolder, getContext());
            this.adapter = whatsappVideoAdapterclass;
            this.recyclerView.setAdapter(whatsappVideoAdapterclass);
            this.adapter.notifyDataSetChanged();
        }
    }
}
