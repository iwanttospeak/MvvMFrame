package com.yzl.modulelib.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author wang 页面添加，支持主动调用数据进行添加删除等操作时自动更新动画
 */

class PagedList<T> extends ArrayList<T> {

    private Callback mCallback;

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public boolean add(T t) {
        boolean add = super.add(t);
        if (add && mCallback != null) {
            mCallback.onInserted(size() - 1, 1);
        }

        return add;
    }

    @Override
    public void add(int index, T element) {
        super.add(index, element);
        if (mCallback != null) {
            mCallback.onInserted(index, 1);
        }
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean b = super.addAll(c);
        if (b && mCallback != null) {
            mCallback.onInserted(size() - c.size(), c.size());
        }
        return b;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        boolean b = super.addAll(index, c);
        if (b && mCallback != null) {
            mCallback.onInserted(index, c.size());
        }
        return b;
    }

    @Override
    public T remove(int index) {
        T remove = super.remove(index);
        if (remove != null && mCallback != null) {
            mCallback.onRemoved(index, 1);
        }
        return remove;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean b = super.removeAll(c);
        if (b && mCallback != null) {
            mCallback.onRemoved(size(), c.size());
        }
        return b;
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        super.removeRange(fromIndex, toIndex);
        if (mCallback != null) {
            mCallback.onRemoved(fromIndex, toIndex - fromIndex);
        }
    }

    @Override
    public boolean remove(Object o) {
        boolean remove = super.remove(o);
        if (remove && mCallback != null) {
            mCallback.onRemoved(indexOf(o), 1);
        }
        return remove;
    }

    @Override
    public void clear() {
        int size = size();
        super.clear();
        if (mCallback != null) {
            mCallback.onRemoved(0, size);
        }
    }

    /**
     * 用于刷新数据.
     */
    public interface Callback {
        public void onInserted(int position, int count);

        public void onRemoved(int position, int count);
    }

}
