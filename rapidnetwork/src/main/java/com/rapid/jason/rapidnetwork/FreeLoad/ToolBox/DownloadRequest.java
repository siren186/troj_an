package com.rapid.jason.rapidnetwork.FreeLoad.toolbox;

import com.android.volley.Response;
import com.rapid.jason.rapidnetwork.FreeLoad.core.Request;

public class DownloadRequest extends Request<DownloadRequest>{

    private String Url;
    private final Response.Listener<DownloadRequest> listener;

    public DownloadRequest(String url, Response.Listener<DownloadRequest> listener) {
        this.Url = url;
        this.listener = listener;
    }

    @Override
    protected void deliverResponse(DownloadRequest response) {

    }
}
