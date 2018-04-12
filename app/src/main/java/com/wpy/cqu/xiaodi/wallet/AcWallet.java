package com.wpy.cqu.xiaodi.wallet;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;

public class AcWallet extends TopBarAppComptAcitity {

    private static final int STATUS_BAR_COLOR = Color.parseColor("#00dec9");

    private TextView mtvTotalMoney;

    private TextView mtvGoldMoney;

    private TextView mtvSilverMoney;

    private LinearLayout mllRecharge;

    private LinearLayout mllWithdraw;

    private LinearLayout mllInvite;

    private LinearLayout mllXiaodianRecord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR, STATUS_BAR_COLOR);
        super.onCreate(bundle);
        setContentView(R.layout.ac_wallet);
        bindView();
        initView();
        bindEvent();
    }

    private void bindView() {
        mtvBack = (TextView) findViewById(R.id.id_top_back_tv);
        mivBack = (ImageView) findViewById(R.id.id_top_back_iv_img);
        mtvContent = (TextView) findViewById(R.id.id_top_tv_content);
        mtvTotalMoney = (TextView) findViewById(R.id.id_ac_wallet_tv_total_money);
        mtvGoldMoney = (TextView) findViewById(R.id.id_ac_wallet_tv_gold_money);
        mtvSilverMoney = (TextView) findViewById(R.id.id_ac_wallet_tv_silver_money);
        mllRecharge = (LinearLayout) findViewById(R.id.id_ac_wallet_ll_recharge);
        mllWithdraw = (LinearLayout) findViewById(R.id.id_ac_wallet_ll_withdraw);
        mllInvite = (LinearLayout) findViewById(R.id.id_ac_wallet_ll_invite);
        mllXiaodianRecord = (LinearLayout) findViewById(R.id.id_ac_wallet_ll_xiaodian_record);
    }

    private void initView() {
        mtvBack.setText(getResources().getString(R.string.me));
        mtvBack.setTextColor(Color.WHITE);
        mivBack.setImageResource(R.drawable.go_back_white);
        mtvContent.setText(getResources().getString(R.string.wallet));

        float totleMoney = XiaodiApplication.mCurrentUser.GoldMoney + XiaodiApplication.mCurrentUser.SilverMoney;
        mtvTotalMoney.setText(totleMoney + "");
        mtvGoldMoney.setText(XiaodiApplication.mCurrentUser.GoldMoney + "");
        mtvSilverMoney.setText(XiaodiApplication.mCurrentUser.SilverMoney + "");
    }

    private void bindEvent() {
        mtvBack.setOnClickListener(view -> finish());
        mivBack.setOnClickListener(view -> finish());
        mllXiaodianRecord.setOnClickListener(view -> toNext(AcXiaodianRecord.class));
        // TODO: 2018/4/8
    }

    private void toNext(Class<?> next) {
        Intent intent = new Intent(this, next);
        startActivity(intent);
    }

    private void toNextAc(Class<?> nextAc) {
        Intent intent = new Intent(this, nextAc);
        startActivity(intent);
    }
}
