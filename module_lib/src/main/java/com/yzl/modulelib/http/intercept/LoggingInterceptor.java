package com.yzl.modulelib.http.intercept;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URLDecoder;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by 10488 on 2017-11-01.
 */

public class LoggingInterceptor implements Interceptor {

    private static final String POST = "POST";

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        Response response = null;

        Logger.d("请求网址：" + request.url());
        Logger.d("请求方式：" + request.method());

        String method = request.method();
        if (method.equals(POST)) {
            if (request.body() instanceof FormBody) {
                FormBody oldFormBody = (FormBody) request.body();

                Map<String, String> params = new ArrayMap<>();
                for (int i = 0; i < oldFormBody.size(); i++) {
                    params.put(oldFormBody.encodedName(i), URLDecoder.decode(oldFormBody.encodedValue(i)));
                }
                Logger.d("请求参数：" + params);
            }
        }

        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            Logger.d("请求失败" + e);
            throw e;
        }

        Headers requestHeaders = response.networkResponse().request().headers();
        for (int i = 0; i < requestHeaders.size(); i++) {
            String headerName = requestHeaders.name(i);
            String headerValue = requestHeaders.get(headerName);
            Logger.d(headerName + ":" + headerValue + "\n");
        }

        Logger.d("返回码" + response.code());

        ResponseBody responseBody = response.peekBody(1024 * 1024);
        Logger.json(responseBody.string());

        return response;
    }
}