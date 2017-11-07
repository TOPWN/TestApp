package com.dfire.danggui.testapp.dfirenetwork;

import android.annotation.SuppressLint;
import android.provider.Settings;
import android.util.DisplayMetrics;

import com.dfire.danggui.testapp.TestApplication;

/**
 * @author DangGui
 * @create 2017/1/16.
 */

public class DeviceUtil {
    public static String getDeviceId() {
        @SuppressLint("HardwareIds") String callDeviceId = Settings.Secure.getString(TestApplication.getTestApplication().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return callDeviceId;
    }

    public static String getScreenWidAndHeight() {
        DisplayMetrics dm = TestApplication.getTestApplication().getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        return w_screen + "*" + h_screen;
    }

}
