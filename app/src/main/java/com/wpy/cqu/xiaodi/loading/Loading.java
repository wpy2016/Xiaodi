package com.wpy.cqu.xiaodi.loading;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.wpy.cqu.xiaodi.R;

/**
 * Created by wangpeiyu on 2018/4/6.
 */

public class Loading {

    public static PopupWindow showLoading(AppCompatActivity activity,View location) {
        View view  = activity.getLayoutInflater().inflate(R.layout.loading,null);
        LoadingPopwindow popwindow = new LoadingPopwindow(view,activity);
        popwindow.showAtLocation(location, Gravity.CENTER,0,0);
        return popwindow;
    }

    public static void stopLoading(PopupWindow popupWindow) {
        popupWindow.dismiss();
    }
}
