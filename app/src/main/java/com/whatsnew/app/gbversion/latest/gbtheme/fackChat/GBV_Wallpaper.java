package com.whatsnew.app.gbversion.latest.gbtheme.fackChat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.service.GBV_BaseActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class GBV_Wallpaper extends GBV_BaseActivity implements OnClickListener {
    public static ImageView ivUserSavedPhoto;
    public static Uri selectedImageUri;
    LinearLayout backmenu;
    TextView defautimage;
    TextView newimage;
    String online;
    SharedPreferences preferences;
    SharedPreferences prefs;
    byte[] profile;
    String typing;
    int user_id;
    String username;

    private class btnUserSavPhotoListner implements OnClickListener {
        public void onClick(View view) {
            GBV_Wallpaper.this.startActivityForResult(new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI), 101);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_wallpaper);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ivUserSavedPhoto = findViewById(R.id.chatwallpaper);
        this.backmenu = findViewById(R.id.backmenu);
        this.user_id = getIntent().getExtras().getInt("USER_ID");
        this.username = getIntent().getExtras().getString("USER_NAME");
        this.profile = getIntent().getExtras().getByteArray("USER_PROFILE");
        this.online = getIntent().getExtras().getString("USER_ONLINE");
        this.typing = getIntent().getExtras().getString("USER_TYPING");
        this.defautimage = findViewById(R.id.defaultimage);
        this.newimage = findViewById(R.id.newimage);
        this.defautimage.setOnClickListener(this);
        this.newimage.setOnClickListener(this);
        this.backmenu.setOnClickListener(this);
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        GBV_UserChat.tokenname = this.prefs.getString("IMAGE", "");
        if (GBV_UserChat.tokenname.equals("")) {
            ivUserSavedPhoto.setImageResource(R.drawable.default_background);
        } else {
            selectedImageUri = Uri.parse(GBV_UserChat.tokenname);
            ivUserSavedPhoto.setImageURI(selectedImageUri);
        }
        ivUserSavedPhoto.setOnClickListener(new btnUserSavPhotoListner());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && requestCode == 101) {
            onSelectFromGalleryResult(data);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        try {
            selectedImageUri = data.getData();
            ivUserSavedPhoto.setImageURI(selectedImageUri);
            SaveImageURI(selectedImageUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SaveImageURI(Uri selectedImageUri) throws IOException {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Editor editor = this.preferences.edit();
        editor.putString("IMAGE", selectedImageUri.toString());
        editor.apply();
    }

    @RequiresApi(api = 21)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backmenu:
                Intent backintent = new Intent(GBV_Wallpaper.this, GBV_UserChat.class);
                backintent.putExtra("USER_ID", user_id);
                backintent.putExtra("USER_NAME", username);
                backintent.putExtra("USER_ONLINE", online);
                backintent.putExtra("USER_TYPING", typing);
                backintent.putExtra("USER_PROFILE", profile);
                startActivity(backintent);
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                return;
            case R.id.defaultimage:
                Drawable drawable = getDrawable(R.drawable.default_background);
                ivUserSavedPhoto.setImageDrawable(drawable);
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                bitmap.compress(CompressFormat.JPEG, 100, new ByteArrayOutputStream());
                selectedImageUri = Uri.parse(Media.insertImage(getContentResolver(), bitmap, "Title", null));
                try {
                    SaveImageURI(selectedImageUri);
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            case R.id.newimage:
                try {
                    GBV_UserChat.tokenname = selectedImageUri.toString();
                    Intent intent = new Intent(GBV_Wallpaper.this, GBV_UserChat.class);
                    intent.putExtra("USER_ID", user_id);
                    intent.putExtra("USER_NAME", username);
                    intent.putExtra("USER_ONLINE", online);
                    intent.putExtra("USER_TYPING", typing);
                    intent.putExtra("USER_PROFILE", profile);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                    return;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return;
                }
            default:
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}
