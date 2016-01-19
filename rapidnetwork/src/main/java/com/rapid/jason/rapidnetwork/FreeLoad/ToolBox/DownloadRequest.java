package com.rapid.jason.rapidnetwork.FreeLoad.toolbox;

import com.rapid.jason.rapidnetwork.FreeLoad.core.Response;
import com.rapid.jason.rapidnetwork.FreeLoad.core.Request;

public class DownloadRequest extends Request<String>{

    private String Url;
    private final Response.Listener<String> listener;

    public DownloadRequest(String url, Response.Listener<String> listener) {
        this.Url = url;
        this.listener = listener;
    }

    @Override
    protected void deliverResponse(String response) {
        this.listener.onResponse(response);
    }
}
