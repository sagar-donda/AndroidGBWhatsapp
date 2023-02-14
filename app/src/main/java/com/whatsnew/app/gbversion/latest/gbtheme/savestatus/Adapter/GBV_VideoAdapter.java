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
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Ad_class;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Constant;
import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.emojiText.GBV_Texttoemoji;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.GBV_VideoActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Models.GBV_Status;
import com.whatsnew.app.gbversion.latest.gbtheme.savestatus.Utils.GBV_Common;
import com.whatsnew.app.gbversion.latest.gbtheme.start.GBV_ExitActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.start.GBV_MainActivity;

import java.util.List;

public class GBV_VideoAdapter extends RecyclerView.Adapter<GBV_ItemViewHolder> {

    private final List<GBV_Status> videoList;
    private final RelativeLayout container;
    private Context context;

    public GBV_VideoAdapter(List<GBV_Status> videoList, RelativeLayout container) {
        this.videoList = videoList;
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

        final GBV_Status GBVStatus = videoList.get(position);
        Glide.with(context).asBitmap().load(GBVStatus.getFile()).into(holder.imageView);

        holder.share.setOnClickListener(v -> {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);

            shareIntent.setType("image/mp4");
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + GBVStatus.getFile().getAbsolutePath()));
            context.startActivity(Intent.createChooser(shareIntent, "Share image"));

        });

        LayoutInflater inflater = LayoutInflater.from(context);
        final View view1 = inflater.inflate(R.layout.gbv_view_video_full_screen, null);

        holder.imageView.setOnClickListener(v -> {
            if (Constant.AD_STATUS == "true") {
                Ad_class.adCounter++;
                Ad_class.showInterAd(GBV_ExitActivity.this, new Ad_class.onLisoner() {
                    @Override
                    public void click() {
                        String filePath = GBVStatus.getFile().getAbsolutePath();
                        Intent intent = new Intent(context, GBV_VideoActivity.class);
                        intent.putExtra("video", filePath);
                        context.startActivity(intent);                    }
                });
            } else {
                String filePath = GBVStatus.getFile().getAbsolutePath();
                Intent intent = new Intent(context, GBV_VideoActivity.class);
                intent.putExtra("video", filePath);
                context.startActivity(intent);            }


//            final AlertDialog.Builder alertDg = new AlertDialog.Builder(context);
//
//            FrameLayout mediaControls = view1.findViewById(R.id.videoViewWrapper);
//
//            if (view1.getParent() != null) {
//                ((ViewGroup) view1.getParent()).removeView(view1);
//            }
//
//            alertDg.setView(view1);
//
//            final VideoView videoView = view1.findViewById(R.id.video_full);
//
//            final MediaController mediaController = new MediaController(context, false);
//
//            videoView.setOnPreparedListener(mp -> {
//
//                mp.start();
//                mediaController.show(0);
//                mp.setLooping(true);
//            });
//
//            videoView.setMediaController(mediaController);
//            mediaController.setMediaPlayer(videoView);
//            videoView.setVideoURI(Uri.fromFile(GBVStatus.getFile()));
//            videoView.requestFocus();
//
//            ((ViewGroup) mediaController.getParent()).removeView(mediaController);
//
//            if (mediaControls.getParent() != null) {
//                mediaControls.removeView(mediaController);
//            }
//
//            mediaControls.addView(mediaController);
//
//            final AlertDialog alert2 = alertDg.create();
//
//            alert2.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
//            alert2.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            alert2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//            alert2.show();

        });

        holder.save.setOnClickListener(v -> GBV_Common.copyFile(GBVStatus, context, container));

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

}
