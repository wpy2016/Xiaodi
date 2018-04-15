package com.wpy.cqu.vertifycode.base;

import android.content.Context;
import android.widget.EditText;

/**
 * Created by wangpeiyu on 2018/3/29.
 */

public interface IVertifyCode {

    void init(Context context);

    void sendCode(String phone, Context ctx, EditText etCode, IVertifyResp resp);

    void vertifyCode(String phone, String code, Context ctx, IVertifyResp resp);

}
