package com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.GBV_ImageActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.GBV_VideoActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Models.GBV_Status;

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
                String filePath = GBVStatus.getFile().getAbsolutePath();
                Intent intent = new Intent(context, GBV_VideoActivity.class);
                intent.putExtra("video", filePath);
                context.startActivity(intent);

//                final AlertDialog.Builder alertDg = new AlertDialog.Builder(context);
//
//                FrameLayout mediaControls = view1.findViewById(R.id.videoViewWrapper);
//
//                if (view1.getParent() != null) {
//                    ((ViewGroup) view1.getParent()).removeView(view1);
//                }
//
//                alertDg.setView(view1);
//
//                final VideoView videoView = view1.findViewById(R.id.video_full);
//
//                final MediaController mediaController = new MediaController(context, false);
//
//                videoView.setOnPreparedListener(mp -> {
//
//                    mp.start();
//                    mediaController.show(0);
//                    mp.setLooping(true);
//                });
//
//                videoView.setMediaController(mediaController);
//                mediaController.setMediaPlayer(videoView);
//                videoView.setVideoURI(Uri.fromFile(GBVStatus.getFile()));
//                videoView.requestFocus();
//
//                ((ViewGroup) mediaController.getParent()).removeView(mediaController);
//
//                if (mediaControls.getParent() != null) {
//                    mediaControls.removeView(mediaController);
//                }
//
//                mediaControls.addView(mediaController);
//
//                final AlertDialog alert2 = alertDg.create();
//
//                alert2.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
//                alert2.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                alert2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//                alert2.show();

            } else {
                String filePath = GBVStatus.getFile().getAbsolutePath();
                Intent intent = new Intent(context, GBV_ImageActivity.class);
                intent.putExtra("picture", filePath);
                context.startActivity(intent);

//                final AlertDialog.Builder alertD = new AlertDialog.Builder(context);
//                LayoutInflater inflater1 = LayoutInflater.from(context);
//                View view = inflater1.inflate(R.layout.gbv_view_image_full_screen, null);
//                alertD.setView(view);
//
//                ImageView imageView = view.findViewById(R.id.img);
//                Picasso.get().load(GBVStatus.getFile()).into(imageView);
//
//                AlertDialog alert = alertD.create();
//                alert.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
//                alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                alert.show();

            }

        });

    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

}
