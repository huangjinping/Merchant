package com.hdfex.merchantqrshow.net;

/**
 * 接口地址
 * Created by harrishuang on 16/7/4.
 */
public interface NetConst {
    /**
     * 根目录
     */
//  正式
//    String HOME_URL = "http://101.200.185.96:8280/hd-merchant-biz-app";

//  测试
//    String HOME_URL = "http://182.92.6.16:8081/hd-merchant-biz-app";


    String HOME_URL = "https://ib.daiyutech.com/hd-merchant-biz-app";

    /**
     * 测试张
     */
//    String HOME_URL = "http://10.2.0.234:7005/bizapp/";
//    String HOME_URL = "http://10.1.21.120:8001/hd-merchant-biz-app";


    String httpss_1 = HOME_URL.startsWith("https://ib.daiyutech.com") ? "https://ic.daiyutech.com/hd-merchant-web/mobile/" : "https://daiyutech.com/hd-merchant-web/mobile/";

    String GET_SYSTEM_CONFIG = httpss_1 + "getSystemConfig";
    String PERMISSION_URL = "https://daiyutech.com/appagree/bPermissionIndex.html";


    /**
     * 新希望
     */
//    String HOME_URL = "http://60.205.122.224/hd-merchant-biz-app";

//    张宇
//    String HOME_URL = "http://10.1.19.128:8001/hd-merchant-biz-app";

    /**
     * 房源地址
     */
//    String HOME_HOUSE_URL = "http://10.1.19.128:9800/house/app";
//      String HOME_HOUSE_URL = "http://182.92.6.16:9999/house/app";
    String HOME_HOUSE_URL = "http://h.daiyutech.com/house/app";


    /**
     * 版本更新
     */
    //正式
//    String BASEURL = "http://123.56.233.192/hd-merchant-web/mobile/";
    //测试
    String BASEURL = "http://ic.daiyutech.com/hd-merchant-web/mobile/";
    /**
     * 4.1.	登陆
     */
    String LOGIN = HOME_URL + "/app/login";
    /**
     * 获取权限接口
     */
    String AUTH_HT = HOME_URL + "/app/auth/ht";
    /**
     * 获取签约列表
     */
    String SIGN_STATUS_LIST = HOME_URL + "/app/query/signstatuslist";

    /**
     *
     */
    String QUERY_SIGNURL = HOME_URL + "/app/query/signurl";

    /**
     * 4.2.	商品分类查询
     */
    String COMMODITY_TYPE = HOME_URL + "/app/product/queryCategory";

    /**
     * 4.3.	商品列表
     */
    String PRODUCT_LIST = HOME_URL + "/app/product/list";

    /**
     * 4.4.	分期专案查询
     */
    String COMMODITY_DETAILS = HOME_URL + "/app/case";

    /**
     * 4.4专案查询
     */

    String APP_CASE = HOME_URL + "/app/case";


    /**
     * 4.5生成二维码
     */

    String APP_GRENTATE = HOME_URL + "/app/qrgenrate";

    /**
     * 4.6.	商品包信息查询
     */

    String QUERY_COMMODITY_INFO = HOME_URL + "/app/queryCommodityInfo";

    /**
     * 4.7.	扫描未提交记录查询
     */

    String QUERY_UNCOMPELETE = HOME_URL + "/app/queryUncompelete";

    /**
     * 4.8.订单列表
     */

    String ORDER_LIST = HOME_URL + "/app/orderList";
    /**
     * 4.8.订单列表 新的
     */

    String LIST_ORDER = HOME_URL + "/app/listOrder";

    /**
     * 4.9.订单详情
     */

    String ORDER_DETAIL = HOME_URL + "/app/orderDetail";


    /**
     * 4.12.账单详情
     */

    String REPAY_DETAIL = HOME_URL + "/app/repayDetail";

    /**
     * 4.10.商品新增
     */

    String COMMODITY_CREATE = HOME_URL + "/app/product/commodityAdd";

    /**
     * 4.11.合同上传
     */
    String UPLOAD_CONTRACT = HOME_URL + "/app/product/uploadContract";

    /**
     * 4.12.租房类商品包二维码信息查询
     */
    String REALTY_QR_QUERY = HOME_URL + "/app/queryCommodityPackageForZufang";

    /**
     * 4.13.基础数据获取
     */
    String ADDITIONAL_DATA = HOME_URL + "/app/queryBaseDateForCj";
    /**
     * 获取基础数据
     */
    String QUERY_BASEDATA = HOME_URL + "/app/queryBaseData";

