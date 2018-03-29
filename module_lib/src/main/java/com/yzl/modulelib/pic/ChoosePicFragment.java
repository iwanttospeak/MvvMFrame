package com.yzl.modulelib.pic;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import static com.yzl.modulelib.pic.PicConfig.INT_MIN_CHOOSE_NUMBER;


/**
 * @author wang
 * @date 2018-02-06
 */

public class ChoosePicFragment extends Fragment {

    private static final String CHOOSE_PIC_TAG = "choose_pic_tag";

    /**
     * 依附到activity上.
     *
     * @param fragmentManager
     * @return
     */
    public static ChoosePicFragment injectIfNeededIn(FragmentManager fragmentManager) {
        ChoosePicFragment choosePicFragment = (ChoosePicFragment) fragmentManager.findFragmentByTag(CHOOSE_PIC_TAG);
        if (choosePicFragment == null) {
            choosePicFragment = new ChoosePicFragment();
            fragmentManager.beginTransaction().add(choosePicFragment, CHOOSE_PIC_TAG).commit();
        }

        return choosePicFragment;
    }

    /**
     * 配置.
     */
    private PicConfig mPicConfig;

    public void setPicConfig(PicConfig picConfig) {
        mPicConfig = picConfig;
    }

    /**
     * 开始获取图片.
     */
    public void startChoosePic() {
        if (mPicConfig.chooseFromCamera) {
            chooseFromCamera();
        } else {
            chooseFromGallery();
        }
    }

    /**
     * 拍照选择.
     */
    private void chooseFromCamera() {
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .maxSelectNum(INT_MIN_CHOOSE_NUMBER)
                .enableCrop(mPicConfig.needCrop)
                .withAspectRatio(mPicConfig.xRatio, mPicConfig.yRatio)
                .compress(mPicConfig.compress)
                .cropCompressQuality(50)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    /**
     * 从相册选择.
     */
    private void chooseFromGallery() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(mPicConfig.chooseMax == INT_MIN_CHOOSE_NUMBER ? PictureConfig.SINGLE : PictureConfig.MULTIPLE)
                .maxSelectNum(mPicConfig.chooseMax)
                .enableCrop(mPicConfig.needCrop)
                .withAspectRatio(mPicConfig.xRatio, mPicConfig.yRatio)
                .isCamera(true)
                .compress(mPicConfig.compress)
                .cropCompressQuality(50)
                .forResult(PictureConfig.CHOOSE_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != requestCode) {
            return;
        }

        List<LocalMedia> localMedias = PictureSelector.obtainMultipleResult(data);
        if (localMedias.isEmpty()) {
            return;
        }

        PicConfig.OnChooseSuccessListener onChooseSuccessListener = mPicConfig.onChooseSuccessListener;
        if (onChooseSuccessListener != null) {
            onChooseSuccessListener.onChooseSuccess(getChoosePicList(requestCode, localMedias));
        }
    }

    /**
     * 获取选择的图片.
     *
     * @param requestCode 返回的code.
     * @param localMedias 存储的数据
     * @return 选择图片的数组
     */
    private List<String> getChoosePicList(int requestCode, List<LocalMedia> localMedias) {
        List<String> picList = new ArrayList<>();
        if (requestCode != PictureConfig.CHOOSE_REQUEST) {
            return picList;
        }

        if (localMedias.size() > INT_MIN_CHOOSE_NUMBER) {
            for (LocalMedia media : localMedias) {
                if (mPicConfig.compress) {
                    picList.add(media.getCompressPath());
                } else {
                    picList.add(media.getPath());
                }
            }
        } else if (localMedias.size() == INT_MIN_CHOOSE_NUMBER) {
            LocalMedia media = localMedias.get(0);
            if (media.isCut()) {
                picList.add(media.getCutPath());
            } else if (media.isCompressed()) {
                picList.add(media.getCompressPath());
            } else {
                picList.add(media.getPath());
            }
        }
        return picList;
    }
}


