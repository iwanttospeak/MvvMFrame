package com.yzl.modulelib.http.bean;

/**
 * Created by shen on 2017/5/5.
 * 后台返回的基本格式
 */

public class BaseResponse<T> {
    private Integer errcode;
    private String message;
    private T content;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
