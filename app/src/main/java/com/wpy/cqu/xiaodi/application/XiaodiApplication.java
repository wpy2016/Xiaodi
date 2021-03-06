package com.wpy.cqu.xiaodi.application;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.model.User;
import com.wpy.cqu.xiaodi.net.UserRequest;

import java.io.File;

import io.rong.imkit.RongIM;


public class XiaodiApplication extends Application {

    public static String IMG_SAVE_PATH;

    public static String USER_SAVE_PATH;

    public static String USER_SAVE_FILEPATH;

    public static User mCurrentUser;

    @Override
    public void onCreate() {
        super.onCreate();
        IMG_SAVE_PATH = getFilesDir().getAbsolutePath() + "/crop_photo";
        USER_SAVE_PATH = getFilesDir().getAbsolutePath() + "/login_user";
        USER_SAVE_FILEPATH = USER_SAVE_PATH + "/" + "user";
        confirmDirIsExist();
        initLogger();

        //初始化融云即时通讯
        RongIM.init(this);

        //定时更新用户数据
        UserRequest.intervalUpdateUserInfo();
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
