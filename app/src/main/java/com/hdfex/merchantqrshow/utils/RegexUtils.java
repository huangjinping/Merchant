package com.hdfex.merchantqrshow.utils;

import android.text.TextUtils;

/**
 * 常用较验工具
 *
 * @author longtaoge
 */
public class RegexUtils {
    /**
     * 拆分线
     */
    public   static  final  String  SPLIT_LINE="-";


    // 匹配身份证号码
    private static final String idcard = "^(\\d{14}|\\d{17})(\\d|[xX])$";
    // 匹配信用卡号码
    private static final String creditcard = "((?:4\\d{3})|(?:5[1-5]\\d{2})|(?:6011)|(?:3[68]\\d{2})|(?:30[012345]\\d))[ -]?(\\d{4})[ -]?(\\d{4})[ -]?(\\d{4}|3[4,7]\\d{13})";
    // 匹配电子邮箱
    private static final String email = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
    // 匹配电话号码
    // private static final String phone =
    // "((\\(\\d{2,3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}(\\-\\d{1,4})?";
    private static final String phone = "^1[3|4|5|7|8][0-9]{9}$";
    // 匹配住宅电话
    // private static final String familyPhone = "[1-9](\\d{6,7}|\\d{10})";
//    private static final String familyPhone = "[1-9](\\d{6,7})";
    private static final String familyPhone = "([0-9]{3,4}-)?[0-9]{6,8}";

    // 匹配手机号码
    private static final String mobile = "((\\(\\d{2,3}\\))|(\\d{3}\\-))?(1[0123456789]\\d{9})";
    // 匹配网址
    private static final String url = "http://[A-Za-z0-9]+\\.[A-Za-z0-9]+[/=\\?%\\-&_~`@[\\]\':+!]*([^<>\"])*";
    // 匹配IP地址
    private static final String ip = "(0|[1-9]\\d?|[0-1]\\d{2}|2[0-4]\\d|25[0-5]).(0|[1-9]\\d?|[0-1]\\d{2}|2[0-4]\\d|25[0-5]).(0|[1-9]\\d?|[0-1]\\d{2}|2[0-4]\\d|25[0-5]).(0|[1-9]\\d?|[0-1]\\d{2}|2[0-4]\\d|25[0-5])";
    // 匹配货币
    private static final String currency = "\\d+(\\.\\d+)?";
    // 匹配邮政编码
    private static final String zip = "[0-9]\\d{5}";
    // 匹配QQ号码
    private static final String qq = "[1-9]\\d{4,9}";
    // 匹配英文字母
    private static final String english = "[A-Za-z]+";
    // 匹配大写字母
    private static final String uppercase = "[A-Z]+";
    // 匹配小写字母
    private static final String lowercase = "[a-z]+";
    // 匹配汉字
    private static final String chinese = "[\\u0391-\\uFFE5]+";
    // 匹配护照号码
    private static final String passport = " (P\\d{7})|(G\\d{8})";
    // 匹配国际书刊号
    private static final String isbn = "(\\d[- ]*){9}[\\dxX]";
    // 匹配数字，大小写字母，下划线
    private static final String username = "\\w{4,16}";
    // 匹配匹配自然数(0和正整数)
    private static final String number = "\\d+";
    // 匹配整数
    private static final String integer = "-?\\d+";
    // 匹配正整数
    private static final String positiveInteger = "[1-9][0-9]*";
    // 匹配负整数
    private static final String negativeInteger = "-[1-9][0-9]*";
    // 匹配浮点数
    private static final String doubled = "(-?\\d+)(\\.\\d+)?";

    // 用户名
    private static final String user_name = "[a-zA-Z][a-zA-Z0-9]{3,19}";

    // 密码
    private static final String user_password_login = "[a-zA-Z0-9]{6,20}";
//    private static final String user_password = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$";

    private  static  final  String  user_password="^[0-9A-Za-z]{6,}$";


    // 中文 英文 数字
    private static final String ChEngNum = "[\u4e00-\u9fa5\\w]+";





    private static  final  String  USER_BASE_NAME="[\\u4E00-\\u9FA5]{1,20}(?:·[\\u4E00-\\u9FA5]{1,20})*";


    public static class Result {
        /**
         * 验证消息
         */
        public String msg = "";
        /**
         * 是否匹配
         */
        public boolean match = false;

