package com.wpy.cqu.xiaodi.net;

import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.User;
import com.wpy.cqu.xiaodi.model.UserResultResp;
import com.wpy.cqu.xiaodi.net.request.IUserRequest;
import com.wpy.cqu.xiaodi.net.resp.Error;
import com.wpy.cqu.xiaodi.net.resp.IResp;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

/**
 * Created by wangpeiyu on 2018/4/5.
 */

public class UserRequest {

    public static void Register(String phone, String pass, String nickName, String imgPath, IResp<User> userResp) {
        MediaType textType = MediaType.parse("text/plain");
        RequestBody phoneBody = RequestBody.create(textType, phone);
        RequestBody passBody = RequestBody.create(textType, pass);
        RequestBody nickNameBody = RequestBody.create(textType, nickName);

        MediaType fileType = MediaType.parse("multipart/form-data");
        File imgFile = new File(imgPath);
        RequestBody imgBody = RequestBody.create(fileType, imgFile);
        MultipartBody.Part imgData = MultipartBody.Part.createFormData("img", "img.png", imgBody);
        Retrofit retrofit = BaseRetrofit.getInstance();
        IUserRequest userRequest = retrofit.create(IUserRequest.class);
        Observable<UserResultResp> register = userRequest.Register(phoneBody, passBody, nickNameBody, imgData);
        register.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> {
                    if (Error.SUCCESS != resp.getResultCode()) {
                        userResp.fail(new ResultResp(resp.getResultCode(), resp.getMessage()));
                        return;
                    }
                    userResp.success(resp.user);
                }, Error.getErrorConsumer(userResp));
    }
}
