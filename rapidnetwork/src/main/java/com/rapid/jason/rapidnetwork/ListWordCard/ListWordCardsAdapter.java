package com.rapid.jason.rapidnetwork.ListWordCard;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.rapid.jason.rapidnetwork.ListViewUtil.BaseCardsAdapter;
import com.rapid.jason.rapidnetwork.ListViewUtil.BaseViewHolder;
import com.rapid.jason.rapidnetwork.R;

public class ListWordCardsAdapter extends BaseCardsAdapter {

    private final static String TAG = ListWordCardsAdapter.class.getName();

    public ListWordCardsAdapter(Context context) {
        super(context, R.layout.list_word_card_layout);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public void setViewHolder(int position, BaseViewHolder baseViewHolder) {
        TextView textView = baseViewHolder.getView(R.id.tv_filename);
        textView.setText((String) (position + ""));
    }
}
