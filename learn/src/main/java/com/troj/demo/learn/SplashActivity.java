package com.troj.demo.learn;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.troj.demo.learn.logic.init_app.AppInit;
import com.troj.demo.learn.ui.main.HomeActivity;

public class SplashActivity extends Activity {

    private static final long SHOW_TIME = 1000;
    private AsyncTask<Void, Void, Integer> mInitTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        startTask();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unInit();
    }

    private void init() {
        mInitTask = new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                return initAppGlobalField();
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                skip2homeActivity();
            }
        };
    }

    private void unInit() {
        if (null != mInitTask) {
            if (!mInitTask.isCancelled()) {
                mInitTask.cancel(true);
            }
            mInitTask = null;
        }
    }

    private void startTask() {
        if (null != mInitTask) {
            mInitTask.execute();
        }
    }

    private Integer initAppGlobalField() {
        Integer ret;
        long startTime = System.currentTimeMillis();
        ret = AppInit.getInstance().init(SplashActivity.this);
        long loadingTime = System.currentTimeMillis() - startTime;

        if (loadingTime < SHOW_TIME) {
            try {
                Thread.sleep(SHOW_TIME - loadingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }

    private void skip2homeActivity() {
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        finish();
    }
}
