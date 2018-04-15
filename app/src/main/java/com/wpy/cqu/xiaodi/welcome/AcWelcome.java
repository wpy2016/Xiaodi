package com.wpy.cqu.xiaodi.welcome;

import android.Manifest;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;
import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.base_activity.CheckPermissionsActivity;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.guide.AcGuide;
import com.wpy.cqu.xiaodi.home.AcHome;
import com.wpy.cqu.xiaodi.im_chat.Rongyun;
import com.wpy.cqu.xiaodi.login.AcLogin;
import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.User;
import com.wpy.cqu.xiaodi.net.UserRequest;
import com.wpy.cqu.xiaodi.net.resp.IResp;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class AcWelcome extends CheckPermissionsActivity {

    private static final int DELAY_SECONDS_TO_NEXT_AC = 3;

    private static final int StatusBarColor = Color.parseColor("#08131d");

    private static final String[] PERMISSION = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR, StatusBarColor);
        bundle.putStringArray(CheckPermissionsActivity.PEMISSION, PERMISSION);
        super.onCreate(bundle);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.ac_welcome);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFirstTimeUse()) {
            closeFirstUse();
            delayToNextAc(AcGuide.class);
            return;
        }

        if (isHasAccount()) {
            UserRequest.GetMyInfo(XiaodiApplication.mCurrentUser.Id,
                    XiaodiApplication.mCurrentUser.Token, new IResp<User>() {
                        @Override
                        public void success(User user) {
                            XiaodiApplication.mCurrentUser = user;
                            XiaodiApplication.mCurrentUser.saveToLocalFile();
                        }

                        @Override
                        public void fail(ResultResp resp) {
                            Logger.i("auto update userinfo fail=%s", resp.message);
                        }
                    });

            Observable.timer(DELAY_SECONDS_TO_NEXT_AC, TimeUnit.SECONDS)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(i -> Rongyun.toHomeAc(this, true, false));
            return;
        }
        delayToNextAc(AcLogin.class);
    }

    private boolean isFirstTimeUse() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("conf", Context.MODE_PRIVATE);
        boolean isFirstUse = sharedPreferences.getBoolean("isFirstUse", true);
        Logger.i("is first use %s", isFirstUse);
        return isFirstUse;
    }

    private void closeFirstUse() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("conf", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean("isFirstUse", false);
        edit.apply();
    }

    private void toNextAc(Class<?> nextClass) {
        Intent intent = new Intent(this, nextClass);
        startActivity(intent);
        finish();
    }

    private void delayToNextAc(final Class<?> nextClass) {
        Observable.timer(DELAY_SECONDS_TO_NEXT_AC, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(i -> toNextAc(nextClass));
    }

    private boolean isHasAccount() {
        User user = User.loadFormFile();
        if (null == user) {
            return false;
        }
        XiaodiApplication.mCurrentUser = user;
        return true;
    }
}
