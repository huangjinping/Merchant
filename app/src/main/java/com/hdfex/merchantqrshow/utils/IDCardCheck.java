package com.hdfex.merchantqrshow.utils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 验证身份证是否合法的
 * @author harris.huang
 *
 */
public class IDCardCheck {
    final static int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4,
            2, 1 };
    final static int[] vi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };
    private static int[] ai = new int[18];

    public IDCardCheck() {

    }

    /**
     *
     * 描述: 身份证校验 param idcard return
     */
    public static boolean Verify(String idcard) {
        idcard = idcard.toUpperCase();
        if (idcard.length() == 15) {
            idcard = uptoeighteen(idcard);
        }
        if (idcard.length() != 18) {
            return false;
        }
        if (!verifyDate(idcard)) {
            return false;
        }
        String verify = idcard.substring(17, 18);
        if (verify.equals(getVerify(idcard))) {
            return true;
        }
        return false;
    }

    public static String getIDCardTest(String inputStr) {
        if (getVerify(inputStr).equalsIgnoreCase("x")) {
            String str = inputStr.substring(0, 17) + "X";
            return str;
        } else {
            return inputStr;
        }
    }

    /**
     *
     * 描述: 获得身份证最后一位校验位 param eightcardid return
     */
    public static String getVerify(String eightcardid) {
        int remaining = 0;
        if (eightcardid.toUpperCase().length() == 18) {
            eightcardid = eightcardid.substring(0, 17);
        }
        if (eightcardid.toUpperCase().length() == 17) {
            int sum = 0;
            for (int i = 0; i < 17; i++) {
                String k = eightcardid.substring(i, i + 1);
                ai[i] = Integer.parseInt(k);
            }
            for (int i = 0; i < 17; i++) {
                sum = sum + wi[i] * ai[i];
            }
            remaining = sum % 11;
        }
        return remaining == 2 ? "X" : String.valueOf(vi[remaining]);
    }

    /**
     *
     * 描述: 15位身份证到升级到18位的算法 param fifteencardid return
     */
    public static String uptoeighteen(String fifteencardid) {
        String eighteencardid = fifteencardid.substring(0, 6);
        eighteencardid = eighteencardid + "19";
        eighteencardid = eighteencardid + fifteencardid.substring(6, 15);
        eighteencardid = eighteencardid + getVerify(eighteencardid);
        return eighteencardid;
    }

    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public static String dateOfBir = "";

    /**
     *
     * 描述:验证18位身份证中的日期是否合法,接受日期格式
     */
    public static boolean verifyDate(String eighteencardid) {

        String date1 = eighteencardid.substring(6, 10);
        date1 += "-";
        date1 += eighteencardid.substring(10, 12);
        date1 += "-";
        date1 += eighteencardid.substring(12, 14);
        dateOfBir = date1;
        Calendar c = Calendar.getInstance();
        String str = df.format(c.getTime());
        String[] astr = str.split("-");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < astr.length; i++) {
            sb.append(astr[i]);
        }
        String[] astra1 = date1.split("-");
        StringBuilder sba = new StringBuilder();
        for (int i = 0; i < astra1.length; i++) {
            sba.append(astra1[i]);
        }
        int aTemInt = Integer.parseInt(sb.toString());
        int aDate = Integer.parseInt(sba.toString());
        if (aDate > aTemInt) {
            System.out.println("对不起 生日不能大于当前的日期");
        }

        // System.out.println(sb);
        // 判断年月日的正则表达式，接受输入格式为2010-12-24，可接受平年闰年的日期
        String v = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)";
        Pattern p = Pattern.compile(v);
        Matcher m = p.matcher(date1);
        return m.matches();
    }

    /**
     *
     * 描述: 身份证15to18的完整算法 return
     */
    public static String get15To18(String sfzh) {
        if (sfzh == null || sfzh.length() != 15) {
            return sfzh;
        }
        // 校验码
        char[] sVC = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
        // 加权因子
        int[] sEQ = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
        String strTemp;
        int intTemp = 0;
        strTemp = sfzh.substring(0, 6) + "19" + sfzh.substring(6);
        try {
            for (int i = 0; i < strTemp.length(); i++) {
                intTemp += Integer.parseInt(strTemp.substring(i, i + 1))
                        * sEQ[i];
            }
        } catch (Exception e) {
            return sfzh;
        }
        intTemp = intTemp % 11;
        return strTemp + sVC[intTemp];
    }

    private static String getSex(String sfzh) {
        if (sfzh.length() == 18) {
            return sfzh.substring(16, 17);
        } else if (sfzh.length() == 15) {
            return sfzh.substring(13, 14);
        }
        return "";
    }

    public static String getDate(String eighteencardid) {
        String date1 = eighteencardid.substring(6, 10);
        date1 += "/";
        date1 += eighteencardid.substring(10, 12);
        date1 += "/";
        date1 += eighteencardid.substring(12, 14);
        dateOfBir = date1;
        return dateOfBir;
    }

    /**
     * <p>
     * 获取性别编码 0:女 1：男
     * </p>
     *
     * @param aStr
     * @return
     * @author
     * @date 2013-1-4
     */
    public static String getSexCode(String aStr) {
        if (Integer.parseInt(getSex(aStr)) % 2 == 0) {
            return "0"; // 女
        } else {
            return "1"; // 男
        }
    }

}