package com.wpy.cqu.xiaodi.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wangpeiyu on 2018/4/4.
 * 每次操作返回的信息，表示是否执行成功
 */
public class ResultResp {

    @SerializedName("status_code")
    public int ResultCode;//200表示成功

    @SerializedName("status_msg")
    public String message;//消息


    public ResultResp(int code, String message) {
        this.ResultCode = code;
        this.message = message;
    }
}
