package com.yzl.modulelib.adapters;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.webkit.WebView;

/**
 * Created by 沈小建 on 2018-01-12.
 */

public class WebViewAdapters {

    @BindingAdapter("webUrl")
    public static void setWebUrl(WebView webView, String webUrl) {
        if (TextUtils.isEmpty(webUrl)) {
            webView.loadUrl(webUrl);
        }
    }
}
