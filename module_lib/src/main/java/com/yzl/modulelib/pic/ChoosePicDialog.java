package com.yzl.modulelib.pic;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.yzl.modulelib.R;

import static com.yzl.modulelib.pic.PicConfig.INT_MAX_CHOOSE_NUMBER;
import static com.yzl.modulelib.pic.PicConfig.INT_MIN_CHOOSE_NUMBER;

/**
 * @author 10488
 * @date 2017-07-06
 */

public class ChoosePicDialog extends BottomSheetDialogFragment {

    /**
     * 选择图片的fragment.
     */
    private ChoosePicFragment mChoosePicFragment;

    /**
     * 配置.
     */
    private PicConfig mPicConfig;

    /**
     * 弹窗布局.
     */
    private View mView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        return dialog;
    }

    public void setPicConfig(PicConfig picConfig) {
        mPicConfig = picConfig;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.dialog_choose_pic, container, false);
        init();
        return mView;
    }

    private void init() {
        mView.findViewById(R.id.tv_choose_from_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goPic(true, false);
            }
        });

        mView.findViewById(R.id.tv_choose_from_gallery).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goPic(false, true);
                    }
                });

        mView.findViewById(R.id.tv_cancel).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });
    }

    private void goPic(boolean chooseFromCamera, boolean chooseFromGallery) {
        mPicConfig.setChooseFromCamera(chooseFromCamera);
        mPicConfig.setChooseFromGallery(chooseFromGallery);
        mChoosePicFragment.setPicConfig(mPicConfig);
        mChoosePicFragment.startChoosePic();
        dismiss();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        mChoosePicFragment = ChoosePicFragment.injectIfNeededIn(manager);
    }

    public static ChoosePicDialog getInstance(PicConfig picConfig) {
        ChoosePicDialog choosePicDialog = new ChoosePicDialog();
        choosePicDialog.setPicConfig(picConfig);
        return choosePicDialog;
    }

    public static class Builder {
        private PicConfig mPicConfig;
        private int chooseMax = INT_MIN_CHOOSE_NUMBER;

        public Builder(@NonNull PicConfig.OnChooseSuccessListener onChooseSuccessListener) {
            mPicConfig = new PicConfig();
            mPicConfig.setOnChooseSuccessListener(onChooseSuccessListener);
            mPicConfig.setChooseMax(chooseMax);
        }

        public Builder setCompress(boolean compress) {
            mPicConfig.setCompress(compress);
            return this;
        }

        public Builder setNeedCrop(boolean needCrop) {
            mPicConfig.setNeedCrop(needCrop);
            return this;
        }

        public Builder setXRatio(int xRatio) {
            mPicConfig.setxRatio(xRatio);
            return this;
        }

        public Builder setYRatio(int xRatio) {
            mPicConfig.setyRatio(xRatio);
            return this;
        }

        public Builder setChooseMax(int chooseMax) {
            this.chooseMax = (chooseMax < INT_MIN_CHOOSE_NUMBER ? INT_MIN_CHOOSE_NUMBER : chooseMax) >
                    INT_MAX_CHOOSE_NUMBER ? INT_MAX_CHOOSE_NUMBER : chooseMax;
            mPicConfig.setChooseMax(chooseMax);
            return this;
        }

        public ChoosePicDialog create() {
            ChoosePicDialog dialog = ChoosePicDialog.getInstance(mPicConfig);
            return dialog;
        }
    }
}
