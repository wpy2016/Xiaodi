package com.wpy.cqu.xiaodi.auth;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;

public class AcAuth extends TopBarAppComptAcitity {

    private TextView metSchool;

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

    }

    private void bindView() {
        metSchool = (TextView) findViewById(R.id.id_ac_statu_identificate_tv_school);
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
    }

    private void bindEvent() {
        mbtnAuth.setOnClickListener(this::auth);
        mtvChangeUserType.setOnClickListener(this::changeUserType);

        mtvBack.setOnClickListener(view -> finish());
        mivBack.setOnClickListener(view -> finish());
    }
}
