package com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;


public class Utill {
    public static NativeAd nativeAd;

    public static void fast_Admob_native(Context context) {
        AdLoader.Builder builder = new AdLoader.Builder(context, Constant.NATIVE_ID);
        builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(NativeAd nativeAd2) {
                if (Utill.nativeAd != null) {
                    Utill.nativeAd = null;
                }
                Utill.nativeAd = nativeAd2;
            }
        });
        builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                if (Utill.nativeAd != null) {
                    Utill.nativeAd = null;
                }
                if (Constant.FBAD_STATUS == "true") {
                fbnative.loadNativeAd();}
            }
        }).build().loadAd(new AdRequest.Builder().build());
    }

}

