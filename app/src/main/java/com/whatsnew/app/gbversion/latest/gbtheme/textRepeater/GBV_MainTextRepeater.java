package com.whatsnew.app.gbversion.latest.gbtheme.textRepeater;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Ad_class;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.Constant;
import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.GBV_BaseActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.GBV_Utils;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

public class GBV_MainTextRepeater extends GBV_BaseActivity {
    String Maintext;
    int NoofRepeat;
    String RepeatText;
    ImageView clearTxtBtn;
    ImageView convertButton;
    EditText convertedText;
    ImageView btnCopy;
    EditText emojeeText;
    ImageView imNewLine;
    EditText txtInput;
    boolean isNewLine = false;
    String no;
    ProgressDialog pDialog;
    ImageView btnShare;
    TextView txtNewLine;

    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_text_repeater);
        Ad_class.all_banner(GBV_MainTextRepeater.this, (LinearLayout) findViewById(R.id.adView));
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        this.pDialog = new ProgressDialog(this);
        this.txtNewLine = findViewById(R.id.txtNewLine);
        this.imNewLine = findViewById(R.id.btnNewLine);
        if (this.isNewLine) {
            this.txtNewLine.setText("New Line On");
            this.imNewLine.setImageResource(R.drawable.switch_on);
        } else {
            this.txtNewLine.setText("New Line Off");

            this.imNewLine.setImageResource(R.drawable.switch_off);
        }
        this.imNewLine.setOnClickListener(new newLineClick());
        this.txtInput = findViewById(R.id.inputText);
        this.emojeeText = findViewById(R.id.emojeeTxt);
        this.convertedText = findViewById(R.id.convertedEmojeeTxt);
        this.convertButton = findViewById(R.id.convertEmojeeBtn);
        this.btnCopy = findViewById(R.id.copyTxtBtn);
        this.btnShare = findViewById(R.id.shareTxtBtn);
        this.clearTxtBtn = findViewById(R.id.clearTxtBtn);
        this.convertButton.setOnClickListener(new btnConverListner());
        this.clearTxtBtn.setOnClickListener(new btnClearTextListner());
        this.convertedText.setOnClickListener(new btnConvertedTexListner());
        this.btnCopy.setOnClickListener(new btnCopyListner());
        this.btnShare.setOnClickListener(new btnShareListner());

        convertedText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (view.getId() == R.id.convertedEmojeeTxt) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                return false;
            }
        });

        convertedText.setMovementMethod(new ScrollingMovementMethod());
        convertedText.setVerticalScrollBarEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (Constant.AD_STATUS == "true") {
            Ad_class.adCounter++;
            Ad_class.showInterAd(this, new Ad_class.onLisoner() {
                @Override
                public void click() {
                    GBV_MainTextRepeater.super.onBackPressed();
                }
            });
        } else {
            super.onBackPressed();
        }
    }

    private class btnConverListner implements OnClickListener {
        public void onClick(View view) {
            convertedText.setText("");
            RepeatText = txtInput.getText().toString();
            no = emojeeText.getText().toString();
            try {
                NoofRepeat = Integer.parseInt(no);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
            if (txtInput.getText().toString().isEmpty()) {
                txtInput.setError("Enter Repeat Text");
                txtInput.requestFocus();
            } else if (emojeeText.getText().toString().isEmpty()) {
                emojeeText.setError("Enter Number of Repeat Text");
                emojeeText.requestFocus();
            } else if (NoofRepeat <= 10000) {
                new CreateRepeateText().execute();
            } else {
                Toast.makeText(getApplicationContext(), "Number of Repeter Text Limited Please Enter Limited Number", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class btnClearTextListner implements OnClickListener {
        public void onClick(View view) {
            convertedText.setText("");
        }
    }

    private class btnConvertedTexListner implements OnClickListener {
        @SuppressLint({"WrongConstant"})
        public void onClick(View view) {
            if (!convertedText.getText().toString().isEmpty()) {
                ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(txtInput.getText().toString(), convertedText.getText().toString()));
                Toast.makeText(getApplicationContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class btnCopyListner implements OnClickListener {
        @SuppressLint({"WrongConstant"})
        public void onClick(View view) {
            if (convertedText.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Convert text before copy", Toast.LENGTH_SHORT).show();
                return;
            }
            ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(txtInput.getText().toString(), convertedText.getText().toString()));
            Toast.makeText(getApplicationContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();

        }
    }

    private class btnShareListner implements OnClickListener {
        public void onClick(View view) {
            if (GBV_Utils.appInstalledOrNot(GBV_MainTextRepeater.this, "com.whatsapp")) {
                if (convertedText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Convert text to share", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent shareIntent = new Intent();
                shareIntent.setAction("android.intent.action.SEND");
                shareIntent.setPackage("com.whatsapp");
                shareIntent.putExtra("android.intent.extra.TEXT", convertedText.getText().toString());
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "Select an app to share"));
            } else {
                Toast.makeText(GBV_MainTextRepeater.this, "Please install whatsapp to use this feature.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class CreateRepeateText extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog.setMessage("Please Wait...");
            pDialog.setProgressStyle(0);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        public String doInBackground(String... strings) {
            int i;
            if (isNewLine) {
                for (i = 1; i <= NoofRepeat; i++) {
                    if (i == 1) {
                        Maintext = RepeatText;
                    } else {
                        Maintext += "\n" + RepeatText;
                    }
                }
            } else {
                for (i = 1; i <= NoofRepeat; i++) {
                    if (i == 1) {
                        Maintext = RepeatText;
                    } else {
                        Maintext += "\t" + RepeatText;
                    }
                }
            }
            return null;
        }

        @SuppressLint({"LongLogTag"})
        public void onPostExecute(String result) {
            pDialog.dismiss();
            convertedText.setText(Maintext);
        }
    }

    private class newLineClick implements OnClickListener {

        public void onClick(View v) {
            if (isNewLine) {
                isNewLine = false;
                txtNewLine.setText("New Line Off");
                imNewLine.setImageResource(R.drawable.switch_off);
                return;
            }
            isNewLine = true;
            txtNewLine.setText("New Line On");
            imNewLine.setImageResource(R.drawable.switch_on);
        }
    }
}