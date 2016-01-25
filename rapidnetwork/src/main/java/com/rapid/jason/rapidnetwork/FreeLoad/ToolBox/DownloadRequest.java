package com.rapid.jason.rapidnetwork.FreeLoad.toolbox;

import android.os.Environment;

import com.rapid.jason.rapidnetwork.FreeLoad.core.Response;
import com.rapid.jason.rapidnetwork.FreeLoad.core.Request;

public class DownloadRequest extends Request<String>{

    private final Response.Listener<String> listener;

    public DownloadRequest(String Url, Response.Listener<String> listener) {
        this(Url, null, listener);
    }

    public DownloadRequest(String Url, String fileName, Response.Listener<String> listener) {
        this(Url, fileName, null, listener);
    }

    public DownloadRequest(String Url, String fileName, String fileFolder, Response.Listener<String> listener) {
        this(Url, fileName, fileFolder, 0, listener);
    }

    public DownloadRequest(String Url, String fileName, String fileFolder, int downloadLength, Response.Listener<String> listener) {
        super(Url, fileName, fileFolder, downloadLength);
        this.listener = listener;
    }

    @Override
    protected void deliverResponse(String response) {
        if (this.listener != null) {
            this.listener.onResponse(response);
        }
    }

    @Override
    public void deliverDownloadProgress(long fileSize, long downloadedSize) {
        if (this.listener != null) {
            this.listener.onProgressChange(fileSize, downloadedSize);
        }
    }
}
