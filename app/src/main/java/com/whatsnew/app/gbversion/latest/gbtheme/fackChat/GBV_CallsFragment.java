package com.whatsnew.app.gbversion.latest.gbtheme.fackChat;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.GBV_DebouncedOnClickListener;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class GBV_CallsFragment extends Fragment {
    static Uri selectedImageUri;
    private byte[] bmyimage;
    private ImageView callNow;
    private EditText txtName;
    private String nameProfile;
    private CircleImageView user_profilepic;
    View view;
    GBV_MainFackChat mainFackChat;
    private final String TAG = GBV_CallsFragment.class.getSimpleName();

    public GBV_CallsFragment(GBV_MainFackChat mainFackChat) {
        this.mainFackChat = mainFackChat;
    }

    private class btnUserProfilePicListner implements OnClickListener {
        public void onClick(View view) {
            startActivityForResult(new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI), 101);
        }
    }

    private class btnCallNowListner extends GBV_DebouncedOnClickListener {

        /**
         * The one and only constructor
         *
         * @param minimumIntervalMillis The minimum allowed time between clicks - any click sooner than this after a previous click will be rejected
         */
        public btnCallNowListner(long minimumIntervalMillis) {
            super(minimumIntervalMillis);
        }

        public void onDebouncedClick(View view) {
            nameProfile = txtName.getText().toString();
            Log.i("ContentValues", "onClick: " + nameProfile);
            if (nameProfile.isEmpty()) {
                Toast.makeText(getActivity(), "Please Enter The Caller Name ", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(getActivity(), GBV_Calls.class);
            intent.putExtra("NAME", nameProfile);
            intent.putExtra("ID", 1);
            intent.putExtra("PROFILEPIC", GBV_CallsFragment.selectedImageUri);
            startActivity(intent);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R
                .layout.gbv_fragment_call, container, false);

        this.txtName = this.view.findViewById(R.id.user_name);
        this.user_profilepic = this.view.findViewById(R.id.user_profilepic);
        this.callNow = this.view.findViewById(R.id.callnow);
        this.user_profilepic.setOnClickListener(new btnUserProfilePicListner());
        this.callNow.setOnClickListener(new btnCallNowListner(2000));

        return this.view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1 && requestCode == 101) {
            onSelectFromGalleryResult(data);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        try {
            selectedImageUri = data.getData();
            this.user_profilepic.setImageURI(selectedImageUri);
            this.bmyimage = saveImageInDB(selectedImageUri);
            getPath(selectedImageUri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] saveImageInDB(Uri selectedImageUri) {
        try {
            return getBytes(getActivity().openFileInput(String.valueOf(selectedImageUri)));
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
        Cursor cursor = getActivity().managedQuery(selectedImageUri, new String[]{"_data"}, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow("_data");
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
