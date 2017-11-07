package com.dfire.danggui.testapp;

import android.app.Application;

import com.dfire.danggui.testapp.dfirenetwork.CcdNetWorkConfig;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Settings;


public class TestApplication extends Application {
    public static TestApplication sTestApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        if (null == sTestApplication) {
            sTestApplication = this;
        }
        initLogger();
        initNetWork();
    }

    public static TestApplication getTestApplication() {
        return sTestApplication;
    }

    private void initLogger() {
        Settings settings = Logger.init("TestApp-Tag");
        if (BuildConfig.DEBUG) {
            settings.logLevel(LogLevel.FULL);
        } else {
            settings.hideThreadInfo().logLevel(LogLevel.NONE);
        }
    }

    private void initNetWork() {
        CcdNetWorkConfig.initNetWork();
    }
}
