package com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Ad_class;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Constant;
import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.emojiText.GBV_Texttoemoji;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.GBV_ImageActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Models.GBV_Status;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Utils.GBV_Common;
import com.whatsnew.app.gbversion.latest.gbtheme.start.GBV_ExitActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.start.GBV_MainActivity;

import java.util.List;

public class GBV_ImageAdapter extends RecyclerView.Adapter<GBV_ItemViewHolder> {

    private final List<GBV_Status> imagesList;
    private final RelativeLayout container;
    private Context context;

    public GBV_ImageAdapter(List<GBV_Status> imagesList, RelativeLayout container) {
        this.imagesList = imagesList;
        this.container = container;
    }

    @NonNull
    @Override
    public GBV_ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.gbv_item_status, parent, false);
        return new GBV_ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final GBV_ItemViewHolder holder, int position) {

        final GBV_Status GBVStatus = imagesList.get(position);
        Picasso.get().load(GBVStatus.getFile()).into(holder.imageView);

        holder.save.setOnClickListener(v -> GBV_Common.copyFile(GBVStatus, context, container));

        holder.share.setOnClickListener(v -> {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);

            shareIntent.setType("image/jpg");
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + GBVStatus.getFile().getAbsolutePath()));
            context.startActivity(Intent.createChooser(shareIntent, "Share image"));

        });
        String filePath = GBVStatus.getFile().getAbsolutePath();
        holder.imageView.setOnClickListener(v -> {
            if (Constant.AD_STATUS == "true") {
                Ad_class.adCounter++;
                Ad_class.showInterAd(GBV_ExitActivity.this, new Ad_class.onLisoner() {
                    @Override
                    public void click() {
                        Intent intent = new Intent(context, GBV_ImageActivity.class);
                        intent.putExtra("picture", filePath);
                        context.startActivity(intent);                    }
                });
            } else {
                Intent intent = new Intent(context, GBV_ImageActivity.class);
                intent.putExtra("picture", filePath);
                context.startActivity(intent);            }

        });
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

}
