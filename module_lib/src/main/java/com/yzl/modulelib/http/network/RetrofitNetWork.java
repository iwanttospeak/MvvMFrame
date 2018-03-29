package com.yzl.modulelib.http.network;

import android.support.annotation.NonNull;

import com.yzl.modulelib.http.HttpConstant;
import com.yzl.modulelib.http.bean.BaseResponse;
import com.yzl.modulelib.http.callback.CallBack;
import com.yzl.modulelib.http.callback.ErrorBack;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 10488
 * @date 2017-08-06
 */
public final class RetrofitNetWork {

    /**
     * 存储网络请求.
     */
    private final CompositeDisposable mComposite = new CompositeDisposable();

    /**
     * 网络请求方法.
     *
     * @param observable 对应的observable对象
     * @param callBack   回调获取结果
     * @param errorBack  错误回调结果
     * @param <E>        需要返回的实例类型
     */
    public <E> void request(Observable<BaseResponse<E>> observable, @NonNull CallBack<E> callBack, @NonNull ErrorBack errorBack) {
        mComposite.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(eBaseResponse -> disposeData(eBaseResponse, callBack, errorBack),
                        throwable -> disposeError(throwable, errorBack)));
    }

    /**
     * 收到服务器数据并处理.
     *
     * @param eBaseResponse 返回的数据
     * @param callBack      正确回调
     * @param errorBack     错误回调
     * @param <E>           实体类类型
     */
    private <E> void disposeData(BaseResponse<E> eBaseResponse, @NonNull CallBack<E> callBack, @NonNull ErrorBack errorBack) {
        String message = eBaseResponse.getMessage();
        int code = eBaseResponse.getErrcode();
        if (code == HttpConstant.HTTP_CODE_CORRECT) {
            callBack.onResponse(message, eBaseResponse.getContent());
        } else {
            errorBack.onFailure(code, message);
        }
    }

    /**
     * 未获取数据处理.
     *
     * @param throwable 异常
     * @param errorBack 错误回调
     */
    private void disposeError(Throwable throwable, ErrorBack errorBack) {
        ExceptionHandle.ResponseException responseException = ExceptionHandle.handleException(throwable);
        responseException.printStackTrace();
        errorBack.onFailure(HttpConstant.HTTP_ERROR, "");
    }

    /**
     * 统一取消所有请求.
     */
    public void cancelAllRequest() {
        mComposite.clear();
    }
}
