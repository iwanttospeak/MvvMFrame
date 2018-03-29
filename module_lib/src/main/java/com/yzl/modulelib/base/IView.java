package com.yzl.modulelib.base;

import android.os.Bundle;

/**
 * @author wang
 * @date 2018-02-28
 */

public interface IView {

    /**
     * 布局id.
     *
     * @return 对应的布局
     */
    int getLayoutId();

    /**
     * 生命周期操作(activity代表onCreate生命周期，fragment代表onCreateView生命周期).
     *
     * @param savedInstanceState 保存的资源
     */
    void initView(Bundle savedInstanceState);

    /**
     * 是否需要显示多状态.
     *
     * @return
     */
    boolean showMultiMode();

    /**
     * 请求数据绑定变化.
     *
     * @param bindDataCode 对应码操作
     */
    void onBindDataUpdata(int bindDataCode);
}
