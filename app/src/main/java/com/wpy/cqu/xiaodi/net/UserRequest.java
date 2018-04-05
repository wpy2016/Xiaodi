package com.wpy.cqu.xiaodi.net;

import com.orhanobut.logger.Logger;
import com.wpy.cqu.xiaodi.net.request.IUserRequest;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by wangpeiyu on 2018/4/5.
 */

public class UserRequest {

    public static void Register(String phone, String pass, String nickName, String imgPath) {
        MediaType textType = MediaType.parse("text/plain");
        RequestBody phoneBody = RequestBody.create(textType,phone);
        RequestBody passBody = RequestBody.create(textType, pass);
        RequestBody nickNameBody = RequestBody.create(textType,nickName);

        MediaType fileType = MediaType.parse("multipart/form-data");
        File imgFile = new File(imgPath);
        RequestBody imgBody = RequestBody.create(fileType, imgFile);
        MultipartBody.Part imgData = MultipartBody.Part.createFormData("img", "img.png", imgBody);
        Retrofit retrofit = BaseRetrofit.getInstance();
        IUserRequest userRequest = retrofit.create(IUserRequest.class);
        Call<ResponseBody> register = userRequest.Register(phoneBody, passBody, nickNameBody, imgData);
        register.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Logger.i("register success");
                try {
                    String result = response.body().string();
                    Logger.i(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Logger.e("register fail %s",throwable.toString());
            }
        });
    }
}
