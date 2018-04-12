package com.wpy.cqu.xiaodi.resetpass;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.base_activity.CheckPermissionsActivity;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;

public class AcResetPass extends TopBarAppComptAcitity {

    private static final int STATUS_BAR_COLOR = Color.parseColor("#00dec9");

    public static final String TAG = AcResetPass.class.getName();

    private EditText metNewPass;

    private ImageView mivSee;

    private Button mbtnReset;

    private boolean mdisplayPwd = false;

    private static final String[] PERMISSION = {
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.GET_ACCOUNTS,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR, STATUS_BAR_COLOR);
        bundle.putStringArray(CheckPermissionsActivity.PEMISSION, PERMISSION);
        super.onCreate(bundle);
        setContentView(R.layout.ac_reset_pass);

        bindView();
        initView();
        bindEvent();
    }

    private void resetPass(View view) {
        //此处的更新密码是没有userId的，短信验证更改密码
    }

    private void bindEvent() {
        mivBack.setOnClickListener(v -> finish());
        mtvBack.setOnClickListener(v -> finish());
        mbtnReset.setOnClickListener(this::resetPass);
        mivSee.setOnClickListener(v -> {
            if (!mdisplayPwd) {
                mdisplayPwd = true;
                mivSee.setImageResource(R.drawable.eye);
                metNewPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                mdisplayPwd = false;
                mivSee.setImageResource(R.drawable.eye_close);
                metNewPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
    }

    private void initView() {
        mivBack.setImageResource(R.drawable.go_back_white);
        mtvBack.setTextColor(Color.WHITE);
        mtvBack.setText(getResources().getString(R.string.login));
        mtvContent.setText(getResources().getString(R.string.reset_pass));
    }


    private void bindView() {
        mivBack = (ImageView) findViewById(R.id.id_top_back_iv_img);
        mtvBack = (TextView) findViewById(R.id.id_top_back_tv);
        mtvContent = (TextView) findViewById(R.id.id_top_tv_content);
        mrlTop = (RelativeLayout) findViewById(R.id.id_top_rl);

        metNewPass = (EditText) findViewById(R.id.id_ac_restpass_et_pass);
        mivSee = (ImageView) findViewById(R.id.id_ac_resetpass_iv_pwd_display);
        mbtnReset = (Button) findViewById(R.id.id_ac_resetpass_btn_confirm);
    }
}
