package com.wpy.cqu.xiaodi.setting;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.about.AcAbout;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;
import com.wpy.cqu.xiaodi.login.AcLogin;
import com.wpy.cqu.xiaodi.model.User;
import com.wpy.cqu.xiaodi.resetpass.AcEditPass;
import com.wpy.cqu.xiaodi.util.ActivityManager;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class AcSetting extends TopBarAppComptAcitity {

    private static final int STATUS_BAR_COLOR = Color.parseColor("#00dec9");

    private RelativeLayout mrlResetPass;

    private RelativeLayout mrlMessageNotificationSetting;

    private RelativeLayout mrlAbout;

    private RelativeLayout mrlExitAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR,STATUS_BAR_COLOR);
        super.onCreate(bundle);
        setContentView(R.layout.ac_setting);
        bindView();
        initView();
        bindEvent();
    }

    private void exitAccount(View view) {
        XiaodiApplication.mCurrentUser = null;
        Observable.just("")
                .doOnNext(s -> User.deleteUserLocalFile())
                .subscribeOn(Schedulers.io())
                .subscribe();
        //ActivityManager.getActivityManager().popAllActivityExceptTop(this);
        Intent intent = new Intent(this, AcLogin.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void bindView() {
        mtvBack = (TextView) findViewById(R.id.id_top_back_tv);
        mivBack = (ImageView) findViewById(R.id.id_top_back_iv_img);
        mtvContent = (TextView) findViewById(R.id.id_top_tv_content);
        mrlResetPass = (RelativeLayout) findViewById(R.id.id_setting_rl_password_reset);
        mrlMessageNotificationSetting = (RelativeLayout) findViewById(R.id.id_setting_rl_msg_setting);
        mrlAbout = (RelativeLayout) findViewById(R.id.id_setting_rl_aboutxd);
        mrlExitAccount = (RelativeLayout) findViewById(R.id.id_setting_rl_exit_account);
    }

    private void initView() {
        mtvBack.setText(getResources().getString(R.string.me));
        mtvBack.setTextColor(Color.WHITE);
        mivBack.setImageResource(R.drawable.go_back_white);
        mtvContent.setText(getResources().getString(R.string.setting));
    }

    private void bindEvent() {
        mrlResetPass.setOnClickListener(view -> toNext(AcEditPass.class));
        mrlMessageNotificationSetting.setOnClickListener(view -> toNext(AcMsgSetting.class));
        mrlAbout.setOnClickListener(view -> toNext(AcAbout.class));
        mrlExitAccount.setOnClickListener(this::exitAccount);

        mtvBack.setOnClickListener(view -> finish());
        mivBack.setOnClickListener(view -> finish());
    }

    private void toNext(Class<?> next) {
        Intent intent = new Intent(this,next);
        startActivity(intent);
    }

}
