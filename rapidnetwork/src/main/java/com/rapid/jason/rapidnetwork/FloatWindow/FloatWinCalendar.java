package com.rapid.jason.rapidnetwork.FloatWindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CalendarView;
import android.widget.ImageButton;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.rapid.jason.rapidnetwork.R;

public class FloatWinCalendar {

    private final String LOG_TAG = FloatWinCalendar.class.getSimpleName();
    private View mView = null;
    private WindowManager mWindowManager = null;
    private Context mContext = null;

    private Boolean isShown = false;

    private WindowManager.LayoutParams params = null;

    private View mWindowView = null;

    private ImageButton mCloseBtn = null;

    public void showFloatWinCalendar(final Context context) {
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

        Point point = new Point();
        mWindowManager.getDefaultDisplay().getSize(point);
        int height = point.y / 4 * 3;
        params.height = height;

        params.x = 0;
        params.y = 0;

        params.gravity = Gravity.BOTTOM;

        mWindowManager.addView(mView, params);
    }

    public void hideFloatWinCalendar() {
        if (isShown && null != mView) {
            mWindowManager.removeView(mView);
            isShown = false;
        }
    }

    private View setUpView(final Context context) {
        View view = null;
        view = LayoutInflater.from(context).inflate(R.layout.floatwin_calendar, null);

        setTouchAction(view);

        return view;
    }

    private void setTouchAction(View view) {
        mCloseBtn = (ImageButton) view.findViewById(R.id.floatwin_calendar_btn_close);
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFloatWinCalendar();
            }
        });

        MaterialCalendarView calendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);

        mWindowView = view.findViewById(R.id.floatwin_calendar_layout);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Rect rect = new Rect();
                mWindowView.getGlobalVisibleRect(rect);

                int X = (int) event.getX();
                int Y = (int) event.getY();

                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (!isEffectiveArea(X, Y, rect)) {
                            hideFloatWinCalendar();
                        }
                        break;
                }
                return true;
            }
        });
    }

    private boolean isEffectiveArea(int x, int y, Rect rect) {
        return rect.contains(x, y);
    }
}
