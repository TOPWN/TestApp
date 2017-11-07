package com.dfire.danggui.testapp.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.dfire.danggui.testapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author DangGui
 * @create 2016/12/29.
 */

public class TestEventBusSubActivity extends TestEventBusBaseActivity {
    @Bind(R.id.btSend)
    Button mBtSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_eventbus);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.btSend)
    public void onClick() {
        Intent intent = new Intent(this, TestEventBusThirdActivity.class);
        startActivity(intent);
    }
}
