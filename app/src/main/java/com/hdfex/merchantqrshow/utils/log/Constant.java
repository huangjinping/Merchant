package com.hdfex.merchantqrshow.utils.log;


public interface Constant {

    String DEFAULT_MESSAGE = "execute";
    String LINE_SEPARATOR = System.getProperty("line.separator");
    String NULL_TIPS = "对象为null"; //Log with null object
    int JSON_INDENT = 4;

    int V = 0x1;
    int D = 0x2;
    int I = 0x3;
    int W = 0x4;
    int E = 0x5;
    int A = 0x6;
    int JSON = 0x7;
    int XML = 0x8;
}
