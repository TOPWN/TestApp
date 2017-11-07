package com.dfire.danggui.testapp.utils;

import android.text.TextUtils;

import java.math.BigDecimal;

/**
 * 金额帮助类，设置需要的精度
 *
 * @author DangGui
 * @create 2017/2/23.
 */

public class FeeHelper {
    /**
     * 将originFee根据precision（精度）转化为需要的字符串
     *
     * @param originFee 原始金额
     * @param precision 精度
     * @return
     */
    public static String getDecimalFee(double originFee, int precision) {
        BigDecimal bigDecimal = new BigDecimal(originFee);
        bigDecimal = bigDecimal.setScale(precision, BigDecimal.ROUND_HALF_UP);//设置精度
        return bigDecimal.toString();
    }

    /**
     * @param originFee
     * @return
     */
    public static String getDecimalFee(double originFee) {
        return getDecimalFee(originFee, 2); // 默认精度为2
    }

    /**
     * 将originFee根据precision（精度）转化为需要的字符串
     *
     * @param originFee 原始金额
     * @param precision 精度
     * @return
     */
    public static String getDecimalFee(String originFee, int precision) {
        if (TextUtils.isEmpty(originFee)) {
            return originFee;
        }
        BigDecimal bigDecimal = new BigDecimal(originFee);
        bigDecimal = bigDecimal.setScale(precision, BigDecimal.ROUND_HALF_UP);//设置精度
        return bigDecimal.toString();
    }
}

