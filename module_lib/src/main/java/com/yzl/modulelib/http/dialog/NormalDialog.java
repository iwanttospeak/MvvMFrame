package com.yzl.modulelib.http.dialog;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * @author wang
 * @date 2018-03-01
 */

public class NormalDialog implements IDialog {

    private Context mContext;
    private ProgressDialog mProgressDialog;

    @Override
    public void init(Context context) {
        mProgressDialog = new android.app.ProgressDialog(mContext);
        mProgressDialog.setMessage("加载中");
    }

    @Override
    public void show() {
        mProgressDialog.show();
    }

    @Override
    public void dismiss() {
        mProgressDialog.dismiss();
    }

    @Override
    public void release() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
        mContext = null;
    }
}
