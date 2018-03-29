package com.yzl.modulelib.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.yzl.modulelib.R;
import com.yzl.modulelib.utils.StatusColorUtil;

/**
 * @author wang
 * @date 2018-02-28
 */
public abstract class BaseActivity<E extends ViewDataBinding> extends AppCompatActivity implements IView {

    /**
     * 绑定数据.
     */
    protected E mDataBinding;

    /**
     * 是否有toolbar.
     */
    private boolean isHaveToolbar;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        initToolbar();
        initStatusColor();
        initTransitionManagerAnim();
        initMultiMode();
        initView(savedInstanceState);
    }

    /**
     * 初始化toolbar栏.
     */
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.tool_bar);
        isHaveToolbar = toolbar != null;
        if (isHaveToolbar) {
            setSupportActionBar(toolbar);
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setDisplayShowTitleEnabled(false);
//                toolbar.setNavigationIcon(R.drawable.ic_arrows_left_white); todo:设置返回样式
                toolbar.setNavigationOnClickListener(view -> onBackPressed());
            }
        }
    }

    /**
     * 设置状态栏颜色(需要修改状态栏颜色请重写此方法).
     */
    protected void initStatusColor() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            StatusColorUtil.setStatusColor(this, R.color.colorPrimary);
        }
    }

    /**
     * 设置转场动画.
     */
    protected void initTransitionManagerAnim() {

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

    /**
     * 设置标题.
     */
    protected void setToolTitle(@StringRes int titleStr) {
        setToolTitle(getString(titleStr));
    }

    protected void setToolTitle(String title) {
        TextView textView = findViewById(R.id.tv_title);
        if (textView != null) {
            textView.setText(title);
        }
    }
}
