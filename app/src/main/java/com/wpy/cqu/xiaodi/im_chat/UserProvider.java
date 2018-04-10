package com.wpy.cqu.xiaodi.im_chat;

import android.net.Uri;

import com.orhanobut.logger.Logger;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.User;
import com.wpy.cqu.xiaodi.net.UserRequest;
import com.wpy.cqu.xiaodi.net.resp.IResp;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * Created by wangpeiyu on 2018/4/10.
 */

public class UserProvider implements RongIM.UserInfoProvider {
    @Override
    public UserInfo getUserInfo(String id) {
        UserRequest.GetUserInfo(XiaodiApplication.mCurrentUser.Id, XiaodiApplication.mCurrentUser.Token, id, new IResp<User>() {
            @Override
            public void success(User user) {
                UserInfo userInfo = new UserInfo(user.Id, user.NickName, Uri.parse(user.ImgUrl));
                RongIM.getInstance().refreshUserInfoCache(userInfo);
            }

            @Override
            public void fail(ResultResp resp) {
                Logger.i("UserProvider get user info fail,cause=%s", resp.message);
            }
        });
        return null;
    }
}
