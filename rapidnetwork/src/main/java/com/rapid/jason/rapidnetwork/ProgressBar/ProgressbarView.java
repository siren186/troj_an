package com.rapid.jason.rapidnetwork.ProgressBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.rapid.jason.rapidnetwork.R;

public class ProgressbarView extends RelativeLayout {
    private Context mContext;

    private ImageView mScanImage;
    private RelativeLayout mLayoutParams;
    private RelativeLayout mParentLayoutParams;
    private RelativeLayout mLayoutUpParams;

    private Animation mScanAnimation;

    public ProgressbarView(Context context) {
        super(context);
    }

    public ProgressbarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProgressbarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        View view = View.inflate(context, R.layout.app_manage_scan_apk_path_layout, this);
        mScanImage = (ImageView) view.findViewById(R.id.scan_image);

        mParentLayoutParams = (RelativeLayout) view.findViewById(R.id.progressbar_layout);
        mLayoutParams = (RelativeLayout) view.findViewById(R.id.progress_layout);
        mLayoutUpParams = (RelativeLayout) view.findViewById(R.id.progress_up_layout);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ScanPathView);
        mScanImage.setImageResource(typedArray.getResourceId(R.styleable.ScanPathView_image_bkg, R.drawable.apk_scan_path_selector));

        typedArray.recycle();

        mScanAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_scan);
        mScanImage.setAnimation(mScanAnimation);
    }

    public void startProgressbarAnimation() {
        if (!mScanAnimation.hasStarted()) {
            mScanAnimation.start();
        }
    }

    public void setStopProgress() {
        mScanImage.clearAnimation();
    }

    public void setProgress(int size, String dutime, String startColor, String centerColor, String endColor) {
        int width = mParentLayoutParams.getWidth();
        float widthPer = (float) width / 100;

        RelativeLayout.LayoutParams layoutUpParams = (RelativeLayout.LayoutParams) mLayoutUpParams.getLayoutParams();
        layoutUpParams.width = (int) ((100 - size) * widthPer);
        mLayoutUpParams.setLayoutParams(layoutUpParams);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mLayoutParams.getLayoutParams();
        layoutParams.width = (int) ((size + 1) * widthPer);
        mLayoutParams.setLayoutParams(layoutParams);

        mScanAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_scan);
        mScanAnimation.setDuration(Long.parseLong(dutime));
        mScanImage.setAnimation(mScanAnimation);

        int[] color = new int[3];
        color[0] = Color.parseColor(startColor);
        color[1] = Color.parseColor(centerColor);
        color[2] = Color.parseColor(endColor);

        GradientDrawable mDrawable = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT,
                new int[] { color[0], color[1], color[2] });
        mScanImage.setImageDrawable(mDrawable);
        mScanImage.setMaxWidth(10);

        startProgressbarAnimation();
    }

    public void stopProgressbarAnimation() {
        if (mScanAnimation.hasStarted()) {
            mScanAnimation.cancel();
        }
    }
}