    /**
     * 4.14.图片删除
     */
    String DEL_PIC = HOME_URL + "/app/ product/delPic";
    /**
     * 首页地址
     */
    String INDEX = HOME_URL + "/app/index";
    /**
     * 联系人地址
     */
    String CUSTLIST = HOME_URL + "/app/custList";
    /**
     * 4.18.商品列表（碧水源）
     */
    String BSY_PRODUCT_LIST = HOME_URL + "/app/originwater/product/list";
    /**
     * 提交订单
     */
    String BSY_LIST_ORDER = HOME_URL + "/app/originwater/order/submit";
    /**
     * 商品详情
     */
    String BSY_DETAIL = HOME_URL + "/app/originwater/product/detail";
    /**
     * 订单详情
     */
    String BSY_ORDER_DETAILS = HOME_URL + "/app/originwater/order/detail";
    /**
     * 订单列表
     */
    String BSY_ORDER_LIST = HOME_URL + "/app/originwater/order/list";
    /**
     * 增加房源信息
     */
    String CREATEHOUSE = HOME_URL + "/app/house/createHouse";
    /**
     * 房屋列表
     */
    String HOUSE_LIST = HOME_URL + "/app/house/getHouseList";

    /**
     * 房源管理系统
     */
    String HOUSE_NEW_LIST = HOME_HOUSE_URL + "/house/getHouseList";

    /**
     * 计算房租到期日
     */
    String CALCULATE_DUEDATE = HOME_URL + "/app/product/calculateDueDate";
    /**
     * 计算租房日期
     */
    String CREATE_QRCODE = HOME_URL + "/app/house/createQRcode";
    /**
     * 删除房子
     */
    String DELEHOUSE = HOME_URL + "/app/house/delHouse";
    /**
     * 修改房子
     */
    String MODHOUSE = HOME_URL + "/app/house/modHouse";
    /**
     * 供金额获取接口
     */
    String GETPERIODAMOUNT = HOME_URL + "/app/getPeriodAmount";
    /**
     * 4.30.	获取集中式公寓列表
     */
    String GET_APARTMENT_LIST = HOME_URL + "/app/house/getApartmentList";
    /**
     * 4.31.	提交发货证明
     */
    String SUBMIT_SENDINFO = HOME_URL + "/app/submitSendInfo";
    /**
     * 房租计算器
     */
    String CALCULATE_REFUNDAMT = HOME_URL + "/app/calculateRefundAmt";
    /**
     * 列表记录
     */
    String GETORDERLIST_V2 = HOME_URL + "/app/v2/order/getOrderList";
    /**
     * 月付订单列表
     */
    String CONTRACT_GET_ORDER_LIST = HOME_URL + "/app/contract/getOrderList";
    /**
     * 获取订单
     */
    String CONTRACT_GET_CONTRACT_DETAIL = HOME_URL + "/app/contract/getContractDetail";

    /**
     * 4.36.	业务管理-预警（管理员）
     */
    String BUSINESS_FORWARN = HOME_URL + "/app/admin/businessForWarn";
    /**
     * 4.37.	业务管理-订单当日（管理员）
     */
    String BUSINESS_FORCURORDER = HOME_URL + "/app/admin/businessForCurOrder";
    /**
     * 4.38.	业务管理-订单总量（管理员）
     */
    String BUSINESS_FORTOTALORDER = HOME_URL + "/app/admin/businessForTotalOrder";
    /**
     * 4.39.	 业务管理-房源（管理员）
     */
    String BUSINESS_FORHOUSE = HOME_URL + "/app/admin/businessForHouse";
    /**
     * 4.40.	订单列表（管理员
     */

    String ORDER_LISTFORADMIN = HOME_URL + "/app/admin/getOrderListForAdmin";

    /**
     * 4.41.	团队管理（管理员）
     */

    String GET_TEAM = HOME_URL + "/app/admin/team";

    /**
     * 4.45.	区域人员列表（管理员）
     */
    String REGIONLIST = HOME_URL + "/app/admin/regionList";

    /**
     * 4.43.	人员详情（管理员）
     */
    String PERSONDETAIL = HOME_URL + "/app/admin/personDetail";

    /**
     * 4.44.	人员禁用/启用（管理员
     */
    String PERSON_FORBID = HOME_URL + "/app/admin/personForbid";
    /**
     * 4.42.	人员业绩前10列表（管理员）
     */
    String PERSONRECORDLIST = HOME_URL + "/app/admin/personRecordList";
    /**
     * 4.46.	门店列表（管理员）
     */
    String STORESLIST = HOME_URL + "/app/admin/storesList";
    /**
     * 4.47.	区域列表（管理员）
     */
    String REGION_FOR_ORDERLIST = HOME_URL + "/app/admin/regionForOrderList";
    /**
     * 4.48.	财务管理（管理员）
     */
    String FINANCE_MANAGER = HOME_URL + "/app/admin/financeManager";

