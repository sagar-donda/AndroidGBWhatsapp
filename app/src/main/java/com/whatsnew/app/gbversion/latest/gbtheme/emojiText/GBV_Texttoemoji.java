package com.whatsnew.app.gbversion.latest.gbtheme.emojiText;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.GBV_BaseActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.GBV_Utils;
import com.whatsnew.app.gbversion.latest.gbtheme.R;

import java.io.IOException;
import java.io.InputStream;

public class GBV_Texttoemoji extends GBV_BaseActivity {
    ImageView clearTxtBtn;
    ImageView convertButton;
    EditText convertedText;
    ImageView copyBtn;
    EditText emojeeText;
    EditText inputText;
    ImageView shareButton;

    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_activity_texttoemoji);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        inputText = findViewById(R.id.inputText);
        emojeeText = findViewById(R.id.emojeeTxt);
        convertedText = findViewById(R.id.convertedEmojeeTxt);
        convertButton = findViewById(R.id.convertEmojeeBtn);
        copyBtn = findViewById(R.id.copyTxtBtn);
        shareButton = findViewById(R.id.shareTxtBtn);
        clearTxtBtn = findViewById(R.id.clearTxtBtn);

        convertButton.setOnClickListener(new btnConvertListner());
        clearTxtBtn.setOnClickListener(new btnClearTextListner());
        convertedText.setOnClickListener(new btnConvertedTextListner());
        copyBtn.setOnClickListener(new btnCopyButtonListner());
        shareButton.setOnClickListener(new btnShareListner());

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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class btnConvertListner implements OnClickListener {
        public void onClick(View view) {
            if (inputText.getText().toString().isEmpty()) {
                inputText.setError("Enter text here");
                inputText.requestFocus();
                return;
            } else if (emojeeText.getText().toString().isEmpty()) {
                emojeeText.setError("Enter emoji or text");
                emojeeText.requestFocus();
                return;
            }
            char[] charArray = inputText.getText().toString().toCharArray();
            convertedText.setText(".\n");
            for (char character : charArray) {
                byte[] localObject3;
                InputStream localObject2;
                if (character == '?') {
                    try {
                        InputStream localObject1 = getBaseContext().getAssets().open("ques.txt");
                        localObject3 = new byte[localObject1.available()];
                        localObject1.read(localObject3);
                        localObject1.close();
                        convertedText.append(new String(localObject3).replaceAll("[*]", emojeeText.getText().toString()) + "\n\n");
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                } else if (character == ((char) (character & 95)) || Character.isDigit(character)) {
                    try {
                        localObject2 = getBaseContext().getAssets().open(character + ".txt");
                        localObject3 = new byte[localObject2.available()];
                        localObject2.read(localObject3);
                        localObject2.close();
                        convertedText.append(new String(localObject3).replaceAll("[*]", emojeeText.getText().toString()) + "\n\n");
                    } catch (IOException ioe2) {
                        ioe2.printStackTrace();
                    }
                } else {
                    try {
                        localObject2 = getBaseContext().getAssets().open("low" + character + ".txt");
                        localObject3 = new byte[localObject2.available()];
                        localObject2.read(localObject3);
                        localObject2.close();
                        convertedText.append(new String(localObject3).replaceAll("[*]", emojeeText.getText().toString()) + "\n\n");
                    } catch (IOException ioe22) {
                        ioe22.printStackTrace();
                    }
                }
            }
        }
    }

    private class btnClearTextListner implements OnClickListener {
        public void onClick(View view) {
            convertedText.setText("");
        }
    }

    private class btnConvertedTextListner implements OnClickListener {
        @SuppressLint({"WrongConstant"})
        public void onClick(View view) {
            if (!convertedText.getText().toString().isEmpty()) {
                ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(inputText.getText().toString(), convertedText.getText().toString()));
                Toast.makeText(getApplicationContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class btnCopyButtonListner implements OnClickListener {
        @SuppressLint({"WrongConstant"})
        public void onClick(View view) {
            if (convertedText.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Convert text before copy", Toast.LENGTH_SHORT).show();
                return;
            }
            ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(inputText.getText().toString(), convertedText.getText().toString()));
            Toast.makeText(getApplicationContext(), "Copied to Clipboard", Toast.LENGTH_SHORT).show();

        }
    }

    private class btnShareListner implements OnClickListener {
        public void onClick(View view) {
            if (GBV_Utils.appInstalledOrNot(GBV_Texttoemoji.this, "com.whatsapp")) {
                if (convertedText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Convert text to share", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent shareIntent = new Intent();
                shareIntent.setAction("android.intent.action.SEND");
                shareIntent.setPackage("com.whatsapp");
                shareIntent.putExtra("android.intent.extra.TEXT", convertedText.getText().toString());
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "Select an app to share"));
            } else {
                Toast.makeText(GBV_Texttoemoji.this, "Please install whatsapp to use this feature.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
