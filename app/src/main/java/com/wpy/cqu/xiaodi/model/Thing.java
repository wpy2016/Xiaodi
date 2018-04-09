package com.wpy.cqu.xiaodi.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.wpy.cqu.xiaodi.R;

import java.io.Serializable;

/**
 * Created by wangpeiyu on 2018/4/6.
 */

public class Thing implements Serializable{

    public static int[] DEFAULT_TYPE_IMG = {R.drawable.good_type_express, R.drawable.good_type_food,
            R.drawable.good_type_paper, R.drawable.good_type_other};

    public static String[] THING_TYPE = {"快递", "餐饮", "纸质", "其他"};

    //类型
    public static final int EXPRESS = 0;
    public static final int FOOD = 1;
    public static final int PAPER = 2;
    public static final int OTHER = 3;

    //重量级
    public static final int IWEIGHT_LIGHT_INT = 0;
    public static final int IWEIGHT_MEDIUM_INT = 1;
    public static final int IWEIGHT_HEAVY_INT = 2;

    @SerializedName("thing_type")
    public int type;

    @SerializedName("thumbnail")
    public String thumbnail;

    @SerializedName("weight")
    public String weight;

    public Thing() {
    }

    public Thing(int type, String thumbnail, String weight) {
        this.type = type;
        this.thumbnail = thumbnail;
        this.weight = weight;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
