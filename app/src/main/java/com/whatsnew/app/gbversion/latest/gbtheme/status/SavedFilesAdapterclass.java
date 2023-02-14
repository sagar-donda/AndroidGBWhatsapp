package com.whatsnew.app.gbversion.latest.gbtheme.status;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

import java.io.File;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class SavedFilesAdapterclass extends RecyclerView.Adapter<SavedFilesAdapterclass.ViewHolder> {
    private Context context;
    private ArrayList<WhatsappStatusModelclass> list;

    public SavedFilesAdapterclass(ArrayList<WhatsappStatusModelclass> arrayList, Context context) {
        this.list = arrayList;
        this.context = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.saved_files_row_resource, viewGroup, false));
    }

    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final WhatsappStatusModelclass whatsappStatusModelclass = this.list.get(i);
        if (whatsappStatusModelclass.getName().endsWith("mp4")) {
            viewHolder.centerIcon.setImageResource(R.drawable.ic_video_play);
            Glide.with(this.context).asBitmap().load(whatsappStatusModelclass.getUri()).into(viewHolder.thumbnail);
        } else {
            Glide.with(this.context).asBitmap().load(whatsappStatusModelclass.getUri()).into(viewHolder.thumbnail);
            viewHolder.centerIcon.setVisibility(4);
        }
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() { // from class: com.saver.image.video.business.wp.SavedFilesAdapterclass.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (whatsappStatusModelclass.getName().endsWith("mp4")) {
                    AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                    Intent intent = new Intent(appCompatActivity.getApplicationContext(), ViewFullVideoActivityclass.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("file", whatsappStatusModelclass);
                    intent.putExtras(bundle);
                    appCompatActivity.startActivity(intent);
                    return;
                }
                AppCompatActivity appCompatActivity2 = (AppCompatActivity) view.getContext();
                Intent intent2 = new Intent(appCompatActivity2.getApplicationContext(), ViewFullImageActivityclass.class);
                Bundle bundle2 = new Bundle();
                bundle2.putParcelable("file", whatsappStatusModelclass);
                intent2.putExtras(bundle2);
                appCompatActivity2.startActivity(intent2);
            }
        });
        viewHolder.delete.setOnClickListener(new View.OnClickListener() { // from class: com.saver.image.video.business.wp.SavedFilesAdapterclass.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                new File(whatsappStatusModelclass.getPath()).delete();
                SavedFilesAdapterclass.this.notifyItemRemoved(viewHolder.getAdapterPosition());
                SavedFilesAdapterclass.this.list.remove(i);
            }
        });
        viewHolder.share.setOnClickListener(new View.OnClickListener() { // from class: com.saver.image.video.business.wp.SavedFilesAdapterclass.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.SEND");
                Uri parse = Uri.parse(whatsappStatusModelclass.getPath());
                intent.setType("*/*");
                intent.putExtra("android.intent.extra.STREAM", parse);
                ((AppCompatActivity) view.getContext()).startActivity(Intent.createChooser(intent, "Share using"));
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    /* loaded from: classes.dex */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView centerIcon;
        ImageView delete;
        ImageView share;
        ImageView thumbnail;

        public ViewHolder(View view) {
            super(view);
            this.share = (ImageView) view.findViewById(R.id.share);
            this.delete = (ImageView) view.findViewById(R.id.delete);
            this.centerIcon = (ImageView) view.findViewById(R.id.center_icon);
            this.thumbnail = (ImageView) view.findViewById(R.id.saved_file_thumb);
            this.cardView = (CardView) view.findViewById(R.id.saved_file_card_view);
        }
    }
}
