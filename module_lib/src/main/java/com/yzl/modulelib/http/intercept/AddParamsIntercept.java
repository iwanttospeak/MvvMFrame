package com.yzl.modulelib.http.intercept;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.yzl.modulelib.utils.GlobalUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 添加参数.
 *
 * @author wang
 * @date 2017-11-01
 */
public class AddParamsIntercept implements Interceptor {

    /**
     * 请求方式.
     */
    private static final String GET = "GET";
    private static final String POST = "POST";

    /**
     * 用户token对应网络的key.
     */
    private static final String TOKEN_KEY = "token";

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request original = chain.request();

        //token为空则不进行任何添加参数操作
        String token = GlobalUtil.getToken();
        if (TextUtils.isEmpty(token)) {
            return chain.proceed(original);
        }

        Request request = null;

        String method = original.method();
        if (method.equals(GET)) {
            request = addParamsToGetMethod(original, token);
        } else if (method.equals(POST)) {
            request = addParamsToPostMethod(original, token);
        }

        assert request != null;
        return chain.proceed(request);
    }

    /**
     * GET请求时添加参数.
     *
     * @param original 原先请求
     * @param token    用户token
     * @return 添加参数后的请求
     */
    private Request addParamsToGetMethod(Request original, String token) {
        HttpUrl originalHttpUrl = original.url();
        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter(TOKEN_KEY, token)
                .build();
        Request.Builder requestBuilder = original.newBuilder().url(url);
        return requestBuilder.build();
    }

    /**
     * POST请求添加参数(目前支只处理表单提交).
     *
     * @param original 原先请求
     * @param token    用户token
     * @return 添加参数后的请求
     */
    private Request addParamsToPostMethod(Request original, String token) {
        if (original.body() instanceof FormBody) {
            FormBody.Builder newFormBody = new FormBody.Builder();
            FormBody oidFormBody = (FormBody) original.body();
            for (int i = 0; i < oidFormBody.size(); i++) {
                newFormBody.addEncoded(oidFormBody.encodedName(i), oidFormBody.encodedValue(i));
            }

            if (!TextUtils.isEmpty(token)) {
                newFormBody.add(TOKEN_KEY, token);
            }

            return original.newBuilder().method(original.method(), newFormBody.build()).build();
        }

        return original;
    }
}
