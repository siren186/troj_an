package com.rapid.jason.rapidnetwork.ListViewUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Scroller;

public class SlideCutListView extends ListView {

    private int slidePosition;

    private int downY;
    private int downX;

    private int screenWidth;

    private View itemView;

    private Scroller scroller;
    private static final int SNAP_VELOCITY = 600;

    private VelocityTracker velocityTracker;

    private boolean isSlide = false;

    private int mTouchSlop;

    private MoveDirection mMoveDirection;
    private MoveListener mMoveListener;

    public enum MoveDirection {
        RIGHT, LEFT;
    }

    public SlideCutListView(Context context) {
        this(context, null);
    }

    public SlideCutListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideCutListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;

        scroller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public void setMoveListener(MoveListener moveListener) {
        this.mMoveListener = moveListener;
    }

    public interface MoveListener {
        public void moveItem(MoveDirection direction, int position);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                addVelocityTracker(event);

                if (!isScrollerEffective(event)) {
                    return super.dispatchTouchEvent(event);
                }

                itemView = getChildAt(slidePosition - getFirstVisiblePosition());
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (Math.abs(getScrollVelocity()) > SNAP_VELOCITY ||
                        (Math.abs(event.getX() - downX) > mTouchSlop &&
                        Math.abs(event.getY() - downY) < mTouchSlop)) {
                    isSlide = true;
                }
                break;
            }
            case MotionEvent.ACTION_UP:
                recycleVelocityTracker();
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    private boolean isScrollerEffective(MotionEvent event) {
        if (!scroller.isFinished()) {
            return false;
        }

        downX = (int) event.getX();
        downY = (int) event.getY();

        slidePosition = pointToPosition(downX, downY);

        if (slidePosition == AdapterView.INVALID_POSITION) {
            return false;
        }
        return true;
    }

    private void scrollRight() {
        mMoveDirection = MoveDirection.RIGHT;
        final int delta = (screenWidth + itemView.getScrollX());
        // 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
        scroller.startScroll(itemView.getScrollX(), 0, -delta, 0, Math.abs(delta));
        postInvalidate();
    }

    private void scrollLeft() {
        mMoveDirection = MoveDirection.LEFT;
        final int delta = (screenWidth - itemView.getScrollX());
        // 调用startScroll方法来设置一些滚动的参数，我们在computeScroll()方法中调用scrollTo来滚动item
        scroller.startScroll(itemView.getScrollX(), 0, delta, 0, Math.abs(delta));
        postInvalidate();
    }

    private void scrollByDistanceX() {
        if (itemView.getScrollX() >= screenWidth / 2) {
            scrollLeft();
        } else if (itemView.getScrollX() <= -screenWidth / 2) {
            scrollRight();
        } else {
            itemView.scrollTo(0, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isSlide && slidePosition != AdapterView.INVALID_POSITION) {
            requestDisallowInterceptTouchEvent(true);
            addVelocityTracker(ev);
            final int action = ev.getAction();
            int x = (int) ev.getX();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    MotionEvent cancelEvent = MotionEvent.obtain(ev);
                    cancelEvent.setAction(MotionEvent.ACTION_CANCEL |
                            (ev.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));
                    onTouchEvent(cancelEvent);

                    int deltaX = downX - x;
                    downX = x;

                    // 手指拖动itemView滚动, deltaX大于0向左滚动，小于0向右滚
                    itemView.scrollBy(deltaX, 0);

                    return true;  //拖动的时候ListView不滚动
                case MotionEvent.ACTION_UP:
                    int velocityX = getScrollVelocity();
                    if (velocityX > SNAP_VELOCITY) {
                        scrollRight();
                    } else if (velocityX < -SNAP_VELOCITY) {
                        scrollLeft();
                    } else {
                        scrollByDistanceX();
                    }

                    recycleVelocityTracker();
                    isSlide = false;
                    break;
            }
        }

        //否则直接交给ListView来处理onTouchEvent事件
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (!scroller.computeScrollOffset()) {
            return;
        }

        itemView.scrollTo(scroller.getCurrX(), scroller.getCurrY());
        postInvalidate();

        if (!scroller.isFinished()) {
            return;
        }

        itemView.scrollTo(0, 0);

        if (mMoveListener != null) {
            mMoveListener.moveItem(mMoveDirection, slidePosition);
        }
    }

    private void addVelocityTracker(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
    }

    private void recycleVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

    private int getScrollVelocity() {
        velocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) velocityTracker.getXVelocity();
        return velocity;
    }
}
