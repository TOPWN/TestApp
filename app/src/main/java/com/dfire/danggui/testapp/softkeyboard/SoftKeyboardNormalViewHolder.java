package com.dfire.danggui.testapp.softkeyboard;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dfire.danggui.testapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author DangGui
 * @create 2017/7/29.
 */

public class SoftKeyboardNormalViewHolder extends KeyboardBaseRecyclerHolder<KeyboardModel> implements Divided{
    @Bind(R.id.btn_keys)
    TextView mBtnKeys;
    private KeyboardModel mKeyboardModel;

    public SoftKeyboardNormalViewHolder(Context context, View itemView, RecyclerView.Adapter adapter) {
        super(context, itemView, adapter);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void initView(KeyboardBaseRecyclerHolder holder, KeyboardModel item, int position) {
        if (null == holder)
            return;
        if (null == item)
            return;
        mKeyboardModel = item;
        mBtnKeys.setText(mKeyboardModel.getValue());
//        mBtnKeys.setBackgroundColor(Color.parseColor("#e0e0e0"));
    }
}
