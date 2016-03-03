package com.rapid.jason.rapidnetwork.FloatWindow;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;

public class FloatView extends ImageView {

    private int mTouchStartX = 0;
    private int mTouchStartY = 0;

    private int mEventStartX = 0;
    private int mEventStartY = 0;

    private WindowManager mWindowManager = null;
    private WindowManager.LayoutParams mWindowManagerParams = null;

    private OnClickListener mClickListener = null;

    public FloatView(Context context) {
        super(context);
        initFloatwin(context);
    }

    public FloatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFloatwin(context);
    }

    public void initFloatwin(Context context) {
        mWindowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    }

    public void setFloatWin(WindowManager.LayoutParams params) {
        mWindowManagerParams = params;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY() - 25;

        Rect rect = new Rect();
        getGlobalVisibleRect(rect);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchStartX = (int) event.getX();
                mTouchStartY = (int) event.getY() + 25;

                mEventStartX = rawX;
                mEventStartY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                updateView(rawX - mTouchStartX, rawY - mTouchStartY);
                break;
            case MotionEvent.ACTION_UP:
                int moveDistanceX = rawX - mEventStartX;
                int moveDistanceY = rawY - mEventStartY;
                if ((moveDistanceX > 30 || moveDistanceX < -30) ||
                    (moveDistanceY > 30 || moveDistanceY < -30)) {
                    mTouchStartX = mTouchStartY = 0;
                } else {
                    mClickListener.onClick(this);
                }
                break;
            default:
                break;
        }

        return true;
    }

    public void updateView(int x, int y) {
        mWindowManagerParams.x = x;
        mWindowManagerParams.y = y;
        mWindowManager.updateViewLayout(this, mWindowManagerParams);
    }

    @Override
    public void setOnClickListener(OnClickListener listener) {
        this.mClickListener = listener;
    }
}
