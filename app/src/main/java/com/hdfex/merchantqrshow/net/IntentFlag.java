package com.hdfex.merchantqrshow.net;


/**
 * Created by harrishuang on 16/7/11.
 */
public interface IntentFlag {

    String CATCH_CITY = "CATCH_CITY";

    String SUBMIT_ORDER = "SUBMIT_ORDER";
    String SUBMIT_HUABEI_REQUEST="SUBMIT_HUABEI_REQUEST";
    String SCAN_DETAILS = "SCAN_DETAILS";
    String HUABEI_DETAILS = "HUABEI_DETAILS";
    /**
     * 订单信息
     */
    String  PAY_ORDER_QUERY="PayOrderQuery";


    /**
     * 选取照片
     */
    String PICTURE_SELECT = "PICTURE_SELECT";
    int PICTURE_REQUESTCODE = 521;
    int PICTURE_RESULT = 123;
    /**
     * 参数传递
     */
    String CONFORM = "CONFORM";
    String POSITION = "POSITION";
    String SERIALIZABLE = "SERIALIZABLE";
    String INTENT_NAME = "INTENT_NAME";
    String INTENT_CUSTOMER = "INTENT_CUSTOMER";
    /**
     * 意图传播
     */
    String INTENT_PIC = "INTENT_PIC";
    String INTENT_ROW_TYPE = "INTENT_ROW_TYPE";
    /**
     * 提交数据
     */
    String INTENT_ADDITIONAL = "Additional";

    String MAIN = "MAIN";


    String INTENT_HOUSE = "INTENT_HOUSE";

    String INTENT_CASE_WITH_CASEID = "INTENT_CASE_WITH_CASEID";

    String SCAN_TYPE_ZUFANG = "1";
    String SCAN_TYPE_OTHER = "2";
    String SCAN_TYPE_PAY = "3";

    /**
     * 清退
     */
    String HOSUE_TYPE_ELIMINATE = "0";
    /**
     * 闲置
     */
    String HOSUE_TYPE_FREE = "1";
    /**
     *
     */
    String HOSUE_TYPE_RENTED = "2";
    /**
     * 扫码类型
     */
    String INTENT_SCAN_TYPE = "INTENT_SCAN_TYPE";

    String INTENT_OPEN_TYPE = "INTENT_OPEN_TYPE";
    String INTENT_OPEN_QUERY = "INTENT_OPEN_QUERY";
    String INTENT_OPEN_CREATE = "INTENT_OPEN_SCAN";
    /**
     *
     */
    String INTENT_CONFORM_ZUFANG = "INTENT_CONFORM_ZUFANG";
    /**
     * 选择房屋
     */
    String INTENT_SELECT_HOUSE = "INTENT_SELECT_HOUSE";

    /**
     * 打开搜索页面
     */
    String INTENT_OPEN_SEARCH = "INTENT_OPEN_SEARCH";
    /**
     * 集中式
     */
    String HOUSE_TYPE_CONCENTRATE = "1";
    /**
     * 分散式
     */
    String HOUSE_TYPE_DISPERSE = "2";
    /**
     * 添加房源
     */
    String HOUSE_TYPE_NOAUTH_ADD = "0";

    /**
     * 房屋类型
     */
    String HOUSE_TYPE = "HOUSE_TYPE";
    /**
     * 事件处理
     */
    String HOUSE_APARTMENT_EVENT = "HOUSE_APARTMENT";
    /**
     * 选择的期数
     */
    String CURRENT_DURATION = "CURRENT_DURATION";


    String INDEX = "INDEX";
    /**
     * 订单的提交
     */
    String INTENT_ORDER = "INTENT_ORDER";
    /**
     * 提交
     */
    String INTENT_TYPEMODAL = "INTENT_TYPEMODEL";

    String SEARCH_NAME = "SEARCH_NAME";

    String APPLYID = "APPLYID";


    String TURN_DESC_KEY="TURN_DESC_KEY";
    String TURN_DESC_KEY_UPDATE="TURN_DESC_KEY_UPDATE";
    /**
     * 结束数据
     */
    String DETAILS_COMPLATE_UPDATE="DETAILS_COMPLATE_UPDATE";


    String FOLLOW_LIST_UPDATE="FOLLOW_LIST_UPDATE";


    int LEFT = 1;
    int RIGHT = 0;
    /**
     * 业务员详情接口
     */
    String SALES_DETAILS = "SALES_DETAILS";
    String TURN_ORDER = "TURN_ORDER";

    /**
     * 业务员Salesman
     */
    String ROLE_SALESMAN = "2";
    /**
     * 管理员Administrators
     */
    String ROLE_ADMINISTRATOR = "1";

    String ROLE_ADMIN = "3";
    /**
     * 销售员
     */
    String ROLE_APLIY_SALE="4";



    /**
     * 门店切换
     */
    String BUSSINESS_ID = "BUSSINESS_ID";
    /**
     * 信息问题
     */
    String INDEX_DRAWER = "INDEX_DRAWER";
    /**
     * 退出登录
     */
   String LOGOUT="LOGOUT";
    /**
     * 信息
     */
    String REGION="REGION";



    String COMM_LIST="COMM_LIST";
    /**
     * 订单id
     */
    String ORDER_ID="ORDER_ID";

    /**
     * 红包下载
     */
    String RED_LOAD="RED_LOAD";
    /**
     *花呗扫码
     */
    String HUABEI_SCAN="HUABEI_SCAN";
    /**
     * 淘宝扫码
     */
    String TAOBAO_SCAN="TAOBAO_SCAN";
    /**
     * 花呗选择商品
     */
    String  HUABEI_SELECT_COMMITY="HUABEI_SELECT_COMMITY";
    /**
     * 微信code
     */
    String   WX_QRCODE="WX_QRCODE";
    /**
     * 支付宝
     */
    String  ALIPAY_QRCODE="ALIPAY_QRCODE";
    /**
     * 修改登录密码
     */
    String  UPDATE_LOGIN_PASSWORD="UPDATE_LOGIN_PASSWORD";
    /**
     * 忘记登录密码
     */
    String  FORGOT_LOGIN_PASSWORD="FORGOT_LOGIN_PASSWORD";
    /**
     *设置体现密码
     */
    String   SETTING_WITHDRAW_PASSWORD="SETTING_WITHDRAW_PASSWORD";


    /**
     * 身份证信息
     */
    String  ID_CARD="ID_CARD";
    /**
     * 体现密码
     */
    String WITH_DRAW_PASSWORD="WITH_DRAW_PASSWORD";
    /**
     * 不允许添加房间
     */
    String  ADD_HOUSE_FLAG_NO="0";

    String   EXTENDS_BUSINESS=" <h4 style=\"color:red;\">1.支付宝账号类型说明</h4>\n" +
            "        <p>\n" +
            "            支付宝企业账号优先享有花呗分期3期的使用\n" +
            "        </p>\n" +
            "        <h4>2.支付宝账号说明</h4>\n" +
            "        <p>\n" +
            "            支付宝账户一般为邮箱或手机号，如已绑定邮箱，必须提供邮箱账号；未绑定邮箱的提供手机号。\n" +
            "        </p>\n" +
            "        <p>\n" +
            "            此支付宝账号作为收款账号，请务必保持正确。\n" +
            "        </p>\n" +
            "        <h4>3.支付宝PID查询方法</h4>\n" +
            "        <p>\n" +
            "            查询方法：登录支付宝-鼠标右键，查询网页源代码-Ctrl+F进行搜索-输入2088进行搜索-以2088开头的16位数字即为PID码\n" +
            "        </p>";

}
