package com.wpy.cqu.xiaodi.application;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.wpy.cqu.xiaodi.model.User;

import java.io.File;


public class XiaodiApplication extends Application {

    public static String IMG_SAVE_PATH;

    public static String USER_SAVE_PATH;

    public static String USER_SAVE_FILEPATH;

    public static User mCurrentUser;

    @Override
    public void onCreate() {
        super.onCreate();
        IMG_SAVE_PATH = getExternalFilesDir(null).getAbsolutePath() + "/crop_photo";
        USER_SAVE_PATH = getExternalFilesDir(null).getAbsolutePath() + "/login_user";
        USER_SAVE_FILEPATH = USER_SAVE_PATH + "/" + "user";
        confirmDirIsExist();
        initLogger();
    }

    private void confirmDirIsExist() {
        File img = new File(IMG_SAVE_PATH);
        if (!img.exists()) {
            img.mkdirs();
        }

        File user = new File(USER_SAVE_PATH);
        if (!user.exists()) {
            user.mkdirs();
        }
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  //（可选）是否显示线程信息。 默认值为true
                .methodCount(1)         // （可选）要显示的方法行数。 默认2
                .tag("XIAODI")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }
}