        public Result(boolean match, String msg) {
            this.match = match;
            this.msg = msg;
        }

        @Override
        public String toString() {
            return new StringBuilder("msg：").append(msg).append("，match：")
                    .append(match).toString();
        }
    }



    public static boolean idcard(String value) {
        return isNotNull(value) && IDCardCheck.Verify(value);
    }

    public static Result idcard(String label, String value) {
        boolean match = idcard(value);
        String msg = null;
        if (!match) {
            msg = label + "应该符合身份证号码格式";
        }
        return new Result(match, msg);
    }

    /**
     * 1.不为null<br>
     * 2.不为不为空字符串或者空格<br>
     * 3.不为"null"字符串<br>
     *
     * @param value 需要验证的数值
     * @return 验证它结果
     */
    public static boolean required(String value) {
        boolean match = isNotNull(value) && isNotBlank(value)
                && !"null".equalsIgnoreCase(value);
        return match;
    }

    public static boolean checkPhone(String value) {
        boolean match = value.length() == 7 || value.length() == 8;
        return match;
    }

    public static Result required(String label, String value) {
        boolean match = required(value);
        String msg = null;
        if (!match) {
            msg = label + "为必填项";
        }
        return new Result(match, msg);
    }

    /**
     * 不为null
     *
     * @param value 需要验证的数值
     * @return 验证它结果
     */
    public static boolean isNotNull(String value) {
        return null != value;
    }

    /**
     * 不为空字符串或者空格
     *
     * @param value 需要验证的数值
     * @return 验证它结果
     */
    public static boolean isNotBlank(String value) {
        return value.trim().length() != 0;
    }

//    public static boolean idcard(String value) {
//        return isNotNull(value) && IDCardCheck.Verify(value);
//    }

//    public static Result idcard(String label, String value) {
//        boolean match = idcard(value);
//        String msg = null;
//        if (!match) {
//            msg = label + "应该符合身份证号码格式";
//        }
//        return new Result(match, msg);
//    }

    public static boolean creditcard(String value) {
        return isNotNull(value) && value.matches(creditcard);
    }

    public static Result creditcard(String label, String value) {
        boolean match = creditcard(value);
        String msg = null;
        if (!match) {
            msg = label + "应该符合信用卡号码格式";
        }
        return new Result(match, msg);
    }

    public static boolean email(String value) {
        return isNotNull(value) && value.matches(email);
    }

    public static Result email(String label, String value) {
        boolean match = email(value);
        String msg = null;
        if (!match) {
            msg = label + "应该符合电子邮箱格式";
        }
        return new Result(match, msg);
    }

    public static boolean familyPhone(String value) {
        return isNotNull(value) && value.matches(familyPhone);
    }

    public static Result familyPhone(String label, String value) {
        boolean match = familyPhone(value);
        String msg = null;
        if (!match) {
            msg = label + "应该符合电话号码格式";
        }
        return new Result(match, msg);
    }

    public static boolean phone(String value) {
        return isNotNull(value) && value.matches(phone);
    }

    public static Result phone(String label, String value) {
        boolean match = phone(value);
        String msg = null;
        if (!match) {
            msg = label + "应该符合电话号码格式";
        }
        return new Result(match, msg);
    }

    public static boolean mobile(String value) {
        return isNotNull(value) && value.matches(mobile);
    }

    public static Result mobile(String label, String value) {
        boolean match = mobile(value);
        String msg = null;
        if (!match) {
            msg = label + "应该符合手机号码格式";
        }
        return new Result(match, msg);
    }

    public static boolean url(String value) {
        return isNotNull(value) && value.matches(url);
    }

    public static Result url(String label, String value) {
        boolean match = url(value);
        String msg = null;
        if (!match) {
            msg = label + "应该符合网页地址格式";
        }
        return new Result(match, msg);
    }

    public static boolean ip(String value) {
        return isNotNull(value) && value.matches(ip);
    }

    public static Result ip(String label, String value) {
        boolean match = ip(value);
        String msg = null;
        if (!match) {
            msg = label + "应该符合IP地址格式";
        }
        return new Result(match, msg);
    }

    public static boolean currency(String value) {
        return isNotNull(value) && value.matches(currency);
    }

    public static Result currency(String label, String value) {
        boolean match = currency(value);
        String msg = null;
        if (!match) {
            msg = label + "应该符合货币格式";
        }
        return new Result(match, msg);
    }

