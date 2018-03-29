package com.yzl.modulelib.utils;

import android.app.Activity;
import android.support.annotation.ColorRes;

import com.gyf.barlibrary.ImmersionBar;
import com.yzl.modulelib.R;

/**
 * @author wang
 * @date 2017-08-06
 * 状态栏颜色设置工具.
 */

public class StatusColorUtil {

    /**
     * 设置状态颜色.
     *
     * @param activity 对应的 activity
     * @param colorId  颜色资源id
     */
    public static void setStatusColor(Activity activity, @ColorRes int colorId) {
        ImmersionBar.with(activity).statusBarColor(colorId).navigationBarColor(colorId).keyboardEnable(true).fitsSystemWindows(true).init();
    }

    /**
     * 透明状态栏
     *
     * @param activity 对应的activity
     */
    public static void transparentStatus(Activity activity) {
        ImmersionBar.with(activity).transparentStatusBar().navigationBarColor(R.color.colorPrimary).barAlpha(0).init();
    }

    /**
     * 清除状态栏资源.
     *
     * @param activity 对应的activity
     */
    public static void destroy(Activity activity) {
        ImmersionBar.with(activity).destroy();
    }
}
