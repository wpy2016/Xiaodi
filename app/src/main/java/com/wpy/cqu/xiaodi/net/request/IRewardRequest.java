package com.wpy.cqu.xiaodi.net.request;

import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.ShowReward;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by wangpeiyu on 2018/4/6.
 */

public interface IRewardRequest {
    @POST("/reward/send")
    @Multipart
    Observable<ResultResp> SendWithThumbnail(@Part("user_id") RequestBody userId, @Part("token") RequestBody token,
                                             @Part("phone") RequestBody phone, @Part("xiaodian") RequestBody xiaodian,
                                             @Part("dead_line") RequestBody deadLine, @Part("origin_location") RequestBody originLocation,
                                             @Part("dst_location") RequestBody dstLocation, @Part("describe") RequestBody describe,
                                             @Part("thing_type") RequestBody ThingType, @Part("weight") RequestBody Weight,
                                             @Part MultipartBody.Part thumbnail);

    @POST("/reward/send")
    @FormUrlEncoded
    Observable<ResultResp> SendWithOutThumbnail(@Field("user_id") String userId, @Field("token") String token,
                                                @Field("phone") String phone, @Field("xiaodian") int xiaodian,
                                                @Field("dead_line") String deadLine, @Field("origin_location") String originLocation,
                                                @Field("dst_location") String dstLocation, @Field("describe") String describe,
                                                @Field("thing_type") int ThingType, @Field("weight") String Weight);

    @POST("/reward/show")
    @FormUrlEncoded
    Observable<ShowReward> ShowRewards(@Field("pages") int pages,@Field("user_id") String userId,@Field("token") String token);

    @POST("/reward/carry")
    @FormUrlEncoded
    Observable<ResultResp> CarryReward(@Field("_id") String id,@Field("user_id") String userId,@Field("token") String token);
}
