package com.yzl.modulelib.adapter.callback;

import android.databinding.ViewDataBinding;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * @author wang
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> viewArray;

    private ViewDataBinding binding;

    public ViewDataBinding getBinding() {
        return binding;
    }

    public void setBinding(ViewDataBinding binding) {
        this.binding = binding;
    }

    public BaseViewHolder(View itemView) {
        super(itemView);
        viewArray = new SparseArray<>();
    }

    public <T extends View> T getView(@IdRes int id) {
        View view = viewArray.get(id);
        if (null == view) {
            view = getBinding().getRoot().findViewById(id);
            viewArray.put(id, view);
        }
        return (T) view;
    }
}