    /**
     * 4.49.	提交订单（花呗分期）
     */
    String APLIPAYORDER_SUBMITORDER = HOME_URL + "/app/alipayOrder/submitOrder";
    /**
     * 4.50.	扫码支付（花呗分期）
     */
    String ALIPAORDER_SANPAY = HOME_URL + "/app/alipayOrder/scanPay";
    /**
     * 4.51.	订单列表（花呗分期）
     */
    String ALIPAY_ORDER = HOME_URL + "/app/alipayOrder/orderList";
    /**
     * 4.51.	4.52.	订单详情（花呗分期）
     */
    String ALIPAY_ORDER_DETAILS = HOME_URL + "/app/alipayOrder/orderDetail";
    /**
     * 4.59.	获取消息列表
     */
    String APP_MESSAGE = HOME_URL + "/app/message";

    /**
     * 4.61.	消息详情
     */
    String APP_MESSAGE_DETAILS = HOME_URL + "/app/message/detail";

    /**
     * 4.61.	消息详情
     */
    String APP_MESSAGE_RES = HOME_URL + "/app/message/read";


    /**
     * 4.57.	打开红包
     */

    String OPEN_REDPACKAGE = HOME_URL + "/app/openRedPackage";

    /**
     * 淘宝授权认证
     */
    String ALIPAY_USERAUTH = HOME_URL + "/app/aliPayUserAuth";
    /**
     *
     */
    String ALIPAY_WITHDRAW_DEPOSIT = HOME_URL + "/app/aliPay/withdrawDeposit";
    /**
     * 添加数据
     */
    String ALIPAY_WITHDRAW_PRECREATE = HOME_URL + "/app/alipayOrder/precreate";

    /**
     * 4.55.	账户余额查询
     */
    String RED_PACKAGEACCOUNT_INFO = HOME_URL + "/app/redPackageAccountInfo";
    /**
     * 红包列表
     */
    String RED_PACKAGE_LIST = HOME_URL + "/app/redPackageList";
    /**
     * 4.56.	支付宝账号解绑
     */
    String ALIPAY_ACCOUNTUNBUND = HOME_URL + "/app/aliPay/accountUnbund";
    /**
     * 4.56.	支付订单查询（花呗分期）
     */
    String PAY_ORDER_QUERY = HOME_URL + "/app/alipayOrder/payOrderQuery";
    /**
     * 提交预订单
     */
    String PRE_SUBMIT_ORDER = HOME_URL + "/app/alipayOrder/preSubmitOrder";
    /**
     * 结束订单
     */
    String FINISH_ORDER = HOME_URL + "/app/alipayOrder/finishOrder";
    /**
     * 扫码
     */
    String SCAN_PAY_V2 = HOME_URL + "/app/alipayOrder/scanPayV2";
    /**
     * 4.57.	花呗分期手续费查询（花呗分期）
     */
    String DURATION_PAYMENT = HOME_URL + "/app/alipayOrder/durationPayment";
    /**
     * 提交地址顺序
     */
    String ADDRESSBOOK = HOME_URL + "/app/addressBook";
    /**
     * 4.67.	登录页banner
     */

    String BUSSNESS_BANNER_QUERY = HOME_URL + "/app/bussnessBannerQuery";
    /**
     * 界面处理
     */
    String ID_CARD_UPLOAD = HOME_URL + "/app/idCardUpload";
    /**
     * 获取认证信息
     */
    String ID_CARD_QUERY = HOME_URL + "/app/idCardQuery";

    /**
     * 4.69.1.	客户预约列表
     */
    String BUS_SUBS_CRIBE_LIST = HOME_URL + "/app/discovery/busSubscribeList";
    /**
     * 4.69.2.	公共线索列表
     */
    String BUS_PUBLIC_CLUE_LIST = HOME_URL + "/app/discovery/busPublicClueList";

