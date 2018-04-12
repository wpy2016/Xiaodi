package com.wpy.cqu.xiaodi.net.request;

import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.UserResultResp;
import com.wpy.cqu.xiaodi.sign.SignResp;

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
    Observable<UserResultResp> Register(@Part("phone") RequestBody phone, @Part("pass") RequestBody pass,
                                        @Part("nick_name") RequestBody nickName, @Part MultipartBody.Part img);

    @POST("user/login")
    @FormUrlEncoded
    Observable<UserResultResp> Login(@Field("phone") String phone, @Field("pass") String pass);

    @POST("user/sign")
    @FormUrlEncoded
    Observable<ResultResp> Sign(@Field("user_id") String userid, @Field("token") String token, @Field("day") String day);

    @POST("user/sign/list")
    @FormUrlEncoded
    Observable<SignResp> SignList(@Field("user_id") String userid, @Field("token") String token, @Field("year") String year, @Field("month") String month);

    @POST("user/auth")
    @FormUrlEncoded
    Observable<ResultResp> Auth(@Field("user_id") String userId, @Field("token") String token,
                                @Field("school_id") String schoolId,
                                @Field("school_pass") String schoolPass, @Field("real_name") String realName);

    @POST("user/get")
    @FormUrlEncoded
    Observable<UserResultResp> GetMyInfo(@Field("user_id") String userId, @Field("token") String token);

    @POST("user/get/id")
    @FormUrlEncoded
    Observable<UserResultResp> GetUserInfo(@Field("user_id") String userId, @Field("token") String token, @Field("_id") String id);

    @POST("user/update/pass")
    @FormUrlEncoded
    Observable<ResultResp> UpdatePass(@Field("user_id") String userid, @Field("token") String token, @Field("pass") String newPass, @Field("old_pass") String oldPass);


    @POST("user/update/nickname")
    @FormUrlEncoded
    Observable<ResultResp> UpdateNickName(@Field("user_id") String userid, @Field("token") String token, @Field("nick_name") String nickName);

    @POST("user/update/img")
    @Multipart
    Observable<ResultResp> UpdateImg(@Part("user_id") RequestBody userid, @Part("token") RequestBody token, @Part MultipartBody.Part img);

}
