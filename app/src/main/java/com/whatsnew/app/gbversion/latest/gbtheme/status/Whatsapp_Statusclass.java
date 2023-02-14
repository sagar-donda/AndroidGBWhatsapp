package com.whatsnew.app.gbversion.latest.gbtheme.status;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class Whatsapp_Statusclass extends AppCompatActivity {
    public static FrameLayout main_container3;
    LinearLayout sliderDotspanel;
    String strQpath;
    String which;
    String strNormalPath;
    LinearLayout status;
    LinearLayout more;
    LinearLayout download;
    LinearLayout setting;
    ImageView statusu;
    ImageView moreu;
    ImageView downloadu;
    ImageView settingu;
    ImageView statuss;
    ImageView mores;
    ImageView downloads;
    ImageView settings;
    TextView statustu;
    TextView moretu;
    TextView downloadtu;
    TextView settingtu;
    TextView statusts;
    TextView morets;
    TextView downloadts;
    TextView settingts;
    Fragment fragment33;
    boolean showingFirst = true;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPager viewPager1;
    private int dotscount;
    private ImageView[] dots;

    boolean isPermissionGranted;
    String[] permissions = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};

    private static final int REQUEST_PERMISSIONS = 1234;
    private static final String[] PERMISSIONS = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final String MANAGE_EXTERNAL_STORAGE_PERMISSION = "android:manage_external_storage";


    @Override
    // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_whatsapp_status_resource);

        Commonclass.APP_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator + Commonclass.DIR_NAME;
        StringBuilder sb = new StringBuilder();
        sb.append("onCreate: ");
        sb.append(Commonclass.APP_DIR);
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).commit();
        checkPermissions();

        this.tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        this.viewPager = (ViewPager) findViewById(R.id.view_pager);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        this.main_container3 = findViewById(R.id.main_container3);

        setupViewPager(this.viewPager);
        this.tabLayout.setupWithViewPager(this.viewPager);

