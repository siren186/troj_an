package com.rapid.jason.rapidnetwork;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class NewsfeedCardsAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, Object>> mList;
    private final Context mContext;

    private final OnClickListener itemButtonClickListener;

    final int TYPE_1 = 0;

    public final int mLocalCardsIdLimite = 50;

    private class ViewHolder1 {
        TextView showText;
        Button cardButton;
        ImageView imageView;
    }

    public NewsfeedCardsAdapter(Context context, ArrayList<HashMap<String, Object>> list,
                                OnClickListener itemButtonClickListener) {
        this.mList = list;
        this.mContext = context;
        this.itemButtonClickListener = itemButtonClickListener;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            int type = getItemViewType(position);
            convertView = getConvertView(parent, type);
        }

        setViewHolder(convertView);

        return convertView;
    }

    private void setViewHolder(View convertView) {
        if (convertView == null || itemButtonClickListener == null) {
            return;
        }

        setViewHolder1(convertView);
    }

    private void setViewHolder1(View convertView) {
        ViewHolder1 viewHolder = (ViewHolder1) convertView.getTag();

        if (viewHolder == null) {
            return;
        }

        viewHolder.showText.setText("");
        //viewHolder.cardButton.setOnClickListener(itemButtonClickListener);
    }

    private View getConvertView(ViewGroup parent, int type) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View convertView = null;

        convertView = getConvertViewType1(parent, inflater);

        return convertView;
    }

    private View getConvertViewType1(ViewGroup parent, LayoutInflater inflater) {
        View convertView = null;
        convertView = inflater.inflate(R.layout.apkicon_image_card_layout, parent, false);
        ViewHolder1 holder = getViewHolder1(convertView);
        convertView.setTag(holder);
        return convertView;
    }

    private ViewHolder1 getViewHolder1(View convertView) {
        ViewHolder1 holder = new ViewHolder1();
        holder.showText = (TextView) convertView.findViewById(R.id.tv_apkname);
        holder.imageView = (ImageView) convertView.findViewById(R.id.iv_apkicon);
        return holder;
    }
}