package com.hdfex.merchantqrshow.bean.salesman.message;

import com.hdfex.merchantqrshow.bean.BaseBean;

/**
 * author Created by harrishuang on 2017/7/3.
 * email : huangjinping@hdfex.com
 */

public class MessageItem extends BaseBean {


//    {
//        "mid":2751,
//            "icon":"http://xxx"
//        "isread":0
//        "cumid":2751,
//            "title":"分期通知",
//            "publishtime":"2015-03-10 10:30:32",
//            "content":"您有...",
//    }



    /**
     * 消息
     */
    public  static  final  String MESSAGE_NEWS="2";
    /**
     * 公告
     */
    public  static  final  String MESSAGE_NOTICE="1";

    /**
     * 3商户公告
     */
    public  static  final  String MESSAGE_ANNOUNCEMENT="3";
    /**
     * 消息已读
     */
    public static final  int  ISREAD_YES=1;
    /**
     * 消息未读
     */
    public  static final  int ISREAD_NO=0;

    /**
     * content : 测试公告
     * icon : https://hdfex.com/hd-merchant-web/mobile/picture/down?file=null
     * title : 测试titile1
     * publishtime : 2017-06-30 15:28:47
     * isread : 0
     * mid : 2
     * cumid : 1
     */

    private String content;
    private String icon;
    private String title;
    private String publishtime;
    private int isread;
    private String mid;
    private String cumid;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }

    public int getIsread() {
        return isread;
    }

    public void setIsread(int isread) {
        this.isread = isread;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getCumid() {
        return cumid;
    }

    public void setCumid(String cumid) {
        this.cumid = cumid;
    }
}
