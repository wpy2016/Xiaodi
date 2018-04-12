package com.wpy.cqu.xiaodi.wallet;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.adapter.common.CommonAdapter;
import com.wpy.cqu.xiaodi.adapter.common.ViewHolder;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;
import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.Reward;
import com.wpy.cqu.xiaodi.model.Thing;
import com.wpy.cqu.xiaodi.net.RewardRequst;
import com.wpy.cqu.xiaodi.net.resp.IResp;
import com.wpy.cqu.xiaodi.util.ToastUtil;

import java.util.List;

public class AcXiaodianRecord extends TopBarAppComptAcitity {

    private ListView mlvXiaodianRecord;

    private CommonAdapter<Reward> adapter;

    private List<Reward> rewards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xiaodian_record);
        bindView();
        initView();
        bindEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindData();
    }

    private void bindData() {
        RewardRequst.ShowRewardsMyFinish(XiaodiApplication.mCurrentUser.Id,
                XiaodiApplication.mCurrentUser.Token, new IResp<List<Reward>>() {
                    @Override
                    public void success(List<Reward> object) {
                        rewards = object;
                        showData();
                    }

                    @Override
                    public void fail(ResultResp resp) {
                        ToastUtil.toast(AcXiaodianRecord.this, resp.message);
                    }
                });
    }

    private void showData() {
        if (null == rewards || rewards.isEmpty()) {
            ToastUtil.toast(AcXiaodianRecord.this, getResources().getString(R.string.not_xiaodian_record));
            return;
        }
        adapter = new CommonAdapter<Reward>(this, rewards, R.layout.xiaodian_record_item) {
            @Override
            public void convert(ViewHolder helper, Reward item) {
                convertView(helper, item);
            }
        };
        mlvXiaodianRecord.setAdapter(adapter);
    }

    private void convertView(ViewHolder helper, Reward reward) {
        helper.setText(R.id.id_reward_tv_name, Thing.THING_TYPE[reward.thing.type]);
        helper.setText(R.id.id_reward_tv_weight, reward.thing.weight);
        helper.setText(R.id.id_reward_tv_start_place, reward.originLocation);
        helper.setText(R.id.id_reward_tv_end_place, reward.dstLocation);
        if (reward.Publisher.Id.equals(XiaodiApplication.mCurrentUser.Id)) {
            Picasso.with(AcXiaodianRecord.this)
                    .load(reward.Receiver.ImgUrl)
                    .error(R.drawable.default_headimg)
                    .into((ImageView) helper.getConvertView().findViewById(R.id.id_reward_iv_img));
            helper.setText(R.id.id_reward_tv_user_name, reward.Receiver.NickName);
            helper.setText(R.id.id_reward_tv_credit, "-" + reward.xiaodian);
            helper.setTextColor(R.id.id_reward_tv_credit, Color.parseColor("#fe5753"));
            return;
        }
        Picasso.with(AcXiaodianRecord.this)
                .load(reward.Publisher.ImgUrl)
                .error(R.drawable.default_headimg)
                .into((ImageView) helper.getConvertView().findViewById(R.id.id_reward_iv_img));
        helper.setText(R.id.id_reward_tv_user_name, reward.Publisher.NickName);
        helper.setText(R.id.id_reward_tv_credit, "+" + reward.xiaodian);
        helper.setTextColor(R.id.id_reward_tv_credit, Color.parseColor("#00a779"));
    }

    private void bindView() {
        mlvXiaodianRecord = findViewById(R.id.id_ac_xiaodian_record_listview);

        mtvBack = (TextView) findViewById(R.id.id_top_back_tv);
        mivBack = (ImageView) findViewById(R.id.id_top_back_iv_img);
        mtvContent = (TextView) findViewById(R.id.id_top_tv_content);
    }

    private void initView() {
        mtvBack.setText(getResources().getString(R.string.wallet));
        mtvBack.setTextColor(Color.WHITE);
        mivBack.setImageResource(R.drawable.go_back_white);
        mtvContent.setText(getResources().getString(R.string.xiaodian_record));
    }

    private void bindEvent() {
        mtvBack.setOnClickListener(view -> finish());
        mivBack.setOnClickListener(view -> finish());
    }
}
