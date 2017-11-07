package com.dfire.danggui.testapp.flexboxlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.dfire.danggui.testapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author DangGui
 * @create 2017/4/18.
 */

public class TestFlexboxLayoutActivity extends AppCompatActivity {
    @Bind(R.id.layout_custom_flexbox)
    CustomFlexboxLayout mLayoutCustomFlexbox;
    @Bind(R.id.button)
    Button mButton;
    @Bind(R.id.text_checked)
    TextView mTextChecked;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flextboxlayout);
        ButterKnife.bind(this);
        loadList();
    }

    private void loadList() {
        List<CustomFlexItem> customFlexItems = new ArrayList<>(0);
        for (int i = 0; i < 4; i++) {
            CustomFlexItem customFlexItem = new CustomFlexItem(i + "", "name" + i);
            customFlexItems.add(customFlexItem);
        }
        mLayoutCustomFlexbox.initSource(customFlexItems, false);
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        List<CustomFlexItem> customFlexItemList = mLayoutCustomFlexbox.getCheckedItemList();

    }
}
