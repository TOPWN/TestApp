package com.dfire.danggui.testapp.dfirenetwork;

import android.os.Build;

import com.dfire.danggui.testapp.BuildConfig;
import com.dfire.danggui.testapp.TestApplication;
import com.dfire.mobile.network.RequestInterceptor;
import com.dfire.mobile.network.RequestModel;
import com.dfire.sdk.util.MD5Util;

import java.util.HashMap;
import java.util.Map;

/**
 * 云收银网络请求初始化配置
 *
 * @author DangGui
 * @create 2017/1/14.
 */

public class CcdNetWorkConfig {

    public static void initNetWork() {
//        NetworkConfig networkConfig = NetworkConfig.builder()
//                .setAppSecret(BuildConfig.APP_SECRET)
//                .setDebugMode(BuildConfig.DEBUG)
//                .setRequestInterceptors(apiParamsInterceptor)
////                .setDnsEnable(true)//开启http dns，默认为不开启
////                .setDnsKey("dnsKey")//dfire dns解密key，开启dns时必须设置，key必须加密到so文件中
////                .setDnsPreloadHosts("api.2dfire.com", "dpush.2dfire.com")//预加载的host，可以提前解析dns，优化第一次网络访问
////                .setJsonConverter(jsonConverter)//json解析器，不设置时默认使用network:gson-mapper,内部实现是gson
//                .build();
//        NetworkService.init(networkConfig);//使用NetworkConfig初始化
//        NetworkService.init(BuildConfig.APP_SECRET, BuildConfig.DEBUG, apiParamsInterceptor);//含拦截器的初始化
    }

    /**
     * 公共参数
     */
    private static RequestInterceptor apiParamsInterceptor = new RequestInterceptor() {
        @Override
        public RequestModel intercept(RequestModel requestModel) {
            String callDeviceId = DeviceUtil.getDeviceId();
            if (callDeviceId.length() > 16) {
                callDeviceId = callDeviceId.substring(16, callDeviceId.length());
            }
            Map<String, String> apiParamMap = new HashMap<String, String>();
            apiParamMap.put("s_os", "android");                                         //系统.    android / ios /
            apiParamMap.put("s_osv", String.valueOf(android.os.Build.VERSION.SDK_INT)); //Android系统版本4.3.1
            apiParamMap.put("s_apv", BuildConfig.VERSION_NAME);                         //应用版本
            apiParamMap.put("s_net", getNetType());                                     //网络(有线:1 wifi:2 3G:3 4G:4 5G:5)
            apiParamMap.put("s_sc", DeviceUtil.getScreenWidAndHeight());                                       //屏幕尺寸(800x600);
            apiParamMap.put("s_br", Build.MODEL);                                       //手机品牌:  huawei
            apiParamMap.put("s_did", MD5Util.encode(callDeviceId));                     //设备ID（获取设备uuid再进行md5）
            apiParamMap.put("format", "json");                                          //返回格式,目前只支持json
//            apiParamMap.put("appKey", BuildConfig.APP_KEY);                             //api分配给每个应用的key
//            apiParamMap.put("sign", "1.0");                                             //由dop-sdk生成，仓库中找到jar,使用见http://git.test.dihuo.org/sdk/dop-sdk/tree/master
            apiParamMap.put("v", "1.0");                                                //API协议版本，可选值：1.0
            apiParamMap.put("timestamp", String.valueOf(System.currentTimeMillis()));   //时间戳
            return requestModel.newBuilder().addUrlParameters(apiParamMap).build();
        }
    };
    private static RequestInterceptor sessionInterceptor = new RequestInterceptor() {
        @Override
        public RequestModel intercept(RequestModel requestModel) {
            return requestModel.newBuilder().addHeader("sessionId", "121314124141").build();
        }
    };

    private static String getNetType() {
        switch (NetworkUtils.getNetworkType(TestApplication.getTestApplication())) {
            case NETWORK_WIFI:
                return "2";
            case NETWORK_4G:
                return "4";
            case NETWORK_3G:
            case NETWORK_2G:
                return "3";
            case NETWORK_UNKNOWN:
                return "";
        }
        return "";
    }
}
