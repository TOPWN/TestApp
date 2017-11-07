package com.dfire.danggui.testapp.dfirenetwork;

/**
 * 应用内所有的网络请求method<br />
 * <b>根据模块进行划分</b>
 *
 * @author DangGui
 * @create 2017/1/16.
 */

public class NetWorkMethodConstants {
    //根URL，是每次请求都需要携带的

    /**
     * “关注的桌位”模块
     */
    public static class ATTENTION_DESK {
        /*
         * 获得关注的桌位
         */
        public static final String GET_WATCHED_SEAT_LIST = "com.dfire.cloudcash.getWatchedSeatList";
    }
}
