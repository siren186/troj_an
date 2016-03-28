package com.troj.demo.learn.ui.main.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.troj.demo.learn.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainFragment0RecyclerViewAdapter extends RecyclerView.Adapter<MainFragment0RecyclerViewAdapter.MyViewHolder> {
    private WeakReference<Context> mCtxWeekRef = null;
    ArrayList<String> mDataList = null;

    public MainFragment0RecyclerViewAdapter(Context ctx) {
        mCtxWeekRef = new WeakReference<>(ctx);
    }

    public void setDataList(ArrayList<String> dataList) {
        mDataList = dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context ctx = mCtxWeekRef.get();
        if (null != ctx) {
            return new MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.main_fragment_list_item_layout, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTv1.setText(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        if (null != mDataList) {
            return mDataList.size();
        }
        return 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.main_fragment_list_item_layout_tv1)
        TextView mTv1;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mTv1.setText("666");
        }
    }
}
