package com.dfire.danggui.testapp.glide;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.dfire.danggui.testapp.R;

/**
 * 自定义减价弹框
 *
 * @author DangGui
 * @create 2017/5/24.
 */

public class CustomDialogFragment extends DialogFragment {
    public static final String EDIT_DIGISTS = "0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private TextView mTextViewCancel;
    private TextView mTextViewSure;
    private EditText mEditText;
    private EditText mNormalEditText;

    private String mTitle, mEditHint;
    private int mType;

    public static CustomDialogFragment newInstance(String title, String hint, int type) {
        Bundle args = new Bundle();
        CustomDialogFragment fragment = new CustomDialogFragment();
        args.putString("title", title);
        args.putString("hint", hint);
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (null != bundle) {
            mTitle = bundle.getString("title");
            mEditHint = bundle.getString("hint");
        }
        return inflater.inflate(R.layout.module_receipt_layout_custom_reduce_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        mNormalEditText = (EditText) view.findViewById(R.id.edit_normal);
        TextView mTextViewTitle = (TextView) view.findViewById(R.id.text_title);
        mTextViewCancel = (TextView) view.findViewById(R.id.text_cancel);
        mTextViewSure = (TextView) view.findViewById(R.id.text_sure);
        mNormalEditText.setVisibility(View.VISIBLE);
        mEditText = mNormalEditText;
        if (!TextUtils.isEmpty(mTitle)) {
            mTextViewTitle.setText(mTitle);
        }
        if (!TextUtils.isEmpty(mEditHint)) {
            mEditText.setHint(mEditHint);
        }
        mEditText.setSelectAllOnFocus(true);
        //点击输入框，让输入框内容全部被选中，方便用户直接输入覆盖内容
        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.clearFocus();
                mEditText.requestFocus();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && null != getDialog().getWindow()) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            getDialog().getWindow().setLayout((int) (dm.widthPixels * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (null != mEditText) {
            mEditText.setText("");
        }
        if (null != mNormalEditText) {
            mNormalEditText.setText("");
        }
    }
}
