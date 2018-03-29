package com.yzl.modulelib.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 沈小建 on 2018-02-28.
 */

public abstract class BaseFragment extends Fragment implements IView {

    /**
     * 数据绑定对象.
     */
    protected ViewDataBinding mDataBinding;

    /**
     * 当前Fragment页面.
     */
    protected View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mView = mDataBinding.getRoot();

        initMultiMode();
        initView(savedInstanceState);
        return mView;
    }

    /**
     * 初始化多状态布局.
     */
    protected void initMultiMode() {
        if (showMultiMode()) {

        }
    }

    /**
     * 默认不显示多状态.
     */
    @Override
    public boolean showMultiMode() {
        return false;
    }
}
