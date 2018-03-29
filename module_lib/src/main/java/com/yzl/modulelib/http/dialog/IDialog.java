package com.yzl.modulelib.http.dialog;

import android.content.Context;

import io.reactivex.annotations.NonNull;

/**
 * @author wang
 * @date 2018-03-01
 * 弹窗.
 */

public interface IDialog {

    void init(@NonNull Context context);

    void show();

    void dismiss();

    void release();
}
