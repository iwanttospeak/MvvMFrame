package com.yzl.modulelib.pic;

import android.content.Context;

import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.List;

/**
 *
 * @author 10488
 * @date 2017-07-20
 */

public class PicConfig {

    /**
     * 是否需要裁剪.
     */
    protected boolean needCrop;

    /**
     * x比例.
     */
    protected int xRatio;

    /**
     * y比例.
     */
    protected int yRatio;

    /**
     * 選擇的最大數量圖片.
     */
    protected int chooseMax;

    /**
     * 是否需要裁剪.
     */
    protected boolean compress;

    /**
     * 回調.
     */
    protected OnChooseSuccessListener onChooseSuccessListener;

    /**
     * 是否选择拍照.
     */
    protected boolean chooseFromCamera;

    /**
     * 是否选择图片.
     */
    protected boolean chooseFromGallery;

    public void setNeedCrop(boolean needCrop) {
        this.needCrop = needCrop;
    }

    public void setxRatio(int xRatio) {
        this.xRatio = xRatio;
    }

    public void setyRatio(int yRatio) {
        this.yRatio = yRatio;
    }

    public void setChooseMax(int chooseMax) {
        this.chooseMax = chooseMax;
    }

    public void setCompress(boolean compress) {
        this.compress = compress;
    }

    public void setOnChooseSuccessListener(OnChooseSuccessListener onChooseSuccessListener) {
        this.onChooseSuccessListener = onChooseSuccessListener;
    }

    public void setChooseFromCamera(boolean chooseFromCamera) {
        this.chooseFromCamera = chooseFromCamera;
    }

    public void setChooseFromGallery(boolean chooseFromGallery) {
        this.chooseFromGallery = chooseFromGallery;
    }

    /**
     * 最小选择数量.
     */
    public static final int INT_MIN_CHOOSE_NUMBER = 1;

    /**
     * 最大选择数量.
     */
    public static final int INT_MAX_CHOOSE_NUMBER = 8;

    /**
     * 清除所有选择图片的缓存.
     *
     * @param context 上下文
     */
    public static void clearAllCachePic(Context context) {
        PictureFileUtils.deleteCacheDirFile(context);
    }

    /**
     * 选择结束的回调.
     */
    public interface OnChooseSuccessListener {
        public void onChooseSuccess(List<String> picList);
    }
}
