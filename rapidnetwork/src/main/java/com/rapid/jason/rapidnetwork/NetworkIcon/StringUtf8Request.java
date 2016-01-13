package com.rapid.jason.rapidnetwork.NetworkIcon;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;

public class StringUtf8Request extends Request<String> {
    private final Response.Listener<String> mListener;

    public StringUtf8Request(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = listener;
    }

    public StringUtf8Request(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(0, url, listener, errorListener);
    }

    protected void deliverResponse(String response) {
        this.mListener.onResponse(response);
    }

    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, "UTF-8");
        } catch (UnsupportedEncodingException var4) {
            parsed = new String(response.data);
        }

        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }
}