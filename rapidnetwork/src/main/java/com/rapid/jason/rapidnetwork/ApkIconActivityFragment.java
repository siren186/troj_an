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

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A placeholder fragment containing a simple view.
 */
public class ApkIconActivityFragment extends Fragment {

    private ListView lvCard = null;

    private CloudCardsView mCloudCardsView = null;
    private NetworkTask mNetworkTask;

    private ArrayList<HashMap<String, Object>> hashMapApkIconArrayList = null;

    private Response.Listener<JSONObject> mJsonObjectListener = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hashMapApkIconArrayList = new ArrayList<HashMap<String, Object>>();
        createNewJsonListener();

        mNetworkTask = new NetworkTask(this.getActivity());

        String strUrlFormat = "http://cms.sjk.ijinshan.com/app/api/cdn/app/%d.json";

        for (int i = 0; i < 8; ++i) {
            String strUrl = String.format(strUrlFormat, 3000 + i);
            mNetworkTask.addNewTask(strUrl, mJsonObjectListener);
        }
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

            HashMap<String, Object> hashMapApkIcon = new HashMap<String, Object>();
            JSONObject jo = jsonObject.getJSONObject("data");

            if (!jo.isNull("pkname")) {
                hashMapApkIcon.put("pkname", jo.getString("pkname"));
            }

            if (!jo.isNull("logoHdUrl")) {
                String url = jo.getString("logoHdUrl");
                if (!TextUtils.isEmpty(url)) {
                    hashMapApkIcon.put("logoHdUrl", url);
                }
            }

            hashMapApkIconArrayList.add(hashMapApkIcon);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return true;
    }

    private void refreshView() {
        if (mCloudCardsView == null) {
            return;
        }

        mCloudCardsView.setItemList(hashMapApkIconArrayList);
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
