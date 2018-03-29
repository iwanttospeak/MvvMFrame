package com.yzl.modulelib.widget;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import com.github.nukc.stateview.StateView;


/**
 * Created by shen on 2017/5/5.
 * 多状态切换.
 */

public class ModeStateView {

    /**
     * 状态View.
     */
    private StateView mStateView;

    /**
     * 是否显示了其他页面.
     */
    private boolean showOtherPager;

    /**
     * 初始化状态View.
     *
     * @param view       需要的view
     * @param hasToolBar 是否还有toolbar
     */
    public ModeStateView(View view, boolean hasToolBar) {
        mStateView = StateView.inject((ViewGroup) view, hasToolBar);
    }

    public void setLoadingLayout(@LayoutRes int loadingLayout) {
        mStateView.setLoadingResource(loadingLayout);
    }

    public void setEmptyLayout(@LayoutRes int emptyLayout) {
        mStateView.setEmptyResource(emptyLayout);
    }

    public void setRetryLayout(@LayoutRes int retryLayout) {
        mStateView.setRetryResource(retryLayout);
    }

    public void setOnRetryListener(OnRetryListener listener) {
        mStateView.setOnRetryClickListener(listener::onRetry);
    }

    /**
     * 初始化状态.
     */
    public void initState() {
        showOtherPager = false;
    }

    /**
     * 显示加载.
     */
    public void showLoading() {
        showOtherPager = false;
        mStateView.showLoading();
    }

    /**
     * 显示数据为空.
     */
    public View showEmpty() {
        showOtherPager = true;
        return mStateView.showEmpty();
    }

    /**
     * 显示内容.
     */
    public void showContent() {
        if (!showOtherPager) {
            mStateView.showContent();
        }
    }

    /**
     * 显示出错重试.
     */
    public void showErrorRetry() {
        showOtherPager = true;
        mStateView.showRetry();
    }

    public interface OnRetryListener {
        /**
         * 重试.
         */
        void onRetry();
    }
}
