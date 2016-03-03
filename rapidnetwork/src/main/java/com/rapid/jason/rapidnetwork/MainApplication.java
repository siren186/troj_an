package com.rapid.jason.rapidnetwork;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {

    public static Context mContext = null;

    @Override
    public void onCreate() {
        //ªÒ»°Context
        mContext = getApplicationContext();
    }
}
