package com.rapid.jason.rapidnetwork;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ApkIconActivityFragment extends Fragment {

    private ListView lvCard = null;

    private CloudCardsView mCloudCardsView = null;
    private NetworkTask mNetworkTask;

    private ArrayList<HashMap<String, Object>> hashMapApkIconArrayList = null;

    private Response.Listener<JSONObject> mJsonObjectListener = null;
    private Response.Listener<String> mStringListener = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hashMapApkIconArrayList = new ArrayList<HashMap<String, Object>>();
        mJsonObjectListener = createNewJsonListener();
        mStringListener = createNewStringListener();

        mNetworkTask = new NetworkTask(this.getActivity());

        String strUrlFormat = "http://cms.sjk.ijinshan.com/app/api/cdn/app/%d.json";
        strUrlFormat = "http://apk.gfan.com/Product/App%d.html";//1087029

        for (int i = 0; i < 8; ++i) {
            String strUrl = String.format(strUrlFormat, 1087029 + i);
            mNetworkTask.addNewStringTask(strUrl, mStringListener);
        }
    }

    private Response.Listener<String> createNewStringListener() {
        Response.Listener<String> stringListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (!parseString(s)) {
                    return;
                }
                refreshView();
            }
        };
        return stringListener;
    }

    private boolean parseString(String stringObject) {
        String str = stringObject;
        int nPlace = str.indexOf("app-view png");

        int nStart = 0;
        int nEnd = 0;

        char c[] = new char[1024];
        String strAll;
        if(nPlace != -1) {
            nStart = str.indexOf("src=", nPlace);
            nEnd = str.indexOf("alt=", nStart + 6);

            str.getChars(nStart + 5, nStart + 5 + 40, c, 0);

        }
        strAll = new String(c);
        return true;
    }

    private Response.Listener<JSONObject> createNewJsonListener() {
        Response.Listener<JSONObject> jsonObjectListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (!parseJson(jsonObject)) {
                    return;
                }
                refreshView();
            }
        };
        return jsonObjectListener;
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
