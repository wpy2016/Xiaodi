package com.wpy.cqu.vertifycode.factory;

import com.wpy.cqu.vertifycode.base.IVertifyCode;
import com.wpy.cqu.vertifycode.bmob.BmobVertifyCode;

/**
 * Created by wangpeiyu on 2018/3/29.
 */

public class VertifyCodeFactory {

    public static enum VertifyCodeEnum {BMOB}

    public static IVertifyCode getInstance(VertifyCodeEnum vertifyCodeEnum) {
        IVertifyCode vertifyCode = null;
        switch (vertifyCodeEnum) {
            case BMOB:
                vertifyCode = new BmobVertifyCode();
                break;
            default:
                vertifyCode = new BmobVertifyCode();
        }
        return vertifyCode;
    }

}
