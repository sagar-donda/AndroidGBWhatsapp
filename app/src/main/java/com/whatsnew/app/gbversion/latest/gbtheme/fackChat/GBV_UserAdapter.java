package com.whatsnew.app.gbversion.latest.gbtheme.fackChat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.fackChat.DataBaseDetails.GBV_UserDetails;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

class GBV_UserAdapter extends BaseAdapter {

    static ArrayList<GBV_UserDetails> userdetails;
    private Context context;
    private LayoutInflater inflater;

    public static class Holder {
        CircleImageView img;
        TextView username;
        TextView visibility;
    }

    GBV_UserAdapter(Context context, ArrayList<GBV_UserDetails> userdetailsses) {
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        userdetails = userdetailsses;
    }

    public int getCount() {
        return userdetails.size();
    }

    public Object getItem(int i) {
        return i;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        GBV_UserDetails userlist = userdetails.get(i);
        Holder holder = new Holder();
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.gbv_activity_custlist_userprofile, null);
        holder.username = rowView.findViewById(R.id.username);
        holder.visibility = rowView.findViewById(R.id.visibilitystatus);
        holder.img = rowView.findViewById(R.id.user_icon);
        holder.username.setText(userlist.getUname());
        if (userlist.getUtyping().equals("typing")) {
            holder.visibility.setText("typing...");
        } else {
            holder.visibility.setText("");
        }
        holder.img.setImageBitmap(getImagefromdatabase(userlist.getBytes()));
        return rowView;
    }

    private Bitmap getImagefromdatabase(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}