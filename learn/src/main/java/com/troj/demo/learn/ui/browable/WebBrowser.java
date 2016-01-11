package com.troj.demo.learn.ui.browable;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.troj.demo.learn.ui.main.HomeActivity;

public class WebBrowser extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if(uri != null) {
                Intent it = new Intent(this, HomeActivity.class);
                it.putExtra("tv_test", uri.toString());
                startActivity(it);
            }
        }

        finish();
    }
}
