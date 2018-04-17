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
import android.view.View;
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
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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

    private int chatFgPos = 0;

    private ViewPager.OnPageChangeListener onPageChangeListener;

    //取消订阅者
    private Disposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR, STATUS_BAR_COLOR);
        bundle.putStringArray(CheckPermissionsActivity.PEMISSION, PERMISSION);
        super.onCreate(bundle);
        setContentView(R.layout.ac_chat);
        bindView();
        bindEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
        initViewPager();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != disposable) {
            disposable.dispose();
        }
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
        mivBack.setVisibility(View.VISIBLE);
        mtvBack.setVisibility(View.VISIBLE);
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
        if (null == rewards || rewards.isEmpty()) {
            return;
        }
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

        //延迟2s更新，防止出现更新太快，屏幕闪一下
        disposable = Observable.timer(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(l -> {
                    mAdaper.mFragments = fragments;
                    mAdaper.notifyDataSetChanged();
                    mvpChat.setCurrentItem(chatFgPos);
                    //切换时，更新title
                    onPageChangeListener = new ChatChangeListener();
                    mvpChat.addOnPageChangeListener(onPageChangeListener);
                });
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

    private class ChatChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == chatFgPos) {
                String targetName = getIntent().getData().getQueryParameter("title");
                mtvContent.setText(targetName);
                mivBack.setVisibility(View.VISIBLE);
                mtvBack.setVisibility(View.VISIBLE);
                return;
            }
            if (position < chatFgPos) {
                mtvContent.setText(getResources().getString(R.string.my_carry_not_finish_reward));
                mivBack.setVisibility(View.INVISIBLE);
                mtvBack.setVisibility(View.INVISIBLE);
                return;
            }

            mtvContent.setText(getResources().getString(R.string.my_send_not_finish_reward));
            mivBack.setVisibility(View.INVISIBLE);
            mtvBack.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
