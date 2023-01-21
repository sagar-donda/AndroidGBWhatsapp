package com.whatsnew.app.gbversion.latest.gbtheme.fackChat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.whatsnew.app.gbversion.latest.gbtheme.AdsIntegration.service.GBV_BaseActivity;
import com.whatsnew.app.gbversion.latest.gbtheme.R;
import com.whatsnew.app.gbversion.latest.gbtheme.fackChat.DataBaseDetails.GBV_DatabaseHelper;

public class GBV_EditMessageActivity extends GBV_BaseActivity implements OnClickListener {
    String chatid;
    GBV_DatabaseHelper databaseHelper;
    TextView delete_usermessage;
    TextView edit_usermessage;
    String message;
    RadioGroup messagestatus;
    EditText msgedit;
    String online;
    int position;
    byte[] profile;
    String sender;
    RadioGroup senduser;
    String status;
    String typing;
    String username;

    private class btnSendUserListner implements OnCheckedChangeListener {
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.me) {
                GBV_EditMessageActivity.this.sender = "yes";
                return;
            }
            GBV_EditMessageActivity.this.sender = "no";
        }
    }

    private class btnMessageStatusListner implements OnCheckedChangeListener {
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.send) {
                GBV_EditMessageActivity.this.status = "send";
            } else if (checkedId == R.id.receive) {
                GBV_EditMessageActivity.this.status = "receive";
            } else {
                GBV_EditMessageActivity.this.status = "read";
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gbv_edit_message);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Bundle bundle = getIntent().getExtras();
        this.databaseHelper = new GBV_DatabaseHelper(this);
        if (bundle != null) {
            this.position = bundle.getInt("POSITION");
            this.message = bundle.getString("MESSAGE");
            this.sender = bundle.getString("SENDER");
            this.status = bundle.getString("STATUS");
            this.chatid = bundle.getString("CHATID");
            this.username = getIntent().getExtras().getString("USER_NAME");
            this.profile = getIntent().getExtras().getByteArray("USER_PROFILE");
            this.online = getIntent().getExtras().getString("USER_ONLINE");
            this.typing = getIntent().getExtras().getString("USER_TYPING");
        }
        this.msgedit = findViewById(R.id.msgedit);
        this.edit_usermessage = findViewById(R.id.edit_usermessage);
        this.delete_usermessage = findViewById(R.id.delete_usermessage);
        this.senduser = findViewById(R.id.senduser);
        this.messagestatus = findViewById(R.id.messagestatus);
        this.edit_usermessage.setOnClickListener(this);
        this.delete_usermessage.setOnClickListener(this);
        this.msgedit.setText(this.message + "");
        if (this.sender.equals("yes")) {
            this.senduser.check(R.id.me);
        } else {
            this.senduser.check(R.id.myfriend);
        }
        if (this.status.equals("send")) {
            this.messagestatus.check(R.id.send);
        } else if (this.status.equals("receive")) {
            this.messagestatus.check(R.id.receive);
        } else {
            this.messagestatus.check(R.id.read);
        }
        this.senduser.setOnCheckedChangeListener(new btnSendUserListner());
        this.messagestatus.setOnCheckedChangeListener(new btnMessageStatusListner());
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.delete_usermessage:
                databaseHelper.DeleteMessage(chatid);
                Intent editmessageintent = new Intent(GBV_EditMessageActivity.this, GBV_UserChat.class);
                editmessageintent.putExtra("USER_ID", position);
                editmessageintent.putExtra("USER_NAME", username);
                editmessageintent.putExtra("USER_ONLINE", online);
                editmessageintent.putExtra("USER_TYPING", typing);
                editmessageintent.putExtra("USER_PROFILE", profile);
                startActivity(editmessageintent);
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                return;
            case R.id.edit_usermessage:
                message = msgedit.getText().toString();
                databaseHelper.UpdateMessageDetails(chatid, sender, message, status);
                Intent intent = new Intent(GBV_EditMessageActivity.this, GBV_UserChat.class);
                intent.putExtra("USER_ID", position);
                intent.putExtra("USER_NAME", username);
                intent.putExtra("USER_ONLINE", online);
                intent.putExtra("USER_TYPING", typing);
                intent.putExtra("USER_PROFILE", profile);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                return;
            default:
                return;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
