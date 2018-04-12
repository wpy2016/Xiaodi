package com.wpy.cqu.xiaodi.sign;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wangpeiyu on 2018/4/12.
 */

public class SignResp {

    @SerializedName("status_code")
    public int ResultCode;//200表示成功

    @SerializedName("status_msg")
    public String Message;//消息

    @SerializedName("days")
    public List<String> Days;

    public SignResp(int resultCode, String message, List<String> days) {
        ResultCode = resultCode;
        Message = message;
        Days = days;
    }

    public SignResp() {
    }

    public int getResultCode() {
        return ResultCode;
    }

    public void setResultCode(int resultCode) {
        ResultCode = resultCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<String> getDays() {
        return Days;
    }

    public void setDays(List<String> days) {
        Days = days;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
