package com.hdfex.merchantqrshow.net.okhttp.utils;

import java.io.IOException;

/**
 * Created by maidou521 on 2016/7/21.
 */
public class FailureToConnectException extends IOException {
    public FailureToConnectException() {
        throw new RuntimeException("服务连接失败");
    }
}