    public static boolean number(String value) {
        return isNotNull(value) && value.matches(number);
    }

    public static Result number(String label, String value) {
        boolean match = number(value);
        String msg = null;
        if (!match) {
            msg = label + "应该为数字";
        }
        return new Result(match, msg);
    }

    public static boolean zip(String value) {
        return value.matches(zip);
    }

    public static Result zip(String label, String value) {
        boolean match = zip(value);
        String msg = null;
        if (!match) {
            msg = label + "应该符合邮编格式";
        }
        return new Result(match, msg);
    }

    public static boolean qq(String value) {
        return isNotNull(value) && value.matches(qq);
    }

    public static Result qq(String label, String value) {
        boolean match = qq(value);
        String msg = null;
        if (!match) {
            msg = label + "应该符合QQ号码格式";
        }
        return new Result(match, msg);
    }

    public static boolean english(String value) {
        return isNotNull(value) && value.matches(english);
    }

    public static Result english(String label, String value) {
        boolean match = english(value);
        String msg = null;
        if (!match) {
            msg = label + "应该为英文字母";
        }
        return new Result(match, msg);
    }

    public static boolean uppercase(String value) {
        return isNotNull(value) && value.matches(uppercase);
    }

    public static Result uppercase(String label, String value) {
        boolean match = uppercase(value);
        String msg = null;
        if (!match) {
            msg = label + "应该为大写英文字母";
        }
        return new Result(match, msg);
    }

    public static boolean lowercase(String value) {
        return isNotNull(value) && value.matches(lowercase);
    }

    public static Result lowercase(String label, String value) {
        boolean match = lowercase(value);
        String msg = null;
        if (!match) {
            msg = label + "应该为小写英文字母";
        }
        return new Result(match, msg);
    }

    public static boolean chineseName(String value) {
        return isNotNull(value) && value.matches(USER_BASE_NAME);
    }



    public static boolean chinese(String value) {
        return isNotNull(value) && value.matches(chinese);
    }

    public static Result chinese(String label, String value) {
        boolean match = chinese(value);
        String msg = null;
        if (!match) {
            msg = label + "应该为汉字";
        }
        return new Result(match, msg);
    }

    public static boolean isbn(String value) {
        return isNotNull(value) && value.matches(isbn);
    }

    public static Result isbn(String label, String value) {
        boolean match = isbn(value);
        String msg = null;
        if (!match) {
            msg = label + "应该符合国际书刊号格式";
        }
        return new Result(match, msg);
    }

    public static boolean passport(String value) {
        return isNotNull(value) && value.matches(passport);
    }

    public static Result passport(String label, String value) {
        boolean match = passport(value);
        String msg = null;
        if (!match) {
            msg = label + "应该符合护照号码格式";
        }
        return new Result(match, msg);
    }

    public static boolean integer(String value) {
        return isNotNull(value) && value.matches(integer);
    }

    public static Result integer(String label, String value) {
        boolean match = integer(value);
        String msg = null;
        if (!match) {
            msg = label + "应该为整数";
        }
        return new Result(match, msg);
    }

    public static boolean positiveInteger(String value) {
        return isNotNull(value) && value.matches(positiveInteger);
    }

    public static Result positiveInteger(String label, String value) {
        boolean match = positiveInteger(value);
        String msg = null;
        if (!match) {
            msg = label + "应该为正整数";
        }
        return new Result(match, msg);
    }

    public static boolean negativeInteger(String value) {
        if (isNotNull(value)) {
            return value.matches(negativeInteger);
        } else {
            return false;
        }

    }

    public static Result negativeInteger(String label, String value) {
        boolean match = negativeInteger(value);
        String msg = null;
        if (!match) {
            msg = label + "应该为负整数";
        }
        return new Result(match, msg);
    }

    public static boolean doubled(String value) {
        return isNotNull(value) && value.matches(doubled);
    }

    public static Result doubled(String label, String value) {
        boolean match = doubled(value);
        String msg = null;
        if (!match) {
            msg = label + "应该为浮点数";
        }
        return new Result(match, msg);
    }

    public static boolean username(String value) {
        return isNotNull(value) && value.matches(username);
    }

