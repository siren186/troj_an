package com.troj.demo.learn.logic.init_app;

import android.content.Context;

import com.monet.furansujin.ChannelUtils;
import com.xsj.crasheye.Crasheye;

public class AppInit {
    private static AppInit mTheApp = new AppInit();

    public static AppInit getInstance() {
        return mTheApp;
    }

    public Integer init(Context context) {
        initCrasheye(context);
        return 0;
    }

    private void initCrasheye(Context context) {
        Crasheye.init(context, "eaa37bd0");
        Crasheye.setFlushOnlyOverWiFi(true);
        Crasheye.setChannelID(ChannelUtils.getChannel(context));
    }
}
