package com.wpy.cqu.xiaodi.model;

/**
 * Created by wangpeiyu on 2018/4/4.
 * 每次操作返回的信息，表示是否执行成功
 */
public class ResultResp {

    public int ResultCode;//200表示成功

    public String message;//消息


    public ResultResp(int code, String message) {
        this.ResultCode = code;
        this.message = message;
    }
}
