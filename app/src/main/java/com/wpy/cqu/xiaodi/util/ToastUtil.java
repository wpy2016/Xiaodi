package com.wpy.cqu.xiaodi.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wangpeiyu on 2018/3/30.
 */

public class ToastUtil {

    public static void toast(Context ctx, String content){
        Toast.makeText(ctx,content,Toast.LENGTH_SHORT).show();
    }
}
