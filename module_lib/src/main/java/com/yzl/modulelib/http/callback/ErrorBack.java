package com.yzl.modulelib.http.callback;

/**
 * Created by 沈小建 on 2017-12-04.
 */
public interface ErrorBack {

    /**
     * 请求失败回调.
     *
     * @param code    后台返回的code
     * @param message 后台返回的消息
     */
    void onFailure(int code, String message);
}
