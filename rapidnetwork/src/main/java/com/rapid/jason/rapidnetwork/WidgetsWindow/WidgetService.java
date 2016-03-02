package com.rapid.jason.rapidnetwork.WidgetsWindow;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.IBinder;

import com.rapid.jason.rapidnetwork.FloatWindow.WindowUtils;

public class WidgetService extends Service {
    private WidgetBroadcastReceiver widgetBroadcastReceiver = new WidgetBroadcastReceiver();

    private UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    public WidgetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        System.out.println("WidgetService:onStartCommand");

        Uri uri = intent.getData();
        if (uri == null) {
            return 0;
        }

        String strPath = uri.getPath();
        strPath = strPath.replace("/", "");
        if ("floatwin".equals(strPath)) {
            WindowUtils windowUtils = new WindowUtils();
            windowUtils.showPopupWindow(this.getApplicationContext());
        }

        return 1;
    }

    private class WidgetBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            WindowUtils windowUtils = new WindowUtils();
            windowUtils.showPopupWindow(WidgetService.this);
        }
    }
}
