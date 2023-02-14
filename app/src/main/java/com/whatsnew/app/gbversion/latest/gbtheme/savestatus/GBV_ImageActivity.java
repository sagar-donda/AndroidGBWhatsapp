package com.whatsnew.app.gbversion.latest.gbtheme.savestatus;

import static com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Fragments.GBV_ImageFragment.GBVImageAdapter;
import static com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Fragments.GBV_ImageFragment.swipeRefreshLayout;
import static com.whatsnew.app.gbversion.latest.gbtheme.walkChat.GBV_BasicAccessibilityService.context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Utils.GBV_Common;

import java.io.File;

public class GBV_ImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gbv_image);

        final String path = getIntent().getStringExtra("picture");
        Bitmap org_bmp = BitmapFactory.decodeFile(path);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File fdelete = new File(path);
                if (fdelete.exists()) {
                    if (fdelete.delete()) {
                        onBackPressed();
                        GBVImageAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(true);
                    }
                }
            }
        });
        findViewById(R.id.shar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/jpg");
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + path));
                startActivity(Intent.createChooser(shareIntent, "Share image"));
            }
        });


        ImageView image = (ImageView) findViewById(R.id.img);

        image.setImageBitmap(org_bmp);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}