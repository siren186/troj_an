package com.troj.demo.learn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.xsj.crasheye.Crasheye;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.activity_main_tv1)
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initCrasheye();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        onCreateImpl();
    }

    private void initCrasheye() {
        Crasheye.init(this, "eaa37bd0");
        Crasheye.setFlushOnlyOverWiFi(true);
    }

    private void onCreateImpl() {
        tv1.setText(R.string.helloworld);
    }
}
