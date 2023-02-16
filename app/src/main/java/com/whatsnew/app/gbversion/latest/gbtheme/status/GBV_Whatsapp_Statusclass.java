package com.whatsnew.app.gbversion.latest.gbtheme.status;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Ad_class;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Constant;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

import java.util.ArrayList;
import java.util.List;


public class GBV_Whatsapp_Statusclass extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
   
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.gbv_activity_whatsapp_status_resource);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.wp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(getPackageManager().getLaunchIntentForPackage("com.whatsapp"));
                } catch (Exception e) {
                    Toast.makeText(GBV_Whatsapp_Statusclass.this,  R.string.whatsapp_not_install, 0).show();
                    e.printStackTrace();
                }
            }
        });
        Ad_class.all_banner(GBV_Whatsapp_Statusclass.this, (LinearLayout) findViewById(R.id.adView));
        initialiseViews();
        setupViewPager(this.viewPager);
        this.tabLayout.setupWithViewPager(this.viewPager);

        setTabBG(R.drawable.tab1, R.drawable.tab2, R.drawable.tab3);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tabLayout.getSelectedTabPosition() == 0) {
                    setTabBG(R.drawable.tab1, R.drawable.tab2, R.drawable.tab3);
                } else if (tabLayout.getSelectedTabPosition() == 1) {
                    setTabBG(R.drawable.tab4, R.drawable.tabs5, R.drawable.tab3);
                } else {
                    setTabBG(R.drawable.tab4, R.drawable.tab2, R.drawable.tab6);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tabLayout.getSelectedTabPosition() == 0) {
                    setTabBG(R.drawable.tab1, R.drawable.tab2, R.drawable.tab3);
                } else if (tabLayout.getSelectedTabPosition() == 1) {
                    setTabBG(R.drawable.tab4, R.drawable.tabs5, R.drawable.tab3);
                } else {
                    setTabBG(R.drawable.tab4, R.drawable.tab2, R.drawable.tab6);
                }
            }
        });

    }

    private void setTabBG(int tab1, int tab2, int tab3) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ViewGroup tabStrip = (ViewGroup) tabLayout.getChildAt(0);
            View tabView1 = tabStrip.getChildAt(0);
            View tabView2 = tabStrip.getChildAt(1);
            View tabView3 = tabStrip.getChildAt(2);
            if (tabView1 != null) {
                ViewCompat.setBackground(tabView1, AppCompatResources.getDrawable(tabView1.getContext(), tab1));
            }
            if (tabView2 != null) {
                ViewCompat.setBackground(tabView2, AppCompatResources.getDrawable(tabView2.getContext(), tab2));
            }
            if (tabView3 != null) {
                ViewCompat.setBackground(tabView3, AppCompatResources.getDrawable(tabView3.getContext(), tab3));
            }
        }
    }

    public void initialiseViews() {
        this.tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        this.viewPager = (ViewPager) findViewById(R.id.view_pager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.add(new GBV_WhatsappImagesFragmentclass(), getString(R.string.tabimage));
        viewPagerAdapter.add(new GBV_WhatsappVideoFragmentclass(), getString(R.string.tabvideo));
        viewPagerAdapter.add(new GBV_SavedFilesFragment(), getString(R.string.save));
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (Constant.AD_STATUS == "true") {
            Ad_class.adCounter++;
            Ad_class.showInterAd(this, new Ad_class.onLisoner() {
                @Override
                public void click() {
                    GBV_Whatsapp_Statusclass.super.onBackPressed();
                }
            });
        } else {
            super.onBackPressed();
        }
    }

   
    
    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;
        private List<String> titleList;

        private ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            this.fragmentList = new ArrayList();
            this.titleList = new ArrayList();
        }

        @Override
        public int getItemPosition(Object obj) {
            return -2;
        }

        void add(Fragment fragment, String str) {
            this.fragmentList.add(fragment);
            this.titleList.add(str);
        }

        @Override 
        public Fragment getItem(int i) {
            return this.fragmentList.get(i);
        }

        @Override
        public CharSequence getPageTitle(int i) {
            return this.titleList.get(i);
        }

        @Override
        public int getCount() {
            return this.fragmentList.size();
        }
    }
}
