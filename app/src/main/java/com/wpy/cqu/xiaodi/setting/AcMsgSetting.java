package com.wpy.cqu.xiaodi.setting;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;
import com.wpy.cqu.xiaodi.view.SwitchView;

public class AcMsgSetting extends TopBarAppComptAcitity {

    private SwitchView msvSound;

    private SwitchView msvShake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_msg_setting);
        bindView();
        initView();
        bindEvent();
    }


    private void bindView() {
        mtvBack = (TextView) findViewById(R.id.id_top_back_tv);
        mivBack = (ImageView) findViewById(R.id.id_top_back_iv_img);
        mtvContent = (TextView) findViewById(R.id.id_top_tv_content);
        msvSound = (SwitchView) findViewById(R.id.id_ac_msg_setting_sv_sound);
        msvShake = (SwitchView) findViewById(R.id.id_ac_msg_setting_sv_shake);
    }

    private void initView() {
        mtvBack.setText(getResources().getString(R.string.setting));
        mtvBack.setTextColor(Color.WHITE);
        mivBack.setImageResource(R.drawable.go_back_white);
        mtvContent.setText(getResources().getString(R.string.msg_setting));
    }

    private void bindEvent() {
        mtvBack.setOnClickListener(view -> finish());
        mivBack.setOnClickListener(view -> finish());
        msvSound.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn() {
                msvSound.toggleSwitch(true);
            }

            @Override
            public void toggleToOff() {
                msvSound.toggleSwitch(false);
            }
        });

        msvShake.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn() {
                msvShake.toggleSwitch(true);
            }

            @Override
            public void toggleToOff() {
                msvShake.toggleSwitch(false);
            }
        });
    }
}
