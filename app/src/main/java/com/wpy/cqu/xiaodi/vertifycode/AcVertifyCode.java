package com.wpy.cqu.xiaodi.vertifycode;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wpy.cqu.vertifycode.base.IVertifyCode;
import com.wpy.cqu.vertifycode.factory.VertifyCodeFactory;
import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.base_activity.CheckPermissionsActivity;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;
import com.wpy.cqu.xiaodi.register.AcRegister;
import com.wpy.cqu.xiaodi.resetpass.AcResetPass;
import com.wpy.cqu.xiaodi.util.ToastUtil;

public class AcVertifyCode extends TopBarAppComptAcitity {

    private static final int STATUS_BAR_COLOR = Color.parseColor("#00dec9");

    private final static String[] PERMISSION_ARRAY = {
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS,
            Manifest.permission.GET_ACCOUNTS,
    };

    private EditText metPhone;

    private EditText metVertify;

    private Button mbtnGetVertify;

    private Button mbtnNext;

    private IVertifyCode vertifyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR, STATUS_BAR_COLOR);
        bundle.putStringArray(CheckPermissionsActivity.PEMISSION, PERMISSION_ARRAY);
        super.onCreate(bundle);
        setContentView(R.layout.ac_register_vertiry_code);
        bindView();
        initView();
        bindEvent();
        vertifyCode = VertifyCodeFactory.getInstance(VertifyCodeFactory.VertifyCodeEnum.BMOB);
    }

    private void sendCode(View v) {
        String phone = metPhone.getText().toString();
        Logger.i("phone=%s", phone);
        if (!phone.matches("1[0-9]{10}")) {
            ToastUtil.toast(this, getResources().getString(R.string.phone_no_right));
            return;
        }

        vertifyCode.sendCode(phone, this, metVertify, ex -> {
            Logger.i("sendcode return exception=%s", ex);
            if (null == ex) {
                mbtnGetVertify.setEnabled(false);
                ToastUtil.toast(this, getResources().getString(R.string.vertify_have_send));
                return;
            }
            ToastUtil.toast(this, getResources().getString(R.string.verify_send_fail));
        });
    }

    private void next(View v) {
        String phone = metPhone.getText().toString();
        String code = metVertify.getText().toString();
        Logger.i("phone=%s,code=%s", phone, code);
        if (!phone.matches("1[0-9]{10}")) {
            ToastUtil.toast(this,getResources().getString(R.string.phone_no_right));
            return;
        }
        vertifyCode.vertifyCode(phone, code, this, ex -> {
            if (null == ex) {
                toNext();
                return;
            }
            ToastUtil.toast(this,getResources().getString(R.string.vertify_fail));
        });

    }

    private void bindView() {
        mivBack = (ImageView) findViewById(R.id.id_top_back_iv_img);
        mtvBack = (TextView) findViewById(R.id.id_top_back_tv);
        mtvContent = (TextView) findViewById(R.id.id_top_tv_content);
        mrlTop = (RelativeLayout) findViewById(R.id.id_top_rl);
        metPhone = (EditText) findViewById(R.id.id_ac_register_et_phone);
        mbtnGetVertify = (Button) findViewById(R.id.id_ac_register_btn_get_vertify);
        metVertify = (EditText) findViewById(R.id.id_ac_register_et_vertify);
        mbtnNext = (Button) findViewById(R.id.id_ac_register_btn_next);
    }

    private void initView() {
        mivBack.setImageResource(R.drawable.go_back_white);
        mtvBack.setTextColor(Color.WHITE);
        mtvBack.setText(getResources().getString(R.string.login));
        mtvContent.setText(getResources().getString(R.string.vertify_phone));
    }

    private void bindEvent() {
        mivBack.setOnClickListener(v -> finish());
        mtvBack.setOnClickListener(v -> finish());
        mbtnGetVertify.setOnClickListener(this::sendCode);
        mbtnNext.setOnClickListener(this::next);
    }

    private void toNext() {
        Intent intent;
        String tag = getIntent().getStringExtra("next");
        if (AcRegister.TAG.equals(tag)) {
            intent = new Intent(this, AcRegister.class);
        } else if (AcResetPass.TAG.equals(tag)) {
            intent = new Intent(this, AcResetPass.class);
        } else {
            Logger.i("验证手机号界面，无法定位要跳转到哪个界面");
            ToastUtil.toast(this,getResources().getString(R.string.tell_me_to_where));
            return;
        }
        startActivity(intent);
        finish();
    }
}
