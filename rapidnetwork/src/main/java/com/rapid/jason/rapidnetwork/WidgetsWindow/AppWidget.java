package com.rapid.jason.rapidnetwork.WidgetsWindow;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Button;
import android.widget.RemoteViews;

import com.rapid.jason.rapidnetwork.FloatWindow.WindowUtils;
import com.rapid.jason.rapidnetwork.MainActivity;
import com.rapid.jason.rapidnetwork.R;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        System.out.println("onUpdate");

        final int length = appWidgetIds.length;
        for (int i = 0; i < length; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }


    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        System.out.println("onEnabled");
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        System.out.println("onDisabled");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("onReceive");

        if (intent == null) {
            return;
        }

        Uri uri = intent.getData();
        if (uri != null) {
            /*WindowUtils windowUtils = new WindowUtils();
            windowUtils.showPopupWindow(context);*/
        }

        super.onReceive(context, intent);
    }

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);

        // Create an Intent to launch ExampleActivity
//        Intent intent = new Intent(context, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        setOpenDateWindow(context, views);
        setOpenNetworkWindow(context, views);
        setOpenCMSInfo(context, views);
        setOpenSJZSInfo(context, views);
        setOpenCalendar(context, views);
        setFastCall(context, views);
        setFloatwin(context, views);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private void setOpenDateWindow(Context context, RemoteViews views) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings","com.android.settings.DateTimeSettingsSetupWizard");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.btn_time, pendingIntent);
    }

    private void setOpenNetworkWindow(Context context, RemoteViews views) {
        Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.btn_network, pendingIntent);
    }

    private void setOpenCMSInfo(Context context, RemoteViews views) {
        Uri packageURI = Uri.parse("package:" + "com.cleanmaster.security_cn");
        Intent intent =  new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.button6, pendingIntent);
    }

    private void setOpenSJZSInfo(Context context, RemoteViews views) {
        Uri packageURI = Uri.parse("package:" + "com.ijinshan.ShouJiKongService");
        Intent intent =  new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.button7, pendingIntent);
    }

    private void setOpenCalendar(Context context, RemoteViews views) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.android.calendar", "com.android.calendar.LaunchActivity"));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.btn_calendar, pendingIntent);
    }

    private void setFastCall(Context context, RemoteViews views) {
        Uri phoneCallURI = Uri.parse("tel:552");
        Intent intent =  new Intent(Intent.ACTION_CALL, phoneCallURI);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        views.setOnClickPendingIntent(R.id.btn_fastcall, pendingIntent);
    }

    private void setCollection(Context context, RemoteViews views) {
//        Uri phoneCallURI = Uri.parse("tel:552");
//        Intent intent =  new Intent(Intent.ACTION_CALL, phoneCallURI);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//
//        views.setOnClickPendingIntent(R.id.btn_collection, pendingIntent);
    }

    private void setFloatwin(Context context, RemoteViews views) {
        Intent intent = new Intent(context, WidgetService.class);
        intent.setData(Uri.parse("show://com.rapid.jason/floatwin"));
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.btn_floatwin, pendingIntent);
    }
}

