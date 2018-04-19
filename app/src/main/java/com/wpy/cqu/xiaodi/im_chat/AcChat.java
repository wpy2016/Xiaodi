package com.wpy.cqu.xiaodi.im_chat;

import android.Manifest;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.wpy.cqu.xiaodi.util.DpUtil;
import com.wpy.cqu.xiaodi.util.ToastUtil;
import com.wpy.cqu.xiaodi.view.CircleIndicator;
import com.wpy.cqu.xiaodi.view.DepthPageTransformer;

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
            Manifest.permission.CALL_PHONE,
    };

    private ChatPagerAdaper mAdaper;

    private LinearLayout mllViewPagerRoot;

    private ViewPager mvpNotFinishRewards;

    private CircleIndicator mcircleIndicator;

    private FloatingActionButton mFloatingBtnPhone;

    private String mPhone;

    private ImageView mivTopRight;
    private boolean isShow = true;
    private ValueAnimator animator;
    private float currentOffset = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR, STATUS_BAR_COLOR);
        bundle.putStringArray(CheckPermissionsActivity.PEMISSION, PERMISSION);
        super.onCreate(bundle);
        setContentView(R.layout.ac_chat);
        bindView();
        initView();
        bindEvent();
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
        mvpNotFinishRewards = (ViewPager) findViewById(R.id.id_ac_chat_viewpager);
        mcircleIndicator = (CircleIndicator) findViewById(R.id.id_ac_chat_indicator);
        mFloatingBtnPhone = (FloatingActionButton) findViewById(R.id.id_fg_chat_floating_btn);
        mllViewPagerRoot = (LinearLayout) findViewById(R.id.id_ac_chat_viewpager_ll);
        mivTopRight = (ImageView) findViewById(R.id.id_top_right_iv_img);
    }

    private void bindEvent() {
        mtvBack.setOnClickListener(view -> finish());
        mivBack.setOnClickListener(view -> finish());
        mFloatingBtnPhone.setOnClickListener(view -> callPhone());
        mivTopRight.setOnClickListener(this::showOrDismiss);
    }

    private void showOrDismiss(View view) {
        if (isShow) {
            isShow = false;
            mivTopRight.setImageResource(R.drawable.go_to_down_white);
            if (null != animator && animator.isRunning()) {
                animator.cancel();
            }
            viewpagerAnimation(currentOffset, 50);
            return;
        }
        isShow = true;
        mivTopRight.setImageResource(R.drawable.go_to_up_white);
        if (null != animator && animator.isRunning()) {
            animator.cancel();
        }
        viewpagerAnimation(currentOffset, 130);
    }

    private void callPhone() {
        Logger.i("phone=%s", mPhone);
        Intent intent_to_phone = new Intent();
        intent_to_phone.setAction(Intent.ACTION_CALL);
        intent_to_phone.setData(Uri.parse("tel:" + mPhone));
        startActivity(intent_to_phone);
    }

    private void initView() {
        mivBack.setVisibility(View.VISIBLE);
        mtvBack.setVisibility(View.VISIBLE);
        mtvBack.setText(getResources().getString(R.string.back));
        mtvBack.setTextColor(Color.WHITE);
        mivBack.setImageResource(R.drawable.go_back_white);

        String targetName = getIntent().getData().getQueryParameter("title");
        if (TextUtils.isEmpty(targetName)) {
            targetName = getResources().getString(R.string.loading);
        }
        mtvContent.setText(targetName);
    }

    private void initViewPager() {
        mllViewPagerRoot.setVisibility(View.GONE);
        mFloatingBtnPhone.setVisibility(View.GONE);
        mivTopRight.setVisibility(View.GONE);
        isShow = false;
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
        List<Fragment> fragments = new ArrayList<>();
        //在左边设置为我领取的未完成的订单
        for (Reward reward : rewards) {
            Fragment fragment = new FgRewardDetail();
            Bundle bundle = new Bundle();
            bundle.putSerializable("reward", reward);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        mAdaper = new ChatPagerAdaper(fragments, getSupportFragmentManager());
        mvpNotFinishRewards.setAdapter(mAdaper);
        mvpNotFinishRewards.setPageTransformer(true, new DepthPageTransformer());
        mcircleIndicator.setViewPager(mvpNotFinishRewards);

        viewpagerAnimation(50, 130);

        mFloatingBtnPhone.setVisibility(View.VISIBLE);

        mivTopRight.setVisibility(View.VISIBLE);
        mivTopRight.setImageResource(R.drawable.go_to_up_white);
        isShow = true;

        //设置获取手机号
        getPhone(rewards, 0); // 默认为第0个，避免只有一个时的bug
        mvpNotFinishRewards.addOnPageChangeListener(new OnPagerChangeListenerGetPhone(rewards));
    }

    private void viewpagerAnimation(float start, float end) {
        mllViewPagerRoot.setVisibility(View.VISIBLE);
        animator = ValueAnimator.ofFloat(start, end);
        animator.setDuration(500);
        animator.addUpdateListener(valueAnimator -> {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mllViewPagerRoot.getLayoutParams();
            currentOffset = (Float) valueAnimator.getAnimatedValue();
            layoutParams.height = DpUtil.dip2px(this, currentOffset);
            mllViewPagerRoot.setLayoutParams(layoutParams);
        });
        animator.start();
    }

    private void getPhone(List<Reward> rewards, int position) {
        Reward reward = rewards.get(position);
        if (reward.Publisher.Id.equals(XiaodiApplication.mCurrentUser.Id)) {
            mPhone = reward.Receiver.Phone;
            return;
        }
        mPhone = reward.phone;
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

    private class OnPagerChangeListenerGetPhone implements ViewPager.OnPageChangeListener {

        private List<Reward> rewards;

        OnPagerChangeListenerGetPhone(List<Reward> rewards) {
            this.rewards = rewards;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            getPhone(rewards, position);
        }


        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
