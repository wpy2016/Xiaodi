package com.wpy.cqu.xiaodi.net;

import com.orhanobut.logger.Logger;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.model.OneTokenResp;
import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.User;
import com.wpy.cqu.xiaodi.model.UserResultResp;
import com.wpy.cqu.xiaodi.net.request.IUserRequest;
import com.wpy.cqu.xiaodi.net.resp.Error;
import com.wpy.cqu.xiaodi.net.resp.IResp;
import com.wpy.cqu.xiaodi.net.resp.ResultRespConsumer;
import com.wpy.cqu.xiaodi.sign.SignResp;

import java.io.File;
import java.util.concurrent.TimeUnit;

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

    public static void Sign(String userId, String token, String day, IResp<ResultResp> resp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IUserRequest userRequest = retrofit.create(IUserRequest.class);
        Observable<ResultResp> sign = userRequest.Sign(userId, token, day);
        sign.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultRespConsumer("Sign", resp), Error.getErrorConsumer(resp));
    }

    public static void GetSignList(String userId, String token, String year, String month, IResp<SignResp> resp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IUserRequest userRequest = retrofit.create(IUserRequest.class);
        Observable<SignResp> signList = userRequest.SignList(userId, token, year, month);
        signList.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SignResp>() {
                    @Override
                    public void accept(SignResp signResp) throws Exception {
                        if (Error.SUCCESS != signResp.ResultCode) {
                            resp.fail(new ResultResp(signResp.ResultCode, signResp.Message));
                            return;
                        }
                        resp.success(signResp);
                    }
                }, Error.getErrorConsumer(resp));
    }

    public static void Auth(String userId, String token, String schoolId, String schoolPass, String realName, IResp<ResultResp> userResp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IUserRequest userRequest = retrofit.create(IUserRequest.class);
        Observable<ResultResp> authObservable = userRequest.Auth(userId, token, schoolId, schoolPass, realName);
        authObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultRespConsumer("Auth", userResp), Error.getErrorConsumer(userResp));
    }

    public static void UpdatePass(String userId, String token, String oldPass, String newPass, IResp<ResultResp> userResp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IUserRequest userRequest = retrofit.create(IUserRequest.class);
        Observable<ResultResp> updatePassObservable = userRequest.UpdatePass(userId, token, newPass, oldPass);
        updatePassObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultRespConsumer("UpdatePass", userResp), Error.getErrorConsumer(userResp));
    }

    public static void UpdateNickname(String userId, String token, String nickName, IResp<ResultResp> userResp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IUserRequest userRequest = retrofit.create(IUserRequest.class);
        Observable<ResultResp> updateNicknameObservable = userRequest.UpdateNickName(userId, token, nickName);
        updateNicknameObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultRespConsumer("UpdateNickname", userResp), Error.getErrorConsumer(userResp));
    }

    public static void UpdateImg(String userId, String token, String imgPath, IResp<ResultResp> userResp) {
        MediaType textType = MediaType.parse("text/plain");
        RequestBody userIdBody = RequestBody.create(textType, userId);
        RequestBody tokenBody = RequestBody.create(textType, token);

        MediaType fileType = MediaType.parse("multipart/form-data");
        File imgFile = new File(imgPath);
        RequestBody imgBody = RequestBody.create(fileType, imgFile);
        MultipartBody.Part imgData = MultipartBody.Part.createFormData("img", "img.png", imgBody);

        Retrofit retrofit = BaseRetrofit.getInstance();
        IUserRequest userRequest = retrofit.create(IUserRequest.class);
        Observable<ResultResp> updateImgObservable = userRequest.UpdateImg(userIdBody, tokenBody, imgData);
        updateImgObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultRespConsumer("UpdateImg", userResp), Error.getErrorConsumer(userResp));
    }


    public static void GetMyInfo(String userId, String token, IResp<User> userResp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IUserRequest userRequest = retrofit.create(IUserRequest.class);
        Observable<UserResultResp> getObservable = userRequest.GetMyInfo(userId, token);
        getObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new UserConsumer("GetMyInfo", userResp), Error.getErrorConsumer(userResp));
    }

    public static void GetOneToken(String phone, IResp<String> resp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IUserRequest userRequest = retrofit.create(IUserRequest.class);
        Observable<OneTokenResp> oneTokenObservable = userRequest.GetOneToken(phone);
        oneTokenObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(oneTokenResp -> {
                    if (Error.SUCCESS != oneTokenResp.ResultCode) {
                        resp.fail(new ResultResp(oneTokenResp.ResultCode, oneTokenResp.message));
                        return;
                    }
                    resp.success(oneTokenResp.token);
                }, Error.getErrorConsumer(resp));
    }

    public static void AuthOneTokenAndUpdatePass(String phone, String token, String newPass, IResp<ResultResp> resp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IUserRequest userRequest = retrofit.create(IUserRequest.class);
        Observable<ResultResp> authOneTokenObservable = userRequest.AuthOneTokenAndUpdatePass(phone, token, newPass);
        authOneTokenObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultRespConsumer("AuthOneTokenAndUpdatePass", resp), Error.getErrorConsumer(resp));

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

    /**
     * 每隔60s更新用户数据
     */
    public static void intervalUpdateUserInfo() {
        Observable.interval(0, 30, TimeUnit.SECONDS)
                .doOnNext(l -> {
                    if (null == XiaodiApplication.mCurrentUser) {
                        return;
                    }
                    updateUserInfo();
                })
                .subscribeOn(Schedulers.computation())
                .subscribe();
    }

    public static void updateUserInfo() {
        UserRequest.GetMyInfo(XiaodiApplication.mCurrentUser.Id,
                XiaodiApplication.mCurrentUser.Token,
                new IResp<User>() {
                    @Override
                    public void success(User user) {
                        XiaodiApplication.mCurrentUser = user;
                    }

                    @Override
                    public void fail(ResultResp resp) {

                    }
                });
    }
}
