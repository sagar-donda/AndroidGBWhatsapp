package com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

public class Ad_class {
    public static int adCounter = 0;
    public static int adDisplayCounter = 5;
    public static InterstitialAd interstitialAd;
    public static NativeAd nativeAd;
    static InterstitialAd mInterstitialAd;

    public static void initAd(Context context) {
        MobileAds.initialize(context, AdController$$ExternalSyntheticLambda0.INSTANCE);
    }

    public static void loadInterAd(Context context) {
        InterstitialAd.load(context, Constant.INTERSTRIAL_ID, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
            public void onAdLoaded(InterstitialAd interstitialAd) {
                Ad_class.mInterstitialAd = interstitialAd;
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                Ad_class.mInterstitialAd = null;

            }
        });
    }
    public interface onLisoner {
        void click();
    }
    public static void showInterAd(final Activity context, onLisoner onlisoner) {
        InterstitialAd interstitialAd = mInterstitialAd;
        if (adCounter == 1) {
            interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    Ad_class.loadInterAd(context);
                    onlisoner.click();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    if (Constant.FBAD_STATUS == "true") {
                        showIntertitialAd(context, (Intent) onlisoner);
                    }
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    Ad_class.mInterstitialAd = null;
                }
            });
            mInterstitialAd.show(context);
        } else {
            if (adCounter == Constant.NEXT_CLICK_COUNT) {
                adCounter = 0;
                onlisoner.click();
            } else {
                onlisoner.click();
            }
        }


    }


    static void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }

    public static void loadAd(Activity activity) {
        InterstitialAd.load(activity, Constant.INTERSTRIAL_ID, new AdRequest.Builder().build(), new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
            }

            public void onAdLoaded(InterstitialAd interstitialAd2) {
                Ad_class.interstitialAd = interstitialAd2;
                interstitialAd2.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {

                    }
                });
            }
        });
    }

    public static void showIntertitialAd(Context contex, final Intent intent) {
        final com.facebook.ads.InterstitialAd interstitialAd = new com.facebook.ads.InterstitialAd(contex, Constant.FBINTERSTRIAL_ID);
        int i = adCounter;
        int i2 = adDisplayCounter;
        if (i != i2) {
            if (i == i2) {
                adCounter = 0;
            }
            startActivity(contex, intent);
            return;
        }
        adCounter = 0;
        interstitialAd.setAdListener(new InterstitialAdListener() {
            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                Ad_class.loadInterAd(contex);
                Ad_class.startActivity(contex, intent);
            }

            @Override
            public void onInterstitialDisplayed(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                interstitialAd.show();
            }
        });
        interstitialAd.loadAd();
    }

    @SuppressLint("WrongConstant")
    public static void populateNativeAdView(NativeAd nativeAd2, NativeAdView nativeAdView) {
        nativeAdView.setMediaView((MediaView) nativeAdView.findViewById(R.id.ad_media));
        nativeAdView.setHeadlineView(nativeAdView.findViewById(R.id.ad_headline));
        nativeAdView.setBodyView(nativeAdView.findViewById(R.id.ad_body));
        nativeAdView.setCallToActionView(nativeAdView.findViewById(R.id.ad_call_to_action));
        nativeAdView.setIconView(nativeAdView.findViewById(R.id.ad_app_icon));
        nativeAdView.setPriceView(nativeAdView.findViewById(R.id.ad_price));
        nativeAdView.setStarRatingView(nativeAdView.findViewById(R.id.ad_stars));
        nativeAdView.setStoreView(nativeAdView.findViewById(R.id.ad_store));
        nativeAdView.setAdvertiserView(nativeAdView.findViewById(R.id.ad_advertiser));
        ((TextView) nativeAdView.getHeadlineView()).setText(nativeAd2.getHeadline());
        nativeAdView.getMediaView().setMediaContent(nativeAd2.getMediaContent());
        if (nativeAd2.getBody() == null) {
            nativeAdView.getBodyView().setVisibility(4);
        } else {
            nativeAdView.getBodyView().setVisibility(0);
            ((TextView) nativeAdView.getBodyView()).setText(nativeAd2.getBody());
        }
        if (nativeAd2.getCallToAction() == null) {
            nativeAdView.getCallToActionView().setVisibility(4);
        } else {
            nativeAdView.getCallToActionView().setVisibility(0);
            ((Button) nativeAdView.getCallToActionView()).setText(nativeAd2.getCallToAction());
        }
        if (nativeAd2.getIcon() == null) {
            nativeAdView.getIconView().setVisibility(8);
        } else {
            ((ImageView) nativeAdView.getIconView()).setImageDrawable(nativeAd2.getIcon().getDrawable());
            nativeAdView.getIconView().setVisibility(0);
        }
        if (nativeAd2.getPrice() == null) {
            nativeAdView.getPriceView().setVisibility(4);
        } else {
            nativeAdView.getPriceView().setVisibility(0);
            ((TextView) nativeAdView.getPriceView()).setText(nativeAd2.getPrice());
        }
        if (nativeAd2.getStore() == null) {
            nativeAdView.getStoreView().setVisibility(4);
        } else {
            nativeAdView.getStoreView().setVisibility(0);
            ((TextView) nativeAdView.getStoreView()).setText(nativeAd2.getStore());
        }
        if (nativeAd2.getStarRating() == null) {
            nativeAdView.getStarRatingView().setVisibility(4);
        } else {
            ((RatingBar) nativeAdView.getStarRatingView()).setRating(nativeAd2.getStarRating().floatValue());
            nativeAdView.getStarRatingView().setVisibility(0);
        }
        if (nativeAd2.getAdvertiser() == null) {
            nativeAdView.getAdvertiserView().setVisibility(4);
        } else {
            ((TextView) nativeAdView.getAdvertiserView()).setText(nativeAd2.getAdvertiser());
            nativeAdView.getAdvertiserView().setVisibility(0);
        }
        nativeAdView.setNativeAd(nativeAd2);
        VideoController videoController = nativeAd2.getMediaContent().getVideoController();
        if (videoController.hasVideoContent()) {
            videoController.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    super.onVideoEnd();
                }
            });
        }
    }

    public static void refreshAd(final FrameLayout frameLayout, final Activity activity) {
        if (Constant.AD_STATUS == "true") {
            AdLoader.Builder builder = new AdLoader.Builder(activity, Constant.NATIVE_ID);
            builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                @Override
                public void onNativeAdLoaded(NativeAd nativeAd2) {
                    if ((Build.VERSION.SDK_INT >= 17 ? activity.isDestroyed() : false) || activity.isFinishing() || activity.isChangingConfigurations()) {
                        nativeAd2.destroy();
                        return;
                    }
                    if (Ad_class.nativeAd != null) {
                        Ad_class.nativeAd.destroy();
                    }
                    Ad_class.nativeAd = nativeAd2;
                    NativeAdView nativeAdView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.ad_unified, (ViewGroup) null);
                    Ad_class.populateNativeAdView(nativeAd2, nativeAdView);
                    frameLayout.removeAllViews();
                    frameLayout.addView(nativeAdView);
                }
            });
            builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(LoadAdError loadAdError) {
                }
            }).build().loadAd(new AdRequest.Builder().build());
        }
    }

    public static void all_banner(Activity context, LinearLayout mAdViewLayout) {
        if (Constant.AD_STATUS == "true") {
            if (Constant.BANNER_VISIBLE == "true") {
                AdView mAdView = new AdView(context);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.setAdSize(com.google.android.gms.ads.AdSize.BANNER);
                mAdView.setAdUnitId(Constant.ADMOB_BANNER_ID);

                mAdView.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        mAdView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        mAdView.setVisibility(View.GONE);
                        showFBBannerAd(context, mAdViewLayout);
                    }
                });

                mAdViewLayout.addView(mAdView);
                mAdView.loadAd(adRequest);
            }
        }
    }

    public static void showFBBannerAd(Context context, LinearLayout mAdViewLayout) {
        if (Constant.AD_STATUS == "true") {
            if (Constant.FBBANNER_VISIBLE == "true") {
                com.facebook.ads.AdView adView = new com.facebook.ads.AdView(context, Constant.FBBANNER_ID, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
                mAdViewLayout.addView(adView);
                adView.loadAd();
            }
        }
    }

    public static void lambda$initAd$0(InitializationStatus initializationStatus) {
    }

}