    /**
     * 转单
     */
    String BUSSINESS_TURN_ORDER = HOME_URL + "/app/discovery/bussinessTurnOrder";
    /**
     * 4.69.5.	接单/拒绝
     */
    String UPDATE_BUSS_SUBSCRIBE_STATUS = HOME_URL + "/app/discovery/updateBussSubscribeStatus";
    /**
     * 4.69.6.抢单
     */
    String APP_DISCOVERY_RESHORDER = HOME_URL + "/app/discovery/rushOrder";
    /**
     * 预约详情
     */
    String BUSSINESS_SUBSCRIBE_DETAIL = HOME_URL + "/app/discovery/bussinessSubscribeDetail";
    /**
     * 4.69.9.	客户跟进列表
     */
    String FOLLOW_UP_LIST_QUERY = HOME_URL + "/app/discovery/followUpListQuery";
    /**
     * 4.69.9.	客户跟进列表v2
     */
    String FOLLOW_UP_LIST_QUERY_V2 = HOME_URL + "/app/discovery/followUpListQueryV2";
    /**
     * 4.69.10.	客户跟进详情
     */
    String FOLLOW_UP_DETAIL_QUERY = HOME_URL + "/app/discovery/followUpDetailQuery";
    /**
     * 4.69.11.	客户跟进
     */
    String FOLLOW_UP_UPDATE = HOME_URL + "/app/discovery/followUpUpdate";
    /**
     * 上传图片接口
     */
    String AVATAR_UPLOAD = HOME_URL + "/app/avatarUpload";
    /**
     * 添加数据
     */
    String FINISH_SUBSCRIBE = HOME_URL + "/app/discovery/finishSubscribe";
    /**
     * 数据异常列表
     */
    String DATA_ERROR_LIST = HOME_URL + "/app/discovery/dataErrorList";
    /**
     * 4.70.13.	待还款/逾期列表
     */
    String DISCOVERY_REPAYLIST = HOME_URL + "/app/discovery/repayList";
    /**
     * 订单列表
     */
    String DISCOVERY_BACKORDERLIST = HOME_URL + "/app/discovery/backOrderList";
    /**
     * 4.70.11.	客户跟进详情（电核）
     */
    String FOLLOW_UPDATE_DETAIL_QUERY = HOME_URL + "/app/discovery/followUpDetailQuery";
    /**
     * 4.70.12.	客户跟进详情（其他资料）
     */
    String FOLLOW_UP_MATERICAL_DETAIL_QUERY = HOME_URL + "/app/discovery/followUpMaterialDetailQuery";
    /**
     * 4.70.15.	资料异常更新
     */
    String FOLLOW_UP_ORTHER_UPDATE = HOME_URL + "/app/discovery/followUpOrtherUpdate";
    /**
     * 4.70.17.	跟进历史列表
     */
    String DATA_ERROR_HIS_LIST = HOME_URL + "/app/discovery/dataErrorHisList";
    /**
     * 4.70.20.	更新电话次数
     */
    String UPDATE_PHONE_COUNT = HOME_URL + "/app/discovery/updatePhoneCount";
    /**
     * 4.70.21.	更新短信次数
     */
    String UPDATE_SMS_COUNT = HOME_URL + "/app/discovery/updateSMSCount";
    /**
     * 管理员首页
     */
    String ADMIN_PERSON_COUNT = HOME_URL + "/app/admin/commodityCount";
    /**
     * 4.36.16.	新增商品（管理员）
     */
    String ADMIN_SAVE_COMMODIY = HOME_URL + "/app/admin/saveCommodity";
    /**
     * 4.36.19.	保存业务员（管理员）
     */
    String SAVE_BIZ_USER = HOME_URL + "/app/admin/saveBizUser";
    /**
     * 4.36.19.	更新业务员（管理员）
     */
    String UPDATE_BIZ_USER = HOME_URL + "/app/admin/updateBizUser";

    /**
     * 获取基本信息
     */
    String FIND_COMMODITY_DETAIL = HOME_URL + "/app/admin/findCommodityDetail";
    /**
     * 4.36.21.	专案列表（管理员）
     */
    String BUSSINESS_CASE_INFO = HOME_URL + "/app/admin/queryBussinessCaseInfo";
    /**
     * 4.36.17.	商品列表（管理员
     */
    String FIND_COMMODITY_LIST_BY_COUNTER_FLG = HOME_URL + "/app/admin/findCommodityListByCounterFlag";
    /**
     * 4.36.16.	更新商品状态（管理员）
     */
    String UPDATE_COMMODITY_STATUS = HOME_URL + "/app/admin/updateCommodityStatus";
    /**
     * 4.5.	分期专案查询(商户专案)
     */
    String BUSSINESS_CASE = HOME_URL + "/app/bussinessCase";
    /**
     * 4.38.13.	预生成订单V2（花呗分期）
     */
    String PRE_SUBMIT_ORDERV2 = HOME_URL + "/app/alipayOrder/preSubmitOrderV2";

