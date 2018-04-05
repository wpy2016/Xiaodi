package com.wpy.cqu.xiaodi.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wangpeiyu on 2018/4/5.
 */

public class UserResultResp {

    @SerializedName("status_code")
    public int ResultCode;//200表示成功


    @SerializedName("status_msg")
    public String message;//消息

    @SerializedName("user")
    public User user;

    public UserResultResp() {
    }

    public int getResultCode() {
        return ResultCode;
    }

    public void setResultCode(int resultCode) {
        ResultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
