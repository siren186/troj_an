package com.rapid.jason.rapidnetwork.NetworkIcon;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Response;
import com.rapid.jason.rapidnetwork.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import de.greenrobot.event.EventBus;

public class ApkIconActivityFragment extends Fragment {

    private final static String TAG = ApkIconActivityFragment.class.getName();
    private ListView lvCard = null;

    private CloudCardsView mCloudCardsView = null;
    private NetworkTask mNetworkTask;

    private ArrayList<HashMap<String, Object>> hashMapApkIconArrayList = null;

    private Response.Listener<JSONObject> mJsonObjectListener = null;
    private Response.Listener<String> mStringListener = null;

    private int mApkIconUrlId = 1000000;
    private String mStrUrlFormat = "http://apk.gfan.com/Product/App%d.html";

    private int mRqSize = 0;
    private int mRqListSize = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hashMapApkIconArrayList = new ArrayList<HashMap<String, Object>>();
        mJsonObjectListener = createNewJsonListener();
        mStringListener = createNewStringListener();

        mNetworkTask = new NetworkTask(this.getActivity());

        addNetworkTask(300);

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(MessageEvent event) {
        String msg = "onEventMainThread:" + event.getMsg();
        Log.d(TAG, msg);
    }

    public void onEvent(MessageEvent event) {
        String msg = event.getMsg();
        if (msg.equals("add request") && mRqSize <= 0) {
            addNetworkTask(200);
        }
        Log.d(TAG, msg);
    }

    private void addNetworkTask(int nRqSize) {
        mRqSize = nRqSize;
        mRqListSize = 0;
        for (int i = 0; i < nRqSize; ++i) {
            mApkIconUrlId = mApkIconUrlId + i;
            String strUrl = String.format(mStrUrlFormat, mApkIconUrlId);
            mNetworkTask.addNewStringTask(strUrl, mStringListener);
        }
    }

    private void checkListEnough() {
        if (mRqListSize < 15) {
            addNetworkTask(100);
        }
    }

    private Response.Listener<String> createNewStringListener() {
        Response.Listener<String> stringListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                --mRqSize;
                if (!parseString(s)) {
                    return;
                }
                ++mRqListSize;

                if (mRqSize <= 0) {
                    checkListEnough();
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
        if (strPicUrl == null) {
            return false;
        }
        if (strPicUrl.equals("")) {
            return false;
        } else if (strPicUrl != null) {
            strPicUrl = strPicUrl.replaceAll("\\u0000", "");
        }
        Log.d(TAG, strPicUrl);

        String strPicName = parseNameString(strAllPiece);
        if (strPicName == null) {
            return false;
        }
        if (strPicName.equals("")) {
            return false;
        } else {
            strPicName = strPicName.replaceAll("\\u0000", "");
        }
        Log.d(TAG, strPicName);

        HashMap<String, Object> hashMapApkIcon = new HashMap<String, Object>();
        hashMapApkIcon.put("pkname", strPicName);
        hashMapApkIcon.put("logoHdUrl", strPicUrl);
        hashMapApkIconArrayList.add(hashMapApkIcon);

        LruCache lruCache = null;

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
