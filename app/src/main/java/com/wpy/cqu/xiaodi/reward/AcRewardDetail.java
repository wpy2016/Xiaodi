package com.wpy.cqu.xiaodi.reward;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;
import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.base_activity.CheckPermissionsActivity;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;
import com.wpy.cqu.xiaodi.loading.Loading;
import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.Reward;
import com.wpy.cqu.xiaodi.model.Thing;
import com.wpy.cqu.xiaodi.model.User;
import com.wpy.cqu.xiaodi.net.RewardRequst;
import com.wpy.cqu.xiaodi.net.resp.IResp;
import com.wpy.cqu.xiaodi.util.ToastUtil;

import java.io.Serializable;

import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

public class AcRewardDetail extends TopBarAppComptAcitity {

    private static final int STATUS_BAR_COLOR = Color.parseColor("#00dec9");

    private static final String[] PERMISSION = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE,
    };

    private ImageView mivThingImg;

    private TextView mtvXiaodian;

    private TextView mtvWeight;

    private TextView mtvStartPlace;

    private TextView mtvDstPlace;

    private TextView mtvDeadline;

    private TextView mtvDescribe;

    private ImageView mivPublisherImg;

    private TextView mtvPublisherNickName;


    private TextView mtvAuthStatus;

    private TextView mtvCredit;

    protected Button mbtnArray;

    protected Reward reward;

    private PopupWindow mLoadingPopWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR, STATUS_BAR_COLOR);
        bundle.putStringArray(CheckPermissionsActivity.PEMISSION, PERMISSION);
        super.onCreate(bundle);
        setContentView(R.layout.ac_details);
        bindView();
        initView();
        bindEvent();
    }

    private void carryReward(View view) {
        if (XiaodiApplication.mCurrentUser.Id.equals(reward.Publisher.Id)) {
            ToastUtil.toast(this, getResources().getString(R.string.can_not_carry_owner));
            return;
        }
        if (null == mLoadingPopWindow) {
            mLoadingPopWindow = Loading.getLoadingPopwindown(this);
        }
        Loading.showLoading(this, mLoadingPopWindow);
        RewardRequst.CarryRewards(reward.id, XiaodiApplication.mCurrentUser.Id,
                XiaodiApplication.mCurrentUser.Token, new IResp<ResultResp>() {
                    @Override
                    public void success(ResultResp resp) {
                        carrySuccess();
                    }

                    @Override
                    public void fail(ResultResp resp) {
                        mLoadingPopWindow.dismiss();
                        ToastUtil.toast(AcRewardDetail.this, resp.message);
                    }
                });

    }

    private void carrySuccess() {
        mLoadingPopWindow.dismiss();
        ToastUtil.toast(AcRewardDetail.this, getResources().getString(R.string.carry_success));
        if (null != RongIM.getInstance()) {
            RongIM.getInstance().startPrivateChat(AcRewardDetail.this, reward.Publisher.Id, reward.Publisher.NickName);
            //并发送一条领取成功消息
            sendCarrySuccessMsg(reward);
        }
        finish();
    }

    private void sendCarrySuccessMsg(Reward reward) {
        TextMessage textMessage = TextMessage.obtain("领取成功,正在全速递送...");
        Message message = Message.obtain(reward.Publisher.Id, Conversation.ConversationType.PRIVATE, textMessage);
        if (null == RongIM.getInstance()) {
            return;
        }
        RongIM.getInstance().sendMessage(message, null, null, new IRongCallback.ISendMediaMessageCallback() {
            @Override
            public void onProgress(Message message, int i) {

            }

            @Override
            public void onCanceled(Message message) {

            }

            @Override
            public void onAttached(Message message) {

            }

            @Override
            public void onSuccess(Message message) {
                Logger.i("carry reward to send message successful.");
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                Logger.i("carry reward to send message fail,error=%s", message.toString());
            }
        });
    }

    private void initView() {
        mivBack.setImageResource(R.drawable.go_back_white);
        mivBack.setVisibility(View.VISIBLE);
        mtvBack.setText(getResources().getString(R.string.reward_hall));
        mtvBack.setTextColor(Color.WHITE);
        mtvContent.setText(getResources().getString(R.string.reward_detail));

        Serializable serializable = getIntent().getSerializableExtra("reward");
        if (null == serializable || !(serializable instanceof Reward)) {
            ToastUtil.toast(this, getResources().getString(R.string.no_such_reward));
            finish();
            return;
        }
        reward = (Reward) serializable;
        mivThingImg.setImageResource(Thing.DEFAULT_TYPE_IMG[reward.thing.type]);
        Picasso.with(this)
                .load(reward.thing.thumbnail)
                .placeholder(Thing.DEFAULT_TYPE_IMG[reward.thing.type])
                .error(Thing.DEFAULT_TYPE_IMG[reward.thing.type])
                .into(mivThingImg);
        mtvXiaodian.setText(reward.xiaodian + "");
        mtvWeight.setText(reward.thing.weight);
        mtvStartPlace.setText(reward.originLocation);
        if (reward.Publisher.Id.equals(Reward.XIAODIAN_CENTER)) {//说明是笑递中心
            mtvDstPlace.setText(reward.Receiver.NickName);
        } else {
            mtvDstPlace.setText(reward.dstLocation);
        }
        mtvDeadline.setText(reward.deadline);
        mtvDescribe.setText(reward.describe);
        mivPublisherImg.setImageResource(R.drawable.default_headimg);
        Picasso.with(this)
                .load(reward.Publisher.ImgUrl)
                .placeholder(R.drawable.default_headimg)
                .error(R.drawable.default_headimg)
                .into(mivPublisherImg);
        mtvPublisherNickName.setText(reward.Publisher.NickName);
        mtvAuthStatus.setText(User.AuthStatus[reward.Publisher.UserType]);
        mtvCredit.setText(reward.Publisher.Creditibility + "");
    }

    private void bindView() {
        mivThingImg = (ImageView) findViewById(R.id.id_ac_details_iv_good_picture);
        mtvXiaodian = (TextView) findViewById(R.id.id_ac_details_tv_grade_num);
        mtvWeight = (TextView) findViewById(R.id.id_ac_details_tv_type);
        mtvStartPlace = (TextView) findViewById(R.id.id_ac_details_tv_start_place);
        mtvDstPlace = (TextView) findViewById(R.id.id_ac_details_tv_arrive_place);
        mtvDeadline = (TextView) findViewById(R.id.id_ac_details_tv_start_stop_time);
        mtvDescribe = (TextView) findViewById(R.id.id_ac_details_tv_text_des);
        mivPublisherImg = (ImageView) findViewById(R.id.id_ac_details_iv_head_picture);
        mtvPublisherNickName = (TextView) findViewById(R.id.id_ac_details_tv_owner_name);
        mtvAuthStatus = (TextView) findViewById(R.id.id_xd_des_tv_real_name);
        mtvCredit = (TextView) findViewById(R.id.id_xd_des_tv_credit);
        mbtnArray = (Button) findViewById(R.id.id_xd_des_btn_receive);
        mtvBack = (TextView) findViewById(R.id.id_top_back_tv);
        mivBack = (ImageView) findViewById(R.id.id_top_back_iv_img);
        mtvContent = (TextView) findViewById(R.id.id_top_tv_content);
    }

    private void bindEvent() {
        mtvBack.setOnClickListener(v -> finish());
        mivBack.setOnClickListener(v -> finish());
        mbtnArray.setOnClickListener(this::carryReward);
    }
}
