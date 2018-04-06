package com.wpy.cqu.xiaodi.net.resp;

import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.User;
import com.wpy.cqu.xiaodi.model.UserResultResp;

/**
 * Created by wangpeiyu on 2018/4/5.
 */

public interface IResp<T> {
    void success(T object);
    void fail(ResultResp resp);
}
