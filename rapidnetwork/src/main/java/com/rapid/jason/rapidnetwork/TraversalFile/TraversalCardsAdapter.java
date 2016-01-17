package com.rapid.jason.rapidnetwork.TraversalFile;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.rapid.jason.rapidnetwork.ListViewUtil.BaseCardsAdapter;
import com.rapid.jason.rapidnetwork.R;

import java.util.HashMap;

public class TraversalCardsAdapter extends BaseCardsAdapter{

    private final static String TAG = TraversalCardsAdapter.class.getName();

    private Context mContext;

    private class ViewHolder {
        TextView showText;
        ImageView imageView;
    }

    public TraversalCardsAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return getItemListCount();
    }

    @Override
    public Object getItem(int position) {
        return getItemListByPos(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = getConvertView(parent);
        }

        setViewHolder(convertView, position);

        return convertView;
    }

    private void setViewHolder(View convertView, int position) {
        if (convertView == null) {
            return;
        }

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        if (viewHolder == null) {
            return;
        }

        HashMap<String, Object> hashMapList = getItemListByPos(position);

        viewHolder.showText.setText((String) hashMapList.get("filename"));
        Bitmap bitmap = (Bitmap) hashMapList.get("imageview");
        if (bitmap != null) {
            viewHolder.imageView.setImageBitmap(bitmap);
        }
    }

    private View getConvertView(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View convertView = inflater.inflate(R.layout.traversal_file_card_layout, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.showText = (TextView) convertView.findViewById(R.id.tv_filename);
        holder.imageView = (ImageView) convertView.findViewById(R.id.iv_fileicon);
        convertView.setTag(holder);

        return convertView;
    }
}
