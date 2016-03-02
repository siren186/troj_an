package com.rapid.jason.rapidnetwork.WidgetsWindow;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.rapid.jason.rapidnetwork.FloatWindow.WindowUtils;

public class WidgetService extends Service {
    private WidgetBroadcastReceiver widgetBroadcastReceiver = new WidgetBroadcastReceiver();

    public WidgetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("to_service");
        registerReceiver(widgetBroadcastReceiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private class WidgetBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            WindowUtils windowUtils = new WindowUtils();
            windowUtils.showPopupWindow(WidgetService.this);
        }
    }
}
