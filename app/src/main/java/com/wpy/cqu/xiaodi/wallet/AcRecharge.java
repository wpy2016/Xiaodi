package com.wpy.cqu.xiaodi.wallet;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wpy.cqu.pay.IPay;
import com.wpy.cqu.pay.IPayResp;
import com.wpy.cqu.pay.PayFactory;
import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;
import com.wpy.cqu.xiaodi.util.ToastUtil;

public class AcRecharge extends TopBarAppComptAcitity {

    private static final int STATUS_BAR_COLOR = Color.parseColor("#00dec9");

    /**
     * 支付相关
     */
    private final static int FIVE = 5;
    private final static int EIGHT = 8;
    private final static int FIFTEEN = 15;
    private final static int TWENTY_FIVE = 25;
    private final static int FIFTY = 50;
    private final static int ONE_HUNDRED = 100;
    private int mRmbLevel = FIVE;

    private LinearLayout mllFive;

    private LinearLayout mllEight;

    private LinearLayout mllFifteen;

    private LinearLayout mllTwentyFive;

    private LinearLayout mllFifty;

    private LinearLayout mllOneHundred;

    private Button mbtnPay;
    private int chooseOptionColor = Color.parseColor("#00dec9");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR, STATUS_BAR_COLOR);
        super.onCreate(bundle);
        setContentView(R.layout.ac_recharge);

        bindView();
        initView();
        bindEvent();
    }

    private void recharge(View view) {
        ToastUtil.toast(AcRecharge.this, getResources().getString(R.string.recharge_wait_to_complete));
        //// TODO: 2018/4/13 支付功能待实现 ，之后的实现请在pay module中完成。
        /*
        String tradename = getPayName(mRmbLevel);
        String outtradeno = XiaodiApplication.mCurrentUser.Id + System.currentTimeMillis();
        Long amount = 10L; //// TODO: 2018/4/13 改为实际的钱
        String backparams = "";
        String notifyurl = "http://119.29.164.153:1688/user/recharge";
        String userid = XiaodiApplication.mCurrentUser.Id;
        IPay pay = PayFactory.getInstance();
        pay.Pay(this, tradename, outtradeno, amount, backparams, notifyurl, userid, new IPayResp() {
            @Override
            public void onSuccess() {
                ToastUtil.toast(AcRecharge.this, getResources().getString(R.string.recharge_success));

                //进行笑点充值，或再服务断进行充值
            }

            @Override
            public void onFail(String message) {
                ToastUtil.toast(AcRecharge.this, getResources().getString(R.string.recharge_fail) + message);
            }
        });
        */
    }

    private void bindView() {
        mllFive = findViewById(R.id.id_ac_recharge_ll_five);
        mllEight = findViewById(R.id.id_ac_recharge_ll_eight);
        mllFifteen = findViewById(R.id.id_ac_recharge_ll_fifteen);
        mllTwentyFive = findViewById(R.id.id_ac_recharge_ll_twenty_five);
        mllFifty = findViewById(R.id.id_ac_recharge_ll_fifty);
        mllOneHundred = findViewById(R.id.id_ac_recharge_ll_one_hundred);
        mbtnPay = findViewById(R.id.id_ac_recharge_btn_recharge);

        mtvBack = findViewById(R.id.id_top_back_tv);
        mivBack = findViewById(R.id.id_top_back_iv_img);
        mtvContent = findViewById(R.id.id_top_tv_content);
    }

    private void initView() {
        mtvBack.setText(getResources().getString(R.string.wallet));
        mtvBack.setTextColor(Color.WHITE);
        mivBack.setImageResource(R.drawable.go_back_white);
        mtvContent.setText(getResources().getString(R.string.recharge));

        mllFive.setBackgroundColor(chooseOptionColor);
    }

    private void bindEvent() {
        mtvBack.setOnClickListener(view -> finish());
        mivBack.setOnClickListener(view -> finish());

        mllFive.setOnClickListener(view -> chooseOption(view, FIVE));
        mllEight.setOnClickListener(view -> chooseOption(view, EIGHT));
        mllFifteen.setOnClickListener(view -> chooseOption(view, FIFTEEN));
        mllTwentyFive.setOnClickListener(view -> chooseOption(view, TWENTY_FIVE));
        mllFifty.setOnClickListener(view -> chooseOption(view, FIFTY));
        mllOneHundred.setOnClickListener(view -> chooseOption(view, ONE_HUNDRED));
        mbtnPay.setOnClickListener(this::recharge);
    }

    private void chooseOption(View view, int rmbLevel) {
        mRmbLevel = rmbLevel;
        resetImg();
        view.setBackgroundColor(chooseOptionColor);
    }

    private void resetImg() {
        mllFive.setBackgroundResource(R.drawable.rectangle_light_blue);
        mllEight.setBackgroundResource(R.drawable.rectangle_light_blue);
        mllFifteen.setBackgroundResource(R.drawable.rectangle_light_blue);
        mllTwentyFive.setBackgroundResource(R.drawable.rectangle_light_blue);
        mllFifty.setBackgroundResource(R.drawable.rectangle_light_blue);
        mllOneHundred.setBackgroundResource(R.drawable.rectangle_light_blue);
    }

    private String getPayName(int rmbLevel) {
        //String payName = XiaodiApplication.mCurrentUser.Id;
        String payName = "";
        switch (mRmbLevel) {
            case FIVE:
                payName += "购买500笑点";
                break;
            case EIGHT:
                payName += "购买820笑点";
                break;
            case FIFTEEN:
                payName += "购买1550笑点";
                break;
            case TWENTY_FIVE:
                payName += "购买2575笑点";
                break;
            case FIFTY:
                payName += "购买5100笑点";
                break;
            case ONE_HUNDRED:
                payName += "购买10200笑点";
                break;
        }
        return payName;
    }
}
