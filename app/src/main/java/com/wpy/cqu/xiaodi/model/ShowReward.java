package com.wpy.cqu.xiaodi.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wangpeiyu on 2018/4/7.
 */

public class ShowReward {

    @SerializedName("status_code")
    public int ResultCode;//200表示成功


    @SerializedName("status_msg")
    public String message;//消息

    @SerializedName("rewards")
    public List<Reward> rewards;

    public ShowReward() {
    }

    public ShowReward(int resultCode, String message, List<Reward> rewards) {
        ResultCode = resultCode;
        this.message = message;
        this.rewards = rewards;
    }

    public int getResultCode() {
        return ResultCode;
    }

    public void setResultCode(int resultCode) {
        ResultCode = resultCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public void setRewards(List<Reward> rewards) {
        this.rewards = rewards;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
