package com.troj.demo.learn.ui.main.utils;

import android.content.Context;
import android.widget.Toast;

import com.troj.demo.learn.R;

public class ExitAppUtils {

    private static long mBackKeyPressedTime = 0;
    private static final int MAX_WAIT_BACK_KEY_PRESSE_TIME = 2000;

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
