package com.dfire.danggui.testapp.softkeyboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * @author DangGui
 * @create 2016/12/19.
 */
public abstract class KeyboardBaseRecyclerHolder<T> extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    public Context context;
    public RecyclerView.Adapter adapter;

    public KeyboardBaseRecyclerHolder(Context context, View itemView, RecyclerView.Adapter adapter) {
        super(itemView);
        this.context = context;
        this.mViews = new SparseArray<>();
        this.adapter = adapter;
    }

    public abstract void initView(KeyboardBaseRecyclerHolder holder, T item, int position);

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public KeyboardBaseRecyclerHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }
}
