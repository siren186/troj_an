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

        String strUrlFormat = "http://apk.gfan.com/Product/App%d.html";

        for (int i = 0; i < 20; ++i) {
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
        String strAll = stringObject;
        int nPlace = strAll.indexOf("app-view png");

        if(nPlace == -1) {
            return false;
        }

        char chPiece[] = new char[1024];
        strAll.getChars(nPlace, nPlace + 512, chPiece, 0);
        String strAllPiece = new String(chPiece);

        String strPicUrl = parseUrlString(strAllPiece);
        if (strPicUrl != null && strPicUrl.equals("")) {
            return false;
        } else if (strPicUrl != null) {
            strPicUrl = strPicUrl.replaceAll("\\u0000", "");
        }
        String strPicName = parseNameString(strAllPiece);
        if (strPicName != null && strPicName.equals("")) {
            return false;
        }

        HashMap<String, Object> hashMapApkIcon = new HashMap<String, Object>();
        hashMapApkIcon.put("pkname", strPicName);
        hashMapApkIcon.put("logoHdUrl", strPicUrl);
        hashMapApkIconArrayList.add(hashMapApkIcon);

        return true;
    }

    private String parseUrlString(String strAllPiece) {
        int nStart = 0;
        int nEnd = 0;

        nStart = strAllPiece.indexOf("src=");
        if (nStart == -1) {
            return "";
        }
        nEnd = strAllPiece.indexOf(" alt=", nStart);
        if (nEnd == -1) {
            return "";
        }

        if (nEnd - (nStart + 5) == 0) {
            return "";
        }

        char ch[] = new char[nEnd - nStart - 4];
        strAllPiece.getChars(nStart + 5, nEnd - 1, ch, 0);
        String strPicUrl = new String(ch);
        return strPicUrl;
    }

    private String parseNameString(String strAllPiece) {
        int nStart = 0;
        int nEnd = 0;

        nStart = strAllPiece.indexOf("alt=\"");
        if (nStart == -1) {
            return "";
        }
        nEnd = strAllPiece.indexOf("\"/>", nStart);
        if (nEnd == -1) {
            return "";
        }

        if (nEnd - (nStart + 5) == 0) {
            return "";
        }

        char ch[] = new char[nEnd - nStart - 4];
        strAllPiece.getChars(nStart + 5, nEnd, ch, 0);
        String strPicUrl = new String(ch);
        return strPicUrl;
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
