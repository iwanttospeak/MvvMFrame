package com.yzl.modulelib.base;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;
import android.databinding.BaseObservable;
import android.support.annotation.CallSuper;

import com.yzl.modulelib.http.HttpRequest;

/**
 * Created by 沈小建 on 2018-02-28.
 */

public class BaseModel extends BaseObservable implements GenericLifecycleObserver {

    /**
     * 请求网络的对象（可能为空）.
     */
    protected HttpRequest mHttpRequest;

    /**
     * 上下文对象.
     */
    protected Context mContext;

    /**
     * 观察者.
     */
    protected IView mIView;

    public BaseModel(BaseActivity baseActivity) {
        baseActivity.getLifecycle().addObserver(this);

        mContext = baseActivity;
        mIView = baseActivity;
    }

    public BaseModel(BaseFragment baseFragment) {
        baseFragment.getLifecycle().addObserver(this);

        mContext = baseFragment.getContext();
        mIView = baseFragment;
    }

    /**
     * 通知view执行对应的操作.
     *
     * @param bindDataCode 对应码操作
     */
    public final void notifyToView(int bindDataCode) {
        mIView.onBindDataUpdata(bindDataCode);
    }

    @Override
    public final void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        switch (event) {
            case ON_DESTROY:
                onDestroy();
                break;
            default:
                break;
        }
    }

    /**
     * 销毁资源.
     */
    @CallSuper
    public void onDestroy() {
        if (mHttpRequest != null) {
            mHttpRequest.onDestroy();
        }
    }
}
