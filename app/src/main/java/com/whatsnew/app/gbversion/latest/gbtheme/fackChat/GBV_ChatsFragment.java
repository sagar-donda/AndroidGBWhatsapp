package com.whatsnew.app.gbversion.latest.gbtheme.fackChat;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.clans.fab.FloatingActionButton;
import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.fackChat.DataBaseDetails.GBV_DatabaseHelper;
import com.whatsnew.app.gbversion.latest.gbtheme.fackChat.DataBaseDetails.GBV_UserDetails;

import java.util.ArrayList;

public class GBV_ChatsFragment extends Fragment implements OnItemClickListener, OnItemLongClickListener {
    private final String TAG = GBV_ChatsFragment.class.getSimpleName();
    private static ArrayList<GBV_UserDetails> arrayList;
    private GBV_DatabaseHelper databaseHelper;
    private GBV_UserAdapter userAdapter;
    private ListView userList;
    View view;
    FrameLayout txt_no_data;
    LinearLayout ll_no_data;
    TextView txt_no_chats;

    GBV_MainFackChat mainFackChat;

    public GBV_ChatsFragment(GBV_MainFackChat mainFackChat) {
        this.mainFackChat = mainFackChat;
    }

    private class btnFamListner implements OnClickListener {
        public void onClick(View view) {
            startActivity(new Intent(requireActivity(), GBV_ChatProfile.class));
        }
    }

    private static class btnDialogNoListner implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
        }
    }

    private class btnDialogRateUsListner implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.gbv_fragment_chat, container, false);

        this.databaseHelper = new GBV_DatabaseHelper(requireActivity());
        FloatingActionButton materialDesignFAM = this.view.findViewById(R.id.material_design_android_floating_action_menu);
        this.userList = this.view.findViewById(R.id.userlist);
        txt_no_data = view.findViewById(R.id.txt_no_data);
        ll_no_data = view.findViewById(R.id.ll_no_data);
        txt_no_chats = view.findViewById(R.id.txt_no_chats);

        CallList();
        materialDesignFAM.setOnClickListener(new btnFamListner());

        return this.view;
    }

    private void CallList() {
        GetUserDetails();
        this.userList.setOnItemClickListener(this);
        this.userList.setOnItemLongClickListener(this);
    }

    private void GetUserDetails() {
        Cursor objCursor = this.databaseHelper.ViewUserList();
        arrayList = new ArrayList<>();
        Log.d("Total Colounmn", objCursor.getCount() + "");
        objCursor.moveToFirst();
        for (int i = 0; i < objCursor.getCount(); i++) {
            @SuppressLint("Range") int id = objCursor.getInt(objCursor.getColumnIndex("uid"));
            @SuppressLint("Range") String name = objCursor.getString(objCursor.getColumnIndex("uname"));
            @SuppressLint("Range") String status = objCursor.getString(objCursor.getColumnIndex("ustatus"));
            @SuppressLint("Range") String typing = objCursor.getString(objCursor.getColumnIndex("utyping"));
            @SuppressLint("Range") String online = objCursor.getString(objCursor.getColumnIndex("uonline"));
            @SuppressLint("Range") byte[] blob = objCursor.getBlob(objCursor.getColumnIndex("uprofile"));
            GBV_UserDetails GBVUserDetails = new GBV_UserDetails();
            GBVUserDetails.setUid(id);
            GBVUserDetails.setUname(name);
            GBVUserDetails.setUstatus(status);
            GBVUserDetails.setUonline(online);
            GBVUserDetails.setUtyping(typing);
            GBVUserDetails.setBytes(blob);
            arrayList.add(GBVUserDetails);
            objCursor.moveToNext();
            this.userAdapter = new GBV_UserAdapter(requireActivity(), arrayList);
            this.userList.setAdapter(this.userAdapter);

            if (arrayList.size() != 0) {
                txt_no_data.setBackgroundColor(Color.parseColor("#ffffff"));
                ll_no_data.setVisibility(View.GONE);
                txt_no_chats.setVisibility(View.GONE);
            }
        }
    }

    private long lastClickTime = 0L;

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        long now = System.currentTimeMillis();
        if (now - lastClickTime > 2000) {
            lastClickTime = now;
            Intent intent = new Intent(getActivity(), GBV_UserChat.class);
            GBV_UserDetails userlist = arrayList.get(i);
            Log.i("ContentValues", "SEND ID: " + userlist.getUid());
            intent.putExtra("USER_ID", userlist.getUid());
            intent.putExtra("USER_NAME", userlist.getUname());
            intent.putExtra("USER_ONLINE", userlist.getUonline());
            intent.putExtra("USER_TYPING", userlist.getUtyping());
            intent.putExtra("USER_PROFILE", i);
            Log.e("TAG1", "profile " + i);
            startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
        }
    }

    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        CallDialog(i);
        return true;
    }

    private void CallDialog(final int i) {
        final GBV_UserDetails GBVUserDetails = arrayList.get(i);
        Log.i("ContentValues", "SEND ID: " + GBVUserDetails.getUid());
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireActivity());
        alertDialog.setTitle("Delete Conversation.");
        alertDialog.setMessage("Are you sure you want to delete this conversation?");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                databaseHelper.DeleteUserProfile(GBVUserDetails.getUid());
                GBV_ChatsFragment.arrayList.remove(i);
                userAdapter.notifyDataSetChanged();

                if (arrayList.size() != 0) {
                    txt_no_data.setBackgroundColor(Color.parseColor("#ffffff"));
                    ll_no_data.setVisibility(View.GONE);
                    txt_no_chats.setVisibility(View.GONE);
                } else {
                    txt_no_data.setBackgroundColor(Color.parseColor("#F2FDF0"));
                    ll_no_data.setVisibility(View.VISIBLE);
                    txt_no_chats.setVisibility(View.VISIBLE);
                }
            }
        });
        alertDialog.setNegativeButton("NO", new btnDialogNoListner());
        alertDialog.setNeutralButton("Rate Us", new btnDialogRateUsListner());
        alertDialog.show();
    }
}
