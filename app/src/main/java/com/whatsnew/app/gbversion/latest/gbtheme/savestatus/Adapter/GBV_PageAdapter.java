package com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Fragments.GBV_ImageFragment;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Fragments.GBV_SavedFilesFragment;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Fragments.GBV_VideoFragment;

public class GBV_PageAdapter extends FragmentPagerAdapter {

    private final int totalTabs;

    public GBV_PageAdapter(@NonNull FragmentManager fm, int totalTabs) {
        super(fm);
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        if (position == 1) {
            return new GBV_VideoFragment();
        }else if (position == 2)
            return new GBV_SavedFilesFragment();
        return new GBV_ImageFragment();

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
