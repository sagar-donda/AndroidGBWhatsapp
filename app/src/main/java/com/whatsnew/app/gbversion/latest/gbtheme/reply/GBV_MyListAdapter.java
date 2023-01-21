package com.whatsnew.app.gbversion.latest.gbtheme.reply;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.service.GBV_BaseActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

public class GBV_MyListAdapter extends RecyclerView.Adapter<GBV_MyListAdapter.ViewHolder> {
    private GBV_MyListData[] listdata;
    GBV_BaseActivity GBVBaseActivity;
    private final String TAG = GBV_MyListAdapter.class.getSimpleName();

    public GBV_MyListAdapter(GBV_MyListData[] listdata, GBV_BaseActivity GBVBaseActivity) {
        this.listdata = listdata;
        this.GBVBaseActivity = GBVBaseActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.gbv_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final GBV_MyListData myListData = listdata[position];
        holder.textView.setText(listdata[position].getDescription());
        holder.textView.setSelected(true);

        holder.cpy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = myListData.getDescription();
                ((ClipboardManager) GBVBaseActivity.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("Copied Text", str));
                Toast.makeText(GBVBaseActivity, "Copied To Clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String FACE = myListData.getDescription();
                Intent whatsappIntent = new Intent("android.intent.action.SEND");
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra("android.intent.extra.TEXT", FACE);
                try {
                    GBVBaseActivity.startActivity(whatsappIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(GBVBaseActivity, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public RelativeLayout relativeLayout;
        public ImageView cpy;
        public ImageView send;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            cpy = (ImageView) itemView.findViewById(R.id.cpy);
            send = (ImageView) itemView.findViewById(R.id.send);
        }
    }
}