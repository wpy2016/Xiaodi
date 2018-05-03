package com.wpy.cqu.xiaodi.evaluate;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;
import com.wpy.cqu.xiaodi.model.BaseUser;
import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.Reward;
import com.wpy.cqu.xiaodi.net.RewardRequst;
import com.wpy.cqu.xiaodi.net.resp.IResp;
import com.wpy.cqu.xiaodi.util.ToastUtil;

public class AcEvaluate extends TopBarAppComptAcitity {

    private ImageView mivImg;

    private TextView mtvName;

    private TextView mtvXiaodian;

    private TextView mtvXiaodianTip;

    private RatingBar mrbStars;

    private Button mbtnEvaluate;

    private Reward reward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_evaluate);
        reward = (Reward) getIntent().getSerializableExtra("reward");
        if (null == reward) {
            finish();
        }
        bindView();
        initView();
        bindEvent();
    }

    private void initView() {
        mtvBack.setText(getResources().getString(R.string.back));
        mtvBack.setTextColor(Color.WHITE);
        mivBack.setImageResource(R.drawable.go_back_white);
        mtvContent.setText(getResources().getString(R.string.evaluate));

        BaseUser showUser;
        if (reward.Publisher.Id.equals(XiaodiApplication.mCurrentUser.Id)) {
            showUser = reward.Receiver;
            mtvXiaodianTip.setText(R.string.you_have_pay);
        } else {
            showUser = reward.Publisher;
            mtvXiaodianTip.setText(R.string.you_have_get_pay);
        }
        mivImg.setImageResource(R.drawable.default_headimg);
        Picasso.with(this)
                .load(showUser.ImgUrl)
                .placeholder(R.drawable.default_headimg)
                .error(R.drawable.default_headimg).into(mivImg);
        mtvName.setText(showUser.NickName);
        mtvXiaodian.setText(reward.xiaodian + "");
    }

    private void bindView() {
        mtvBack = findViewById(R.id.id_top_back_tv);
        mivBack = findViewById(R.id.id_top_back_iv_img);
        mtvContent = findViewById(R.id.id_top_tv_content);

        mivImg = findViewById(R.id.id_ac_evaluate_iv_img);
        mtvName = findViewById(R.id.id_ac_evaluate_tv_name);
        mtvXiaodian = findViewById(R.id.id_ac_evaluate_tv_smilepoint);
        mtvXiaodianTip = findViewById(R.id.id_ac_evaluate_tv_smilepoint_tip);
        mrbStars = findViewById(R.id.id_ac_evaluate_ratingbar);
        mbtnEvaluate = findViewById(R.id.id_ac_evaluate_btn_confirm);
    }

    private void bindEvent() {
        mtvBack.setOnClickListener(view -> finish());
        mivBack.setOnClickListener(view -> finish());
        mbtnEvaluate.setOnClickListener(this::evaluate);
    }

    private void evaluate(View view) {
        float stars = mrbStars.getRating();
        if (0 == stars) {
            ToastUtil.toast(AcEvaluate.this, getResources().getString(R.string.please_evalute));
            return;
        }
        RewardRequst.EvaluateRewards(reward.id, stars, XiaodiApplication.mCurrentUser.Id,
                XiaodiApplication.mCurrentUser.Token, new IResp<ResultResp>() {
                    @Override
                    public void success(ResultResp object) {
                        ToastUtil.toast(AcEvaluate.this, getResources().getString(R.string.evaluate_successful));
                        finish();
                    }

                    @Override
                    public void fail(ResultResp resp) {
                        ToastUtil.toast(AcEvaluate.this, resp.message);
                    }
                });
    }
}
