package com.wpy.cqu.xiaodi.resetpass;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
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
import com.wpy.cqu.xiaodi.net.UserRequest;
import com.wpy.cqu.xiaodi.net.resp.IResp;
import com.wpy.cqu.xiaodi.reward.AcEditReward;
import com.wpy.cqu.xiaodi.util.ToastUtil;

public class AcEditPass extends TopBarAppComptAcitity {

    private EditText metOldPass;

    private EditText metNewPass;

    private EditText metPassAgain;

    private Button mbtnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_edit_password);
        bindView();
        initView();
        bindEvent();
    }

    private void resetPass(View view) {
        String newPass = metNewPass.getText().toString();
        String newPassConfirm = metPassAgain.getText().toString();
        String oldPass = metOldPass.getText().toString();
        if (TextUtils.isEmpty(newPass) || TextUtils.isEmpty(newPassConfirm) || TextUtils.isEmpty(oldPass)) {
            ToastUtil.toast(this, getResources().getString(R.string.password_not_black));
            return;
        }
        if (!newPass.equals(newPassConfirm)) {
            ToastUtil.toast(this, getResources().getString(R.string.twice_pass_not_the_same));
            return;
        }
        String oldPassEncrypt = AESEncrypt.Base64AESEncrypt(oldPass);
        String newPassEncrypt = AESEncrypt.Base64AESEncrypt(newPass);
        UserRequest.UpdatePass(XiaodiApplication.mCurrentUser.Id, XiaodiApplication.mCurrentUser.Token,
                oldPassEncrypt, newPassEncrypt, new IResp<ResultResp>() {
                    @Override
                    public void success(ResultResp object) {
                        ToastUtil.toast(AcEditPass.this, getResources().getString(R.string.update_pass_successful));
                        finish();
                    }

                    @Override
                    public void fail(ResultResp resp) {
                        ToastUtil.toast(AcEditPass.this, resp.message);
                    }
                });
    }

    private void bindView() {
        metOldPass = (EditText) findViewById(R.id.id_ac_editpass_et_oldpass);
        metNewPass = (EditText) findViewById(R.id.id_ac_editpass_et_newpass);
        metPassAgain = (EditText) findViewById(R.id.id_ac_editpass_et_newpassconfirm);
        mbtnReset = (Button) findViewById(R.id.id_ac_btn_reset_pass);

        mtvBack = (TextView) findViewById(R.id.id_top_back_tv);
        mivBack = (ImageView) findViewById(R.id.id_top_back_iv_img);
        mtvContent = (TextView) findViewById(R.id.id_top_tv_content);
    }

    private void initView() {
        mtvBack.setText(getResources().getString(R.string.setting));
        mtvBack.setTextColor(Color.WHITE);
        mivBack.setImageResource(R.drawable.go_back_white);
        mtvContent.setText(getResources().getString(R.string.reset_pass));
    }

    private void bindEvent() {
        mtvBack.setOnClickListener(view -> finish());
        mivBack.setOnClickListener(view -> finish());
        mbtnReset.setOnClickListener(this::resetPass);
    }

}
