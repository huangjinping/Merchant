package com.hdfex.merchantqrshow.net.okhttp;

import java.io.IOException;

/**
 * Created by maidou521 on 2016/5/26.
 */
public class MyTimeOutException extends IOException {

    public MyTimeOutException() {
        throw new RuntimeException("网络不通畅");
    }
}