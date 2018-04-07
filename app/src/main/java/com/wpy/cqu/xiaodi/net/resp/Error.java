package com.wpy.cqu.xiaodi.net.resp;

import com.orhanobut.logger.Logger;
import com.wpy.cqu.xiaodi.model.ResultResp;

import java.io.FileNotFoundException;
import java.net.ConnectException;

import io.reactivex.functions.Consumer;

/**
 * Created by wangpeiyu on 2018/4/5.
 */

public class Error {

    public static final int SUCCESS = 200;

    private static Consumer<Throwable> errorConsumer;

    public synchronized static Consumer<Throwable> getErrorConsumer(IResp resp) {
        if (null == errorConsumer) {
            errorConsumer = new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) {
                    Logger.e(throwable.getMessage());
                    if (throwable instanceof FileNotFoundException) {
                        resp.fail(new ResultResp(444, "请设置图片"));
                        return;
                    }
                    if (throwable instanceof ConnectException) {
                        resp.fail(new ResultResp(444, "网络错误"));
                        return;
                    }
                    Logger.i("request fail %s, %s",throwable.getStackTrace().toString(),throwable.getMessage());
                    throwable.printStackTrace();
                    resp.fail(new ResultResp(444, throwable.getMessage()));
                }
            };
        }
        return errorConsumer;
    }

}
