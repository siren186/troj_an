package com.troj.demo.learn.ui.main.utils;

import android.graphics.drawable.Drawable;

public class FragmentInfo {
    private Class mFragmentClass = null;
    private String mTitle = null;
    private Drawable mIcon = null;

    public FragmentInfo(Class<?> fragmentClass, String title, Drawable icon) {
        mFragmentClass = fragmentClass;
        mTitle = title;
        mIcon = icon;

        // TextView.setCompoundDrawables()方法传入的Drawable对象, 得执行下面这句, 才能正常显示
        mIcon.setBounds(0, 0, mIcon.getMinimumWidth(), mIcon.getMinimumHeight());
    }

    public final Class getFragmentClass() {
        return mFragmentClass;
    }

    public final String getTitle() {
        return mTitle;
    }

    public final Drawable getIcon() {
        return mIcon;
    }
}
