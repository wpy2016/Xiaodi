package com.wpy.cqu.xiaodi.reward;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.evaluate.AcEvaluate;

/**
 * 更新状态的
 */
public class AcRewardDetailCanNotCarry extends AcRewardDetail {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mtvBack.setText(getResources().getString(R.string.carry_recode));

        mbtnArray.setOnClickListener(this::evaluate);
        mbtnArray.setVisibility(View.GONE);
        if (reward.Publisher.Id.equals(XiaodiApplication.mCurrentUser.Id)) {
            if (0 == reward.receiveGrade) {
                mbtnArray.setVisibility(View.VISIBLE);
                mbtnArray.setText(getResources().getString(R.string.evaluate));
            }
        }

        if (reward.Receiver.Id.equals(XiaodiApplication.mCurrentUser.Id)) {
            if (0 == reward.publisherGrade) {
                mbtnArray.setVisibility(View.VISIBLE);
                mbtnArray.setText(getResources().getString(R.string.evaluate));
            }
        }
    }

    private void evaluate(View view) {
        Intent intent = new Intent(this, AcEvaluate.class);
        intent.putExtra("reward", reward);
        startActivity(intent);
    }
}
