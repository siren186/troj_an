package com.rapid.jason.rapidnetwork.ListViewUtil;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 */
public abstract class BaseCardsView {

    private Context mAttachContext;
    private ListView mListView = null;
    private BaseCardsAdapter mBaseCardsAdapter = null;

    private ArrayList<HashMap<String, Object>> mItemsLists;

    public BaseCardsView(ListView listView, Context attachContext) {
        this.mListView = listView;
        this.mAttachContext = attachContext;
    }

    public Context getAttachContext() {
        return mAttachContext;
    }

    public HashMap<String, Object> getItemByPos(int position) {
        return mItemsLists.get(position);
    }

    public void showBaseCards() {
        mBaseCardsAdapter = getCardsAdapter();
        mListView.setAdapter(mBaseCardsAdapter);

        mListView.setOnItemClickListener(getCardsOneItemListener());
    }

    /**
     * @param itemsLists 修改显示列表
     */
    public void setItemList(ArrayList<HashMap<String, Object>> itemsLists) {
        if (mListView != null) {
            mBaseCardsAdapter.setList(itemsLists);
        }

        mItemsLists = itemsLists;
    }

    /**
     * 设置适配器。
     * @return 基于BaseAdapter的适配器
     */
    abstract protected BaseCardsAdapter getCardsAdapter();

    /**
     * 获取列表事件监听
     * @return 事件监听器
     */
    abstract protected View.OnClickListener getCardsListener();

    /**
     * 获取单列事件监听
     * @return 事件监听器
     */
    abstract protected AdapterView.OnItemClickListener getCardsOneItemListener();
}
