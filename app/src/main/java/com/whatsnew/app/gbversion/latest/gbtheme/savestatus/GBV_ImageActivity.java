package com.whatsnew.app.gbversion.latest.gbtheme.savestatus;

import static com.whatsnew.app.gbversion.latest.gbtheme.walkChat.GBV_BasicAccessibilityService.context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Ad_class;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Constant;
import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.chating.GBV_ChattingActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Models.GBV_Status;

import java.util.List;

public class GBV_ImageActivity extends AppCompatActivity {
    private GBV_Status imageModel;
    private List<GBV_Status> imagesList;
    private RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gbv_image);
        context = this;
        final String path = getIntent().getStringExtra("picture");
        Bitmap org_bmp = BitmapFactory.decodeFile(path);
//        final GBV_Status GBVStatus =  imagesList.get(Integer.parseInt("picture"));
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                File fdelete = new File(path);
//                if (fdelete.exists()) {
//                    if (fdelete.delete()) {
//                        Toast.makeText(GBV_ImageActivity.this, "123", Toast.LENGTH_SHORT).show();
//                        onBackPressed();
//                    } else {
//                        Toast.makeText(GBV_ImageActivity.this, "abc", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
//        findViewById(R.id.shar).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("image/jpg");
//                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + path));
//                startActivity(Intent.createChooser(shareIntent, "Share image"));
//            }
//        }); findViewById(R.id.down).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                GBV_Common.copyFile(GBVStatus, context, container);
//            }
//        });


        ImageView image = (ImageView) findViewById(R.id.img);

        image.setImageBitmap(org_bmp);

    }

    @Override
    public void onBackPressed() {
        if (Constant.AD_STATUS == "true") {
            Ad_class.adCounter++;
            Ad_class.showInterAd(this, new Ad_class.onLisoner() {
                @Override
                public void click() {
                    GBV_ImageActivity.super.onBackPressed();
                }
            });
        } else {
            super.onBackPressed();
        }
    }
}