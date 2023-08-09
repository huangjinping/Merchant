package com.hdfex.merchantqrshow.utils;

import java.math.BigDecimal;

/**
 * Created by harrishuang on 16/7/11.
 */
public  class MathUtils {

    public static double add(double d1, double d2)
    {        // 进行加法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.add(b2).doubleValue();
    }
    public static double sub(double d1, double d2)
    {        // 进行减法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.subtract(b2).doubleValue();
    }
    public static String sub(String d1, String d2)
    {        // 进行减法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.subtract(b2).doubleValue()+"";
    }
    public static double mul(double d1, double d2)
    {        // 进行乘法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.multiply(b2).doubleValue();
    }



    public static double div(double d1,
                             double d2,int len) {// 进行除法运算
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2,len,BigDecimal.
                ROUND_HALF_UP).doubleValue();
    }
    public static double round(double d,
                               int len) {     // 进行四舍五入

        BigDecimal b1 = new BigDecimal(d);
        BigDecimal b2 = new BigDecimal(1);
        // 任何一个数字除以1都是原数字
        // ROUND_HALF_UP是BigDecimal的一个常量，

        return b1.divide(b2, len, BigDecimal.
                ROUND_HALF_UP).doubleValue();
    }


}
