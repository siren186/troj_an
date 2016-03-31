package com.rapid.jason.rapidnetwork;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class PreferencesManager {

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    public SharedPreferences getPreferences() {
        return mPreferences;
    }

    public PreferencesManager(Context context, String name, int mode) {
        if (context != null) {
            try {
                mPreferences = context.getSharedPreferences(name, mode);
                mEditor = mPreferences.edit();
            } catch (Exception e) {
                // TODO: handle exception
            }

        }
    }

    public void clear() {
        if (mEditor != null) {
            mEditor.clear().commit();
        } else if (mPreferences != null) {
            mEditor = mPreferences.edit();
            mEditor.clear().commit();
        }
    }

    public void remove(String key) {
        mPreferences.edit().remove(key).commit();
    }

    public Map<String, ?> getAll() {
        return mPreferences.getAll();
    }

    public boolean contains(String key) {
        return mPreferences.contains(key);
    }

    public boolean getBoolean(String key, boolean defValue) {
        if (mPreferences != null) {
            return mPreferences.getBoolean(key, defValue);
        }
        return defValue;
    }

    public float getFloat(String key, float defValue) {
        if (mPreferences != null) {
            return mPreferences.getFloat(key, defValue);
        }
        return defValue;
    }

    public int getInt(String key, int defValue) {
        if (mPreferences != null) {
            return mPreferences.getInt(key, defValue);
        }
        return defValue;
    }

    public long getLong(String key, long defValue) {
        if (mPreferences != null) {
            return mPreferences.getLong(key, defValue);
        }
        return defValue;
    }

    public String getString(String key, String defValue) {
        if (mPreferences != null) {
            return mPreferences.getString(key, defValue);
        }
        return defValue;
    }

    public void putBoolean(String key, boolean b) {
        if (mEditor != null) {
            mEditor.putBoolean(key, b);
        }
    }

    public void putInt(String key, int i) {
        if (mEditor != null) {
            mEditor.putInt(key, i);
        }
    }

    public void putFloat(String key, float f) {
        if (mEditor != null) {
            mEditor.putFloat(key, f);
        }
    }

    public void putLong(String key, long l) {
        if (mEditor != null) {
            mEditor.putLong(key, l);
        }
    }

    public void putString(String key, String s) {
        if (mEditor != null) {
            mEditor.putString(key, s);
        }
    }

    public boolean commit() {
        boolean bRet = false;
        if (mEditor != null) {
            bRet = mEditor.commit();
        }
        return bRet;
    }
}
