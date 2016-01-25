package com.rapid.jason.rapidnetwork.FreeLoad.core;

/**
 * Created by Jzcloud on 16/1/23.
 * Action prepare before download.
 */
public interface Prepare {
    public boolean preparePerform(Request<?> request);
}
