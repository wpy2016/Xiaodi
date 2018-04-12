package com.wpy.cqu.xiaodi.net;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.Reward;
import com.wpy.cqu.xiaodi.model.ShowReward;
import com.wpy.cqu.xiaodi.net.request.IRewardRequest;
import com.wpy.cqu.xiaodi.net.resp.Error;
import com.wpy.cqu.xiaodi.net.resp.IResp;
import com.wpy.cqu.xiaodi.net.resp.ResultRespConsumer;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

/**
 * Created by wangpeiyu on 2018/4/6.
 */

public class RewardRequst {

    public static void CarryRewards(String rewardId, String userId, String token, IResp<ResultResp> resp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IRewardRequest iRewardRequest = retrofit.create(IRewardRequest.class);
        Observable<ResultResp> carryRewardObservable = iRewardRequest.CarryReward(rewardId, userId, token);
        carryRewardObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultRespConsumer("CarryRewards", resp), Error.getErrorConsumer(resp));
    }

    public static void DeliveryRewards(String rewardId, String userId, String token, IResp<ResultResp> resp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IRewardRequest iRewardRequest = retrofit.create(IRewardRequest.class);
        Observable<ResultResp> deliveryRewardObservable = iRewardRequest.DeliveryReward(rewardId, userId, token);
        deliveryRewardObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultRespConsumer("DeliveryRewards", resp), Error.getErrorConsumer(resp));
    }


    public static void FinishRewards(String rewardId, String userId, String token, IResp<ResultResp> resp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IRewardRequest iRewardRequest = retrofit.create(IRewardRequest.class);
        Observable<ResultResp> finishRewardObservable = iRewardRequest.FinishReward(rewardId, userId, token);
        finishRewardObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultRespConsumer("DeliveryRewards", resp), Error.getErrorConsumer(resp));
    }

    public static void ShowRewardsMyFinish(String userId, String token, IResp<List<Reward>> resp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IRewardRequest iRewardRequest = retrofit.create(IRewardRequest.class);
        Observable<ShowReward> finishRewardObservable = iRewardRequest.ShowRewardsMyFinish(userId, token);
        finishRewardObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ShowRewardConsumer("ShowRewardsMyFinish", resp), Error.getErrorConsumer(resp));
    }

    public static void EvaluateRewards(String rewardId, float evaluate, String userId, String token, IResp<ResultResp> resp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IRewardRequest iRewardRequest = retrofit.create(IRewardRequest.class);
        Observable<ResultResp> evaluateRewardObservable = iRewardRequest.EvaluateReward(rewardId, evaluate, userId, token);
        evaluateRewardObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultRespConsumer("DeliveryRewards", resp), Error.getErrorConsumer(resp));
    }

    public static void ShowRewardsSortXiaodian(int pages, String userId, String token, IResp<List<Reward>> resp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IRewardRequest iRewardRequest = retrofit.create(IRewardRequest.class);
        Observable<ShowReward> showRewardSortXiaodianObservable = iRewardRequest.ShowRewardsSortXiaodian(pages, userId, token);
        showRewardSortXiaodianObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ShowRewardConsumer("ShowRewardsSortXiaodian", resp), Error.getErrorConsumer(resp));
    }

    public static void ShowRewardsKeyword(int pages, String keyword, String userId, String token, IResp<List<Reward>> resp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IRewardRequest iRewardRequest = retrofit.create(IRewardRequest.class);
        Observable<ShowReward> showRewardKeywordObservable = iRewardRequest.ShowRewardsKeyword(pages, keyword, userId, token);
        showRewardKeywordObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ShowRewardConsumer("ShowRewardsKeyword", resp), Error.getErrorConsumer(resp));
    }

    public static void ShowRewardsMySend(String userId, String token, IResp<List<Reward>> resp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IRewardRequest iRewardRequest = retrofit.create(IRewardRequest.class);
        Observable<ShowReward> showRewardMysend = iRewardRequest.ShowRewardsMySend(userId, token);
        showRewardMysend.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ShowRewardConsumer("ShowRewardsMySend", resp), Error.getErrorConsumer(resp));
    }

    public static void ShowRewardsOurNotFinish(String userId, String receiveId, String token, IResp<List<Reward>> resp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IRewardRequest iRewardRequest = retrofit.create(IRewardRequest.class);
        Observable<ShowReward> showRewardOur = iRewardRequest.ShowRewardsOurNotFinish(userId, receiveId, token);
        showRewardOur.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ShowRewardConsumer("ShowRewardsMySend", resp), Error.getErrorConsumer(resp));
    }

    public static void ShowRewardsMyCarry(String userId, String token, IResp<List<Reward>> resp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IRewardRequest iRewardRequest = retrofit.create(IRewardRequest.class);
        Observable<ShowReward> showRewardMyCarry = iRewardRequest.ShowRewardsMyCarry(userId, token);
        showRewardMyCarry.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ShowRewardConsumer("ShowRewardsMyCarry", resp), Error.getErrorConsumer(resp));
    }

    public static void ShowRewards(int pages, String userId, String token, IResp<List<Reward>> resp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IRewardRequest iRewardRequest = retrofit.create(IRewardRequest.class);
        Observable<ShowReward> showRewardObservable = iRewardRequest.ShowRewards(pages, userId, token);
        showRewardObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ShowRewardConsumer("ShowRewards", resp), Error.getErrorConsumer(resp));
    }

    public static void DeleteRewards(String rewardId, String userId, String token, IResp<ResultResp> resp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IRewardRequest iRewardRequest = retrofit.create(IRewardRequest.class);
        Observable<ResultResp> deleteRewardObservable = iRewardRequest.DeleteReward(rewardId, userId, token);
        deleteRewardObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultRespConsumer("DeleteRewards", resp), Error.getErrorConsumer(resp));
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
                .subscribe(new ResultRespConsumer("SendRewardWithThumbnail", resp), Error.getErrorConsumer(resp));
    }

    private static void SendRewardWithOutThumbnail(Reward reward, String userId, String token, IResp<ResultResp> resp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IRewardRequest iRewardRequest = retrofit.create(IRewardRequest.class);
        Observable<ResultResp> sendRequest = iRewardRequest.SendWithOutThumbnail(userId, token,
                reward.phone, reward.xiaodian, reward.deadline, reward.originLocation, reward.dstLocation,
                reward.describe, reward.thing.type, reward.thing.weight);
        sendRequest.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultRespConsumer("SendRewardWithOutThumbnail", resp), Error.getErrorConsumer(resp));
    }

    public static void UpdateReward(Reward reward, String userId, String token, IResp<ResultResp> resp) {
        if (TextUtils.isEmpty(reward.thing.thumbnail)) {
            UpdateRewardWithOutThumbnail(reward, userId, token, resp);
            return;
        }
        UpdateRewardWithThumbnail(reward, userId, token, resp);
    }

    private static void UpdateRewardWithThumbnail(Reward reward, String userId, String token, IResp<ResultResp> resp) {
        MediaType textType = MediaType.parse("text/plain");
        RequestBody rewardIdBody = RequestBody.create(textType, reward.id);
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
        Observable<ResultResp> updateRequest = iRewardRequest.UpdateWithThumbnail(rewardIdBody, userIdBody, tokenBody,
                phoneBody, xiaodianBody, deadlineBody, originLocationBody, dstLocationBody, decribeBody,
                thingTypeBody, weightBody, imgData);
        updateRequest.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultRespConsumer("UpdateRewardWithThumbnail", resp), Error.getErrorConsumer(resp));
    }

    private static void UpdateRewardWithOutThumbnail(Reward reward, String userId, String token, IResp<ResultResp> resp) {
        Retrofit retrofit = BaseRetrofit.getInstance();
        IRewardRequest iRewardRequest = retrofit.create(IRewardRequest.class);
        Observable<ResultResp> updateRequest = iRewardRequest.UpdateWithOutThumbnail(reward.id, userId, token,
                reward.phone, reward.xiaodian, reward.deadline, reward.originLocation, reward.dstLocation,
                reward.describe, reward.thing.type, reward.thing.weight);
        updateRequest.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResultRespConsumer("UpdateRewardWithOutThumbnail", resp), Error.getErrorConsumer(resp));
    }

    private static class ShowRewardConsumer implements Consumer<ShowReward> {

        private String method;

        private IResp<List<Reward>> resp;

        private ShowRewardConsumer(String methodStr, IResp<List<Reward>> iResp) {
            this.method = methodStr;
            this.resp = iResp;
        }

        @Override
        public void accept(ShowReward showReward) {
            Logger.i(method + " sussess.code is %d message is %s", showReward.ResultCode, showReward.message);
            if (Error.SUCCESS != showReward.ResultCode) {
                resp.fail(new ResultResp(showReward.ResultCode, showReward.message));
                return;
            }
            resp.success(showReward.rewards);
        }
    }

}
