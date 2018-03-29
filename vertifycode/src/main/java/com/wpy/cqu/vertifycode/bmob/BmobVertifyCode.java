package com.wpy.cqu.vertifycode.bmob;

import android.content.Context;
import android.widget.EditText;

import com.wpy.cqu.vertifycode.base.IVertifyCode;
import com.wpy.cqu.vertifycode.base.IVertifyResp;

import cn.bmob.newsmssdk.BmobSMS;
import cn.bmob.newsmssdk.exception.BmobException;
import cn.bmob.newsmssdk.listener.RequestSMSCodeListener;
import cn.bmob.newsmssdk.listener.SMSCodeListener;
import cn.bmob.newsmssdk.listener.VerifySMSCodeListener;

/**
 * Created by wangpeiyu on 2018/3/29.
 */

public class BmobVertifyCode implements IVertifyCode {


    @Override
    public void sendCode(String phone, Context ctx, EditText etCode, IVertifyResp resp) {
        BmobSMS.initialize(ctx.getApplicationContext(), "087ef70fd16f64aa1bc0adac148b8235", new SMSCodeListener() {
            @Override
            public void onReceive(String s) {
                if (null != etCode) {
                    etCode.setText(s);
                }
            }
        });

        BmobSMS.requestSMSCode(ctx, phone, "笑递", new RequestSMSCodeListener() {

            @Override
            public void done(Integer smsId, BmobException ex) {
                if (null != resp) {
                    resp.resp(ex);
                }
            }
        });

    }

    @Override
    public void vertifyCode(String phone, String code, Context ctx, IVertifyResp resp) {
        BmobSMS.verifySmsCode(ctx, phone, code, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException ex) {
                if (null != resp) {
                    resp.resp(ex);
                }
            }
        });
    }

}
