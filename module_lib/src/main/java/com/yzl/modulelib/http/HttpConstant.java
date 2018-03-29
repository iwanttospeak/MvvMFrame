package com.yzl.modulelib.http;

/**
 * @author wang
 * @date 2018-02-28
 */

public class HttpConstant {

    /**
     * 请求地址.
     */
    public static final String URL = "http://spore.zertone2.com/app/";

    /**
     * 设备号.
     */
    public static final String DEVICE_T = "2";

    /**
     * 默认读写时间.
     */
    public static final int DEFAULT_TIME_SECONDS = 10;

    /**
     * 正确返回码.
     */
    public static final int HTTP_CODE_CORRECT = 0;

    /**
     * 无法访问网络状态码.
     */
    public static final int HTTP_CODE_NO_NET = -100;

    /**
     * 连接服务器的一系列错误，具体看log.
     */
    public static final int HTTP_ERROR = -101;
}
