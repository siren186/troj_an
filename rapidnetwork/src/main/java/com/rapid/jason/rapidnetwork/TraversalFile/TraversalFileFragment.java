package com.rapid.jason.rapidnetwork.TraversalFile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rapid.jason.rapidnetwork.ListViewUtil.MessageEvent;
import com.rapid.jason.rapidnetwork.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import de.greenrobot.event.EventBus;

public class TraversalFileFragment extends Fragment {

    private final static String TAG = TraversalFileFragment.class.getName();
    private ListView lvCard = null;

    private TraversalCardsView traversalCardsView = null;

    private ArrayList<HashMap<String, Object>> mItemsLists;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_traversal_file, container, false);
        lvCard = (ListView) view.findViewById(R.id.lv_card);

        traversalCardsView = new TraversalCardsView(lvCard, this.getContext());

        mItemsLists = getFileListOnDownloadDir("/storage/");
        traversalCardsView.setItemList(mItemsLists);

        return view;
    }

    public ArrayList<HashMap<String, Object>> getFileListOnDownloadDir(String path) {
        ArrayList<HashMap<String, Object>> fileList;
        fileList = GetFileName(path);
        return fileList;
    }

    public static ArrayList<HashMap<String, Object>> GetFileName(String fileAbsolutePath) {
        ArrayList<HashMap<String, Object>> fileList = new ArrayList<HashMap<String, Object>>();
        File file = new File(fileAbsolutePath);
        File[] subFile = file.listFiles();

        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("fullpath", subFile[iFileLength].getPath());
            hashMap.put("filename", subFile[iFileLength].getName());
            hashMap.put("imageview", null);
            fileList.add(hashMap);
        }
        return fileList;
    }

    public void onEventMainThread(MessageEvent event) {
        String msg = "onEventMainThread:" + event.getMsg();
        Log.d(TAG, msg);
    }

    public void onEvent(MessageEvent event) {
        String msg = event.getMsg();
        Log.d(TAG, msg);

        int pos = Integer.parseInt(msg);
        String fullPath = (String) mItemsLists.get(pos).get("fullpath");
        File file = new File(fullPath);
        if (file.isDirectory()) {
            mItemsLists = getFileListOnDownloadDir(fullPath);
            traversalCardsView.setItemList(mItemsLists);
        }
    }
}
