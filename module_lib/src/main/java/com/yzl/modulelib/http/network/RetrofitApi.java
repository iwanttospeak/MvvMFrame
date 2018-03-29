package com.yzl.modulelib.http.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yzl.modulelib.http.HttpConstant;
import com.yzl.modulelib.http.intercept.AddParamsIntercept;
import com.yzl.modulelib.http.intercept.LoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author wang
 * @date 2018-02-28
 * 初始化okHttp和retrofit
 */

public class RetrofitApi {

    public final Retrofit mRetrofit;

    public static RetrofitApi getInstance() {
        return SingleHolder.INSTANCE;
    }

    private static class SingleHolder {
        private static final RetrofitApi INSTANCE = new RetrofitApi();
    }

    private RetrofitApi() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(HttpConstant.URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 获取初始化的okHttp实例.
     *
     * @return okHttp实例
     */
    private OkHttpClient getOkHttpClient() {
        AddParamsIntercept paramsIntercept = new AddParamsIntercept();
        LoggingInterceptor loggingInterceptor = new LoggingInterceptor();

        return new OkHttpClient.Builder()
                .connectTimeout(HttpConstant.DEFAULT_TIME_SECONDS, TimeUnit.SECONDS)
                .readTimeout(HttpConstant.DEFAULT_TIME_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(HttpConstant.DEFAULT_TIME_SECONDS, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(paramsIntercept)
                .addInterceptor(loggingInterceptor)
                .build();
    }
}
