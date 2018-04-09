package com.wpy.cqu.xiaodi.net.resp;

import com.orhanobut.logger.Logger;
import com.wpy.cqu.xiaodi.model.ResultResp;

import io.reactivex.functions.Consumer;

public class ResultRespConsumer implements Consumer<ResultResp> {

    private String method;

    private IResp<ResultResp> resp;

    public ResultRespConsumer(String methodStr, IResp<ResultResp> iResp) {
        this.method = methodStr;
        this.resp = iResp;
    }

    @Override
    public void accept(ResultResp resultResp) {
        Logger.i(method + " sussess.code is %d message is %s", resultResp.ResultCode, resultResp.message);
        if (Error.SUCCESS != resultResp.ResultCode) {
            resp.fail(new ResultResp(resultResp.ResultCode, resultResp.message));
            return;
        }
        resp.success(resultResp);
    }
}