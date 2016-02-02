package com.freeload.jason.toolbox;

import com.rapid.jason.rapidnetwork.FreeLoad.core.Response;
import com.rapid.jason.rapidnetwork.FreeLoad.core.Request;

public class DownloadRequest extends Request<String>{

    private final Response.Listener<String> listener;

    public DownloadRequest(int id, String Url, Response.Listener<String> listener) {
        this(id, Url, 0, listener);
    }

    public DownloadRequest(int id, String Url, int downloadStart, Response.Listener<String> listener) {
        this(id, Url, downloadStart, null, listener);
    }

    public DownloadRequest(int id, String Url, int downloadStart, String fileName, Response.Listener<String> listener) {
        this(id, Url, downloadStart, fileName, null, listener);
    }

    public DownloadRequest(int id, String Url, int downloadStart, String fileName, String fileFolder, Response.Listener<String> listener) {
        this(id, Url, downloadStart, fileName, fileFolder, 0, listener);
    }

    public DownloadRequest(int id, String Url, int downloadStart, String fileName, String fileFolder, int downloadLength, Response.Listener<String> listener) {
        super(id, Url, downloadStart, fileName, fileFolder, downloadLength);
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
