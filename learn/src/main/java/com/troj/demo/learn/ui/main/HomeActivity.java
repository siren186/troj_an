package com.troj.demo.learn.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.troj.demo.learn.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class HomeActivity extends Activity {

    private long mBackKeyPressedTime = 0;
    @Bind(R.id.activity_home_tv_test)
    TextView mTvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String str = intent.getStringExtra("tv_test");
        if (!TextUtils.isEmpty(str)) {
            mTvTest.setText(str);
        }
    }

    @Override
    public void onBackPressed() {
        if((System.currentTimeMillis() - mBackKeyPressedTime) > 2000){
            Toast.makeText(getApplicationContext(), R.string.press_once_more_to_exit, Toast.LENGTH_SHORT).show();
            mBackKeyPressedTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
            finish();
            System.exit(0);
        }
    }
}
