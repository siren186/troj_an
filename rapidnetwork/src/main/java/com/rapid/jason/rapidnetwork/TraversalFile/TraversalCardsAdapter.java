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
import com.rapid.jason.rapidnetwork.ListViewUtil.BaseViewHolder;
import com.rapid.jason.rapidnetwork.R;

import java.util.HashMap;

public class TraversalCardsAdapter extends BaseCardsAdapter{

    private final static String TAG = TraversalCardsAdapter.class.getName();

    private class ViewHolder {
        TextView showText;
        ImageView imageView;
    }

    public TraversalCardsAdapter(Context context) {
        super(context, R.layout.traversal_file_card_layout);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public void setViewHolder(int position, BaseViewHolder baseViewHolder) {


        HashMap<String, Object> hashMapList = getItemListByPos(position);

        ((TextView) baseViewHolder.getView(R.id.tv_filename)).setText((String) hashMapList.get("filename"));

        //viewHolder.showText.setText((String) hashMapList.get("filename"));
        /*Bitmap bitmap = (Bitmap) hashMapList.get("imageview");
        if (bitmap != null) {
            viewHolder.imageView.setImageBitmap(bitmap);
        }*/
    }
}
