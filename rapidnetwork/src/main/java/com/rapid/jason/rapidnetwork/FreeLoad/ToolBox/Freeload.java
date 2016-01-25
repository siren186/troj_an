package com.rapid.jason.rapidnetwork.FreeLoad.toolbox;

import android.content.Context;

import com.rapid.jason.rapidnetwork.FreeLoad.core.RequestQueue;

public class Freeload {

    public static RequestQueue newRequestQueue(Context context) {
        BasicDownload basicDownload = new BasicDownload();
        PrepareDownload prepareDownload = new PrepareDownload();

        RequestQueue queue = new RequestQueue(basicDownload, prepareDownload);
        queue.start();

        return queue;
    }
}
