package com.troj.demo.learn.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.troj.demo.learn.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class HomeActivity extends Activity {

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
}
