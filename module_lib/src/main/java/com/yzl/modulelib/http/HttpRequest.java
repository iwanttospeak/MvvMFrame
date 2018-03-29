package com.yzl.modulelib.http;

import android.content.Context;

import com.yzl.modulelib.http.bean.BaseResponse;
import com.yzl.modulelib.http.bean.QiniuBean;
import com.yzl.modulelib.http.callback.CallBack;
import com.yzl.modulelib.http.callback.ErrorBack;
import com.yzl.modulelib.http.dialog.IDialog;
import com.yzl.modulelib.http.dialog.NormalDialog;
import com.yzl.modulelib.http.network.RetrofitNetWork;
import com.yzl.modulelib.http.upload.UploadRequest;
import com.yzl.modulelib.widget.ModeStateView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

import static com.yzl.modulelib.http.HttpConstant.DEVICE_T;
import static com.yzl.modulelib.http.HttpUtil.checkModeStateNotNon;
import static com.yzl.modulelib.http.HttpUtil.isNetworkAvailable;

/**
 * @author wang
 * @date 2017-08-15
 */

public final class HttpRequest {

    /**
     * 上下文.
     */
    private Context mContext;

    /**
     * 处理网络请求的对象.
     */
    private RetrofitNetWork mNetwork;

    /**
     * 处理文件上传的对象.
     */
    private UploadRequest mUploadRequest;

    /**
     * 多状态切换.
     */
    private ModeStateView mModeStateView;

    /**
     * 请求时的弹窗,
     */
    private IDialog mDialog;

    /**
     * 构造参数,默认一个 context就够.
     * 如果不传多状态对象,则无法构造多状态切换.
     */
    public HttpRequest(@NonNull Context context) {
        mContext = context;
    }

    /**
     * 设置多状态（如果需要）.
     */
    public void setModeStateView(@NonNull ModeStateView modeStateView) {
        mModeStateView = modeStateView;
    }

    /**
     * 请求网络(状态随之变换).
     */
    public <E> void requestWithState(@NonNull Observable<BaseResponse<E>> observable, @NonNull CallBack<E> callBack) {
        requestWithState(observable, callBack, null);
    }

    /**
     * 请求网络(状态随之变换).
     *
     * @param observable 对应的observable对象
     * @param callBack   回调获取结果
     * @param errorBack  错误信息回调
     * @param <E>        需要返回的实例类型
     */
    public <E> void requestWithState(Observable<BaseResponse<E>> observable, CallBack<E> callBack, ErrorBack errorBack) {
        checkModeStateNotNon(mModeStateView);
        if (!isNetworkAvailable(errorBack)) {
            return;
        }

        createNetworkObject();
        CallBack<E> requestCallBack = (message, data) -> {
            callBack.onResponse(message, data);
        };

        ErrorBack requestErrorBack = (code, message) -> {
            if (errorBack != null) {
                errorBack.onFailure(code, message);
            }
        };

        mModeStateView.setOnRetryListener(() -> mNetwork.request(observable, requestCallBack, requestErrorBack));
        mNetwork.request(observable, requestCallBack, requestErrorBack);
    }

    /**
     * 创建网络对象.
     */
    private void createNetworkObject() {
        if (mNetwork == null) {
            mNetwork = new RetrofitNetWork();
        }
    }

    /**
     * 请求网络(显示弹窗).
     */
    public <E> void requestWithDialog(@NonNull Observable<BaseResponse<E>> observable, @NonNull CallBack<E> callBack, boolean showDialog) {
        requestWithDialog(observable, callBack, null, showDialog);
    }

    /**
     * 请求网络(显示弹窗).
     *
     * @param observable   对应的observable对象
     * @param callBack     回调获取结果
     * @param errorBack    错误回调
     * @param <E>          需要返回的实例类型
     * @param isShowDialog 是否显示弹窗
     */
    public <E> void requestWithDialog(Observable<BaseResponse<E>> observable, CallBack<E> callBack, ErrorBack errorBack, boolean isShowDialog) {
        if (!isNetworkAvailable(errorBack)) {
            return;
        }

        createNetworkObject();
        createDialogObject(isShowDialog);

        CallBack<E> requestCallBack = (message, data) -> {
            callBack.onResponse(message, data);
            hideDialog(isShowDialog);
        };

        ErrorBack requestErrorBack = (code, message) -> {
            if (errorBack != null) {
                errorBack.onFailure(code, message);
            }
            hideDialog(isShowDialog);
        };

        mNetwork.request(observable, requestCallBack, requestErrorBack);
    }

    /**
     * 创建弹窗对象.
     */
    private void createDialogObject(boolean isShowDialog) {
        if (isShowDialog && mDialog == null) {
            mDialog = new NormalDialog();
            mDialog.init(mContext);
        }
    }

    /**
     * 消失弹窗.
     */
    private void hideDialog(boolean isShowDialog) {
        if (isShowDialog && mDialog != null) {
            mDialog.dismiss();
        }
    }

    /**
     * 上传单张图片.
     */
    public void uploadSingleFile(String picPath, CallBack<String> callBack) {
        createUploadObject();
        getUploadToken((message, data) -> mUploadRequest.uploadSingleFile(picPath, data.getToken(), callBack));
    }

    /**
     * 创建上传对象.
     */
    private void createUploadObject() {
        if (mUploadRequest == null) {
            mUploadRequest = new UploadRequest();
        }
    }

    /**
     * 请求获取token.
     */
    private void getUploadToken(CallBack<QiniuBean> callBack) {
        requestWithDialog(mUploadRequest.mUploadRequestService.getQiNiuToken(DEVICE_T), callBack, false);
    }

    /**
     * 上传多张图片.
     */
    public void uploadMultiFile(List<String> picPathList, CallBack<String> callBack) {
        createUploadObject();
        getUploadToken((message, data) -> mUploadRequest.uploadMultiFile(picPathList, data.getToken(), callBack));
    }

    /**
     * 销毁资源.
     */
    public void onDestroy() {
        if (mNetwork != null) {
            mNetwork.cancelAllRequest();
        }

        if (mUploadRequest == null) {
            mUploadRequest.cancelAllUpload();
        }

        if (mDialog != null) {
            mDialog.release();
        }

        mContext = null;
    }
}
