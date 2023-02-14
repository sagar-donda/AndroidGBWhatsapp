package com.whatsnew.app.gbversion.latest.gbtheme.whatsWebScan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.FileChooserParams;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;

import androidx.annotation.RequiresApi;

import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.service.GBV_BaseActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

public class GBV_WebActivity extends GBV_BaseActivity {
    public static Handler handler;
    private static ValueCallback<Uri[]> mUploadMessageArr;
    String TAG = GBV_WebActivity.class.getSimpleName();
    ProgressBar progressBar;
    private WebView webViewscanner;

    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(api = 17)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_web_main);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        InitHandler();
        this.progressBar = findViewById(R.id.progressBar);
        if (VERSION.SDK_INT >= 24) {
            this.webViewscanner = findViewById(R.id.webViewscan);
            this.webViewscanner.clearFormData();
            this.webViewscanner.getSettings().setSaveFormData(true);
            this.webViewscanner.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0");
            this.webViewscanner.setLayoutParams(new LayoutParams(-1, -1));
            this.webViewscanner.setWebChromeClient(new webChromeClients());
            this.webViewscanner.setWebViewClient(new MyBrowser());
            this.webViewscanner.getSettings().setAppCacheMaxSize(5242880);
            this.webViewscanner.getSettings().setAllowFileAccess(true);
            this.webViewscanner.getSettings().setAppCacheEnabled(true);
            this.webViewscanner.getSettings().setJavaScriptEnabled(true);
            this.webViewscanner.getSettings().setDefaultTextEncodingName( "UTF-8");
            this.webViewscanner.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            this.webViewscanner.getSettings().setDatabaseEnabled(true);
            this.webViewscanner.getSettings().setBuiltInZoomControls(false);
            this.webViewscanner.getSettings().setSupportZoom(false);
            this.webViewscanner.getSettings().setUseWideViewPort(true);
            this.webViewscanner.getSettings().setDomStorageEnabled(true);
            this.webViewscanner.getSettings().setAllowFileAccess(true);
            this.webViewscanner.getSettings().setLoadWithOverviewMode(true);
            this.webViewscanner.getSettings().setLoadsImagesAutomatically(true);
            this.webViewscanner.getSettings().setBlockNetworkImage(false);
            this.webViewscanner.getSettings().setBlockNetworkLoads(false);
            this.webViewscanner.getSettings().setLoadWithOverviewMode(true);
            this.webViewscanner.loadUrl("https://web.whatsapp.com/%F0%9F%8C%90/en");
        } else {
            this.webViewscanner = findViewById(R.id.webViewscan);
            this.webViewscanner.clearFormData();
            this.webViewscanner.getSettings().setSaveFormData(true);
            this.webViewscanner.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:60.0) Gecko/20100101 Firefox/60.0");
            this.webViewscanner.setLayoutParams(new LayoutParams(-1, -1));
            this.webViewscanner.setWebChromeClient(new webChromeClients());
            this.webViewscanner.setWebViewClient(new MyBrowser());
            this.webViewscanner.getSettings().setAppCacheMaxSize(5242880);
            this.webViewscanner.getSettings().setAllowFileAccess(true);
            this.webViewscanner.getSettings().setAppCacheEnabled(true);
            this.webViewscanner.getSettings().setJavaScriptEnabled(true);
            this.webViewscanner.getSettings().setDefaultTextEncodingName( "UTF-8");
            this.webViewscanner.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            this.webViewscanner.getSettings().setDatabaseEnabled(true);
            this.webViewscanner.getSettings().setBuiltInZoomControls(false);
            this.webViewscanner.getSettings().setSupportZoom(false);
            this.webViewscanner.getSettings().setUseWideViewPort(true);
            this.webViewscanner.getSettings().setDomStorageEnabled(true);
            this.webViewscanner.getSettings().setAllowFileAccess(true);
            this.webViewscanner.getSettings().setLoadWithOverviewMode(true);
            this.webViewscanner.getSettings().setLoadsImagesAutomatically(true);
            this.webViewscanner.getSettings().setBlockNetworkImage(false);
            this.webViewscanner.getSettings().setBlockNetworkLoads(false);
            this.webViewscanner.getSettings().setLoadWithOverviewMode(true);
            this.webViewscanner.loadUrl("https://web.whatsapp.com/%F0%9F%8C%90/en");
        }
    }

    @SuppressLint("HandlerLeak")
    private static class btnInitHandlerListner extends Handler {
        @SuppressLint({"SetTextI18n"})
        public void handleMessage(Message msg) {
        }
    }

    private static class webChromeClients extends WebChromeClient {
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Log.e("CustomClient", consoleMessage.message());
            return super.onConsoleMessage(consoleMessage);
        }
    }
    
    private class MyBrowser extends WebViewClient {
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            progressBar.setVisibility(View.VISIBLE);
            Log.e(TAG, "progressbar");
            super.onPageStarted(view, url, favicon);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String request) {
            view.loadUrl(request);
            return true;
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.e(TAG, "progressbar GONE");
            progressBar.setVisibility(View.GONE);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1001 && VERSION.SDK_INT >= 21) {
            mUploadMessageArr.onReceiveValue(FileChooserParams.parseResult(i2, intent));
            mUploadMessageArr = null;
        }
    }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean z = true;
        if (keyCode == 4) {
            try {
                if (this.webViewscanner.canGoBack()) {
                    this.webViewscanner.goBack();
                    return z;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        finish();
        z = super.onKeyDown(keyCode, event);
        return z;
    }

    public void onPause() {
        super.onPause();
        this.webViewscanner.clearCache(true);
    }

    public void onDestroy() {
        super.onDestroy();
        this.webViewscanner.clearCache(true);
    }

    public void onStop() {
        this.webViewscanner.clearCache(true);
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @SuppressLint({"HandlerLeak"})
    private void InitHandler() {
        handler = new btnInitHandlerListner();
    }
}
