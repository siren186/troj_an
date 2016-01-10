package com.rapid.jason.rapidnetwork;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by admin on 1/10/2016.
 */
public class CloudCardsView {

    private ListView mListView = null;
    private NewsfeedCardsAdapter newsfeedCardsAdapter;

    public Activity mAtachActivity = null;
    public ArrayList<HashMap<String, Object>> viewItemsArraylists;

    public CloudCardsView(Activity atachActivity) {
        mAtachActivity = atachActivity;
    }

    public void init(ListView listView, int sourceFrom) {
        mListView = listView;

        int itemSize = 6;

        showNewsfeedCards();
    }

    private void showNewsfeedCards() {
        newsfeedCardsAdapter = new NewsfeedCardsAdapter(mAtachActivity, getDatas(), new ListItemButtonClickListener());
        mListView.setAdapter(newsfeedCardsAdapter);
    }

    public ArrayList<HashMap<String, Object>> getDatas() {
        viewItemsArraylists = new ArrayList<HashMap<String, Object>>();
        viewItemsArraylists.add(getHashMapData());

        return viewItemsArraylists;
    }

    private HashMap<String, Object> getHashMapData() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        return hashMap;
    }

    public void setItemList(ArrayList<HashMap<String, Object>> list) {
        newsfeedCardsAdapter.setList(list);
    }

    private final class ListItemButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int nId = v.getId();

            mAtachActivity.finish();
        }
    }
}
