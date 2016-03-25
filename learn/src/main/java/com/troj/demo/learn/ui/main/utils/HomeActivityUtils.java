package com.troj.demo.learn.ui.main.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.troj.demo.learn.R;

public class HomeActivityUtils {

    private static long mBackKeyPressedTime = 0;
    private static final int MAX_WAIT_BACK_KEY_PRESSE_TIME = 2000;

    public static class FragmentInfo {
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

    public static boolean handleExitApp(Context ctx) {
        if (null != ctx) {
            if((System.currentTimeMillis() - mBackKeyPressedTime) > MAX_WAIT_BACK_KEY_PRESSE_TIME) {
                Toast.makeText(ctx, R.string.press_once_more_to_exit, Toast.LENGTH_SHORT).show();
                mBackKeyPressedTime = System.currentTimeMillis();
                return true;
            }
        }
        return false;
    }
}
