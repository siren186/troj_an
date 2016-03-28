package com.troj.demo.learn.ui.utils;

import android.view.View;
import android.view.ViewGroup;

public class ViewUtils {
    public static void removeViewParent(View v) {
        if (null != v) {
            ViewGroup parent = (ViewGroup) v.getParent();
            if (null != parent) {
                parent.removeView(v);
            }
        }
    }
}
