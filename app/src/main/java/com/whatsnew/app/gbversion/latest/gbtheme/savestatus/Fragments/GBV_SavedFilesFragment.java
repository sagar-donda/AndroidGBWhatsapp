package com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Adapter.GBV_FilesAdapter;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Models.GBV_Status;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Utils.GBV_Common;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GBV_SavedFilesFragment extends Fragment {

    private final List<GBV_Status> savedFilesList = new ArrayList<>();
    private final Handler handler = new Handler();
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private GBV_FilesAdapter GBVFilesAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView no_files_found;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gbv_fragment_saved_files, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewFiles);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayoutFiles);
        progressBar = view.findViewById(R.id.progressBar);
        no_files_found = view.findViewById(R.id.no_files_found);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(requireActivity(), android.R.color.holo_orange_dark)
                , ContextCompat.getColor(requireActivity(), android.R.color.holo_green_dark),
                ContextCompat.getColor(requireActivity(), R.color.colorPrimary),
                ContextCompat.getColor(requireActivity(), android.R.color.holo_blue_dark));

        swipeRefreshLayout.setOnRefreshListener(this::getFiles);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), GBV_Common.GRID_COUNT));

        getFiles();

    }

    private void getFiles() {

        final File app_dir = new File(GBV_Common.APP_DIR);

        if (app_dir.exists()) {

            no_files_found.setVisibility(View.GONE);

            new Thread(() -> {
                File[] savedFiles;
                savedFiles = app_dir.listFiles();
                savedFilesList.clear();

                if (savedFiles != null && savedFiles.length > 0) {

                    Arrays.sort(savedFiles);
                    for (File file : savedFiles) {
                        GBV_Status GBVStatus = new GBV_Status(file, file.getName(), file.getAbsolutePath());

                        savedFilesList.add(GBVStatus);
                    }

                    handler.post(() -> {

                        GBVFilesAdapter = new GBV_FilesAdapter(savedFilesList);
                        recyclerView.setAdapter(GBVFilesAdapter);
                        GBVFilesAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                    });

                } else {

                    handler.post(() -> {
                        progressBar.setVisibility(View.GONE);
                        no_files_found.setVisibility(View.VISIBLE);
                    });

                }
                swipeRefreshLayout.setRefreshing(false);
            }).start();

        } else {
            no_files_found.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

    }

}
