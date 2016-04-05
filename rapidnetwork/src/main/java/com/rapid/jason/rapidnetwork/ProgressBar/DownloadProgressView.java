package com.rapid.jason.rapidnetwork.ProgressBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.rapid.jason.rapidnetwork.R;

import java.util.Timer;
import java.util.TimerTask;

public class DownloadProgressView extends ProgressBar {
    private Timer timer = null;
    private TimerTask timerTask = null;

    private boolean mIsTimerRunning = false;
    private boolean mIsFirst = false;

    private int mCurProgressSize = 0;
    private int mTotalProgressSize = 0;
    private int mProgressLimiteSize = 0;
    private int mNewSize = 0;

    public DownloadProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mProgressLimiteSize = getMax();
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final Drawable d = getProgressDrawable();
        if (d != null) {
            final int saveCount = canvas.save();

            int y = getHeight() / 2 + 3;
            Paint paint = new Paint();
            paint.setColor(getResources().getColor(R.color.Black));

            mNewSize += 1;
            if (mNewSize >= mCurProgressSize) {
                mNewSize = 0;
            }

            canvas.drawText("hello", mNewSize, y, paint);

            d.draw(canvas);
            canvas.restoreToCount(saveCount);

            invalidate();
        }
    }

    public void setProgressCurSize(int progressSize) {
        if (progressSize < 10) {
            mCurProgressSize = 10;
        } else {
            mCurProgressSize = progressSize;
        }
    }

    public void setProgressStop() {
        cancel();
    }

    public void setProgressSize(int size) {
        mCurProgressSize = size;
        startTimer();
    }

    public void setProgressEndSize(int size) {
        setSecondaryProgress(size);
        mCurProgressSize = size;
    }

    public void startTimer() {
        if (timer != null || timerTask != null) {
            return;
        }

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {

                setSecondaryProgress(mCurProgressSize);

                mNewSize += 3;
                if (mNewSize >= mCurProgressSize) {
                    mNewSize = 0;
                }
                setProgress(mNewSize);

//                if (mCurProgressSize >= mProgressLimiteSize || mCurProgressSize >= mTotalProgressSize) {
//                    DownloadProgressView.this.cancel();
//                }
            }
        };

        timer.schedule(timerTask, 0, 20);
        mIsTimerRunning = true;
    }

    public void cancel() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }

        mIsTimerRunning = false;
    }
}
