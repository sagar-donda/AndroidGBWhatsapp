package com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration;

import com.google.gson.annotations.SerializedName;

public class RecyclerData {
    @SerializedName("bannerad")
    private  String banner;
    private String adStatus;
    private String show_banner;
    private String fb_adstatus;
    private String show_fb_banner;
    @SerializedName("appopenshow")
    private String appopenad1;
    private String interstitial;
    @SerializedName("native")

    private String native1;
    private String fb_banner;
    private String fb_native;
    private String fb_interstitial;
    private Integer fullscreenadcount;
    private String privacypolicy;
    private String moreapps;

    public  String getbanner() {
        return banner;
    }

    public String getadStatus() {
        return adStatus;
    }

    public String getshow_banner() {
        return show_banner;
    }

    public String getfb_adstatus() {
        return fb_adstatus;
    }

    public String getshow_fb_banner() {
        return show_fb_banner;
    }

    public String getappopenad1() {
        return appopenad1;
    }

    public String getinterstitial() {
        return interstitial;
    }


    public String getnative1() {
        return native1;
    }

    public String getfb_banner() {
        return fb_banner;
    }


    public String getfb_native() {
        return fb_native;
    }


    public String getfb_interstitial() {
        return fb_interstitial;
    }


    public Integer getfullscreenadcount() {
        return fullscreenadcount;
    }


    public String getprivacypolicy() {
        return privacypolicy;
    }


    public String getmoreapps() {
        return moreapps;
    }

}
