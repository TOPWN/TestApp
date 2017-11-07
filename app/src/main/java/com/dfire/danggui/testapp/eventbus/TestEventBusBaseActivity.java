package com.dfire.danggui.testapp.eventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dfire.danggui.testapp.eventbus.event.TestEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author DangGui
 * @create 2016/12/29.
 */

public class TestEventBusBaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    //在Android的主线程中运行
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(TestEvent event) {
        Log.d("onEventMainThread**", getLocalClassName() + "--" + event.toString());
    }
}
