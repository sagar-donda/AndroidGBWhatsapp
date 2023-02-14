package com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Adapter.GBV_ImageAdapter;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Models.GBV_Status;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Utils.GBV_Common;

public class GBV_ImageFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private final List<GBV_Status> imagesList = new ArrayList<>();
    private final Handler handler = new Handler();
    public static GBV_ImageAdapter GBVImageAdapter;
    private RelativeLayout container;
    public static SwipeRefreshLayout swipeRefreshLayout;
    private TextView messageTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gbv_fragment_images, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerViewImage);
        progressBar = view.findViewById(R.id.prgressBarImage);
        container = view.findViewById(R.id.image_container);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        messageTextView = view.findViewById(R.id.messageTextImage);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(requireActivity(), android.R.color.holo_orange_dark)
                , ContextCompat.getColor(requireActivity(), android.R.color.holo_green_dark),
                ContextCompat.getColor(requireActivity(), R.color.colorPrimary),
                ContextCompat.getColor(requireActivity(), android.R.color.holo_blue_dark));

        swipeRefreshLayout.setOnRefreshListener(this::getStatus);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), GBV_Common.GRID_COUNT));
        recyclerView.setAdapter(GBVImageAdapter);
        getStatus();

    }

    private void getStatus() {
        if (GBV_Common.STATUS_DIRECTORY.exists()) {
            execute(GBV_Common.STATUS_DIRECTORY);

        } else if (GBV_Common.STATUS_DIRECTORY_NEW.exists()) {
            execute(GBV_Common.STATUS_DIRECTORY_NEW);

        } else {
            messageTextView.setVisibility(View.VISIBLE);
            messageTextView.setText(R.string.cant_find_whatsapp_dir);
            Toast.makeText(getActivity(), getString(R.string.cant_find_whatsapp_dir), Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    private void execute(File wAFolder) {
        new Thread(() -> {
            File[] statusFiles;
            statusFiles = wAFolder.listFiles();
            imagesList.clear();

            if (statusFiles != null && statusFiles.length > 0) {

                Arrays.sort(statusFiles);
                for (File file : statusFiles) {
                    GBV_Status GBVStatus = new GBV_Status(file, file.getName(), file.getAbsolutePath());

                    if (!GBVStatus.isVideo() && GBVStatus.getTitle().endsWith(".jpg")) {
                        imagesList.add(GBVStatus);
                    }

                }

                handler.post(() -> {

                    if (imagesList.size() <= 0) {
                        messageTextView.setVisibility(View.VISIBLE);
                        messageTextView.setText(R.string.no_files_found);
                    } else {
                        messageTextView.setVisibility(View.GONE);
                        messageTextView.setText("");
                    }

                    GBVImageAdapter = new GBV_ImageAdapter(imagesList, container);
                    recyclerView.setAdapter(GBVImageAdapter);
                    GBVImageAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                });

            } else {

                handler.post(() -> {
                    progressBar.setVisibility(View.GONE);
                    messageTextView.setVisibility(View.VISIBLE);
                    messageTextView.setText(R.string.no_files_found);
                    Toast.makeText(getActivity(), getString(R.string.no_files_found), Toast.LENGTH_SHORT).show();
                });

            }
            swipeRefreshLayout.setRefreshing(false);
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();
        GBVImageAdapter = new GBV_ImageAdapter(imagesList, container);
        recyclerView.setAdapter(GBVImageAdapter);
        GBVImageAdapter.notifyDataSetChanged();
//        swipeRefreshLayout.setRefreshing(true);
    }
}
