package com.wpy.cqu.xiaodi.auth;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;
import com.wpy.cqu.xiaodi.encrypt.AESEncrypt;
import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.User;
import com.wpy.cqu.xiaodi.net.UserRequest;
import com.wpy.cqu.xiaodi.net.resp.IResp;
import com.wpy.cqu.xiaodi.util.ToastUtil;

public class AcAuth extends TopBarAppComptAcitity {

    private TextView mtvSchool;

    private EditText metRealName;

    private EditText metSchoolId;

    private EditText metSchoolPass;

    private TextView mtvChangeUserType;

    private Button mbtnAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_auth);
        bindView();
        initView();
        bindEvent();
    }

    private void changeUserType(View view) {

    }

    private void auth(View view) {
        String realName = metRealName.getText().toString();
        String schoolId = metSchoolId.getText().toString();
        String schoolPass = metSchoolPass.getText().toString();
        boolean isAvaliable = validate(realName, schoolId, schoolPass);
        if (!isAvaliable) {
            return;
        }
        String encryptPass = AESEncrypt.Base64AESEncrypt(schoolPass);
        UserRequest.Auth(XiaodiApplication.mCurrentUser.Id,
                XiaodiApplication.mCurrentUser.Token,
                schoolId, encryptPass, realName, new IResp<ResultResp>() {
                    @Override
                    public void success(ResultResp object) {
                        authSuccess();
                    }

                    @Override
                    public void fail(ResultResp resp) {
                        ToastUtil.toast(AcAuth.this, resp.message);
                    }
                });
    }

    private void authSuccess() {
        ToastUtil.toast(AcAuth.this, getResources().getString(R.string.identificate_success));
        UserRequest.updateUserInfo();
        finish();
    }


    private boolean validate(String realName, String schoolId, String schoolPass) {
        if (TextUtils.isEmpty(realName)) {
            ToastUtil.toast(this, getResources().getString(R.string.write_real_name));
            return false;
        }

        if (TextUtils.isEmpty(schoolId)) {
            ToastUtil.toast(this, getResources().getString(R.string.write_school_id));
            return false;
        }

        if (TextUtils.isEmpty(schoolPass)) {
            ToastUtil.toast(this, getResources().getString(R.string.write_school_pass));
            return false;
        }

        return true;
    }

    private void bindView() {
        mtvSchool = (TextView) findViewById(R.id.id_ac_statu_identificate_tv_school);
        metRealName = (EditText) findViewById(R.id.id_ac_statu_identificate_et_name);
        metSchoolId = (EditText) findViewById(R.id.id_ac_statu_identificate_et_school_number);
        mtvChangeUserType = (TextView) findViewById(R.id.id_ac_statu_identificate_tv_role);
        metSchoolPass = (EditText) findViewById(R.id.id_ac_statu_identificate_et_school_pass);
        mbtnAuth = (Button) findViewById(R.id.id_ac_forget_pass_btn_confirm);

        mtvBack = (TextView) findViewById(R.id.id_top_back_tv);
        mivBack = (ImageView) findViewById(R.id.id_top_back_iv_img);
        mtvContent = (TextView) findViewById(R.id.id_top_tv_content);
    }

    private void initView() {
        mtvBack.setText(getResources().getString(R.string.me));
        mtvBack.setTextColor(Color.WHITE);
        mivBack.setImageResource(R.drawable.go_back_white);
        mtvContent.setText(getResources().getString(R.string.xiaodi_attestation));

        mtvSchool.setText(getResources().getString(R.string.chongqinguniversity));
    }

    private void bindEvent() {
        mbtnAuth.setOnClickListener(this::auth);
        mtvChangeUserType.setOnClickListener(this::changeUserType);

        mtvBack.setOnClickListener(view -> finish());
        mivBack.setOnClickListener(view -> finish());
    }
}
