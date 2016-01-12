package com.rapid.jason.rapidnetwork;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import de.greenrobot.event.EventBus;

public class NewsfeedCardsAdapter extends BaseAdapter {

    private final static String TAG = NewsfeedCardsAdapter.class.getName();

    private ArrayList<HashMap<String, Object>> mList;
    private final Context mContext;

    private final OnClickListener itemButtonClickListener;
    private final RequestQueue mQueue;

    private ImageCache mImageCache = new ImageCache();

    final int TYPE_1 = 0;

    public final int mLocalCardsIdLimite = 50;

    private class ViewHolder {
        TextView showText;
        Button cardButton;
        ImageView imageView;
    }

    public NewsfeedCardsAdapter(Context context, ArrayList<HashMap<String, Object>> list,
                                OnClickListener itemButtonClickListener) {
        this.mList = list;
        this.mContext = context;
        this.itemButtonClickListener = itemButtonClickListener;

        mQueue = Volley.newRequestQueue(context);
    }

    public void setList(ArrayList<HashMap<String, Object>> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            int type = getItemViewType(position);
            convertView = getConvertView(parent, type);
        }

        Log.d(TAG, "getView:" + position + "  listSize:" + mList.size());

        if (mList.size() > 15 && position >= (mList.size() - 2)) {
            EventBus.getDefault().post(new MessageEvent("add request"));
        }

        setViewHolder(convertView, position);

        return convertView;
    }

    private void setViewHolder(View convertView, int position) {
        if (convertView == null || itemButtonClickListener == null) {
            return;
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        if (viewHolder == null) {
            return;
        }

        HashMap<String, Object> hashMapList = mList.get(position);

        viewHolder.showText.setText((String)hashMapList.get("pkname") + ", " + mList.size() + ", " + position);

        ImageLoader imageLoader = new ImageLoader(mQueue, mImageCache);

        ImageLoader.ImageListener listener = ImageLoader.getImageListener(viewHolder.imageView,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher);

        String url = (String)hashMapList.get("logoHdUrl");
        if (url != null && !url.equals("")) {
            imageLoader.get(url, listener);
        }
    }

    private View getConvertView(ViewGroup parent, int type) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View convertView = null;

        convertView = getConvertViewType1(parent, inflater);

        return convertView;
    }

    private View getConvertViewType1(ViewGroup parent, LayoutInflater inflater) {
        View convertView = null;
        convertView = inflater.inflate(R.layout.apkicon_image_card_layout, parent, false);
        ViewHolder holder = getViewHolder1(convertView);
        convertView.setTag(holder);
        return convertView;
    }

    private ViewHolder getViewHolder1(View convertView) {
        ViewHolder holder = new ViewHolder();
        holder.showText = (TextView) convertView.findViewById(R.id.tv_apkname);
        holder.imageView = (ImageView) convertView.findViewById(R.id.iv_apkicon);
        return holder;
    }
}