package com.wpy.cqu.vertifycode.bmob;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.wpy.cqu.vertifycode.base.IVertifyCode;
import com.wpy.cqu.vertifycode.base.IVertifyResp;

import java.util.ConcurrentModificationException;

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
    public void init(Context ctx) {
        try {
            BmobSMS.initialize(ctx, "087ef70fd16f64aa1bc0adac148b8235", new SMSCodeListener() {
                @Override
                public void onReceive(String s) {

                }
            });
        } catch (ConcurrentModificationException e) {
            //多次初始化异常
        } catch (Exception e) {
            Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //调用前先init
    @Override
    public void sendCode(String phone, Context ctx, EditText etCode, IVertifyResp resp) {
        try {
            BmobSMS.requestSMSCode(ctx, phone, "笑递", new RequestSMSCodeListener() {

                @Override
                public void done(Integer smsId, BmobException ex) {
                    if (null != resp) {
                        resp.resp(ex);
                    }
                }
            });
        } catch (ConcurrentModificationException e) {
            Toast.makeText(ctx, "请求过于频繁", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    //调用前先init
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
