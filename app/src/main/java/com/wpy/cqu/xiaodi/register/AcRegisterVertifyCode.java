package com.wpy.cqu.xiaodi.register;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.wpy.cqu.vertifycode.base.IVertifyCode;
import com.wpy.cqu.vertifycode.factory.VertifyCodeFactory;
import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.base_activity.CheckPermissionsActivity;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;

public class AcRegisterVertifyCode extends TopBarAppComptAcitity {

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
            Toast.makeText(this, getResources().getString(R.string.phone_no_right), Toast.LENGTH_LONG).show();
            return;
        }

        vertifyCode.sendCode(phone, this, metVertify, ex -> {
            Logger.i("sendcode return exception=%s", ex);
            if (null == ex) {
                mbtnGetVertify.setEnabled(false);
                Toast.makeText(this, getResources().getString(R.string.vertify_have_send), Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(this, getResources().getString(R.string.verify_send_fail), Toast.LENGTH_LONG).show();
        });
    }

    private void next(View v) {
        toNext(AcRegister.class);
        // TODO: 2018/3/29 去掉注释
        /*
        String phone = metPhone.getText().toString();
        String code = metVertify.getText().toString();
        Logger.i("phone=%s,code=%s", phone, code);
        if (!phone.matches("1[0-9]{10}")) {
            Toast.makeText(this, getResources().getString(R.string.phone_no_right), Toast.LENGTH_LONG).show();
            return;
        }
        vertifyCode.vertifyCode(phone, code, this, ex -> {
            if (null == ex) {
                toNext(AcRegister.class);
                return;
            }
            Toast.makeText(this, getResources().getString(R.string.vertify_fail), Toast.LENGTH_LONG).show();
        });
        */
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
        mivBack.setOnClickListener(v->finish());
        mtvBack.setOnClickListener(v->finish());
        mbtnGetVertify.setOnClickListener(this::sendCode);
        mbtnNext.setOnClickListener(this::next);
    }

    private void toNext(Class<?> next) {
        Intent intent = new Intent(this, next);
        startActivity(intent);
        finish();
    }
}
