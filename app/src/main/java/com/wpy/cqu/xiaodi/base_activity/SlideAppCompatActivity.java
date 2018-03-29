package com.wpy.cqu.xiaodi.base_activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wpy.cqu.xiaodi.R;

/**
 * Created by wangpeiyu on 2018/3/28.
 */

public class SlideAppCompatActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

}
