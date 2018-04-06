package com.wpy.cqu.xiaodi.net.request;

import com.wpy.cqu.xiaodi.model.UserResultResp;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by wangpeiyu on 2018/4/5.
 */

public interface IUserRequest {

    @POST("user/register")
    @Multipart
    Observable<UserResultResp> Register(@Part("phone") RequestBody phone, @Part("pass")RequestBody pass,
                                        @Part("nick_name") RequestBody nickName, @Part MultipartBody.Part img);

    @POST("user/login")
    @FormUrlEncoded
    Observable<UserResultResp> Login(@Field("phone") String phone,@Field("pass") String pass);

}