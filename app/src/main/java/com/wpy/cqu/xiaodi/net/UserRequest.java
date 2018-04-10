package com.wpy.cqu.xiaodi.net;

import com.orhanobut.logger.Logger;
import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.User;
import com.wpy.cqu.xiaodi.model.UserResultResp;
import com.wpy.cqu.xiaodi.net.request.IUserRequest;
import com.wpy.cqu.xiaodi.net.resp.Error;
import com.wpy.cqu.xiaodi.net.resp.IResp;
import com.wpy.cqu.xiaodi.net.resp.ResultRespConsumer;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
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
                .subscribe(new UserConsumer("Register", userResp), Error.getErrorConsumer(userResp));
    }

    public static void Login(String phone, String pass, IResp<User> userResp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IUserRequest userRequest = retrofit.create(IUserRequest.class);
        Observable<UserResultResp> login = userRequest.Login(phone, pass);
        login.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new UserConsumer("Login", userResp), Error.getErrorConsumer(userResp));
    }

    public static void Auth(String userId, String token, String schoolId, String schoolPass, String realName, IResp<ResultResp> userResp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IUserRequest userRequest = retrofit.create(IUserRequest.class);
        Observable<ResultResp> authObservable = userRequest.Auth(userId, token, schoolId, schoolPass, realName);
        authObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultRespConsumer("Auth", userResp), Error.getErrorConsumer(userResp));


    }

    public static void GetMyInfo(String userId, String token, IResp<User> userResp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IUserRequest userRequest = retrofit.create(IUserRequest.class);
        Observable<UserResultResp> getObservable = userRequest.GetMyInfo(userId, token);
        getObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new UserConsumer("GetMyInfo", userResp), Error.getErrorConsumer(userResp));


    }

    public static void GetUserInfo(String userId, String token, String id, IResp<User> userResp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IUserRequest userRequest = retrofit.create(IUserRequest.class);
        Observable<UserResultResp> getUserInfoObservable = userRequest.GetUserInfo(userId, token, id);
        getUserInfoObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new UserConsumer("GetUserInfo", userResp), Error.getErrorConsumer(userResp));


    }

    private static class UserConsumer implements Consumer<UserResultResp> {

        private String method;

        private IResp<User> resp;

        private UserConsumer(String methodStr, IResp<User> iResp) {
            this.method = methodStr;
            this.resp = iResp;
        }

        @Override
        public void accept(UserResultResp resonce) {
            Logger.i(method + " sussess.code is %d message is %s", resonce.ResultCode, resonce.message);
            if (Error.SUCCESS != resonce.ResultCode) {
                resp.fail(new ResultResp(resonce.ResultCode, resonce.message));
                return;
            }
            resp.success(resonce.user);
        }
    }
}
