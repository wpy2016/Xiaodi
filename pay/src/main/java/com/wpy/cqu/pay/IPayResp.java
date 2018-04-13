package com.wpy.cqu.pay;

/**
 * Created by wangpeiyu on 2018/4/13.
 */

public interface IPayResp {
    void onSuccess();

    void onFail(String message);
}
