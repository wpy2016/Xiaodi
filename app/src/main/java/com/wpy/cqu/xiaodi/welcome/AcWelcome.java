package com.wpy.cqu.xiaodi.welcome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.orhanobut.logger.Logger;
import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.guide.AcGuide;
import com.wpy.cqu.xiaodi.home.AcHome;
import com.wpy.cqu.xiaodi.login.AcLogin;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class AcWelcome extends StatusBarAppComptActivity {

    private static final int STATUE_BAR_COLOR = Color.parseColor("#00ff00");

    private static final int DELAY_SECONDS_TO_NEXT_AC = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR, STATUE_BAR_COLOR);
        super.onCreate(bundle);
        setContentView(R.layout.ac_welcome);

        if (isFirstTimeUse()) {
            closeFirstUse();
            delayToNextAc(AcGuide.class);
            return;
        }

        if (isHasAccount()) {
            delayToNextAc(AcHome.class);
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
        edit.commit();
    }

    private void toNextAc(Class<?> nextClass) {
        Intent intent = new Intent(this, nextClass);
        startActivity(intent);
        finish();
    }

    private void delayToNextAc(final Class<?> nextClass) {
        Observable.timer(DELAY_SECONDS_TO_NEXT_AC, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        toNextAc(nextClass);
                    }
                });
    }

    private boolean isHasAccount() {
        return false;//// TODO: 2018/3/27
    }
}
