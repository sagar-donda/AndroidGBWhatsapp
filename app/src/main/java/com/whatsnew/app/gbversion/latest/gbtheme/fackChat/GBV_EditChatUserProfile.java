package com.whatsnew.app.gbversion.latest.gbtheme.fackChat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.service.GBV_BaseActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.fackChat.DataBaseDetails.GBV_DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class GBV_EditChatUserProfile extends GBV_BaseActivity implements OnClickListener {
    LinearLayout backmenu;
    byte[] blob;
    byte[] bmyimage;
    GBV_DatabaseHelper databaseHelper;
    TextView delete_Profile;
    String isonline;
    String istyping;
    Switch online;
    Uri selectedImageUri;
    String status;
    Switch typing;
    TextView update_Profile;
    String user;
    int user_id;
    EditText user_name;
    CircleImageView user_profilepic;
    EditText user_status;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_edit_chat_user_profile);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        this.databaseHelper = new GBV_DatabaseHelper(this);
        this.user_id = getIntent().getExtras().getInt("USER_ID");
        this.user_name = findViewById(R.id.user_name);
        this.user_status = findViewById(R.id.user_status);
        this.user_profilepic = findViewById(R.id.user_profilepic);
        this.online = findViewById(R.id.user_onlile);
        this.typing = findViewById(R.id.user_typing);
        this.backmenu = findViewById(R.id.backmenu);
        this.backmenu.setOnClickListener(this);
        this.update_Profile = findViewById(R.id.edit_userProfile);
        this.delete_Profile = findViewById(R.id.delete_userProfile);
        GetCurrentUserDetails();
        this.update_Profile.setOnClickListener(this);
        this.delete_Profile.setOnClickListener(this);
        this.user_profilepic.setOnClickListener(this);
    }



    private void GetCurrentUserDetails() {
        Cursor c = this.databaseHelper.getUserHistory(this.user_id + "");
        Log.d("Total Colounmn", c.getCount() + "");
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            this.user = c.getString(c.getColumnIndex("uname"));
            this.status = c.getString(c.getColumnIndex("ustatus"));
            this.isonline = c.getString(c.getColumnIndex("uonline"));
            this.istyping = c.getString(c.getColumnIndex("utyping"));
            this.blob = c.getBlob(c.getColumnIndex("uprofile"));
        }
        this.user_name.setText(this.user + "");
        this.user_status.setText(this.status + "");
        if (this.isonline.equals("online")) {
            this.online.setChecked(true);
        } else {
            this.online.setChecked(false);
        }
        if (this.istyping.equals("typing")) {
            this.typing.setChecked(true);
        } else {
            this.typing.setChecked(false);
        }
        this.user_profilepic.setImageBitmap(getImagefromdatabase(this.blob));
    }

    private Bitmap getImagefromdatabase(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backmenu:
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                return;
            case R.id.delete_userProfile:
                new AlertDialog.Builder(GBV_EditChatUserProfile.this)
                        .setCancelable(true)
                        .setMessage("Do you want to delete this chat?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                databaseHelper.DeleteUserProfile(user_id);
                                startActivity(new Intent(GBV_EditChatUserProfile.this, GBV_MainFackChat.class));
                                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                                finish();
                            }
                        })
                        .create().show();
                return;
            case R.id.edit_userProfile:
                String isonline;
                String istyping;
                String uname = user_name.getText().toString();
                String status = user_status.getText().toString();
                if (online.isChecked()) {
                    isonline = "online";
                } else {
                    isonline = "offline";
                }
                if (typing.isChecked()) {
                    istyping = "typing";
                } else {
                    istyping = "nottyping";
                }
                if (bmyimage == null) {
                    Bitmap bitmap = ((BitmapDrawable) user_profilepic.getDrawable()).getBitmap();
                    ByteArrayOutputStream blob = new ByteArrayOutputStream();
                    bitmap.compress(CompressFormat.PNG, 0, blob);
                    bmyimage = blob.toByteArray();
                }
                databaseHelper.getUserDetailsUpdate(user_id, uname, status, isonline, istyping, bmyimage);
                Toast.makeText(GBV_EditChatUserProfile.this, "Profile Updated...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(GBV_EditChatUserProfile.this, GBV_MainFackChat.class));
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                finish();                return;
            case R.id.user_profilepic:
                startActivityForResult(new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI), 101);
                return;
            default:
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && requestCode == 101) {
            onSelectFromGalleryResult(data);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        try {
            this.selectedImageUri = data.getData();
            this.user_profilepic.setImageURI(this.selectedImageUri);
            this.bmyimage = saveImageInDB(this.selectedImageUri);
            getPath(this.selectedImageUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] saveImageInDB(Uri selectedImageUri) {
        try {
            return getBytes(getContentResolver().openInputStream(selectedImageUri));
        } catch (IOException ioe) {
            Log.e("Hello1", "<saveImageInDB> Error : " + ioe.getLocalizedMessage());
            return null;
        }
    }

    private byte[] getBytes(InputStream iStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while (true) {
            int len = iStream.read(buffer);
            if (len == -1) {
                return byteBuffer.toByteArray();
            }
            byteBuffer.write(buffer, 0, len);
        }
    }

    private String getPath(Uri selectedImageUri) {
        Cursor cursor = managedQuery(selectedImageUri, new String[]{"_data"}, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow("_data");
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
