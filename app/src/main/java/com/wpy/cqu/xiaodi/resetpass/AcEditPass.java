package com.wpy.cqu.xiaodi.resetpass;

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
