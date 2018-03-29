package com.yzl.modulelib.http;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yzl.modulelib.R;
import com.yzl.modulelib.http.callback.ErrorBack;
import com.yzl.modulelib.utils.ResourceUtil;
import com.yzl.modulelib.utils.ToastUtils;
import com.yzl.modulelib.widget.ModeStateView;

import static com.yzl.modulelib.http.HttpConstant.HTTP_CODE_NO_NET;
import static com.yzl.modulelib.utils.ResourceUtil.APPLICATION_CONTEXT;

/**
 * @author wang
 * @date 2017-12-08
 */

public class HttpUtil {

    /**
     * 检测网络是否可用.
     */
    protected static boolean isNetworkAvailable(ErrorBack errorBack) {
        ConnectivityManager manager = (ConnectivityManager) APPLICATION_CONTEXT.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        boolean hasNet = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        if (!hasNet) {
            ToastUtils.toastError(R.string.message_http_no_net);
            if (errorBack != null) {
                errorBack.onFailure(HTTP_CODE_NO_NET, ResourceUtil.getString(R.string.message_http_no_net));
            }
        }

        return hasNet;
    }

    /**
     * 检测modeState是否为null.
     */
    protected static void checkModeStateNotNon(ModeStateView modeStateView) {
        if (modeStateView == null) {
            throw new IllegalStateException("you should make activity's or fragment's showMultiMode() method return true");
        }
    }
}
