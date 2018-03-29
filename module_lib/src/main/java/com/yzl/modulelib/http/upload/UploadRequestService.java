package com.yzl.modulelib.http.upload;

import com.yzl.modulelib.http.bean.BaseResponse;
import com.yzl.modulelib.http.bean.QiniuBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by 沈小建 on 2017-12-27.
 */

public interface UploadRequestService {

    /**
     * 获取七牛token.
     */
    @FormUrlEncoded
    @POST("upload/getUploadToken")
    Observable<BaseResponse<QiniuBean>> getQiNiuToken(@Field("t") String t);
}
