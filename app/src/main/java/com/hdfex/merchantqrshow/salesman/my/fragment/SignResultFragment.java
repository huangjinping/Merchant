package com.hdfex.merchantqrshow.salesman.my.fragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseFragment;
import com.hdfex.merchantqrshow.bean.salesman.home.SignStatusItem;
import com.hdfex.merchantqrshow.bean.salesman.home.SignStatusListResponse;
import com.hdfex.merchantqrshow.bean.salesman.login.User;
import com.hdfex.merchantqrshow.net.NetConst;
import com.hdfex.merchantqrshow.net.okhttp.OkHttpUtils;
import com.hdfex.merchantqrshow.net.okhttp.builder.PostFormBuilder;
import com.hdfex.merchantqrshow.net.okhttp.callback.FileCallBack;
import com.hdfex.merchantqrshow.net.okhttp.callback.StringCallback;
import com.hdfex.merchantqrshow.salesman.my.adapter.SignResultAdapter;
import com.hdfex.merchantqrshow.utils.GsonUtil;
import com.hdfex.merchantqrshow.utils.LoadMode;
import com.hdfex.merchantqrshow.utils.StringUtils;
import com.hdfex.merchantqrshow.utils.ToastUtils;
import com.hdfex.merchantqrshow.utils.UserManager;
import com.hdfex.merchantqrshow.utils.log.LogUtil;
import com.hdfex.merchantqrshow.view.MyItemClickListener;
import com.hdfex.merchantqrshow.widget.ActionSheet;
import com.hdfex.merchantqrshow.widget.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * author Created by harrishuang on 2019/5/31.
 * email : huangjinping1000@163.com
 */
public class SignResultFragment extends BaseFragment {

    public String status;
    private MultiStateView multiStateView;
    private RecyclerView rec_sign_result;
    private SmartRefreshLayout xr_freshview;
    private SignResultAdapter mAdapter;
    private List<SignStatusItem> dataList;

    private User user;
    /**
     * 下载数据
     *
     * @param loadMode
     */
    private int page = 0;

    public static BaseFragment getInstance(String status) {
        SignResultFragment fragment = new SignResultFragment();
        fragment.status = status;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_result, container, false);
        user = UserManager.getUser(getActivity());
        initView(view);
        xr_freshview.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                loadData(LoadMode.PULL_REFRSH);
            }
        });
        xr_freshview.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                loadData(LoadMode.UP_REFRESH);
            }
        });
        xr_freshview.setEnableLoadmore(true);
        dataList = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            dataList.add("==" + i);
