package com.yzl.modulelib.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;

/**
 * @author wang
 * @date 2018-03-01
 * 无需context去获取对应的 资源文件工具（适合xml中使用）
 */

public class ResourceUtil {

    public static Context APPLICATION_CONTEXT;

    public static void init(Context context) {
        APPLICATION_CONTEXT = context;
    }

    /**
     * 根据文字id获取文字.
     */
    public static String getString(int strId) {
        return APPLICATION_CONTEXT.getString(strId);
    }

    /**
     * 根据颜色id获取颜色.
     */
    public static int getColor(int colorId) {
        return ContextCompat.getColor(APPLICATION_CONTEXT, colorId);
    }

    /**
     * 根据dimenId获取dimen数字.
     */
    public static float getDimen(int dimenId) {
        return APPLICATION_CONTEXT.getResources().getDimension(dimenId);
    }
}
