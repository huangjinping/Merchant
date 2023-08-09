package com.hdfex.merchantqrshow.utils.contract;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.utils.DialogUtils.NiftyDialogBuilder;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.dexter.OnPermissionListener;
import com.hdfex.merchantqrshow.utils.dexter.SampleMultipleBackgroundPermissionListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Request;

/**
 * author Created by harrishuang on 2017/9/4.
 * email : huangjinping@hdfex.com
 */

public class ContactAddrUtil {


    static String LOGTAG = "ContactAddrUtil";

    private static final String[] PHONES_PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Photo.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID};

    private static final int PHONES_DISPLAY_NAME_INDEX = 0;


    private static final int PHONES_NUMBER_INDEX = 1;

    private static final int PHONES_PHOTO_ID_INDEX = 2;

    private static final int PHONES_CONTACT_ID_INDEX = 3;

    /**
     * @param context
     * @return
     */
    public static ArrayList<ContactInfoData> getContacts(Context context) {
        ArrayList<ContactInfoData> list = new ArrayList<ContactInfoData>();
        Cursor cursor = null;

        try {
            Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String[] projection = new String[]{
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER};
            ContentResolver resolver = context.getContentResolver();
//			String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
            cursor = resolver.query(uri, projection, null, null, null);
            int index = 0;
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    //String nunStr =getFilter(phoneNum);
                    // 当手机号码为空的或者为空字段 跳过当前循环
                    index++;
                    if (TextUtils.isEmpty(phoneNum))
                        continue;

                    //字符转换为字符数组d
                    String phone = String.valueOf(phoneNum.trim().replace(" ", "").replace("+", ""));
                    //添加到对象
                    ContactInfoData man = new ContactInfoData();
                    man.setName(name);
                    man.setMobile(phone);
                    list.add(man);
                }
            }

            uri = Uri.parse("content://icc/adn");
            cursor = context.getContentResolver().query(uri, projection, null, null, null);
            index = 0;
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    index++;
                    if (TextUtils.isEmpty(phoneNum))
                        continue;

                    //字符转换为字符数组d
                    String phone = String.valueOf(phoneNum.trim().replace(" ", "").replace("+", ""));
                    //添加到对象
                    ContactInfoData man = new ContactInfoData();
                    man.setName(name);
                    man.setMobile(phone);
                    list.add(man);
                }
            }

        } catch (Exception e) {
            Log.d("hjp", e.toString());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    @SuppressLint("NewApi")
    public static String getFilter(String phoneNum) {
        Log.e(LOGTAG, "通讯录是这样的     " + phoneNum);
        if (!phoneNum.isEmpty()) {
            //是否合法
            if (phoneNum.substring(0, 1).equals("1")) {
                Log.e(LOGTAG, "字符串是否为数字" + phoneNum.length());
                return isNum(phoneNum);
            } else if (phoneNum.substring(0, 3).equals("+86")) {

                String Num = phoneNum.substring(3);
                if (Num.length() == 11) {
                    return isNum(Num);
                }
            }

        }
        return "";

    }


    /***
     *判断是不是授权联系人信息
     * @return
     */
    public static boolean isPermission(Context context) {
        PackageManager pm = context.getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.READ_CONTACTS", context.getPackageName()));
        if (permission) {
            Log.d("hjp", "有这联系人权限");
            return true;
        } else {
            Log.d("hjp", "没有这联系人权限");
            return false;
        }
    }

    /**
     * 获取权限
     *
     * @param context
     */
    public void getContractAuto(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                /**
                 * 请求询问一下网络
                 */
                requestPermission(context);
            } else {
                /**
                 * 请求询问网络
                 */
                upLoadContract(context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 展示权限信息
     *
     * @param
     */
    private void requestPermission(final Context context) {
      try {
          new Thread() {
              @Override
              public void run() {
                  Dexter.checkPermissionsOnSameThread(new SampleMultipleBackgroundPermissionListener(new OnPermissionListener() {
                      @Override
                      public void showPermissionGranted(String permission) {
                          /**
                           * 同意上传通讯录呵呵
                           */
                          upLoadContract(context);
                      }

                      @Override
                      public void showPermissionDenied(String permission, boolean isPermanentlyDenied) {
                          /**
                           * 这人不让上传通讯录
                           */
                          showPromisstionFroContact((Activity) context);
                      }

                      @Override
                      public void showPermissionRationale(PermissionToken token) {
                          token.continuePermissionRequest();
                          /**
                           * 再次询问开启
                           */
                      }
                  }), Manifest.permission.READ_CONTACTS);
              }
          }.start();
      }catch (Exception e){
          e.printStackTrace();
      }
    }

    private void showPromisstionFroContact(final Activity activity) {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(activity);
        dialogBuilder
                .withTitle("信息提示")
                .withTitleColor("#FFFFFF")
                .withMessage("请允许通讯录权限")
                .withMessageColor("#000000")
                .withDialogColor("#FFFFFF")
                .withIcon(activity.getResources().getDrawable(R.mipmap.ic_merchant_logo))
                .isCancelableOnTouchOutside(true)
                .withDuration(100)
                .withButton1Text("知道了")
                .isCancelable(true)
                .isCancelableOnTouchOutside(true)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                        Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.parse("package:" + activity.getPackageName()));
                        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
                        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        activity.startActivity(myAppSettings);
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
    }


    /**
     * 读取并上传通讯录
     */
    public void upLoadContract(Context context) {

        User user = UserManager.getUser(context);
        ArrayList<ContactInfoData> contacts = getContacts(context);
        String gsonString = GsonUtil.createGsonString(contacts);
        OkHttpUtils.post()
                .url(NetConst.ADDRESSBOOK)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("addressList", gsonString)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build()
                .connTimeOut(200000)
                .readTimeOut(200000)
                .writeTimeOut(200000)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void inProgress(float progress) {
                        super.inProgress(progress);
                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);

                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();

                    }

                    @Override
                    public void onResponse(String response) {

                    }
                });


    }


    /**
     * 是否为数字
     *
     * @param
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static String isNum(String Num) {
        //字符串是否为数字
        if (isNumeric(Num)) {
            return Num;
        } else {
            return "";
        }
    }
}
