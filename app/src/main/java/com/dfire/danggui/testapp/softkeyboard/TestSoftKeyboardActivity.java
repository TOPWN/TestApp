package com.dfire.danggui.testapp.softkeyboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dfire.danggui.testapp.R;
import com.dfire.danggui.testapp.editfoodnumview.EditFoodNumberView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author DangGui
 * @create 2017/7/29.
 */

public class TestSoftKeyboardActivity extends AppCompatActivity {

    @Bind(R.id.edit_actual_received)
    EditFoodNumberView mEditFoodNumberView;
    @Bind(R.id.virtualKeyboardView)
    CustomSoftKeyboardView mVirtualKeyboardView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_softkeyboard);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mVirtualKeyboardView.init(this, mEditFoodNumberView.getEditText());
    }
}
