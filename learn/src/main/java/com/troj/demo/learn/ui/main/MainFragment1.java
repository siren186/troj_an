package com.troj.demo.learn.ui.main;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.troj.demo.learn.R;
import com.troj.demo.learn.ui.main.utils.FragmentHolder;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainFragment1 extends Fragment {
    FragmentHolder mFragmentHolder = new FragmentHolder();

    @Bind(R.id.fragment_main_1_tv) TextView mTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mFragmentHolder.create(inflater, container, savedInstanceState, R.layout.fragment_main_1)) {
            ButterKnife.bind(this, mFragmentHolder.getView());
            initView();
        }

        return mFragmentHolder.getView();
    }

    private void initView() {
        mTextView.setText(R.string.second_bottom_tab_title);
    }
}
