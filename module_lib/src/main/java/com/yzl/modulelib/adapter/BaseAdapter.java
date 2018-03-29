package com.yzl.modulelib.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yzl.modulelib.adapter.callback.BaseViewHolder;
import com.yzl.modulelib.adapter.callback.DiffCallBack;

import java.util.List;

/**
 * 一种类型item的adapter基类
 */
public class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    /**
     * 源数据.
     */
    protected List<T> mListData;

    /**
     * 布局.
     */
    private int layoutId;

    /**
     * 布局需要id.
     */
    protected int brId;

    /**
     * 上下文.
     */
    protected Context mContext;

    /**
     * DiffUtil的计算类.
     */
    private DiffAdapterHelper<T> mAdapterHelper;

    public BaseAdapter(List<T> listData, @LayoutRes int layoutId, int brId) {
        this.mListData = listData;
        this.layoutId = layoutId;
        this.brId = brId;
    }

    /**
     * 创建对应diffUtil计算类.需要动态回调
     *
     * @param diffCallBack 需要的对比回调
     */
    public final void setDiffCallBack(@NonNull DiffCallBack<T> diffCallBack) {
        mListData = getNewRegisterList(mListData);
        mAdapterHelper = new DiffAdapterHelper<>(this, diffCallBack);
    }

    /**
     * 设置新值.
     * 如果mAdapterHelper为空,则普通赋值
     *
     * @param listData 列表数据
     */
    public final void setListData(List<T> listData) {
        if (mAdapterHelper != null && listData instanceof PagedList) {
            mAdapterHelper.setListData(listData);
        } else {
            setTrueListData(listData);
        }
    }

    /**
     * 赋值给adapter的值.mNetRequest.getModeStateView().showEmpty();
     *
     * @param listData 对应的集合
     */
    public final void setTrueListData(List<T> listData) {
        this.mListData = listData;
    }

    @Override
    public final BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), layoutId, parent, false);
        BaseViewHolder viewHolder = new BaseViewHolder(viewDataBinding.getRoot());
        viewHolder.setBinding(viewDataBinding);
        return viewHolder;
    }

    @Override
    public final void onBindViewHolder(BaseViewHolder holder, int position) {
        ViewDataBinding binding = holder.getBinding();
        binding.setVariable(brId, getItem(position));

        onBindViewListener(holder, position);
        binding.executePendingBindings();
    }

    @Nullable
    protected T getItem(int position) {
        return mListData.get(position);
    }

    protected void onBindViewListener(BaseViewHolder holder, int position) {

    }

    @Override
    public final int getItemCount() {
        return mListData.size();
    }

    /**
     * 获取PagedList数据.
     */
    public static <T> List<T> getNewRegisterList() {
        return getNewRegisterList(null);
    }

    /**
     * 数据转换,将对应的集合转为PagedList.
     * @param list 对应的集合
     * @param <T>  adapter对应的 泛型
     * @return PagedList
     */
    public static <T> List<T> getNewRegisterList(List<T> list) {
        PagedList<T> pagedList = new PagedList<>();
        if (list != null && !list.isEmpty()) {
            pagedList.addAll(list);
        }
        return pagedList;
    }
}
