package com.wpy.cqu.pay;

/**
 * Created by wangpeiyu on 2018/4/13.
 */

public class PayFactory {

    public static IPay getInstance() {
        return new MyTrPay();
    }

}
