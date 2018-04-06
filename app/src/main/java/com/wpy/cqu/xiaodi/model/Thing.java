package com.wpy.cqu.xiaodi.model;

import com.google.gson.Gson;

/**
 * Created by wangpeiyu on 2018/4/6.
 */

public class Thing {

    public int type;

    public String thumbnail;

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
