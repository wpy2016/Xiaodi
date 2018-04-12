package com.wpy.cqu.xiaodi.setting;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.base_activity.CheckPermissionsActivity;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;
import com.wpy.cqu.xiaodi.util.ToastUtil;
import com.wpy.cqu.xiaodi.view.SwitchView;

import static android.media.AudioManager.RINGER_MODE_SILENT;
import static android.media.AudioManager.RINGER_MODE_VIBRATE;

public class AcMsgSetting extends TopBarAppComptAcitity {

    private static final int STATUS_BAR_COLOR = Color.parseColor("#00dec9");

    private static final String[] PERMISSION = {
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    private SwitchView msvSound;

    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putStringArray(CheckPermissionsActivity.PEMISSION, PERMISSION);
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR, STATUS_BAR_COLOR);
        super.onCreate(bundle);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
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
    }

    private void initView() {
        mtvBack.setText(getResources().getString(R.string.setting));
        mtvBack.setTextColor(Color.WHITE);
        mivBack.setImageResource(R.drawable.go_back_white);
        mtvContent.setText(getResources().getString(R.string.msg_setting));

        if (AudioManager.RINGER_MODE_NORMAL == audioManager.getRingerMode()) {
            msvSound.setState(true);
        }

        if (AudioManager.RINGER_MODE_SILENT == audioManager.getRingerMode()) {
            msvSound.setState(false);
        }

        if (AudioManager.RINGER_MODE_VIBRATE == audioManager.getRingerMode()) {
            msvSound.setState(false);
        }
    }

    private void bindEvent() {
        mtvBack.setOnClickListener(view -> finish());
        mivBack.setOnClickListener(view -> finish());
        msvSound.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn() {
                msvSound.toggleSwitch(true);
                try {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                } catch (SecurityException e) {
                    Logger.i(e.getMessage());
                }
            }

            @Override
            public void toggleToOff() {
                msvSound.toggleSwitch(false);
                try {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                } catch (SecurityException e) {
                    Logger.i(e.getMessage());
                }
            }
        });
    }
}
