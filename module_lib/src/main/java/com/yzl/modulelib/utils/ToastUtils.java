package com.yzl.modulelib.utils;

import android.view.Gravity;

/**
 * @author wang
 * @date 2018-02-28
 * 吐司工具
 */

public class ToastUtils {

    public static void toastMessage(String message) {
        com.blankj.utilcode.util.ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        com.blankj.utilcode.util.ToastUtils.showShort(message);
    }

    public static void toastError(int messageId) {
        com.blankj.utilcode.util.ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        com.blankj.utilcode.util.ToastUtils.showShort(messageId);
    }

    public static void toastError(String message) {
        com.blankj.utilcode.util.ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        com.blankj.utilcode.util.ToastUtils.showShort(message);
    }
}