    public static Result username(String label, String value) {
        boolean match = username(value);
        String msg = null;
        if (!match) {
            msg = label + "应该为数字、字母、_字符";
        }
        return new Result(match, msg);
    }

    public static boolean user_name(String value) {
        return isNotNull(value) && value.matches(user_name);
    }

    public static Result user_name(String label, String value) {
        boolean match = user_name(value);
        String msg = null;
        if (!match) {
            msg = label + "应该以字母开头，并且要在4到20位之间";
        }
        return new Result(match, msg);
    }




    public static boolean user_password(String value) {
        return isNotNull(value) && value.matches(user_password);
    }


    public  static  boolean user_password_login(String value){

        return isNotNull(value) && value.matches(user_password_login);

    }


    public static Result user_password(String label, String value) {
        boolean match = user_password_login(value);
        String msg = null;
        if (!match) {
            msg = label + "不能小于6位，不能大于20位";
        }
        return new Result(match, msg);
    }

    public static boolean date(String value) {
        return false;
    }

    public static boolean maxlength(String value, int max) {
        return isNotNull(value) && value.length() < max;
    }

    public static Result maxlength(String label, String value, int max) {
        boolean match = maxlength(value, max);
        String msg = null;
        if (!match) {
            msg = label + "应该是一个最大长度为" + max + "字符串";
        }
        return new Result(match, msg);
    }

    public static boolean minlength(String value, int min) {
        return isNotNull(value) && value.length() > min;
    }

    public static Result minlength(String label, String value, int min) {
        boolean match = minlength(value, min);
        String msg = null;
        if (!match) {
            msg = label + "应该是一个最小长度为" + min + "字符串";
        }
        return new Result(match, msg);
    }

    public static boolean rangelength(String value, int min, int max) {
        return isNotNull(value) && value.length() > min && value.length() < max;
    }

    public static Result rangelength(String label, String value, int min,
                                     int max) {
        boolean match = rangelength(value, min, max);
        String msg = null;
        if (!match) {
            msg = label + "应该是一个长度介于" + min + "和" + max + "之间的字符串";
        }
        return new Result(match, msg);
    }

    public static boolean max(String value, long max) {
        long val = Long.parseLong(value);
        return isNotNull(value) && val < max;
    }

    public static Result max(String label, String value, long max) {
        boolean match = max(value, max);
        String msg = null;
        if (!match) {
            msg = label + "应该小于" + max;
        }
        return new Result(match, msg);
    }

    public static boolean min(String value, long min) {
        long val = Long.parseLong(value);
        return isNotNull(value) && val > min;
    }

    public static Result min(String label, String value, long min) {
        boolean match = min(value, min);
        String msg = null;
        if (!match) {
            msg = label + "应该大于" + min;
        }
        return new Result(match, msg);
    }

    public static boolean range(String value, long min, long max) {
        long val = Long.parseLong(value);
        return isNotNull(value) && val > min && val < max;
    }

    public static Result range(String label, String value, long min, long max) {
        boolean match = range(value, min, max);
        String msg = null;
        if (!match) {
            msg = label + "应该大于" + min + "并且小于" + max;
        }
        return new Result(match, msg);
    }

    /**
     * 匹配开始字符串
     *
     * @param value
     * @param prefix
     * @return
     */
    public static boolean startsWith(String value, String prefix) {
        return isNotNull(value) && value.startsWith(prefix);
    }

    public static Result startsWith(String label, String value, String prefix) {
        boolean match = startsWith(value, prefix);
        String msg = null;
        if (!match) {
            msg = label + "应该以" + prefix + "开头";
        }
        return new Result(match, msg);
    }

    public static boolean endsWith(String value, String suffix) {
        return isNotNull(value) && value.endsWith(suffix);
    }

    public static Result endsWith(String label, String value, String suffix) {
        boolean match = endsWith(value, suffix);
        String msg = null;
        if (!match) {
            msg = label + "应该以" + suffix + "结尾";
        }
        return new Result(match, msg);
    }

    // 中文+ 英文+数子
    public static boolean ChEngNum(String value) {
        return (!TextUtils.isEmpty(value));
    }

    // 中文+ 英文+数子
    public static Result ChEngNum(String label, String value) {
        boolean match = ChEngNum(value);
        String msg = null;
        if (!match) {
            msg = label + "应该为汉字英文或数字的组合，且不应包含空格";
        }
        return new Result(match, msg);
    }

}