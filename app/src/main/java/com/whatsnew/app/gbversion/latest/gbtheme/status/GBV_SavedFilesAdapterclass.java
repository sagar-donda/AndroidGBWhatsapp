package com.whatsnew.app.gbversion.latest.gbtheme.status;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Ad_class;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Constant;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

import java.io.File;
import java.util.ArrayList;


public class GBV_SavedFilesAdapterclass extends RecyclerView.Adapter<GBV_SavedFilesAdapterclass.ViewHolder> {
    private Context context;
    private ArrayList<GBV_WhatsappStatusModelclass> list;

    public GBV_SavedFilesAdapterclass(ArrayList<GBV_WhatsappStatusModelclass> arrayList, Context context) {
        this.list = arrayList;
        this.context = context;
    }

    @Override 
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gbv_saved_files_row_resource, viewGroup, false));
    }

    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final GBV_WhatsappStatusModelclass whatsappStatusModelclass = this.list.get(i);
        if (whatsappStatusModelclass.getName().endsWith("mp4")) {
            viewHolder.centerIcon.setImageResource(R.drawable.play);
            Glide.with(this.context).asBitmap().load(whatsappStatusModelclass.getUri()).into(viewHolder.thumbnail);
        } else {
            Glide.with(this.context).asBitmap().load(whatsappStatusModelclass.getUri()).into(viewHolder.thumbnail);
            viewHolder.centerIcon.setVisibility(4);
        }
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (Constant.AD_STATUS == "true") {
                    Ad_class.adCounter++;
                    Ad_class.showInterAd((Activity) context, new Ad_class.onLisoner() {
                        @Override
                        public void click() {
                            if (whatsappStatusModelclass.getName().endsWith("mp4")) {
                                AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                                Intent intent = new Intent(appCompatActivity.getApplicationContext(), GBV_ViewFullVideoActivityclass.class);
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("file", whatsappStatusModelclass);
                                intent.putExtras(bundle);
                                appCompatActivity.startActivity(intent);
                                return;
                            }
                            AppCompatActivity appCompatActivity2 = (AppCompatActivity) view.getContext();
                            Intent intent2 = new Intent(appCompatActivity2.getApplicationContext(), GBV_ViewFullImageActivityclass.class);
                            Bundle bundle2 = new Bundle();
                            bundle2.putParcelable("file", whatsappStatusModelclass);
                            intent2.putExtras(bundle2);
                            appCompatActivity2.startActivity(intent2);
                        }
                    });
                } else {
                    if (whatsappStatusModelclass.getName().endsWith("mp4")) {
                        AppCompatActivity appCompatActivity = (AppCompatActivity) view.getContext();
                        Intent intent = new Intent(appCompatActivity.getApplicationContext(), GBV_ViewFullVideoActivityclass.class);
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("file", whatsappStatusModelclass);
                        intent.putExtras(bundle);
                        appCompatActivity.startActivity(intent);
                        return;
                    }
                    AppCompatActivity appCompatActivity2 = (AppCompatActivity) view.getContext();
                    Intent intent2 = new Intent(appCompatActivity2.getApplicationContext(), GBV_ViewFullImageActivityclass.class);
                    Bundle bundle2 = new Bundle();
                    bundle2.putParcelable("file", whatsappStatusModelclass);
                    intent2.putExtras(bundle2);
                    appCompatActivity2.startActivity(intent2);
                }


            }
        });
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                // Set the message show for the Alert time
                builder.setMessage("Are you sure, you want to delete ?");

                // Set Alert Title
                builder.setTitle("Alert !");

                // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                builder.setCancelable(false);

                // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // When the user click yes button then app will close
                    new File(whatsappStatusModelclass.getPath()).delete();
                    GBV_SavedFilesAdapterclass.this.notifyItemRemoved(viewHolder.getAdapterPosition());
                    GBV_SavedFilesAdapterclass.this.list.remove(i);
                });

                // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // If user click no then dialog box is canceled.
                    dialog.cancel();
                });

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();
                // Show the Alert Dialog box
                alertDialog.show();
            }
        });
        viewHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.SEND");
                Uri parse = Uri.parse(whatsappStatusModelclass.getPath());
                intent.setType("*/*");
                intent.putExtra("android.intent.extra.STREAM", parse);
                ((AppCompatActivity) view.getContext()).startActivity(Intent.createChooser(intent, "Share using"));
            }
        });
    }

    @Override 
    public int getItemCount() {
        return this.list.size();
    }

    
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
