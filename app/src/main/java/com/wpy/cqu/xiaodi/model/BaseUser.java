package com.wpy.cqu.xiaodi.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by wangpeiyu on 2018/4/6.
 */

public class BaseUser {
    @SerializedName("_id")
    public String Id;

    @SerializedName("nick_name")
    public String NickName;

    @SerializedName("real_name")
    public String RealName;

    @SerializedName("phone")
    public String Phone;

    @SerializedName("user_type")
    public int UserType; // 0表示普通用户，1表示笑递员

    @SerializedName("campus")
    public String Campus;

    @SerializedName("school_id")
    public String SchoolID;

    @SerializedName("img")
    public String ImgUrl;//头像的网络路径

    @SerializedName("creditibility")
    public float Creditibility;

    public BaseUser() {
    }

    public BaseUser(String id, String nickName, String realName, String phone, int userType, String campus, String schoolID, String imgUrl, float creditibility) {
        Id = id;
        NickName = nickName;
        RealName = realName;
        Phone = phone;
        UserType = userType;
        Campus = campus;
        SchoolID = schoolID;
        ImgUrl = imgUrl;
        Creditibility = creditibility;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getRealName() {
        return RealName;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getUserType() {
        return UserType;
    }

    public void setUserType(int userType) {
        UserType = userType;
    }

    public String getCampus() {
        return Campus;
    }

    public void setCampus(String campus) {
        Campus = campus;
    }

    public String getSchoolID() {
        return SchoolID;
    }

    public void setSchoolID(String schoolID) {
        SchoolID = schoolID;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public float getCreditibility() {
        return Creditibility;
    }

    public void setCreditibility(float creditibility) {
        Creditibility = creditibility;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
