package com.wpy.cqu.xiaodi.wallet;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;
import com.wpy.cqu.xiaodi.util.ToastUtil;

public class AcWithdraw extends TopBarAppComptAcitity {

    private static final int STATUS_BAR_COLOR = Color.parseColor("#00dec9");

    /**
     * 笑点提现等级
     */
    private static final int SMILEPOINT_WITHDRAW_LEVEL1 = 750;
    private static final int SMILEPOINT_WITHDRAW_LEVEL2 = 1000;
    private static final int SMILEPOINT_WITHDRAW_LEVEL3 = 1500;
    private static final int SMILEPOINT_WITHDRAW_LEVEL4 = 2500;
    private static final int SMILEPOINT_WITHDRAW_LEVEL5 = 4000;
    private static final int SMILEPOINT_WITHDRAW_LEVEL6 = 6000;

    private int mWithdrawLevel = SMILEPOINT_WITHDRAW_LEVEL1;

    private LinearLayout mll750;

    private LinearLayout mll1000;

    private LinearLayout mll1500;

    private LinearLayout mll2500;

    private LinearLayout mll4000;

    private LinearLayout mll6000;

    private EditText metAirpayAccout;

    private TextView mtvAvaliableXiaodian;

    private Button mbtnWithdraw;
    private int chooseOptionColor = Color.parseColor("#00dec9");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR, STATUS_BAR_COLOR);
        super.onCreate(bundle);
        setContentView(R.layout.ac_withdraw);
        bindView();
        initView();
        bindEvent();
    }

    private void withdraw(View view) {
        //// TODO: 2018/4/13 提现，等待实现
        ToastUtil.toast(this, getResources().getString(R.string.withdraw_wait_to_complete));
    }

    private void bindView() {
        mll750 = findViewById(R.id.id_ac_withdraw_ll_750);
        mll1000 = findViewById(R.id.id_ac_withdraw_ll_1000);
        mll1500 = findViewById(R.id.id_ac_withdraw_ll_1500);
        mll2500 = findViewById(R.id.id_ac_withdraw_ll_2500);
        mll4000 = findViewById(R.id.id_ac_withdraw_ll_4000);
        mll6000 = findViewById(R.id.id_ac_withdraw_ll_6000);
        mbtnWithdraw = findViewById(R.id.id_ac_recharge_btn_withdraw);
        metAirpayAccout = findViewById(R.id.id_ac_withdraw_et_airpay_count);
        mtvAvaliableXiaodian = findViewById(R.id.id_ac_withdraw_tv_balance);

        mtvBack = findViewById(R.id.id_top_back_tv);
        mivBack = findViewById(R.id.id_top_back_iv_img);
        mtvContent = findViewById(R.id.id_top_tv_content);
    }

    private void initView() {
        mtvBack.setText(getResources().getString(R.string.wallet));
        mtvBack.setTextColor(Color.WHITE);
        mivBack.setImageResource(R.drawable.go_back_white);
        mtvContent.setText(getResources().getString(R.string.withdraw));

        mll750.setBackgroundColor(chooseOptionColor);
        mtvAvaliableXiaodian.setText(XiaodiApplication.mCurrentUser.SilverMoney + "");
        mtvAvaliableXiaodian.setTextColor(Color.parseColor("#ff4500"));
    }

    private void bindEvent() {
        mtvBack.setOnClickListener(view -> finish());
        mivBack.setOnClickListener(view -> finish());

        mll750.setOnClickListener(view -> chooseOption(view, SMILEPOINT_WITHDRAW_LEVEL1));
        mll1000.setOnClickListener(view -> chooseOption(view, SMILEPOINT_WITHDRAW_LEVEL2));
        mll1500.setOnClickListener(view -> chooseOption(view, SMILEPOINT_WITHDRAW_LEVEL3));
        mll2500.setOnClickListener(view -> chooseOption(view, SMILEPOINT_WITHDRAW_LEVEL4));
        mll4000.setOnClickListener(view -> chooseOption(view, SMILEPOINT_WITHDRAW_LEVEL5));
        mll6000.setOnClickListener(view -> chooseOption(view, SMILEPOINT_WITHDRAW_LEVEL6));
        mbtnWithdraw.setOnClickListener(this::withdraw);
    }

    private void chooseOption(View view, int rmbLevel) {
        mWithdrawLevel = rmbLevel;
        resetImg();
        view.setBackgroundColor(chooseOptionColor);
    }

    private void resetImg() {
        mll750.setBackgroundResource(R.drawable.rectangle_light_blue);
        mll1000.setBackgroundResource(R.drawable.rectangle_light_blue);
        mll1500.setBackgroundResource(R.drawable.rectangle_light_blue);
        mll2500.setBackgroundResource(R.drawable.rectangle_light_blue);
        mll4000.setBackgroundResource(R.drawable.rectangle_light_blue);
        mll6000.setBackgroundResource(R.drawable.rectangle_light_blue);
    }
}
