package com.wpy.cqu.xiaodi.listener;

import android.content.Context;
import android.view.View;

import com.wpy.cqu.xiaodi.util.ToastUtil;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by wangpeiyu on 2018/4/9.
 */
public class DoubleClickDoListener implements View.OnClickListener {

    private int times = 0;

    private View.OnClickListener listener;

    private Context context;

    private String tip;

    Disposable subscribe;

    public DoubleClickDoListener(Context context, View.OnClickListener listener, String tip) {
        this.listener = listener;
        this.context = context;
        this.tip = tip;
    }

    @Override
    public void onClick(View view) {
        times++;
        if (1 == times) {
            ToastUtil.toast(context, tip);
            subscribe = Observable.timer(2, TimeUnit.SECONDS)
                    .subscribe(l -> times = 0);
        }
        if (2 == times) {
            listener.onClick(view);
            subscribe.dispose();
        }
    }
}
