package com.yzl.modulelib.application;

import android.support.multidex.MultiDexApplication;

import com.yzl.modulelib.utils.ResourceUtil;

/**
 * Created by 沈小建 on 2018-02-28.
 */

public class MsApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ResourceUtil.init(this);
    }
}
