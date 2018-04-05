package com.wpy.cqu.xiaodi.model;

/**
 * Created by wangpeiyu on 2018/4/4.
 */

public class User {
    public String Id;
    public String NickName;
    public String RealName;
    public String Phone;
    public String Pass;
    public int UserType; // 0表示普通用户，1表示笑递员
    public String Campus;
    public String SchoolID;
    public String ImgUrl;//头像的网络路径
    public String ImgLocalPath;//头像的本地路径
    public float GoldMoney;
    public float SilverMoney;
    public float Creditibility;
    public String Sign;

    public ResultResp Register() {

        return null;
    }

    public ResultResp Update() {

        return null;
    }

    public static ResultResp isPhoneUsed(String phone) {
        return null;
    }

    public static ResultResp isNickUsed(String nickName) {
        return null;
    }
}
