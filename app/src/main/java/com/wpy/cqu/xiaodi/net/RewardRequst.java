package com.wpy.cqu.xiaodi.net;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.Reward;
import com.wpy.cqu.xiaodi.model.ShowReward;
import com.wpy.cqu.xiaodi.net.request.IRewardRequest;
import com.wpy.cqu.xiaodi.net.resp.Error;
import com.wpy.cqu.xiaodi.net.resp.IResp;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

/**
 * Created by wangpeiyu on 2018/4/6.
 */

public class RewardRequst {

    public static void CarryRewards(String id, String userId, String token, IResp<ResultResp> resp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IRewardRequest iRewardRequest = retrofit.create(IRewardRequest.class);
        Observable<ResultResp> carryRewardObservable = iRewardRequest.CarryReward(id, userId, token);
        carryRewardObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responce -> {
                    Logger.i("carry rewards request sussess.code is %d message is %s", responce.ResultCode, responce.message);
                    if (Error.SUCCESS != responce.ResultCode) {
                        resp.fail(new ResultResp(responce.ResultCode, responce.message));
                        return;
                    }
                    resp.success(responce);
                }, Error.getErrorConsumer(resp));
    }

    public static void ShowRewards(int pages, String userId, String token, IResp<List<Reward>> resp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IRewardRequest iRewardRequest = retrofit.create(IRewardRequest.class);
        Observable<ShowReward> showRewardObservable = iRewardRequest.ShowRewards(pages, userId, token);
        showRewardObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responce -> {
                    Logger.i("show rewards request sussess.code is %d message is %s", responce.ResultCode, responce.message);
                    if (Error.SUCCESS != responce.ResultCode) {
                        resp.fail(new ResultResp(responce.ResultCode, responce.message));
                        return;
                    }
                    resp.success(responce.rewards);
                }, Error.getErrorConsumer(resp));
    }

    public static void SendReward(Reward reward, String userId, String token, IResp<ResultResp> resp) {
        if (TextUtils.isEmpty(reward.thing.thumbnail)) {
            SendRewardWithOutThumbnail(reward, userId, token, resp);
            return;
        }
        SendRewardWithThumbnail(reward, userId, token, resp);
    }

    private static void SendRewardWithThumbnail(Reward reward, String userId, String token, IResp<ResultResp> resp) {
        MediaType textType = MediaType.parse("text/plain");
        RequestBody userIdBody = RequestBody.create(textType, userId);
        RequestBody tokenBody = RequestBody.create(textType, token);
        RequestBody phoneBody = RequestBody.create(textType, reward.phone);
        RequestBody xiaodianBody = RequestBody.create(textType, reward.xiaodian + "");
        RequestBody deadlineBody = RequestBody.create(textType, reward.deadline);
        RequestBody originLocationBody = RequestBody.create(textType, reward.originLocation);
        RequestBody dstLocationBody = RequestBody.create(textType, reward.dstLocation);
        RequestBody decribeBody = RequestBody.create(textType, reward.describe);
        RequestBody thingTypeBody = RequestBody.create(textType, reward.thing.type + "");
        RequestBody weightBody = RequestBody.create(textType, reward.thing.weight);

        MediaType fileType = MediaType.parse("multipart/form-data");
        File imgFile = new File(reward.thing.thumbnail);
        RequestBody imgBody = RequestBody.create(fileType, imgFile);
        MultipartBody.Part imgData = MultipartBody.Part.createFormData("thumbnail", "thumbnail.png", imgBody);
        Retrofit retrofit = BaseRetrofit.getInstance();
        IRewardRequest iRewardRequest = retrofit.create(IRewardRequest.class);
        Observable<ResultResp> sendRequest = iRewardRequest.SendWithThumbnail(userIdBody, tokenBody,
                phoneBody, xiaodianBody, deadlineBody, originLocationBody, dstLocationBody, decribeBody,
                thingTypeBody, weightBody, imgData);
        sendRequest.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responce -> {
                    Logger.i("send request sussess.code is %d message is %s", responce.ResultCode, responce.message);
                    if (Error.SUCCESS != responce.ResultCode) {
                        resp.fail(new ResultResp(responce.ResultCode, responce.message));
                        return;
                    }
                    resp.success(responce);
                }, Error.getErrorConsumer(resp));
    }

    private static void SendRewardWithOutThumbnail(Reward reward, String userId, String token, IResp<ResultResp> resp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IRewardRequest iRewardRequest = retrofit.create(IRewardRequest.class);
        Observable<ResultResp> sendRequest = iRewardRequest.SendWithOutThumbnail(userId, token,
                reward.phone, reward.xiaodian, reward.deadline, reward.originLocation, reward.dstLocation,
                reward.describe, reward.thing.type, reward.thing.weight);
        sendRequest.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responce -> {
                    Logger.i("send request sussess.code is %d message is %s", responce.ResultCode, responce.message);
                    if (Error.SUCCESS != responce.ResultCode) {
                        resp.fail(new ResultResp(responce.ResultCode, responce.message));
                        return;
                    }
                    resp.success(responce);
                }, Error.getErrorConsumer(resp));
    }


}
