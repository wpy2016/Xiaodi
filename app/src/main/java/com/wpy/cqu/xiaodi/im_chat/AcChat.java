package com.wpy.cqu.xiaodi.im_chat;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.base_activity.CheckPermissionsActivity;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;
import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.Reward;
import com.wpy.cqu.xiaodi.net.RewardRequst;
import com.wpy.cqu.xiaodi.net.resp.IResp;
import com.wpy.cqu.xiaodi.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.fragment.ConversationFragment;

/**
 * Created by wangpeiyu on 2018/4/10.
 */

public class AcChat extends TopBarAppComptAcitity {

    private static final int STATUS_BAR_COLOR = Color.parseColor("#00dec9");

    private static String[] PERMISSION = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE,
    };

    private ChatPagerAdaper mAdaper;

    private ViewPager mvpChat;

    private ConversationFragment conversationFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR, STATUS_BAR_COLOR);
        bundle.putStringArray(CheckPermissionsActivity.PEMISSION, PERMISSION);
        super.onCreate(bundle);
        setContentView(R.layout.ac_chat);
        bindView();
        bindEvent();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViewPager();
    }

    private void bindView() {
        mtvBack = (TextView) findViewById(R.id.id_top_back_tv);
        mivBack = (ImageView) findViewById(R.id.id_top_back_iv_img);
        mtvContent = (TextView) findViewById(R.id.id_top_tv_content);

        mvpChat = (ViewPager) findViewById(R.id.id_ac_chat_viewpager);
    }

    private void bindEvent() {
        mtvBack.setOnClickListener(view -> finish());
        mivBack.setOnClickListener(view -> finish());
    }

    private void initView() {
        mtvBack.setText(getResources().getString(R.string.message_hall));
        mtvBack.setTextColor(Color.WHITE);
        mivBack.setImageResource(R.drawable.go_back_white);

        String targetName = getIntent().getData().getQueryParameter("title");
        if (TextUtils.isEmpty(targetName)) {
            targetName = getResources().getString(R.string.loading);
        }
        mtvContent.setText(targetName);
    }

    private void initViewPager() {
        conversationFragment = Rongyun.getConversationFragment(this);
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(conversationFragment);
        mAdaper = new ChatPagerAdaper(fragmentList, getSupportFragmentManager());
        mvpChat.setAdapter(mAdaper);

        String targetId = getIntent().getData().getQueryParameter("targetId");
        Logger.i("targetId=%s", targetId);

        RewardRequst.ShowRewardsOurNotFinish(XiaodiApplication.mCurrentUser.Id, targetId,
                XiaodiApplication.mCurrentUser.Token, new IResp<List<Reward>>() {
                    @Override
                    public void success(List<Reward> rewards) {
                        hanlderRewards(rewards);
                    }

                    @Override
                    public void fail(ResultResp resp) {
                        Logger.i("ShowRewardsOurNotFinish fail=%s", resp.message);
                        ToastUtil.toast(AcChat.this, resp.message);
                    }
                });
    }

    private void hanlderRewards(List<Reward> rewards) {
        List<Reward> myCarryNotFinishRewards = new ArrayList<>();
        List<Reward> mySendNotFinishRewards = new ArrayList<>();
        for (Reward reward : rewards) {
            if (reward.Publisher.Id.equals(XiaodiApplication.mCurrentUser.Id)) {
                mySendNotFinishRewards.add(reward);
                continue;
            }
            myCarryNotFinishRewards.add(reward);
        }
        rewards.clear();

        List<Fragment> fragments = new ArrayList<>();
        int chatFgPos = 0;
        //在左边设置为我领取的未完成的订单
        for (Reward myCarryReward : myCarryNotFinishRewards) {
            Fragment fragment = new FgRewardDetail();
            Bundle bundle = new Bundle();
            bundle.putSerializable("reward", myCarryReward);
            fragment.setArguments(bundle);
            fragments.add(fragment);
            chatFgPos++;
        }

        fragments.add(conversationFragment);

        //在右边设置为我发布的未完成的订单
        for (Reward mySendReward : mySendNotFinishRewards) {
            Fragment fragment = new FgRewardDetail();
            Bundle bundle = new Bundle();
            bundle.putSerializable("reward", mySendReward);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }

        mAdaper.mFragments = fragments;
        mAdaper.notifyDataSetChanged();
        mvpChat.setCurrentItem(chatFgPos);
    }

    /**
     * 必须使用FragmentStatePagerAdapter,而且覆写getItemPosition return POSITION_NONE。
     * 使用FragmentPagerAdapter会由于fragment无法修改tag而奔溃
     */
    private class ChatPagerAdaper extends FragmentStatePagerAdapter {

        public List<Fragment> mFragments;

        public ChatPagerAdaper(List<Fragment> fragments, FragmentManager fragmentManager) {
            super(fragmentManager);
            this.mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}
