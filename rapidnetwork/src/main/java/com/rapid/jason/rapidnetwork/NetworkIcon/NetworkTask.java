package com.rapid.jason.rapidnetwork.NetworkIcon;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class NetworkTask {
    private RequestQueue mRequestQueue = null;

    public NetworkTask(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void addNewJsonTask(String Url, Response.Listener<JSONObject> jsonObjectListener) {
        if (mRequestQueue == null) {
            return;
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(
            Url, null, jsonObjectListener,
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("TAG", error.getMessage(), error);
                }
            }
        );

        mRequestQueue.add(jsonRequest);
    }

    public void addNewStringTask(String Url, Response.Listener<String> stringListener) {
        if (mRequestQueue == null) {
            return;
        }

        StringUtf8Request stringRequest = new StringUtf8Request(
                Url, stringListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                }
        );

        mRequestQueue.add(stringRequest);
    }
}
