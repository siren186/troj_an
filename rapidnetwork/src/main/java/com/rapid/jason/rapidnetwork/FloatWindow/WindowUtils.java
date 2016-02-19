package com.rapid.jason.rapidnetwork.FloatWindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.rapid.jason.rapidnetwork.R;

public class WindowUtils {

    private static final String LOG_TAG = WindowUtils.class.getSimpleName();
    private static View mView = null;
    private static WindowManager mWindowManager = null;
    private static Context mContext = null;

    public static Boolean isShown = false;

    private static WindowManager.LayoutParams params = null;

    private static float mTouchStartX = 0;
    private static float mTouchStartY = 0;

    public static void showPopupWindow(final Context context) {
        if (isShown) {
            return;
        }

        isShown = true;

        // 获取应用的Context
        mContext = context.getApplicationContext();
        // 获取WindowManager
        mWindowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);

        mView = setUpView(context);

        params = new WindowManager.LayoutParams();

        // 类型
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

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

        params.width = 300;
        params.height = 200;

        params.x = 0;
        params.y = 0;

        params.gravity = Gravity.LEFT|Gravity.TOP;

        mWindowManager.addView(mView, params);
    }

    /**
     * 隐藏弹出框
     */
    public static void hidePopupWindow() {
        if (isShown && null != mView) {
            mWindowManager.removeView(mView);
            isShown = false;
        }

    }

    public static void updateView(int x, int y) {
        params.x = x;
        params.y = y;
        mWindowManager.updateViewLayout(mView, params);
    }

    private static View setUpView(final Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow,
                null);
        Button positiveBtn = (Button) view.findViewById(R.id.positiveBtn);
        positiveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 打开安装包
                // 隐藏弹窗
                //WindowUtils.hidePopupWindow();

            }
        });

        Button negativeBtn = (Button) view.findViewById(R.id.negativeBtn);
        negativeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //WindowUtils.hidePopupWindow();

            }
        });

        // 点击窗口外部区域可消除
        // 这点的实现主要将悬浮窗设置为全屏大小，外层有个透明背景，中间一部分视为内容区域
        // 所以点击内容区域外部视为点击悬浮窗外部
        final View popupWindowView = view.findViewById(R.id.popup_window);// 非透明的内容区域

        view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getRawX();
                int y = (int) event.getRawY() - 25;

                Rect rect = new Rect();
                popupWindowView.getGlobalVisibleRect(rect);

                if (!rect.contains((int)event.getX(), (int)event.getY())) {
                    return false;
                }

                System.out.println("xy" + x + ":" + y);

                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mTouchStartX = event.getX();
                        mTouchStartY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        WindowUtils.updateView(x-(int)mTouchStartX, y-(int)mTouchStartY);
                        break;
                    case MotionEvent.ACTION_UP:
                        WindowUtils.updateView(x-(int)mTouchStartX, y-(int)mTouchStartY);
                        mTouchStartX = mTouchStartY = 0;
                        break;
                }

                return false;
            }
        });

        // 点击back键可消除
        view.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:
                        WindowUtils.hidePopupWindow();
                        return true;
                    default:
                        return false;
                }
            }
        });

        return view;

    }
}
