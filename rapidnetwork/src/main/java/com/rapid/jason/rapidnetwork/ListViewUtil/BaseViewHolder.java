package com.rapid.jason.rapidnetwork.ListViewUtil;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseViewHolder {

    private final SparseArray<View> views;
    private View mConvertView;

    private BaseViewHolder(Context context, ViewGroup parent, int layoutId){
        this.views = new SparseArray<View>();
        this.mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);

        this.mConvertView.setTag(this);
    }

    public static BaseViewHolder get(Context context, View convertView,
                                     ViewGroup parent, int layoutId) {
        if (convertView == null) {
            return new BaseViewHolder(context, parent, layoutId);
        }
        BaseViewHolder existedHolder = (BaseViewHolder) convertView.getTag();
        return existedHolder;
    }

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView()
    {
        return mConvertView;
    }
}
