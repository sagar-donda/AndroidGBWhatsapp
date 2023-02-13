package com.whatsnew.app.gbversion.latest.gbtheme.savestatus;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Adapter.GBV_PageAdapter;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Utils.GBV_Common;

import java.io.File;

public class GBV_StatusMainActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS = 1234;
    private static final String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final String MANAGE_EXTERNAL_STORAGE_PERMISSION = "android:manage_external_storage";
    static TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_mains);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.wp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent localIntent = getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                if (isPackageInstalled("com.whatsapp", getPackageManager())) {
                    try {
                        startActivity(localIntent);
                        return;
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp")));
                        return;
                    }
                } else {
                    Toast.makeText(GBV_StatusMainActivity.this, "Whatsapp not install in your device!", 0).show();
                    return;
                }

            }
        });

        tabLayout = findViewById(R.id.tabLayout);
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
        viewPager = findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.images)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.videos)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.saved_files)));

        PagerAdapter adapter = new GBV_PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
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
            public void onTabReselected(TabLayout.Tab tab) {
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

    public static void setTabBG(int tab1, int tab2, int tab3) {
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
    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    boolean checkStorageApi30() {
        AppOpsManager appOps = getApplicationContext().getSystemService(AppOpsManager.class);
        int mode = appOps.unsafeCheckOpNoThrow(
                MANAGE_EXTERNAL_STORAGE_PERMISSION,
                getApplicationContext().getApplicationInfo().uid,
                getApplicationContext().getPackageName()
        );
        return mode != AppOpsManager.MODE_ALLOWED;

    }

    private boolean arePermissionDenied() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return checkStorageApi30();
        }

        for (String permissions : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), permissions) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionDenied()) {
            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
            return;
        }

        GBV_Common.APP_DIR = Environment.getExternalStorageDirectory().getPath() +
                File.separator + "StatusDownloader";

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
