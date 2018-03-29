package com.wpy.cqu.xiaodi.register;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
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
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;

public class AcRegister extends TopBarAppComptAcitity {

    private static final int STATUS_BAR_COLOR = Color.parseColor("#00dec9");

    private ImageView mivImg;

    private EditText metName;

    private EditText metPass;

    private ImageView mivSee;

    private Button mbtnRegister;

    private boolean mdisplayPwd = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR, STATUS_BAR_COLOR);
        super.onCreate(bundle);
        setContentView(R.layout.ac_register_create_acount);
        bindView();
        initView();
        bindEvent();
    }

    private void register(View v){

    }

    private void setImg(View v){

    }

    private void bindView() {
        mivBack = (ImageView) findViewById(R.id.id_top_back_iv_img);
        mtvBack = (TextView) findViewById(R.id.id_top_back_tv);
        mtvContent = (TextView) findViewById(R.id.id_top_tv_content);
        mrlTop = (RelativeLayout) findViewById(R.id.id_top_rl);
        mivImg = (ImageView) findViewById(R.id.id_ac_register_iv_img);
        metName = (EditText) findViewById(R.id.id_ac_register_et_nickname);
        metPass = (EditText) findViewById(R.id.id_ac_register_et_pass);
        mivSee = (ImageView) findViewById(R.id.id_ac_register_iv_pwd_display);
        mbtnRegister = (Button) findViewById(R.id.id_ac_register_btn_confirm);
    }

    private void initView(){
        mivBack.setImageResource(R.drawable.go_back_white);
        mtvBack.setText(getResources().getString(R.string.login));
        mtvBack.setTextColor(Color.WHITE);
        mtvContent.setText(getResources().getString(R.string.register));
    }

    private void bindEvent() {
        mivBack.setOnClickListener(v->finish());
        mtvBack.setOnClickListener(v->finish());
        mivImg.setOnClickListener(this::setImg);
        mivSee.setOnClickListener(v -> {
            if (!mdisplayPwd) {
                mdisplayPwd = true;
                mivSee.setImageResource(R.drawable.eye);
                metPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                mdisplayPwd = false;
                mivSee.setImageResource(R.drawable.eye_close);
                metPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
        mbtnRegister.setOnClickListener(this::register);
    }
}
