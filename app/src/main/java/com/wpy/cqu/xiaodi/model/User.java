package com.wpy.cqu.xiaodi.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.orhanobut.logger.Logger;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by wangpeiyu on 2018/4/4.
 */

public class User implements Serializable {

    public static final int NORMAL = 0;

    public static final int XIAODIYUAN = 1;

    public static final String[] AuthStatus = {"未实名认证", "已实名认证"};


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

    @SerializedName("img_local_path")
    public String ImgLocalPath;//头像的本地路径

    @SerializedName("gold_money")
    public float GoldMoney;

    @SerializedName("silver_money")
    public float SilverMoney;

    @SerializedName("creditibility")
    public float Creditibility;

    @SerializedName("sign")
    public String Sign;

    @SerializedName("token")
    public String Token;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

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

    public static void deleteUserLocalFile(){
        File userFile = new File(XiaodiApplication.USER_SAVE_FILEPATH);
        if (userFile.exists()) {
            userFile.delete();
        }
    }

    public void saveToLocalFile() {
        File userFile = new File(XiaodiApplication.USER_SAVE_FILEPATH);
        if (userFile.exists()) {
            userFile.delete();
        }
        ObjectOutputStream userOutput = null;
        try {
            userFile.createNewFile();
            userOutput = new ObjectOutputStream(new FileOutputStream(userFile));
            userOutput.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.e(e.getMessage());
        } finally {
            if (null != userOutput) {
                try {
                    userOutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Logger.e(e.getMessage());
                }
            }
        }
    }

    public static User loadFormFile() {
        File userFile = new File(XiaodiApplication.USER_SAVE_FILEPATH);
        if (!userFile.exists()) {
            return null;
        }
        ObjectInputStream userInput = null;
        User user = null;
        try {
            userInput = new ObjectInputStream(new FileInputStream(userFile));
            user = (User) userInput.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.e(e.getMessage());
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Logger.e(e.getMessage());
            return null;
        } finally {
            if (null != userInput) {
                try {
                    userInput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Logger.e(e.getMessage());
                }
            }
        }
        return user;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        String s = gson.toJson(this);
        return s;
    }
}
