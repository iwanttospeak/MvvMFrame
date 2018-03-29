package com.yzl.modulelib.http.callback;

/**
 * 获取到资源回调.
 *
 * @param <E> 需要回调的具体实体类
 * @author 10488
 */

public interface CallBack<E> {

    /**
     * 成功时回调的方法
     *
     * @param message 后台回调的消息
     * @param data    回调的具体实体类
     */
    void onResponse(String message, E data);
}
