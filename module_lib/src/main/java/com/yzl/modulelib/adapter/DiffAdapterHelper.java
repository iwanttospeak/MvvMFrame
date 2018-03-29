package com.yzl.modulelib.adapter;

import android.os.Handler;
import android.support.v7.util.DiffUtil;

import com.yzl.modulelib.adapter.callback.DiffCallBack;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * DiffCallBack 的帮助类 使用线程池进行 diff计算，之后自动通知刷新
 */
class DiffAdapterHelper<T> {

    /**
     * 线程池(固定4个).
     */
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(4);

    /**
     * diff计算.
     */
    private DiffCallBack<T> mDiffCallback;

    /**
     * 对应的adapter.
     */
    private BaseAdapter<T> mBaseAdapter;

    private Handler mHandler = new Handler();

    DiffAdapterHelper(BaseAdapter<T> baseAdapter, DiffCallBack<T> diffCallback) {
        this.mBaseAdapter = baseAdapter;
        this.mDiffCallback = diffCallback;

        setPagedCallback();
    }

    /**
     * 设置pagedList的刷新回调.
     */
    private void setPagedCallback() {
        ((PagedList) mBaseAdapter.mListData).setCallback(new PagedList.Callback() {
            @Override
            public void onInserted(int position, int count) {
                mHandler.post(() -> {
                    mBaseAdapter.notifyItemRangeInserted(position, count);
                    mBaseAdapter.notifyItemRangeChanged(position, mBaseAdapter.getItemCount() - position);
                });
            }

            @Override
            public void onRemoved(int position, int count) {
                mHandler.post(() -> {
                    mBaseAdapter.notifyItemRangeRemoved(position, count);
                    mBaseAdapter.notifyItemRangeChanged(0, mBaseAdapter.getItemCount());
                });
            }
        });
    }

    /**
     * 设置新值.
     * <p>
     * 通过新旧数据对比计算,进入线程池计算
     * @param listData 列表数据
     */
    void setListData(final List<T> listData) {
        mDiffCallback.setNewData(listData);
        mDiffCallback.setOldData(mBaseAdapter.mListData);

        mExecutorService.submit(() -> {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(mDiffCallback, true);
            //切换线程
            mHandler.post(() -> {
                mBaseAdapter.setTrueListData(listData);
                //调度更新,有动画效果
                diffResult.dispatchUpdatesTo(mBaseAdapter);

                setPagedCallback();
            });
        });
    }
}