//        }
        mAdapter = new SignResultAdapter(dataList);
        rec_sign_result.setLayoutManager(new LinearLayoutManager(getActivity()));
        rec_sign_result.setAdapter(mAdapter);

        mAdapter.setItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                try {
                    SignStatusItem signStatusItem = dataList.get(position);

                    shareAgreement(signStatusItem);
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                openPDFFile("https://ic.daiyutech.com/hd-merchant-web/mobile/picture/down?file=upload/contract/20190531/1209_6540162792669840385.pdf");
            }
        });
        loadData(LoadMode.NOMAL);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void loadData(final LoadMode loadMode) {
        if (!connect()) {
            return;
        }
        if (loadMode == LoadMode.NOMAL) {
            showProgress();
        }
        if (loadMode != LoadMode.UP_REFRESH) {
            page = 0;
        }
        String url = NetConst.SIGN_STATUS_LIST;
        PostFormBuilder postFormBuilder = OkHttpUtils
                .post()
                .url(url)
                .addParams("token", user.getToken())
                .addParams("id", user.getId())
                .addParams("status", "0")
                .addParams("pageIndex", page + "")
                .addParams("pageSize", "10")
                .addParams("status", status);
        postFormBuilder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter() {
                        super.onAfter();
                        if (getActivity() == null) {
                            return;
                        }
                        endLoadView();
                        dismissProgress();

                    }

                    @Override
                    public void onBefore(Request request) {
                        super.onBefore(request);
                        if (getActivity() == null) {
                            return;
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                        if (getActivity() == null) {
                            return;
                        }
                        updateView();
                    }

                    @Override
                    public void onResponse(String response) {
                        if (getActivity() == null) {
                            return;
                        }
                        try {
                            boolean b = checkResonse(response);
                            if (b) {
                                SignStatusListResponse subscribeListResponse = GsonUtil.changeGsonToBean(response, SignStatusListResponse.class);
                                if (loadMode != LoadMode.UP_REFRESH) {
                                    dataList.clear();
                                }
                                if (subscribeListResponse.getResult() != null) {
                                    ArrayList<SignStatusItem> orderItemslist = (ArrayList<SignStatusItem>) subscribeListResponse.getResult();


                                    if (orderItemslist != null && orderItemslist.size() != 0) {
                                        page++;
                                        dataList.addAll(orderItemslist);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        updateView();

                    }
                });
    }

    /**
     * 刷新数据
     */
    private void updateView() {
        if (dataList.size() == 0) {
            onEmpty();
        } else {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
        mAdapter.notifyDataSetChanged();
    }


    /**
     * 为空
     */
    public void onEmpty() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        });
    }


    private void endLoadView() {
        xr_freshview.finishRefresh();//结束刷新
        xr_freshview.finishLoadmore();//结束加载
    }

    private void initView(View view) {
        multiStateView = (MultiStateView) view.findViewById(R.id.multiStateView);
        rec_sign_result = (RecyclerView) view.findViewById(R.id.rec_sign_result);
        xr_freshview = (SmartRefreshLayout) view.findViewById(R.id.xr_freshview);
        xr_freshview.setEnableLoadmore(false);

    }

    /**
     * 分享链接
     *
     * @param signStatusItem
     */
    private void shareAgreement(final SignStatusItem signStatusItem) {
        final String url = signStatusItem.getDownLoadUrl();
        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
        String[] dataArr = {"微信分享", "QQ分享", "其他分享", "复制链接到剪切板"};
        ActionSheet.createBuilder(getActivity(), supportFragmentManager)
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles(dataArr)
                .setCancelableOnTouchOutside(true).setListener(new ActionSheet.ActionSheetListener() {
            @Override
            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

            }

            @Override
            public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                switch (index) {
                    case 0:
//                        shareFile(file, SHARE_WX);
                        openPDFFile(signStatusItem.getDownLoadUrl(), 0);

                        break;
                    case 1:
//                        shareFile(file, SHARE_QQ);
                        openPDFFile(signStatusItem.getDownLoadUrl(), 1);

                        break;
                    case 2:
//                        shareFile(file, );

                        openPDFFile(signStatusItem.getDownLoadUrl(), 2);
                        break;
                    case 3: {
                        //获取剪贴板管理器：
                        ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
                        ClipData mClipData = ClipData.newRawUri("合同链接", Uri.parse(url));
// 将ClipData内容放到系统剪贴板里。
                        cm.setPrimaryClip(mClipData);
                        showToast("复制成功");
                    }
                    break;
                    case 4:
                        umengSharedUrl(signStatusItem);
                        break;
                }
            }
        }).show();
    }

    /**
     * 打开pdf 文件
     *
     * @param url
     */
    private void openPDFFile(String url, int type) {
        if (TextUtils.isEmpty(url)) {
            showToast("文件路径不存在！");
            return;
        }
        String destFileName = StringUtils.md5(url) + ".pdf";
        String destFileDir = getCurrentPath(getActivity());
        File file = new File(destFileDir, destFileName);
        if (file.exists()) {
            selectAppOpenPDF(file, url, type);
        } else {
            try {
                downLoadPdfFile(url, destFileDir, destFileName, type);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 文件处理
     *
     * @param context
     */
    public String getCurrentPath(Context context) {
        final String SD_PATH = "/sdcard/daiyu/pdf/";
        final String IN_PATH = "/daiyu/pdf/";
        String savePath;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {
            savePath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + IN_PATH;
        }
        File filePic = new File(savePath);
        if (!filePic.exists()) {
            filePic.getParentFile().mkdirs();
            filePic.mkdir();
        }
        return savePath;
    }

    /**
     * 下载文件pdf
     *
     * @param path
     */
    private void downLoadPdfFile(final String path, final String destFileDir, final String destFileName, final int type) {

        OkHttpUtils.get()
                .url(path)
                .build()
                .execute(new FileCallBack(destFileDir, destFileName) {
                    @Override
                    public void onBefore(Request request) {
                        showProgress();
                    }

                    @Override
                    public void inProgress(float progress) {
//                LogUtil.e(UpdataUtil.class.getSimpleName(), String.valueOf((int) (progress * 100)));
//                        mProgressBar.setProgress((int) (progress * 100));

                    }

                    @Override
                    public void onAfter() {
                        super.onAfter();
//                        stopParse();
                        dismissProgress();

                    }

                    @Override
                    public void onError(Call call, Exception e) {

                        showToast("请调整您的网络链接和足够的内存空间！");
                        call.cancel();

                        File file = new File(destFileDir, destFileName);
                        if (file.exists()) {
                            file.delete();
                        }

                    }

                    @Override
                    public void onResponse(File response) {

                        selectAppOpenPDF(response, path, type);
                    }
                });
    }

    public void selectAppOpenPDF(final File file, final String url, int type) {

//        Intent intent = new Intent("android.intent.action.VIEW");
//        intent.addCategory("android.intent.category.DEFAULT");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
////              startActivity(intent);
//        startActivity(Intent.createChooser(intent, "请选择打开协议的程序"));
//
//        sendFileByOtherApp(getActivity(), file.getAbsolutePath());


        switch (type) {
            case 0:
                shareFile(file, SHARE_WX);
                break;
            case 1:
                shareFile(file, SHARE_QQ);
                break;
            case 2:
                shareFile(file, "");
                break;
            case 3: {
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
                ClipData mClipData = ClipData.newRawUri("合同链接", Uri.parse(url));
// 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                showToast("复制成功");


            }
            break;
        }


//        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
//        String[] dataArr = {"微信分享", "QQ分享", "其他分享", "复制链接到剪切板"};
//        ActionSheet.createBuilder(getActivity(), supportFragmentManager)
//                .setCancelButtonTitle("取消")
//                .setOtherButtonTitles(dataArr)
//                .setCancelableOnTouchOutside(true).setListener(new ActionSheet.ActionSheetListener() {
//            @Override
//            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
//
//            }
//
//            @Override
//            public void onOtherButtonClick(ActionSheet actionSheet, int index) {
//                switch (index) {
//                    case 0:
//                        shareFile(file, SHARE_WX);
//                        break;
//                    case 1:
//                        shareFile(file, SHARE_QQ);
//                        break;
//                    case 2:
//                        shareFile(file, "");
//                        break;
//                    case 3: {
//                        //获取剪贴板管理器：
//                        ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
//// 创建普通字符型ClipData
//                        ClipData mClipData = ClipData.newRawUri("合同链接", Uri.parse(url));
//// 将ClipData内容放到系统剪贴板里。
//                        cm.setPrimaryClip(mClipData);
//                        showToast("复制成功");
//                    }
//                    break;
//                }
//            }
//        }).show();
    }


    private static final String SHARE_WX = "com.tencent.mm";
    private static final String SHARE_QQ = "com.tencent.mobileqq";

    private void umengSharedUrl(final SignStatusItem signStatusItem) {
        final Activity context = getActivity();

        ShareAction mShareAction = new ShareAction(context).setDisplayList(
                SHARE_MEDIA.WEIXIN)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {

                        UMWeb web = new UMWeb(signStatusItem.getDownLoadUrl());
                        if (!TextUtils.isEmpty(signStatusItem.getCustomerName())) {
                            web.setTitle(signStatusItem.getCustomerName() + "合同内容");//标题

                        } else {
                            web.setTitle("合同内容");//标题
                        }
//                        if (!TextUtils.isEmpty(signStatusItem.getComName())) {
//                            web.setDescription(signStatusItem.getComName());
//
//                        } else {
//                            web.setDescription("点击查看合同");
//                        }
                        web.setDescription("点击查看合同");

                        new ShareAction(context).withMedia(web)
                                .withText("合同文件")
                                .setPlatform(snsPlatform.mPlatform)
                                .setCallback(new UMShareListener() {
                                    @Override
                                    public void onStart(SHARE_MEDIA share_media) {
                                    }

                                    @Override
                                    public void onResult(SHARE_MEDIA share_media) {
                                        ToastUtils.d(context, "分享成功", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                                        LogUtil.d("okhttp", "=======111========>>>>>");
                                        LogUtil.d("okhttp", GsonUtil.createGsonString(throwable));
                                        LogUtil.d("okhttp", "=======222========>>>>>");

                                        ToastUtils.d(context, "分享错误", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCancel(SHARE_MEDIA share_media) {
                                        ToastUtils.d(context, "分享取消", Toast.LENGTH_SHORT).show();
                                    }
                                }).share();
                    }
                });
        mShareAction.open();
    }

    /**
     * 打开PDF
     *
     * @param file
     * @param whatpackage
     */
    private void shareFile(File file, String whatpackage) {
        try {
            Intent share_intent = new Intent();
            share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
            share_intent.setType("*/*");//设置分享内容的类型
            if (!TextUtils.isEmpty(whatpackage)) {
                share_intent.setPackage(whatpackage);
            }
            share_intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));//添加分享内容
            startActivity(share_intent);
        } catch (Exception e) {
            e.printStackTrace();
            showToast("分享失败！请选择其他分享方式");
        }
    }
//    public static void sendFileByOtherApp(Context context, String path) {
//        File file = new File(path);
//        if (file.exists()) {
////            String type = getMimeType(path);
//            Intent shareIntent = new Intent();
//            shareIntent.setAction(Intent.ACTION_SEND);
//            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
////            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            shareIntent.setType("*/*");
//            List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(shareIntent, 0);
//
//            if (resInfo.size() > 0) {
//                ArrayList<Intent> targetIntents = new ArrayList<Intent>();
//                for (ResolveInfo info : resInfo) {
//                    ActivityInfo activityInfo = info.activityInfo;
//                    LogUtil.d("okhttp", activityInfo.packageName);
//                    if (activityInfo.packageName.contains("com.tencent.mobileqq")
//                            || activityInfo.packageName.contains("com.tencent.mm")) {
//                        Intent intent = new Intent(Intent.ACTION_SEND);
//                        intent.setPackage(activityInfo.packageName);
//                        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
//                        intent.setClassName(activityInfo.packageName, activityInfo.name);
//                        targetIntents.add(intent);
//                    }
//                }
//                if (targetIntents.size() > 0) {
//                    Intent chooser = Intent.createChooser(targetIntents.get(0), "合同分享");
//                    chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetIntents.toArray(new Parcelable[]{}));
//                    context.startActivity(chooser);
//                }
//            }
//        }
//
//    }
//
//    /**
//     * 根据文件后缀名获得对应的MIME类型。
//     *
//     * @param filePath
//     */
//    public static String getMimeType(String filePath) {
//        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
//        String mime = "text/plain";
//        if (filePath != null) {
//            try {
//                mmr.setDataSource(filePath);
//                mime = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
//            } catch (IllegalStateException e) {
//                return mime;
//            } catch (IllegalArgumentException e) {
//                return mime;
//            } catch (RuntimeException e) {
//                return mime;
//            }
//        }
//        return mime;
//    }


}

