package com.wpy.cqu.xiaodi.loading;

import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.wpy.cqu.xiaodi.R;

/**
 * Created by wangpeiyu on 2018/4/6.
 */

public class Loading {

    public static PopupWindow getLoadingPopwindown(AppCompatActivity activity) {
        View view  = activity.getLayoutInflater().inflate(R.layout.loading,null);
        LoadingPopwindow popwindow = new LoadingPopwindow(view,activity);
        return popwindow;
    }

    public static void showLoading(AppCompatActivity activity,PopupWindow loadingPopwindow) {
        loadingPopwindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER,0,0);
    }
}