    /**
     * 4.38.12.	扫码支付V2（客户主扫）
     */
    String PRE_CREATE_V2 = HOME_URL + "/app/alipayOrder/precreateV2";
    /**
     * 4.38.11.	扫码支付V3（花呗分期）
     */
    String SCAN_PAY_V3 = HOME_URL + "/app/alipayOrder/scanPayV3";
    /**
     * 4.37.22.	财务管理（管理员）
     */
    String QUERY_FINANCE_INFO = HOME_URL + "/app/admin/queryFinanceInfo";
    /**
     * 4.40.	支付宝提现
     */
    String WITH_DRAW_DEPOSIT = HOME_URL + "/app/aliPay/withdrawDeposit";
    /**
     * 4.38.14.	订单列表（花呗分期-管理员）
     */
    String GET_ALIPAY_ORDER_FOR_ADMIN = HOME_URL + "/app/admin/getAlipayOrderForAdmin";
    /**
     * 4.38.15.	订单详情（花呗分期-管理员）
     */
    String GET_ALIPAY_ORDER_DETAIL_FOR_ADMIN = HOME_URL + "/app/admin/getAlipayOrderDetailForAdmin";

    /**
     * 生成二维码
     */
    String CREATE_QRCODE_PAY = HOME_URL + "/app/admin/createQRCode";
    /**
     * 4.38.18.	生成二维码
     */
    String APLIY_GET_QR_CODE = HOME_URL + "/app/admin/getQRCode";
    /**
     * 4.38.19.	支付宝在线收款
     */
    String SCAN_PAY_FOROL = HOME_URL + "/app/alipayOrder/scanPayForOL";
    /**
     * 更新密码
     */
    String MODIFY_PASSWORD = HOME_URL + "/app/admin/modifyPassword";
    /**
     * 4.37.24.	设置提现密码（管理员）
     */
    String SET_WITH_DRAWAL_PWD = HOME_URL + "/app/admin/setWithdrawalPwd";
    /**
     * 4.53.	房源管理系统创建二维码（新）
     */
    String CREATE_QRCODE_FORHR = HOME_URL + "/app/house/createQRcodeForHR";

    /**
     * 4.54.商品包信息查询（房源管理系统）
     */
    String QUERY_COMMODITY_INFO_FORHR = HOME_URL + "/app/queryCommodityInfoForHR";
    /**
     * 4.55.	房源管理系统支付类型列表（新）
     */
    String GET_PAY_TYPE_LIST = HOME_URL + "/app/contract/getPayTypeList";
    /**
     * 4.4.57.	新闻公告列表查询
     */
    String APP_NOTICE_LIST = HOME_URL + "/app/notice/list";
    /**
     * 4.58.	分期金额试算
     */
    String APP_QUERY_PERIODAMOUNT = HOME_URL + "/app/queryPeriodAmount";

    /**
     * 结束签名
     */
    String BAST_NEW_SIGN = "bestSignUrl/bestSignSucess.html";
    /**
     * 校验显示不显示
     */
    String CHECK_SHOW_BY_BUSS = HOME_URL + "/app/checkShowByBuss";

    /**
     * 获取订单
     */
    String GET_ORDER_LIST_V3 = HOME_URL + "/app/v3/order/getOrderList";
    /**
     * 订单状态接口
     */
    String GET_ORDER_UNSUBMIT_ORDER_DETAIL = HOME_URL + "/app/v3/order/unSubmitOrderDetail";
    /**
     * 获取账单
     */
    String GET_REPAY_DETAIL = HOME_URL + "/app/v3/order/getRepayDetail";
    /**
     * 需变更资方订单列表
     */
    String GET_ORDER_CHANGE_LIST = HOME_URL + "/app/discovery/getOrderChangeList";
    /**
     * 转资方订单获取专案列表
     */
    String GET_CASE_WITH_CASEID_LIST = HOME_URL + "/app/change/order/getCaseWithCaseIdList";
    /**
     * 1.1.	转资方订单提交
     */
    String CONFIRM_CHANGE_ORDER = HOME_URL + "/app/change/order/confirmChangeOrder";
    /**
     * 1.1.	沃付订单列表
     */
    String GET_APP_DISCOVERY_LEDINGORDER_LIST = HOME_URL + "/app/discovery/getWordOrderList";
    /**
     * app/discovery/ledingOrder
     */
    String GET_APP_DISCOVERY_LEDINGORDER = HOME_URL + "/app/discovery/ledingOrder";
    /**
     * app/discovery/cancelOrder
     */
    String GET_APP_DISCOVERY_CANCEL_ORDER = HOME_URL + "/app/discovery/cancelOrder";


}
