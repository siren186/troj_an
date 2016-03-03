package com.rapid.jason.rapidnetwork.FloatWindow;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class WebBrowserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if(uri != null) {
                WindowUtils windowUtils = new WindowUtils();
                windowUtils.showPopupWindow(this.getApplicationContext());
            }
        } else if (Intent.ACTION_SEND.equals(action)) {
            ClipData clipData = intent.getClipData();
            ClipData.Item item = clipData.getItemAt(0);
            String url = (String) item.getText();
        }

        finish();
    }
}
