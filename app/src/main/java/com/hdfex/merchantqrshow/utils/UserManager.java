package com.hdfex.merchantqrshow.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.hdfex.merchantqrshow.app.App;
import com.hdfex.merchantqrshow.bean.salesman.commodity.ImageModel;
import com.hdfex.merchantqrshow.bean.salesman.commodity.UploadData;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.bean.salesman.resource.Apartment;
import com.hdfex.merchantqrshow.bean.salesman.resource.ItemHouse;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理
 * Created by harrishuang on 16/7/5.
 */
public class UserManager {

    /**
     * @param user
     */

    private static final String LOGIN_USER = "LOGIN_USER";
    /**
     * 上传图片
     */
    private static final String UPlOAD_VIEW = "UPlOAD_VIEW";

    public static ImageModel currentImageModel;
    /**
     * 数据问题
     */
    private static final String COMMODITY = "COMMODITY";


    private static final String FIRST = "FIRST";

    private static final String PERMISSION = "hjpPermission";


    /**
     * 获取上传的图片
     *
     * @param uploadData
     */
    public static void saveUploadPic(Context context, UploadData uploadData) {

        PreferencesUtils utils = new PreferencesUtils(context, UPlOAD_VIEW);
        try {
            Gson gson = new Gson();
            utils.putString(LOGIN_USER, gson.toJson(uploadData));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static final String BANNER_URL = "BANNER_URL";


    public static String getBussnessBannder(String userId) {
        PreferencesUtils utils = new PreferencesUtils(App.getAppContext(), BANNER_URL);
        String bannerUrl = utils.getString(userId, "");
        return bannerUrl;
    }


    public static void saveBussnessBanner(String userId, String bannerUrl) {
        PreferencesUtils utils = new PreferencesUtils(App.getAppContext(), BANNER_URL);
        utils.putString(userId, bannerUrl);
    }

    /**
     * 下载上传的图片
     *
     * @return
     */
    public static UploadData getUpLoadPic(Context context) {
        PreferencesUtils utils = new PreferencesUtils(context, UPlOAD_VIEW);
        String userMessage = utils.getString(LOGIN_USER, "");
        UploadData uploadData = null;
        Gson gson = new Gson();
        try {
            uploadData = gson.fromJson(userMessage, UploadData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadData;

    }

    /**
     * @param context
     * @return
     */
    public static boolean isFirst(Context context) {
        PreferencesUtils utils = new PreferencesUtils(context, FIRST);
        String first = utils.getString(FIRST, "");
        if (TextUtils.isEmpty(first)) {
            utils.putString(FIRST, FIRST);
            return false;
        }

        return true;
    }


    /**
     * @param context
     * @return
     */
    public static boolean isPermission(Context context, String versition) {
        PreferencesUtils utils = new PreferencesUtils(context, PERMISSION);
        String first = utils.getString(PERMISSION, "");
        if (TextUtils.isEmpty(first)) {
//            utils.putString(PERMISSION, PERMISSION);
            return false;
        }
        if (!first.equals(versition)) {
            return false;
        }
        return true;
    }

    public static void setPermission(Context context, String versition) {
        PreferencesUtils utils = new PreferencesUtils(context, PERMISSION);
        if (!TextUtils.isEmpty(versition)) {
            utils.putString(PERMISSION, versition);
        }
    }


    /**
     * 清楚图片
     */
    public static void clearUpLoad(Context context) {
        SharedPreferences shared = context.getSharedPreferences(UPlOAD_VIEW, Context.MODE_PRIVATE);
        shared.edit().clear().commit();
    }


    public static void saveUser(Context context, User user) {
        PreferencesUtils utils = new PreferencesUtils(context, LOGIN_USER);
        try {
            Gson gson = new Gson();

            utils.putString(LOGIN_USER, gson.toJson(user));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static User getUser(Context context) {
        PreferencesUtils utils = new PreferencesUtils(context, LOGIN_USER);
        String userMessage = utils.getString(LOGIN_USER, "");
        User body = null;

        Gson gson = new Gson();

        try {
            body = gson.fromJson(userMessage, User.class);

            if (body != null) {
                /**
                 * 查询出来房屋类型sourceType
                 */
                if (body.getSourceType() != null) {
                    Map<String, String> map = parseDataType(body.getSourceType());
                    body.setSourceTypeMap(map);
                }
                /**
                 * houseType房屋类型
                 */
                if (body.getHouseType() != null) {
                    Map<String, String> map = parseDataType(body.getHouseType());
                    body.setHouseTypeMap(map);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }

    public static Map<String, String> parseDataType(String type) {
        if (TextUtils.isEmpty(type)) {
            return new HashMap<>();
        }
        Map<String, String> map;
        String[] arr = type.split("[,]+");
        if (arr != null) {
            map = new HashMap<>();
            for (int i = 0; i < arr.length; i++) {
                map.put(arr[i], arr[i]);
//                if (arr[i].equals("1")) {
//                    map.put("1", "1");
//                } else if (arr[i].equals("2")) {
//                    map.put("2", "2");
//                } else if (arr[i].equals("3")) {
//                    map.put("3", "3");
//                }
            }
            return map;
        }
        return new HashMap<>();
    }

    /**
     * 登录接口
     *
     * @return
     */
    public static boolean isLogin(Context context) {
        User user = getUser(context);
        if (user != null && !TextUtils.isEmpty(user.getToken())) {
            return true;
        }
        return false;
    }

    /**
     * 退出登入
     */
    public static void logout(Context context) {
        User user = getUser(context);
//        LogUtil.e("zbt","logout");
        if (user != null) {
            user.setToken("");
            saveUser(context, user);
        }
    }

    private static final String ADDRESS = "HOUSE_ADDRESS";

    /**
     * 保存数据问题
     *
     * @param context
     * @param itemHouse
     */
    public static void saveAddress(Context context, ItemHouse itemHouse) {
        PreferencesUtils utils = new PreferencesUtils(context, ADDRESS);
        try {
            Gson gson = new Gson();
            utils.putString(ADDRESS, gson.toJson(itemHouse));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ItemHouse getAddress(Context context) {
        PreferencesUtils utils = new PreferencesUtils(context, ADDRESS);
        String data = utils.getString(ADDRESS, "");
        try {
            if (!TextUtils.isEmpty(data)) {
                Gson gson = new Gson();
                ItemHouse itemHouse = gson.fromJson(data, ItemHouse.class);
                return itemHouse;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static final String APARTMENT = "HOUSE_APARTMENT";
//    apartment

    /**
     * 保存数据问题
     *
     * @param context
     * @param apartment
     */
    public static void saveApartment(Context context, Apartment apartment) {
        PreferencesUtils utils = new PreferencesUtils(context, APARTMENT);
        try {
            Gson gson = new Gson();
            utils.putString(APARTMENT, gson.toJson(apartment));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Apartment getApartment(Context context) {
        PreferencesUtils utils = new PreferencesUtils(context, APARTMENT);
        String data = utils.getString(APARTMENT, "");
        try {
            if (!TextUtils.isEmpty(data)) {
                Gson gson = new Gson();
                Apartment apartment = gson.fromJson(data, Apartment.class);
                return apartment;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 清楚图片
     */
    public static void clearApartment(Context context) {
        SharedPreferences shared = context.getSharedPreferences(APARTMENT, Context.MODE_PRIVATE);
        shared.edit().clear().commit();
    }


    private static final String GUIDE = "Guide";

    public static boolean getGuide(Context context) {
        PreferencesUtils utils = new PreferencesUtils(context, GUIDE);
        return utils.getBoolean(GUIDE, true);
    }


    public static void setGuide(Context context, boolean isGuide) {
        PreferencesUtils utils = new PreferencesUtils(context, GUIDE);
        utils.putBoolean(GUIDE, isGuide);
    }

    private static final String FOLLOW_PUBLIC_NUMBER = "FOLLOW_PUBLIC_NUMBER";

    public static boolean getFollow(Context context) {
        PreferencesUtils utils = new PreferencesUtils(context, FOLLOW_PUBLIC_NUMBER);
        return utils.getBoolean(FOLLOW_PUBLIC_NUMBER, true);
    }

    public static void setFollow(Context context, boolean isFollow) {
        PreferencesUtils utils = new PreferencesUtils(context, FOLLOW_PUBLIC_NUMBER);
        utils.putBoolean(FOLLOW_PUBLIC_NUMBER, isFollow);
    }

    /**
     * 推送设置
     */
    private static final String PUSH_SETTING = "PUSH_SETTING";

    /**
     * 是不是能推送
     *
     * @param context
     * @return
     */
    public static boolean isPushEnable(Context context) {


        return PreferencesUtils.getBoolean(context, PUSH_SETTING, true);
    }


    public static void setPushEnable(Context context, boolean isEnable) {
        PreferencesUtils.putBoolean(context, PUSH_SETTING, isEnable);

    }


}
