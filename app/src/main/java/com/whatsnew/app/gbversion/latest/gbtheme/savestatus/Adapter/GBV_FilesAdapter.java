package com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Ad_class;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Constant;
import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.GBV_ImageActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.GBV_VideoActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Models.GBV_Status;
import com.whatsnew.app.gbversion.latest.gbtheme.start.GBV_ExitActivity;

import java.util.List;

public class GBV_FilesAdapter extends RecyclerView.Adapter<GBV_ItemViewHolder> {

    private final List<GBV_Status> imagesList;
    private Context context;

    public GBV_FilesAdapter(List<GBV_Status> imagesList) {
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public GBV_ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.gbv_item_saved_files, parent, false);
        return new GBV_ItemViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final GBV_ItemViewHolder holder, int position) {

        holder.save.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.dels));
        holder.share.setVisibility(View.VISIBLE);
        holder.save.setVisibility(View.VISIBLE);

        final GBV_Status GBVStatus = imagesList.get(position);

        if (GBVStatus.isVideo())
            Glide.with(context).asBitmap().load(GBVStatus.getFile()).into(holder.imageView);
        else
            Picasso.get().load(GBVStatus.getFile()).into(holder.imageView);

        holder.save.setOnClickListener(view -> {
            if (GBVStatus.getFile().delete()) {
                imagesList.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "File Deleted", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(context, "Unable to Delete File", Toast.LENGTH_SHORT).show();
        });

        holder.share.setOnClickListener(v -> {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);

            if (GBVStatus.isVideo())
                shareIntent.setType("image/mp4");
            else
                shareIntent.setType("image/jpg");

            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + GBVStatus.getFile().getAbsolutePath()));
            context.startActivity(Intent.createChooser(shareIntent, "Share image"));

        });

        LayoutInflater inflater = LayoutInflater.from(context);
        final View view1 = inflater.inflate(R.layout.gbv_view_video_full_screen, null);

        holder.imageView.setOnClickListener(v -> {

            if (GBVStatus.isVideo()) {
                if (Constant.AD_STATUS == "true") {
                    Ad_class.adCounter++;
                    Ad_class.showInterAd(GBV_ExitActivity.this, new Ad_class.onLisoner() {
                        @Override
                        public void click() {
                            String filePath = GBVStatus.getFile().getAbsolutePath();
                            Intent intent = new Intent(context, GBV_VideoActivity.class);
                            intent.putExtra("video", filePath);
                            context.startActivity(intent);
                        }
                    });
                } else {
                    String filePath = GBVStatus.getFile().getAbsolutePath();
                    Intent intent = new Intent(context, GBV_VideoActivity.class);
                    intent.putExtra("video", filePath);
                    context.startActivity(intent);
                }

            } else {
                if (Constant.AD_STATUS == "true") {
                    Ad_class.adCounter++;
                    Ad_class.showInterAd(GBV_ExitActivity.this, new Ad_class.onLisoner() {
                        @Override
                        public void click() {
                            String filePath = GBVStatus.getFile().getAbsolutePath();
                            Intent intent = new Intent(context, GBV_ImageActivity.class);
                            intent.putExtra("picture", filePath);
                            context.startActivity(intent);
                        }
                    });
                } else {
                    String filePath = GBVStatus.getFile().getAbsolutePath();
                    Intent intent = new Intent(context, GBV_ImageActivity.class);
                    intent.putExtra("picture", filePath);
                    context.startActivity(intent);
                }

            }

        });

    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

}
