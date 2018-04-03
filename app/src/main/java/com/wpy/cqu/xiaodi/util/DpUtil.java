package com.wpy.cqu.xiaodi.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by wangpeiyu on 2018/4/3.
 */

public class DpUtil {

    public static int dip2px(Context context, float dpValue) {
        float scale = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, context.getResources().getDisplayMetrics());

        return (int) scale;
    }
}
