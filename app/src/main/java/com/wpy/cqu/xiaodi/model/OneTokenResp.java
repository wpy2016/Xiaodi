package com.wpy.cqu.xiaodi.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wangpeiyu on 2018/4/13.
 */

public class OneTokenResp {

    @SerializedName("status_code")
    public int ResultCode;//200表示成功

    @SerializedName("status_msg")
    public String message;//消息

    @SerializedName("token")
    public String token;

    public OneTokenResp() {
    }

    public OneTokenResp(int resultCode, String message, String token) {
        ResultCode = resultCode;
        this.message = message;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
