package com.rapid.jason.rapidnetwork.ListWordCard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rapid.jason.rapidnetwork.ListViewUtil.BaseCardsAdapter;
import com.rapid.jason.rapidnetwork.ListViewUtil.BaseViewHolder;
import com.rapid.jason.rapidnetwork.R;

public class ListWordCardsAdapter extends BaseCardsAdapter {

    private final static String TAG = ListWordCardsAdapter.class.getName();

    private Context mContext;

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

        TextView textView = BaseViewHolder.get(convertView).getView(R.id.tv_filename);
        textView.setText((String) (position + ""));
    }

    private View getConvertView(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View convertView = inflater.inflate(R.layout.list_word_card_layout, parent, false);

        return convertView;
    }
}
