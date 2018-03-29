package com.yzl.modulelib.http.upload;

import android.text.TextUtils;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.yzl.modulelib.http.callback.CallBack;
import com.yzl.modulelib.http.network.RetrofitApi;

import org.json.JSONObject;

import java.util.List;

/**
 * @author wang
 * @date 2018-03-01
 * 上传文件
 */

public class UploadRequest {

    /**
     * 是否继续上传
     */
    private boolean continueUpload = false;

    /**
     * 七牛token的的请求.
     */
    public UploadRequestService mUploadRequestService = RetrofitApi.getInstance().mRetrofit.create(UploadRequestService.class);

    /**
     * 上传单张图片.
     */
    public void uploadSingleFile(String picPath, String token, CallBack<String> callBack) {
        if (TextUtils.isEmpty(picPath)) {
            callBack.onResponse("", "");
            return;
        }

        UploadManager uploadManager = new UploadManager();
        uploadManager.put(picPath, System.currentTimeMillis() + ".jpg", token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {

            }
        }, new UploadOptions(null, null, false, null, new UpCancellationSignal() {
            @Override
            public boolean isCancelled() {
                return continueUpload;
            }
        }));
    }

    /**
     * 上传多张图片.
     */
    public void uploadMultiFile(List<String> picPathList, String token, CallBack<String> callBack) {
        if (picPathList.isEmpty()) {
            callBack.onResponse("", "");
            return;
        }
    }

    /**
     * 取消所有上传任务.
     */
    public void cancelAllUpload() {
        continueUpload = true;
    }
}
