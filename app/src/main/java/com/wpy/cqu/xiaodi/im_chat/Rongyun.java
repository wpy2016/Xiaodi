package com.wpy.cqu.xiaodi.im_chat;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.orhanobut.logger.Logger;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.home.AcHome;
import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.net.resp.IResp;
import com.wpy.cqu.xiaodi.util.ToastUtil;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;


/**
 * Created by wangpeiyu on 2018/4/10.
 */

public class Rongyun {

    /**
     * 登陆调用
     * 注册成功调用
     * 主动登陆调用
     *
     * @param currentAc
     * @param isNeedFinishCurrentAc
     */
    public static void toHomeAc(AppCompatActivity currentAc, boolean isNeedFinishCurrentAc, boolean isNeedClearAcStack) {
        connect(XiaodiApplication.mCurrentUser.RongyunToken, currentAc, new IResp<String>() {
            @Override
            public void success(String object) {
                Logger.i("connect rongyun succcessful.");
                Intent intent = new Intent(currentAc, AcHome.class);
                if (isNeedClearAcStack) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    currentAc.startActivity(intent);
                    return;
                }
                currentAc.startActivity(intent);
                if (isNeedFinishCurrentAc) {
                    currentAc.finish();
                }
            }

            @Override
            public void fail(ResultResp resp) {
                Logger.i("connect rongyun fail." + resp.message);
                ToastUtil.toast(currentAc, resp.message);
            }
        });
    }

    private static void connect(String token, Context context, IResp<String> resp) {
        if (!context.getApplicationInfo().packageName.equals(getCurProcessName(context.getApplicationContext()))) {
            resp.fail(new ResultResp(444, "链接融云失败，进程不一致"));
            return;
        }
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                resp.fail(new ResultResp(444, "token问题"));
            }

            @Override
            public void onSuccess(String userid) {
                resp.success(userid);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                resp.fail(new ResultResp(444, errorCode.getMessage()));
            }
        });
    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

}
