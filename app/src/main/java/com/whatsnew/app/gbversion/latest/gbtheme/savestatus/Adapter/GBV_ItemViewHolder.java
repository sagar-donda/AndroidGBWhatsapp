package com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.whatsnew.app.gbversion.latest.gbtheme.R;

public class GBV_ItemViewHolder extends RecyclerView.ViewHolder{

    public ImageButton save, share;
    public ImageView imageView;

    public GBV_ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.ivThumbnail);
        save = itemView.findViewById(R.id.save);
        share = itemView.findViewById(R.id.share);
    }
}