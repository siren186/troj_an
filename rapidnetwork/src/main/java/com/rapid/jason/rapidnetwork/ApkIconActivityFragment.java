package com.rapid.jason.rapidnetwork;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * A placeholder fragment containing a simple view.
 */
public class ApkIconActivityFragment extends Fragment {

    private ListView lvCard = null;

    private CloudCardsView mCloudCardsView;
    private NetworkTask mNetworkTask;

    private HashMap<String, String> hashMapApkIcon = null;

    private Response.Listener<JSONObject> mJsonObjectListener = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hashMapApkIcon = new HashMap<String, String>();
        createNewJsonListener();

        mNetworkTask = new NetworkTask(this.getActivity());
        mNetworkTask.addNewTask("http://cms.sjk.ijinshan.com/app/api/cdn/app/3390.json", mJsonObjectListener);
        mNetworkTask.addNewTask("http://cms.sjk.ijinshan.com/app/api/cdn/app/3391.json", mJsonObjectListener);
        mNetworkTask.addNewTask("http://cms.sjk.ijinshan.com/app/api/cdn/app/3392.json", mJsonObjectListener);
        mNetworkTask.addNewTask("http://cms.sjk.ijinshan.com/app/api/cdn/app/3393.json", mJsonObjectListener);
    }

    private void createNewJsonListener() {
        mJsonObjectListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (!parseJson(jsonObject)) {
                    return;
                }
                refreshView();
            }
        };
    }

    private boolean parseJson(JSONObject jsonObject) {
        try {
            if (jsonObject.isNull("data")) {
                return false;
            }

            JSONObject jo = jsonObject.getJSONObject("data");

            if (!jo.isNull("pkname")) {
                hashMapApkIcon.put("pkname", jo.getString("pkname"));
            }

            if (!jo.isNull("logoHdUrl")) {
                String url = jo.getString("logoHdUrl");
                if (!TextUtils.isEmpty(url)) {
                    hashMapApkIcon.put("logoUrl", url);
                }
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return true;
    }

    private void refreshView() {
        return;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_apk_icon, container, false);

        lvCard = (ListView) view.findViewById(R.id.lv_card);

        mCloudCardsView = new CloudCardsView(this.getActivity());
        mCloudCardsView.init(lvCard, 100);

        return view;
    }
}
