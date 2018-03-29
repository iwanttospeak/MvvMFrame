package com.yzl.shareciger;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yzl.modulelib.pic.ChoosePicDialog;
import com.yzl.modulelib.pic.PicConfig;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new ChoosePicDialog.Builder(new PicConfig.OnChooseSuccessListener() {
            @Override
            public void onChooseSuccess(List<String> picList) {

            }
        }).setNeedCrop(true).setChooseMax(10).create().show(getSupportFragmentManager(), "");
    }
}
