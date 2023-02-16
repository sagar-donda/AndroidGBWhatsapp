package com.whatsnew.app.gbversion.latest.gbtheme.status;

import android.content.Context;
import android.database.Cursor;
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


public class GBV_WhatsappImagesFragmentclass extends Fragment {
    private GBV_WhatsappImagesAdapterclass adapter;
    private Context context;
    private ArrayList<GBV_WhatsappStatusModelclass> dataHolder = new ArrayList<>();
    private RecyclerView recyclerView;
    private String uri;

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override 
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.gbv_fragment_whatsapp_images_resource, viewGroup, false);
        this.context = inflate.getContext();
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.rv_whatsapp_images);
        this.recyclerView = recyclerView;
        recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        if (GBV_SharePrefHelperclass.getInstance(getContext(), GBV_SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).getValue(GBV_SharePrefHelperclass.Keys.USER_DETAILS) != null) {
            this.uri = GBV_SharePrefHelperclass.getInstance(getContext(), GBV_SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).getValue(GBV_SharePrefHelperclass.Keys.USER_DETAILS);
            Log.d("TAGuri", "onCreateView: " + this.uri);
            if (this.uri != "") {
                execute();
            } else {
                Log.d("checkStatus", "no directory found");
                Toast.makeText(getActivity(), (int) R.string.no_file_found, 0).show();
            }
        } else {
            File file = new File(GBV_Commonclass.WHATSAPP_STATUS_PATH);
            File file2 = new File(GBV_Commonclass.WHATSAPP_STATUS_PATH_OLD);
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
            GBV_WhatsappStatusModelclass whatsappStatusModelclass = new GBV_WhatsappStatusModelclass(String.valueOf(Uri.fromFile(file2)), file2.getName(), file2.getAbsolutePath());
            if (whatsappStatusModelclass.getName().endsWith(".jpg")) {
                Log.d("checkStatusw", "execute: " + whatsappStatusModelclass);
                this.dataHolder.add(whatsappStatusModelclass);
            }
        }
        if (this.dataHolder.size() > 0) {
            GBV_WhatsappImagesAdapterclass whatsappImagesAdapterclass = new GBV_WhatsappImagesAdapterclass(this.dataHolder,this.context);
            this.adapter = whatsappImagesAdapterclass;
            this.recyclerView.setAdapter(whatsappImagesAdapterclass);
            this.adapter.notifyDataSetChanged();
        }
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
            GBV_WhatsappStatusModelclass whatsappStatusModelclass = new GBV_WhatsappStatusModelclass(documentFile.getUri().toString(), documentFile.getName(), documentFile.getUri().getPath());
            if (whatsappStatusModelclass.getName().endsWith(".jpg")) {
                Log.d("checkStatusw", "execute: " + whatsappStatusModelclass);
                this.dataHolder.add(whatsappStatusModelclass);
            }
        }
        if (this.dataHolder.size() > 0) {
            GBV_WhatsappImagesAdapterclass whatsappImagesAdapterclass = new GBV_WhatsappImagesAdapterclass(this.dataHolder,this.context);
            this.adapter = whatsappImagesAdapterclass;
            this.recyclerView.setAdapter(whatsappImagesAdapterclass);
            this.adapter.notifyDataSetChanged();
        }
    }

    private String getFilePath(Uri uri) {
        String str;
        Cursor query = this.context.getContentResolver().query(uri, null, null, null, null);
        if (query == null) {
            return uri.getPath();
        }
        query.moveToFirst();
        try {
            str = query.getString(query.getColumnIndex("_data"));
        } catch (Exception e) {
            Log.d("TAGcursor", "getFilePath: " + e.getLocalizedMessage());
            str = null;
        }
        query.close();
        return str;
    }
}
