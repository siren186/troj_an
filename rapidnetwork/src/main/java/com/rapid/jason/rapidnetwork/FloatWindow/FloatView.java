package com.rapid.jason.rapidnetwork.FloatWindow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rapid.jason.rapidnetwork.CollectPreferencesManager;
import com.rapid.jason.rapidnetwork.DownloadFile.DownloadEvent;
import com.rapid.jason.rapidnetwork.R;

import de.greenrobot.event.EventBus;

public class FloatView extends LinearLayout {

    private int mTouchStartX = 0;
    private int mTouchStartY = 0;

    private int mEventStartX = 0;
    private int mEventStartY = 0;

    private int mLastEndX = 0;
    private int mLastEndY = 0;

    private WindowManager mWindowManager = null;
    private WindowManager.LayoutParams mWindowManagerParams = null;

    private TextView mCollectNumberView = null;

    private OnClickListener mClickListener = null;

    private int mViewWidth = 0;
    private int mViewHeight = 0;

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
        LayoutInflater.from(context).inflate(R.layout.floatwin_layout, this);
        mCollectNumberView = (TextView) findViewById(R.id.floatwin_collect_number);

        int size = CollectPreferencesManager.getInstance().getCollectionWebSize();
        mCollectNumberView.setText("" + size);
        mCollectNumberView.setTextColor(Color.WHITE);

        View view = findViewById(R.id.floatwin_layout);
        mViewWidth = view.getLayoutParams().width;
        mViewHeight = view.getLayoutParams().height;

        EventBus.getDefault().register(this);
    }

    public int getViewWidthEx() {
        return mViewWidth;
    }

    public int getViewHeightEx() {
        return mViewHeight;
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

                    Point point = new Point();
                    mWindowManager.getDefaultDisplay().getSize(point);

                    updateView(point.x - 50 - mTouchStartX, (point.y / 4) - 50 - 25 - mTouchStartY);
                    mLastEndX = point.x - 50 - mTouchStartX;
                    mLastEndY = point.y - 50 - 25 - mTouchStartY;
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

    public void updateView() {
        int size = CollectPreferencesManager.getInstance().getCollectionWebSize();
        mCollectNumberView.setText("" + size);

        updateView(mTouchStartX - mLastEndX, mTouchStartY - mLastEndY);
    }

    @Override
    public void setOnClickListener(OnClickListener listener) {
        this.mClickListener = listener;
    }

    public void onEvent(DownloadEvent event) {
        String msg = event.getMsg();
        if (msg.equals("flash")) {
            updateView();
        }
    }
}
