package com.wpy.cqu.xiaodi.model;

import android.graphics.Color;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.wpy.cqu.xiaodi.R;

import java.io.Serializable;

/**
 * Created by wangpeiyu on 2018/4/6.
 */

public class Reward implements Serializable {

    public static final int REWARD_STATE_SEND = 0;
    public static final int REWARD_STATE_Carry = 1;
    public static final int REWARD_STATE_ARRIVE = 2;
    public static final int REWARD_STATE_FINISH = 3;

    public static final String[] REWARD_STATUS_STRING = {"未领取", "代送中", "已送达", "已完成"};
    public static final int[] REWARD_STATUS_COLOR_TEXT = {Color.parseColor("#82e100"), Color.parseColor("#ffd700"), Color.parseColor("#1e90ff"), Color.parseColor("#ff4500")};

    @SerializedName("_id")
    public String id;

    @SerializedName("publisher")
    public BaseUser Publisher;

    @SerializedName("state")
    public int state;

    @SerializedName("phone")
    public String phone;

    @SerializedName("xiaodian")
    public int xiaodian;

    @SerializedName("dead_line")
    public String deadline;

    @SerializedName("origin_location")
    public String originLocation;

    @SerializedName("dst_location")
    public String dstLocation;

    @SerializedName("receiver")
    public BaseUser Receiver;

    @SerializedName("publisher_grade")
    public float publisherGrade;

    @SerializedName("receive_grade")
    public float receiveGrade;

    @SerializedName("describe")
    public String describe;

    @SerializedName("thing")
    public Thing thing;

    @SerializedName("create_time")
    public String createTIme;

    public String getCreateTIme() {
        return createTIme;
    }

    public void setCreateTIme(String createTIme) {
        this.createTIme = createTIme;
    }

    public Reward() {
    }

    public Reward(String id, BaseUser publisher, int state, String phone, int xiaodian,
                  String deadline, String originLocation, String dstLocation, BaseUser receiver,
                  float publisherGrade, float receiveGrade, String describe,
                  Thing thing, String createTIme) {
        this.id = id;
        Publisher = publisher;
        this.state = state;
        this.phone = phone;
        this.xiaodian = xiaodian;
        this.deadline = deadline;
        this.originLocation = originLocation;
        this.dstLocation = dstLocation;
        Receiver = receiver;
        this.publisherGrade = publisherGrade;
        this.receiveGrade = receiveGrade;
        this.describe = describe;
        this.thing = thing;
        this.createTIme = createTIme;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BaseUser getPublisher() {
        return Publisher;
    }

    public void setPublisher(BaseUser publisher) {
        Publisher = publisher;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getXiaodian() {
        return xiaodian;
    }

    public void setXiaodian(int xiaodian) {
        this.xiaodian = xiaodian;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getOriginLocation() {
        return originLocation;
    }

    public void setOriginLocation(String originLocation) {
        this.originLocation = originLocation;
    }

    public String getDstLocation() {
        return dstLocation;
    }

    public void setDstLocation(String dstLocation) {
        this.dstLocation = dstLocation;
    }

    public BaseUser getReceiver() {
        return Receiver;
    }

    public void setReceiver(BaseUser receiver) {
        Receiver = receiver;
    }

    public float getPublisherGrade() {
        return publisherGrade;
    }

    public void setPublisherGrade(float publisherGrade) {
        this.publisherGrade = publisherGrade;
    }

    public float getReceiveGrade() {
        return receiveGrade;
    }

    public void setReceiveGrade(float receiveGrade) {
        this.receiveGrade = receiveGrade;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Thing getThing() {
        return thing;
    }

    public void setThing(Thing thing) {
        this.thing = thing;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
