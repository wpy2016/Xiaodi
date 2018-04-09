package com.wpy.cqu.xiaodi.reward;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;

public class AcCarryRecord extends TopBarAppComptAcitity {

    private static final int STATUS_BAR_COLOR = Color.parseColor("#00dec9");

    private LinearLayout mllMySend;

    private LinearLayout mllMyCarry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR,STATUS_BAR_COLOR);
        super.onCreate(bundle);
        setContentView(R.layout.ac_carry_recode);
        bindView();
        initView();
        bindEvent();
    }

    private void mySend(View view) {

    }

    private void myCarry(View view) {

    }

    private void bindView() {
        mtvBack = (TextView) findViewById(R.id.id_top_back_tv);
        mivBack = (ImageView) findViewById(R.id.id_top_back_iv_img);
        mtvContent = (TextView) findViewById(R.id.id_top_tv_content);
        mllMySend = (LinearLayout) findViewById(R.id.id_ac_carry_record_send);
        mllMyCarry = (LinearLayout) findViewById(R.id.id_ac_carry_record_carry);
    }

    private void initView() {
        mtvBack.setText(getResources().getString(R.string.me));
        mtvBack.setTextColor(Color.WHITE);
        mivBack.setImageResource(R.drawable.go_back_white);
        mtvContent.setText(getResources().getString(R.string.carry_recode));
    }

    private void bindEvent() {
        mtvBack.setOnClickListener(view -> finish());
        mivBack.setOnClickListener(view -> finish());
        mllMySend.setOnClickListener(this::mySend);
        mllMyCarry.setOnClickListener(this::myCarry);
    }
}
