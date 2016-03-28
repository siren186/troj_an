package com.monet.furansujin;

import android.util.SparseArray;
import android.view.View;

public class ViewHolder {
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> childViews = (SparseArray<View>) view.getTag();
        if (null == childViews) {
            childViews = new SparseArray<>();
            view.setTag(childViews);
        }

        View childView = childViews.get(id);
        if (null == childView) {
            childView = view.findViewById(id);
            childViews.put(id, childView);
        }

        return (T) childView;
    }
}