//        findViewById(R.id.download).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                statusu.setVisibility(View.VISIBLE);
//                statustu.setVisibility(View.VISIBLE);
//                statuss.setVisibility(View.GONE);
//                statusts.setVisibility(View.GONE);
//
//                moreu.setVisibility(View.VISIBLE);
//                moretu.setVisibility(View.VISIBLE);
//                mores.setVisibility(View.GONE);
//                morets.setVisibility(View.GONE);
//
//                downloadu.setVisibility(View.GONE);
//                downloadtu.setVisibility(View.GONE);
//                downloads.setVisibility(View.VISIBLE);
//                downloadts.setVisibility(View.VISIBLE);
//
//                settingu.setVisibility(View.VISIBLE);
//                settingtu.setVisibility(View.VISIBLE);
//                settings.setVisibility(View.GONE);
//                settingts.setVisibility(View.GONE);
//
//
//                Whatsapp_Statusclass.this.fragment33 = new SavedFilesFragment();
//                Whatsapp_Statusclass c_Crick_MultiStart_ActI = Whatsapp_Statusclass.this;
//                loadFragment3(c_Crick_MultiStart_ActI.fragment33, null);
//            }
//        });

        setTabBG(R.drawable.tab1, R.drawable.tab2);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                setTabTypeface(tab, ResourcesCompat.getFont(tab.view.getContext(), R.font.mid));

                if (tabLayout.getSelectedTabPosition() == 0) {
                    setTabBG(R.drawable.tab1, R.drawable.tab2);
                } else if (tabLayout.getSelectedTabPosition() == 1) {
                    setTabBG(R.drawable.tab2, R.drawable.tab1);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setTabTypeface(tab, ResourcesCompat.getFont(tab.view.getContext(), R.font.mid));
                if (tabLayout.getSelectedTabPosition() == 0) {
                    setTabBG(R.drawable.tab1, R.drawable.tab2);
                } else if (tabLayout.getSelectedTabPosition() == 1) {
                    setTabBG(R.drawable.tab2, R.drawable.tab1);
                }
            }
        });
    }

    public boolean loadFragment3(Fragment fragment, String str) {
        if (fragment == null) {
            return false;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container3, fragment, str).commit();
        return true;
    }

    private void setTabTypeface(TabLayout.Tab tab, Typeface typeface) {

        for (int i = 0; i < tab.view.getChildCount(); i++) {
            View tabViewChild = tab.view.getChildAt(i);
            if (tabViewChild instanceof TextView)
                ((TextView) tabViewChild).setTypeface(typeface);
        }
    }

    private void setTabBG(int tab1, int tab2) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ViewGroup tabStrip = (ViewGroup) tabLayout.getChildAt(0);
            View tabView1 = tabStrip.getChildAt(0);
            View tabView2 = tabStrip.getChildAt(1);
            if (tabView1 != null) {
                ViewCompat.setBackground(tabView1, AppCompatResources.getDrawable(tabView1.getContext(), tab1));
            }
            if (tabView2 != null) {
                ViewCompat.setBackground(tabView2, AppCompatResources.getDrawable(tabView2.getContext(), tab2));
            }

        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.add(new WhatsappImagesFragmentclass(), getString(R.string.tabimage));
        viewPagerAdapter.add(new WhatsappVideoFragmentclass(), getString(R.string.tabvideo));
        viewPager.setAdapter(viewPagerAdapter);
    }
    private void checkPermissions() {
        ArrayList arrayList = new ArrayList();
        String[] strArr = this.permissions;
        for (String str : strArr) {
            if (ContextCompat.checkSelfPermission(this, str) != 0) {
                arrayList.add(str);
            }
        }
        if (!arrayList.isEmpty()) {
            execute(Commonclass.WHATSAPP_START_DIR);
            this.isPermissionGranted = false;
            Log.d("permission", "" + arrayList.toString());
            ActivityCompat.requestPermissions(this, (String[]) arrayList.toArray(new String[arrayList.size()]), 10);
            return;
        }
        this.isPermissionGranted = true;
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 10) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                this.isPermissionGranted = false;
            } else {
                this.isPermissionGranted = true;
            }
        }
    }

    private void execute(String str) {
        StorageManager storageManager = (StorageManager) getSystemService("storage");
//        if (Build.VERSION.SDK_INT >= 29) {
        Intent createOpenDocumentTreeIntent = storageManager.getPrimaryStorageVolume().createOpenDocumentTreeIntent();
        String uri = ((Uri) createOpenDocumentTreeIntent.getParcelableExtra("android.provider.extra.INITIAL_URI")).toString();
        Log.d("TAG", "INITIAL_URI scheme: " + uri);
        String replace = uri.replace("/root/", "/document/");
        Uri parse = Uri.parse(replace + "%3A" + str);
        createOpenDocumentTreeIntent.putExtra("android.provider.extra.INITIAL_URI", parse);
        Log.d("TAG", "uri: " + parse.toString());
//            try {
        startActivityForResult(createOpenDocumentTreeIntent, 6);
//            } catch (ActivityNotFoundException unused) {
//            }
//        } else {
//            startActivity(new Intent(this, Whatsapp_Statusclass.class));
//        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        int i3 = -1;
        if (i2 == -1 && i == 6 && intent != null) {
            Uri data = intent.getData();
            if (data.getPath().endsWith(".Statuses")) {
                int flags = intent.getFlags() & 3;
                getContentResolver().takePersistableUriPermission(data, flags);
                if (flags > 0) {
                    SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_DETAILS, data.toString());
                    SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_KYC, true);
                    startActivity(new Intent(this, Whatsapp_Statusclass.class));
                } else {
                    Toast.makeText(this, (int) R.string.please_allow_function, 0).show();
                    SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_DETAILS, "");
                    SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_KYC, false);
                }
            } else {
                Toast.makeText(this, (int) R.string.wrong_path, 0).show();
                Log.d("TAGmycheck", "onActivityResult: wrong path dialog");
                SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_DETAILS, "");
                SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_KYC, false);
            }
            i3 = -1;
        }
        if (i2 == i3 && i == 7 && intent != null) {
            Uri data2 = intent.getData();
            if (data2.getPath().endsWith(".Statuses")) {
                int flags2 = intent.getFlags() & 3;
                getContentResolver().takePersistableUriPermission(data2, flags2);
                if (flags2 > 0) {
                    SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_DETAILS_BUSINESS, data2.toString());
                    SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_KYC_BUSINESS, true);
                    startActivity(new Intent(this, Whatsapp_Statusclass.class));
                    return;
                }
                Toast.makeText(this, (int) R.string.please_allow_function, 0).show();
                SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_DETAILS_BUSINESS, "");
                SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_KYC_BUSINESS, false);
                return;
            }
            Toast.makeText(this, (int) R.string.wrong_path, 0).show();
            Log.d("TAGmycheck", "onActivityResult: wrong path dialog");
            SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_DETAILS, "");
            SharePrefHelperclass.getInstance(this, SharePrefHelperclass.PrefFiles.USER_DETAILS_PREF).add(SharePrefHelperclass.Keys.USER_KYC, false);
        }
    }
    private boolean authenticate() {
        return Build.VERSION.SDK_INT <= 30 || ActivityCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && arePermissionDenied()) {
            requestPermissions(PERMISSIONS, REQUEST_PERMISSIONS);
            return;
        }

        Commonclass.APP_DIR = Environment.getExternalStorageDirectory().getPath() +
                File.separator + "StatusDownloader";
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
    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;
        private List<String> titleList;

        private ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            this.fragmentList = new ArrayList();
            this.titleList = new ArrayList();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getItemPosition(Object obj) {
            return -2;
        }

        void add(Fragment fragment, String str) {
            this.fragmentList.add(fragment);
            this.titleList.add(str);
        }

        @Override // androidx.fragment.app.FragmentPagerAdapter
        public Fragment getItem(int i) {
            return this.fragmentList.get(i);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public CharSequence getPageTitle(int i) {
            return this.titleList.get(i);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return this.fragmentList.size();
        }
    }
}
