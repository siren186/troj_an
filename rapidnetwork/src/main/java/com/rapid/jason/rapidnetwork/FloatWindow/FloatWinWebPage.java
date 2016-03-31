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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rapid.jason.rapidnetwork.CollectPreferencesManager;
import com.rapid.jason.rapidnetwork.DownloadFile.DownloadEvent;
import com.rapid.jason.rapidnetwork.MainApplication;
import com.rapid.jason.rapidnetwork.R;

import de.greenrobot.event.EventBus;

public class FloatWinWebPage {

    private final String LOG_TAG = FloatWinWebPage.class.getSimpleName();

    private View mView = null;
    private WindowManager mWindowManager = null;
    private Context mContext = null;

    private Boolean isShown = false;

    private WindowManager.LayoutParams params = null;

    private View mWindowView = null;
    private WebView mWebView = null;
    private TextView mTextView = null;
    private ImageButton mCloseBtn = null;
    private ImageButton mCollectBtn = null;

    private String mWebUrl = "http://www.baidu.com";

    public void showFloatWinWebpage(final Context context) {
        if (isShown) {
            return;
        }

        isShown = true;

        mContext = context.getApplicationContext();
        mWindowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);

        mView = setUpView(context);

        params = new WindowManager.LayoutParams();

        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

        // WindowManager.LayoutParams.TYPE_SYSTEM_ALERT

        int flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.flags = flags;
        params.format = PixelFormat.TRANSLUCENT;

        Point point = new Point();
        mWindowManager.getDefaultDisplay().getSize(point);
        int height = point.y / 4 * 3;
        params.height = height;

        params.x = 0;
        params.y = 0;

        params.gravity = Gravity.BOTTOM;

        mWindowManager.addView(mView, params);
    }

    public void hideFloatWinWebpage() {
        if (isShown && null != mView) {
            mWindowManager.removeView(mView);
            isShown = false;
        }
    }

    private View setUpView(final Context context) {
        View view = null;
        view = LayoutInflater.from(context).inflate(R.layout.floatwin_webpage, null);

        setTouchAction(view);

        return view;
    }

    private void setTouchAction(View view) {
        mCloseBtn = (ImageButton) view.findViewById(R.id.floatwin_webpage_btn_close);
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFloatWinWebpage();
            }
        });

        mCollectBtn = (ImageButton) view.findViewById(R.id.floatwin_webpage_btn_collect);
        mCollectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CollectPreferencesManager.getInstance().setCollectionWeb(mWebUrl);
                Toast.makeText(MainApplication.mContext, "收藏" + mWebUrl, Toast.LENGTH_SHORT).show();
                EventBus.getDefault().post(new DownloadEvent("flash"));
            }
        });

        mWebView = (WebView) view.findViewById(R.id.floatwin_webpage_webView);
        mWebView.loadUrl(mWebUrl);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.requestFocus(View.FOCUS_DOWN);

        mTextView = (TextView) view.findViewById(R.id.floatwin_webpage_text_url);
        mTextView.setText(mWebUrl);

        mWindowView = view.findViewById(R.id.floatwin_webpage_layout);
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
                            hideFloatWinWebpage();
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
