package com.rapid.jason.rapidnetwork.ListViewUtil;

import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class BaseCardsAdapter extends BaseAdapter{

    private ArrayList<HashMap<String, Object>> mItemList = null;

    protected int getItemListCount() {
        if (mItemList == null) {
            return 0;
        }
        return mItemList.size();
    }

    protected HashMap<String, Object> getItemListByPos(int position) {
        if (mItemList == null) {
            return null;
        }
        return mItemList.get(position);
    }

    /**
     * 设置修改的列表
     * @param list
     */
    public void setList(ArrayList<HashMap<String, Object>> list) {
        mItemList = list;
        notifyDataSetChanged();
    }
}
