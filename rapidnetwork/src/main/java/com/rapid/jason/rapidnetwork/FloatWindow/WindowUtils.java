package com.rapid.jason.rapidnetwork.FloatWindow;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.rapid.jason.rapidnetwork.R;

public class WindowUtils {

    private final String LOG_TAG = WindowUtils.class.getSimpleName();
    private View mView = null;
    private WindowManager mWindowManager = null;
    private Context mContext = null;

    public Boolean isShown = false;

    private WindowManager.LayoutParams params = null;

    private FloatWinWebPage floatWinWebPage = null;
    private FloatWinCalendar floatWinCalendar = null;

    private FloatView mFloatView = null;

    public void showPopupWindow(final Context context) {
        if (isShown) {
            return;
        }

        isShown = true;

        // 获取应用的Context
        mContext = context;
        // 获取WindowManager
        mWindowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);

        params = new WindowManager.LayoutParams();

        // 类型
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        // WindowManager.LayoutParams.TYPE_SYSTEM_ALERT

        // 设置flag
        int flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
        params.flags = flags;
        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.TRANSLUCENT;
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // 设置 FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
        // 不设置这个flag的话，home页的划屏会有问题

        params.gravity = Gravity.LEFT | Gravity.TOP;

        params.x = 0;
        params.y = 0;

        mView = setUpView(context);
        mFloatView.setFloatWin(params);

        params.width = mFloatView.getViewWidthEx();
        params.height = mFloatView.getViewHeightEx();

        mWindowManager.addView(mView, params);
    }

    private View setUpView(final Context context) {

        //View view = LayoutInflater.from(context).inflate(R.layout.popupwindow, null);
        floatWinCalendar = new FloatWinCalendar();
        floatWinWebPage = new FloatWinWebPage();

        mFloatView = new FloatView(context);//(FloatView) view.findViewById(R.id.popup_window_btn);
        mFloatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setComponent(new ComponentName("com.android.calendar", "com.android.calendar.LaunchActivity"));
                mContext.startActivity(intent);*/

                floatWinWebPage.showFloatWinWebpage(mContext);
//                floatWinCalendar.showFloatWinCalendar(mContext);
            }
        });

        return mFloatView;

    }
}
