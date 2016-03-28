package com.troj.demo.learn.ui.main.utils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.troj.demo.learn.ui.utils.ViewUtils;

public class FragmentHolder {
    private View mRootView = null;

    public boolean create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, int resid) {
        boolean isNewCreated = false;

        if (null != mRootView) {
            ViewUtils.removeViewParent(mRootView);
        } else {
            mRootView = inflater.inflate(resid, container, false);
            isNewCreated = true;
        }

        return isNewCreated;
    }

    public View getView() {
        return mRootView;
    }
}
