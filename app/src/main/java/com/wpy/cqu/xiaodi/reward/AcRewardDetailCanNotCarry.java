package com.wpy.cqu.xiaodi.reward;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wpy.cqu.xiaodi.R;

public class AcRewardDetailCanNotCarry extends AcRewardDetail {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mbtnArray.setVisibility(View.GONE);
        mtvBack.setText(getResources().getString(R.string.carry_recode));
    }
}
