package com.wpy.cqu.xiaodi.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;

public class AcRegisterVertifyCode extends StatusBarAppComptActivity {

    private ImageView mivBack;

    private TextView mtvBack;

    private TextView mtvContent;

    private RelativeLayout mrlTop;

    private EditText metPhone;

    private EditText metVertify;

    private Button mbtnGetVertify;

    private Button mbtnNext;


    private PopupWindow mPopWindow;
    private LayoutInflater mLayoutInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_register_vertiry_code);

        initView();
    }

    private void initView() {
        mivBack = (ImageView) findViewById(R.id.id_top_back_iv_img);
        mtvBack = (TextView) findViewById(R.id.id_top_back_tv);
        mtvContent = (TextView) findViewById(R.id.id_top_tv_content);
        mrlTop = (RelativeLayout) findViewById(R.id.id_top_rl);
        metPhone = (EditText) findViewById(R.id.id_ac_register_et_phone);
        mbtnGetVertify = (Button) findViewById(R.id.id_ac_register_btn_get_vertify);
        metVertify = (EditText) findViewById(R.id.id_ac_register_et_vertify);
        mbtnNext = (Button) findViewById(R.id.id_ac_register_btn_next);
    }
}
