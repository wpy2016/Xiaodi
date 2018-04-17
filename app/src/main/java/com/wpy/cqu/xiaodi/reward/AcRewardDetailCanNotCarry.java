package com.wpy.cqu.xiaodi.reward;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.evaluate.AcEvaluate;
import com.wpy.cqu.xiaodi.model.Reward;
import com.wpy.cqu.xiaodi.util.ToastUtil;

import io.rong.imkit.RongIM;

/**
 * 更新状态的
 */
public class AcRewardDetailCanNotCarry extends AcRewardDetail {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mtvBack.setText(getResources().getString(R.string.carry_recode));
        mbtnArray.setVisibility(View.GONE);

        if (reward.Publisher.Id.equals(XiaodiApplication.mCurrentUser.Id)) {
            mySend();
            return;
        }
        myCarry();
    }

    private void myCarry() {
        if (Reward.REWARD_STATE_FINISH != reward.state) {
            mbtnArray.setVisibility(View.VISIBLE);
            mbtnArray.setText(getResources().getString(R.string.contact_ta));
            mbtnArray.setOnClickListener(contactTa(reward.Publisher.Id, reward.Publisher.NickName));
            return;
        }
        if (0 == reward.publisherGrade) {
            mbtnArray.setVisibility(View.VISIBLE);
            mbtnArray.setText(getResources().getString(R.string.evaluate));
            mbtnArray.setOnClickListener(this::evaluate);
        }
    }

    private void mySend() {
        if (Reward.REWARD_STATE_FINISH != reward.state) {
            mbtnArray.setVisibility(View.VISIBLE);
            mbtnArray.setText(getResources().getString(R.string.contact_ta));
            mbtnArray.setOnClickListener(contactTa(reward.Receiver.Id, reward.Receiver.NickName));
            return;
        }
        if (0 == reward.receiveGrade) {
            mbtnArray.setVisibility(View.VISIBLE);
            mbtnArray.setText(getResources().getString(R.string.evaluate));
            mbtnArray.setOnClickListener(this::evaluate);
        }
    }

    private void evaluate(View view) {
        Intent intent = new Intent(this, AcEvaluate.class);
        intent.putExtra("reward", reward);
        startActivity(intent);
    }

    private View.OnClickListener contactTa(String userId, String title) {
        return view -> {
            if (null == RongIM.getInstance()) {
                ToastUtil.toast(AcRewardDetailCanNotCarry.this, getResources().getString(R.string.rong_yun_not_connect));
                return;
            }
            RongIM.getInstance().startPrivateChat(AcRewardDetailCanNotCarry.this, userId, title);
        };
    }
}
