package com.rapid.jason.rapidnetwork.FreeLoad.core;

public interface ResponseDelivery {

    /**
     * Parses a response from the network or cache and delivers it.
     */
    public void postResponse(Request<?> request);

    /**
     * Parses a response from the network or cache and delivers it.
     */
    public void postResponse(Request<?> request, Response<?> response);

    /**
     * Parses a response from the network or cache and delivers it. The provided
     * Runnable will be executed after delivery.
     */
    public void postResponse(Request<?> request, Response<?> response, Runnable runnable);

    /** Posts file download progress stat. */
    public void postDownloadProgress(Request<?> request, long fileSize, long downloadedSize);
}
