package com.rapid.jason.rapidnetwork.FloatWindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.media.Image;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.rapid.jason.rapidnetwork.R;

public class WindowUtils {

    private final String LOG_TAG = WindowUtils.class.getSimpleName();
    private View mView = null;
    private WindowManager mWindowManager = null;
    private Context mContext = null;

    public Boolean isShown = false;

    private WindowManager.LayoutParams params = null;

    private float mTouchStartX = 0;
    private float mTouchStartY = 0;

    private FloatWinCalendar floatWinCalendar = null;
    private View popupWindowView = null;

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

        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.width = 200;
        params.height = 200;

        params.x = 0;
        params.y = 0;

        mView = setUpView(context);
        mFloatView.setFloatWin(params);

        mWindowManager.addView(mView, params);
    }

    public void hidePopupWindow() {
        if (isShown && null != mView) {
            mWindowManager.removeView(mView);
            isShown = false;
        }
    }

    public void updateView(int x, int y) {
        params.x = x;
        params.y = y;
        mWindowManager.updateViewLayout(mView, params);
    }

    private View setUpView(final Context context) {

         /*View view = LayoutInflater.from(context).inflate(R.layout.popupwindow, null);*/
        floatWinCalendar = new FloatWinCalendar();

        mFloatView = new FloatView(context);//(FloatView) view.findViewById(R.id.popup_window_btn);
        mFloatView.setImageResource(R.drawable.floatwin);
        mFloatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatWinCalendar.showFloatWinCalendar(mContext);
            }
        });

        return mFloatView;

    }
}
