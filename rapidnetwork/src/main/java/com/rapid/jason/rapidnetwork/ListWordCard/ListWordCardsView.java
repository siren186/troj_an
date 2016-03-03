package com.rapid.jason.rapidnetwork.ListWordCard;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rapid.jason.rapidnetwork.ListViewUtil.BaseCardsAdapter;
import com.rapid.jason.rapidnetwork.ListViewUtil.BaseCardsView;

public class ListWordCardsView extends BaseCardsView {

    private ListWordCardsAdapter listWordCardsAdapter;
    private ListItemButtonClickListener mClickListener = null;
    private ListOneItemButtonClickListener mOneItemClickListener = null;

    public ListWordCardsView(ListView listView, Context attachContext) {
        super(listView, attachContext);

        onInitView();
    }

    private void onInitView() {
        mClickListener = new ListItemButtonClickListener();
        listWordCardsAdapter = new ListWordCardsAdapter(getAttachContext());

        mOneItemClickListener = new ListOneItemButtonClickListener();

        showBaseCards();
    }

    @Override
    protected BaseCardsAdapter getCardsAdapter() {
        return listWordCardsAdapter;
    }

    @Override
    protected View.OnClickListener getCardsListener() {
        return mClickListener;
    }

    @Override
    protected AdapterView.OnItemClickListener getCardsOneItemListener() {
        return mOneItemClickListener;
    }

    private final class ListItemButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
        }
    }

    private final class ListOneItemButtonClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        }
    }
}
