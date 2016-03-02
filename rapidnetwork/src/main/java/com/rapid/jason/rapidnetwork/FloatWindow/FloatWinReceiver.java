package com.rapid.jason.rapidnetwork.FloatWindow;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class FloatWinReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("FloatWinReceiver:onReceive");

        WindowUtils windowUtils = new WindowUtils();
        windowUtils.showPopupWindow(context);
    }
}
