package com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Fragments.GBV_SavedFilesFragment;

public class GBV_GalleryAdapter extends FragmentPagerAdapter {

    private final int totalTabs;

    public GBV_GalleryAdapter(@NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        return new GBV_SavedFilesFragment();

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
