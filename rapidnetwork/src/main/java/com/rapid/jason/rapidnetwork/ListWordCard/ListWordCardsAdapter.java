package com.rapid.jason.rapidnetwork.ListWordCard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rapid.jason.rapidnetwork.ListViewUtil.BaseCardsAdapter;
import com.rapid.jason.rapidnetwork.R;

import java.util.HashMap;

public class ListWordCardsAdapter extends BaseCardsAdapter {

    private final static String TAG = ListWordCardsAdapter.class.getName();

    private Context mContext;

    private class ViewHolder {
        TextView showText;
    }

    public ListWordCardsAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return getItemListCount();
    }

    @Override
    public Object getItem(int position) {
        return getItemListByPos(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = getConvertView(parent);
        }

        setViewHolder(convertView, position);

        return convertView;
    }

    private void setViewHolder(View convertView, int position) {
        if (convertView == null) {
            return;
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        if (viewHolder == null) {
            return;
        }
        viewHolder.showText.setText((String) (position + ""));
    }

    private View getConvertView(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View convertView = inflater.inflate(R.layout.list_word_card_layout, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.showText = (TextView) convertView.findViewById(R.id.tv_filename);
        convertView.setTag(holder);

        return convertView;
    }
}
