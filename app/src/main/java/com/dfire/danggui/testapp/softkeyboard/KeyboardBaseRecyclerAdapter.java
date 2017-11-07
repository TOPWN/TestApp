package com.dfire.danggui.testapp.softkeyboard;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author DangGui
 * @create 2016/12/19.
 */
public abstract class KeyboardBaseRecyclerAdapter<T> extends RecyclerView.Adapter<KeyboardBaseRecyclerHolder> {

    private List<T> mRealData; /*数据源*/
    private int mItemLayoutId = 0;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;/*item点击监听*/

    public interface OnItemClickListener {

        void onItemClick(View view, Object data, int position);/*item点击监听*/

        boolean onItemLongClick(View view, Object data, int position);/*item长按监听*/
    }

    public KeyboardBaseRecyclerAdapter(RecyclerView v, Collection<T> data, int itemLayoutId) {
        if (data == null) {
            mRealData = new ArrayList<>();
        } else if (data instanceof List) {
            mRealData = (List<T>) data;
        } else {
            mRealData = new ArrayList<>(data);
        }
        mItemLayoutId = itemLayoutId;
        mContext = v.getContext();
    }

    /**
     * Recycler适配器填充方法，需实现自己的viewholder
     *
     * @param holder viewholder
     * @param item   javabean
     */
    public void convert(KeyboardBaseRecyclerHolder holder, T item, int position) {
        if (holder != null) {
            holder.initView(holder, item, position);
        }
    }

    @Override
    public abstract KeyboardBaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(KeyboardBaseRecyclerHolder holder, int position) {
        if (null != holder) {
            convert(holder, position < mRealData.size() ? mRealData.get(position) : null, position);
            holder.itemView.setOnClickListener(getOnClickListener(position));
            holder.itemView.setOnLongClickListener(getOnLongClickListener(position));
        }
    }

    @Override
    public int getItemCount() {
        return mRealData == null ? 0 : mRealData.size();
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }

    public View.OnClickListener getOnClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(@Nullable View v) {
                if (mOnItemClickListener != null && v != null) {
                    mOnItemClickListener.onItemClick(v, position < mRealData.size() ? mRealData.get(position) : null, position);
                }
            }
        };
    }

    public View.OnLongClickListener getOnLongClickListener(final int position) {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null && v != null) {
                    return mOnItemClickListener.onItemLongClick(v, position < mRealData.size() ? mRealData.get(position) : null, position);
                }
                return true;
            }
        };
    }
}
