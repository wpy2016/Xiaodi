package com.wpy.cqu.xiaodi.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wangpeiyu on 2018/4/4.
 */

public class User {

    @SerializedName("_id")
    public String Id;

    @SerializedName("nick_name")
    public String NickName;

    @SerializedName("real_name")
    public String RealName;

    @SerializedName("phone")
    public String Phone;

    @SerializedName("pass")
    public String Pass;

    @SerializedName("user_type")
    public int UserType; // 0表示普通用户，1表示笑递员

    @SerializedName("campus")
    public String Campus;

    @SerializedName("school_id")
    public String SchoolID;

    @SerializedName("img")
    public String ImgUrl;//头像的网络路径

    public String ImgLocalPath;//头像的本地路径

    @SerializedName("gold_money")
    public float GoldMoney;

    @SerializedName("silver_money")
    public float SilverMoney;

    @SerializedName("creditibility")
    public float Creditibility;

    @SerializedName("sign")
    public String Sign;

    public User() {
    }



    public String getId() {
        return Id;
    }

    public String getNickName() {
        return NickName;
    }

    public String getRealName() {
        return RealName;
    }

    public String getPhone() {
        return Phone;
    }

    public String getPass() {
        return Pass;
    }

    public int getUserType() {
        return UserType;
    }

    public String getCampus() {
        return Campus;
    }

    public String getSchoolID() {
        return SchoolID;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public String getImgLocalPath() {
        return ImgLocalPath;
    }

    public float getGoldMoney() {
        return GoldMoney;
    }

    public float getSilverMoney() {
        return SilverMoney;
    }

    public float getCreditibility() {
        return Creditibility;
    }

    public String getSign() {
        return Sign;
    }

    public ResultResp Register() {

        return null;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public void setRealName(String realName) {
        RealName = realName;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setPass(String pass) {
        Pass = pass;
    }

    public void setUserType(int userType) {
        UserType = userType;
    }

    public void setCampus(String campus) {
        Campus = campus;
    }

    public void setSchoolID(String schoolID) {
        SchoolID = schoolID;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public void setImgLocalPath(String imgLocalPath) {
        ImgLocalPath = imgLocalPath;
    }

    public void setGoldMoney(float goldMoney) {
        GoldMoney = goldMoney;
    }

    public void setSilverMoney(float silverMoney) {
        SilverMoney = silverMoney;
    }

    public void setCreditibility(float creditibility) {
        Creditibility = creditibility;
    }

    public void setSign(String sign) {
        Sign = sign;
    }

    public ResultResp Update() {

        return null;
    }
}
