package com.troj.demo.learn.ui.main;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.troj.demo.learn.R;
import com.troj.demo.learn.ui.main.utils.FragmentHolder;
import com.troj.demo.learn.ui.main.utils.MainFragment0RecyclerViewAdapter;
import com.troj.demo.learn.ui.utils.RecycleViewDivider;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainFragment0 extends Fragment {
    FragmentHolder fragmentHolder = new FragmentHolder();
    ArrayList<String> mDataList;

    @Bind(R.id.fragment_main_0_recycler_view)
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentHolder.create(inflater, container, savedInstanceState, R.layout.fragment_main_0)) {
            ButterKnife.bind(this, fragmentHolder.getView());
            initView();
        }

        return fragmentHolder.getView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        MainFragment0RecyclerViewAdapter adapter = new MainFragment0RecyclerViewAdapter(this.getActivity());
        mDataList = new ArrayList<>();
        mDataList.add("hello");
        mDataList.add("world");
        mDataList.add("nihao");
        mDataList.add("shijie");
        adapter.setDataList(mDataList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(this.getActivity(), LinearLayoutManager.VERTICAL));
    }
}
