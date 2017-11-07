package com.dfire.danggui.testapp.dfirenetwork;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;

import com.dfire.danggui.testapp.BuildConfig;
import com.dfire.danggui.testapp.R;
import com.dfire.mobile.network.Callback;
import com.dfire.mobile.network.Network;
import com.dfire.mobile.network.RequestModel;
import com.dfire.mobile.network.ResponseModel;
import com.dfire.mobile.network.service.NetworkService;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author DangGui
 * @create 2017/1/14.
 */

public class Test2DfireNetWorkActivity extends AppCompatActivity {
    @Bind(R.id.button_test_request)
    Button mButtonTestRequest;
    @Bind(R.id.activity_main)
    LinearLayout mActivityMain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_2dfire_network);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_test_request)
    public void onClick() {
        request();
    }

    private void request() {
        final Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("entityId", "10002323");
        requestMap.put("userId", "6847987987");//消息ID
        requestMap.put("method", NetWorkMethodConstants.ATTENTION_DESK.GET_WATCHED_SEAT_LIST);
        RequestModel requestModel = RequestModel.post(BuildConfig.CLOUD_CASH_API)
                .addParameters(requestMap)
                .tag(this)
                .build();
        NetworkService.getDefault().request(requestModel, new Callback<Object>() {
            @Override
            protected void onResponse(ResponseModel<Object> responseModel) {
//                Logger.d(responseModel.isSuccessful() + "/" + responseModel.message());
            }

            @Override
            protected void onSuccess(Object data) {
                Logger.d("data: " + data);
            }

            @Override
            protected void onFailure(Throwable throwable) {
                Logger.e("exception occrred: " + throwable);
                throwable.printStackTrace();
            }
        });
        Network.cancel(this);
    }
}
