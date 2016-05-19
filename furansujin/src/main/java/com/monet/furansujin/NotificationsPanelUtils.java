package com.monet.furansujin;

import android.content.Context;
import android.os.Build;

import java.lang.reflect.Method;

/**
 * 需要在AndroidManifest.xml中加入如下权限
 *   <uses-permission android:name="android.permission.EXPAND_STATUS_BAR" />
 */
public class NotificationsPanelUtils {

    public static void expandNotificationPanel(Context mContext) {
        String methodName = (Build.VERSION.SDK_INT <= 16) ? "expand" : "expandNotificationsPanel";
        invokeMethod(mContext, methodName);
    }

    public static void collapseNotificationPanel(Context mContext) {
        String methodName = (Build.VERSION.SDK_INT <= 16) ? "collapse" : "collapsePanels";
        invokeMethod(mContext, methodName);
    }

    private static void invokeMethod(Context mContext, String methodName) {
        try {
            Object service = mContext.getSystemService("statusbar");
            Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
            Method expand = statusBarManager.getMethod(methodName);
            expand.invoke(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
