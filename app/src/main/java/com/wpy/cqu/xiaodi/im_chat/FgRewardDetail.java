package com.wpy.cqu.xiaodi.im_chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;
import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.evaluate.AcEvaluate;
import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.Reward;
import com.wpy.cqu.xiaodi.model.Thing;
import com.wpy.cqu.xiaodi.model.User;
import com.wpy.cqu.xiaodi.net.RewardRequst;
import com.wpy.cqu.xiaodi.net.resp.IResp;
import com.wpy.cqu.xiaodi.util.ToastUtil;

/**
 * Created by wangpeiyu on 2018/4/10.
 */

public class FgRewardDetail extends Fragment {

    private ImageView mivThingImg;

    private TextView mtvXiaodian;

    private TextView mtvWeight;

    private TextView mtvStartPlace;

    private TextView mtvDstPlace;

    protected Button mbtnArray;

    private Reward reward;

    public FgRewardDetail() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reward = (Reward) getArguments().getSerializable("reward");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ac_details_small, null);
        bindView(view);
        return view;
    }

    private void bindView(View view) {
        mivThingImg = (ImageView) view.findViewById(R.id.id_ac_details_iv_good_picture);
        mtvXiaodian = (TextView) view.findViewById(R.id.id_ac_details_tv_grade_num);
        mtvWeight = (TextView) view.findViewById(R.id.id_ac_details_tv_type);
        mtvStartPlace = (TextView) view.findViewById(R.id.id_ac_details_tv_start_place);
        mtvDstPlace = (TextView) view.findViewById(R.id.id_ac_details_tv_arrive_place);
        mbtnArray = (Button) view.findViewById(R.id.id_xd_des_btn_receive);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        if (null == reward) {
            return;
        }

        //mbtnArray根据reward状态进行更新
        initButtonView();

        mivThingImg.setImageResource(Thing.DEFAULT_TYPE_IMG[reward.thing.type]);
        Picasso.with(getActivity()).load(reward.thing.thumbnail).error(Thing.DEFAULT_TYPE_IMG[reward.thing.type]).into(mivThingImg);
        mtvXiaodian.setText(reward.xiaodian + "");
        mtvWeight.setText(reward.thing.weight);
        mtvStartPlace.setText(reward.originLocation);
        mtvDstPlace.setText(reward.dstLocation);
    }

    private void initButtonView() {
        Logger.i(reward.toString());
        if (isMySendAndRewardIsCarry(reward)) {
            mbtnArray.setText(R.string.wait);
            mbtnArray.setBackgroundResource(R.drawable.circle_search_gray_60);
            mbtnArray.setEnabled(false);
            return;
        }

        if (isMyCarryAndRewardIsCarry(reward)) {
            mbtnArray.setText(R.string.arrive);
            mbtnArray.setBackgroundResource(R.drawable.circle_search_selector);
            mbtnArray.setEnabled(true);
            mbtnArray.setOnClickListener(this::arrive);
            return;
        }

        if (isMySendAndRewardIsArrive(reward)) {
            mbtnArray.setText(R.string.finish);
            mbtnArray.setBackgroundResource(R.drawable.circle_search_selector);
            mbtnArray.setEnabled(true);
            mbtnArray.setOnClickListener(this::finish);
            return;
        }
        if (isMyCarryAndRewardIsArrive(reward)) {

            mbtnArray.setText(R.string.wait);
            Logger.i("33333");
            mbtnArray.setBackgroundResource(R.drawable.circle_search_gray_60);
            mbtnArray.setEnabled(false);
        }
    }

    private void finish(View view) {
        RewardRequst.FinishRewards(reward.id, XiaodiApplication.mCurrentUser.Id,
                XiaodiApplication.mCurrentUser.Token, new IResp<ResultResp>() {
                    @Override
                    public void success(ResultResp object) {
                        ToastUtil.toast(getActivity(), getResources().getString(R.string.already_finish));
                        mbtnArray.setVisibility(View.GONE);
                        toEvaluate();
                    }

                    @Override
                    public void fail(ResultResp resp) {
                        ToastUtil.toast(getActivity(), resp.message);
                    }
                });
    }

    private void arrive(View view) {
        RewardRequst.DeliveryRewards(reward.id, XiaodiApplication.mCurrentUser.Id,
                XiaodiApplication.mCurrentUser.Token, new IResp<ResultResp>() {
                    @Override
                    public void success(ResultResp object) {
                        ToastUtil.toast(getActivity(), getResources().getString(R.string.already_arrive));
                        mbtnArray.setEnabled(false);
                        mbtnArray.setText(R.string.wait);
                        mbtnArray.setBackgroundResource(R.drawable.circle_search_gray_60);
                    }

                    @Override
                    public void fail(ResultResp resp) {
                        ToastUtil.toast(getActivity(), resp.message);
                    }
                });
    }

    //去评价
    private void toEvaluate() {
        Intent intent = new Intent(getActivity(), AcEvaluate.class);
        intent.putExtra("reward", reward);
        startActivity(intent);
    }

    private boolean isMySendAndRewardIsCarry(Reward reward) {
        return reward.Publisher.Id.equals(XiaodiApplication.mCurrentUser.Id) && Reward.REWARD_STATE_Carry == reward.state;
    }

    private boolean isMyCarryAndRewardIsCarry(Reward reward) {
        return reward.Receiver.Id.equals(XiaodiApplication.mCurrentUser.Id) && Reward.REWARD_STATE_Carry == reward.state;
    }

    private boolean isMySendAndRewardIsArrive(Reward reward) {
        return reward.Publisher.Id.equals(XiaodiApplication.mCurrentUser.Id) && Reward.REWARD_STATE_ARRIVE == reward.state;
    }

    private boolean isMyCarryAndRewardIsArrive(Reward reward) {
        return reward.Receiver.Id.equals(XiaodiApplication.mCurrentUser.Id) && Reward.REWARD_STATE_ARRIVE == reward.state;
    }

}
