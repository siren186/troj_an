package com.rapid.jason.rapidnetwork.ListViewUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class BaseCardsAdapter extends BaseAdapter{

    private ArrayList<HashMap<String, Object>> mItemList = null;
    private Context mContext = null;
    private int mLayoutId = 0;

    public BaseCardsAdapter(Context context, int layoutId){
        this.mContext = context;
        this.mLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        return getItemListCount();
    }

    protected int getItemListCount() {
        if (mItemList == null) {
            return 0;
        }
        return mItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return getItemListByPos(position);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder baseViewHolder = BaseViewHolder.get(mContext, convertView, parent, mLayoutId);

        setViewHolder(position, baseViewHolder);

        return baseViewHolder.getConvertView();
    }

    public abstract void setViewHolder(int position, BaseViewHolder baseViewHolder);
}
