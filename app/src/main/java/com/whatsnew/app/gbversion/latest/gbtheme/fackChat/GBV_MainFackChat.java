package com.whatsnew.app.gbversion.latest.gbtheme.fackChat;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.service.GBV_BaseActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class GBV_MainFackChat extends GBV_BaseActivity {
    LinearLayout Camera;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private static class btnCamListner implements OnClickListener {
        public void onClick(View v) {
        }
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @NonNull
        public Fragment getItem(int position) {
            return this.mFragmentList.get(position);
        }

        public int getCount() {
            return this.mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            this.mFragmentList.add(fragment);
            this.mFragmentTitleList.add(title);
        }

        public CharSequence getPageTitle(int position) {
            return this.mFragmentTitleList.get(position);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_main_fack_chat);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        this.viewPager = findViewById(R.id.viewpager);
        setupViewPager(this.viewPager);
        this.tabLayout = findViewById(R.id.tabs);
        this.tabLayout.setupWithViewPager(this.viewPager);
        this.Camera = findViewById(R.id.camera);
        this.Camera.setOnClickListener(new btnCamListner());
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new GBV_ChatsFragment(GBV_MainFackChat.this), "CHATS");
        adapter.addFrag(new GBV_StausFragment(GBV_MainFackChat.this), "STATUS");
        adapter.addFrag(new GBV_CallsFragment(GBV_MainFackChat.this), "CALLS");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
      super.onBackPressed();
    }
}
