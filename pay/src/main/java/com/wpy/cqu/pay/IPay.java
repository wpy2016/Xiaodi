package com.wpy.cqu.pay;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by wangpeiyu on 2018/4/13.
 */

public interface IPay {
    void Pay(AppCompatActivity activity, String tradename, String outtradeno, Long amount, String backparams, String notifyurl, String userid, IPayResp resp);
}
