package com.rapid.jason.rapidnetwork.ListWordCard;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.rapid.jason.rapidnetwork.ListViewUtil.SlideCutListView;
import com.rapid.jason.rapidnetwork.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListWordActivityFragment extends Fragment implements SlideCutListView.MoveListener {

    private SlideCutListView lvCard = null;
    private ListWordCardsView listWordCardsView = null;

    private ArrayList<HashMap<String, Object>> mItemsLists;

    public ListWordActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_word, container, false);

        lvCard = (SlideCutListView) view.findViewById(R.id.lv_card);
        lvCard.setMoveListener(this);

        listWordCardsView = new ListWordCardsView(lvCard, this.getContext());

        mItemsLists = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 10; ++i) {
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("fullpath", i + "");
            hashMap.put("filename", i + "");
            mItemsLists.add(hashMap);
        }

        listWordCardsView.setItemList(mItemsLists);

        return view;
    }

    @Override
    public void moveItem(SlideCutListView.MoveDirection direction, int position) {
        Toast.makeText(this.getContext(), "" + direction, Toast.LENGTH_SHORT).show();
        return;
    }
}
