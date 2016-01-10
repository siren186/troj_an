package com.rapid.jason.rapidnetwork;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by admin on 1/10/2016.
 */
public class NetworkTask {
    private RequestQueue mRequestQueue = null;

    public NetworkTask(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);

    }

    public void addNewTask(String Url, Response.Listener<JSONObject> jsonObjectListener) {
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
}
