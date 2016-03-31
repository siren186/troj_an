package com.rapid.jason.rapidnetwork;

import android.content.Context;

public class CollectPreferencesManager extends PreferencesManager {
    private static final String NEWSFEED_PREFERENCE_NAME = "collect_preference_name";

    private static final String KEY_COLLECTION_WEB = "key_collection_web";
    private static final String KEY_COLLECTION_WEB_SIZE = "key_collection_web_size";

    private static CollectPreferencesManager mInstance = null;
    private CollectPreferencesManager() {
        super(MainApplication.mContext, NEWSFEED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static CollectPreferencesManager getInstance() {
        if(mInstance == null){
            mInstance = new CollectPreferencesManager();
        }
        return mInstance;
    }

    public void setCollectionWebSize(int size) {
        putInt(KEY_COLLECTION_WEB_SIZE, size);
        commit();
    }

    public int getCollectionWebSize() {
        return getInt(KEY_COLLECTION_WEB_SIZE, 0);
    }

    public void setCollectionWeb(String url) {
        int size = getCollectionWebSize();
        String indexWeb = String.format("%s%d", KEY_COLLECTION_WEB, size + 1);
        putString(indexWeb, url);

        setCollectionWebSize(size + 1);
        commit();
    }

    public String getCollectionWeb(int index, String def) {
        String indexWeb = String.format("%s%d", KEY_COLLECTION_WEB, index);
        return getString(indexWeb, def);
    }
}
