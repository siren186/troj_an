package com.rapid.jason.rapidnetwork;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A placeholder fragment containing a simple view.
 */
public class ApkIconActivityFragment extends Fragment {

    private ListView lvCard = null;
    private ArrayAdapter<String> mAdapter = null;

    private static final String[] strs = new String[] {
        "first", "second", "third", "fourth", "fifth"
    };

    public ApkIconActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_apk_icon, container, false);

        lvCard = (ListView) view.findViewById(R.id.lv_card);
        mAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.apkicon_image_card_layout, strs);
        if (mAdapter != null && lvCard != null) {
            lvCard.setAdapter(mAdapter);
        }

        return view;
    }
}
