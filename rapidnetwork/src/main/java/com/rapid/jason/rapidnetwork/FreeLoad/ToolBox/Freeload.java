package com.rapid.jason.rapidnetwork.FreeLoad.toolbox;

import android.content.Context;

import com.rapid.jason.rapidnetwork.FreeLoad.core.RequestQueue;

public class Freeload {

    public static RequestQueue newRequestQueue(Context context) {
        RequestQueue queue = new RequestQueue();
        queue.start();

        return queue;
    }
}
