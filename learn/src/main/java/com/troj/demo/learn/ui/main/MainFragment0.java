package com.troj.demo.learn.ui.main;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.troj.demo.learn.R;
import com.troj.demo.learn.ui.main.utils.MainFragmentUtils;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainFragment0 extends Fragment {
    MainFragmentUtils.FragmentHolder fragmentHolder = new MainFragmentUtils.FragmentHolder();

    @Bind(R.id.fragment_main_0_tv) TextView mTextView;

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
        mTextView.setText(R.string.first_bottom_tab_title);
    }
}
