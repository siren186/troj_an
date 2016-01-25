package com.rapid.jason.rapidnetwork.TraversalFile;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rapid.jason.rapidnetwork.ListViewUtil.BaseCardsAdapter;
import com.rapid.jason.rapidnetwork.ListViewUtil.BaseCardsView;

import de.greenrobot.event.EventBus;

public class TraversalCardsView extends BaseCardsView{

    private TraversalCardsAdapter traversalCardsAdapter;
    private ListItemButtonClickListener mClickListener = null;
    private ListOneItemButtonClickListener mOneItemClickListener = null;

    public TraversalCardsView(ListView listView, Context attachContext) {
        super(listView, attachContext);

        onInitView();
    }

    private void onInitView() {
        mClickListener = new ListItemButtonClickListener();
        traversalCardsAdapter = new TraversalCardsAdapter(getAttachContext());

        mOneItemClickListener = new ListOneItemButtonClickListener();

        showBaseCards();
    }

    @Override
    protected BaseCardsAdapter getCardsAdapter() {
        return traversalCardsAdapter;
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
            int nId = v.getId();
        }
    }

    private final class ListOneItemButtonClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int nId = position;
            EventBus.getDefault().post(new MessageEvent("" + position));
        }
    }
}
