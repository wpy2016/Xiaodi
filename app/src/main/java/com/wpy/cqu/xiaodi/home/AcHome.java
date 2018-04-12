package com.wpy.cqu.xiaodi.home;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.base_activity.CheckPermissionsActivity;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;
import com.wpy.cqu.xiaodi.home.fragment.FgHall;
import com.wpy.cqu.xiaodi.home.fragment.FgMy;
import com.wpy.cqu.xiaodi.im_chat.Rongyun;
import com.wpy.cqu.xiaodi.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class AcHome extends TopBarAppComptAcitity {

    private static final int STATUS_BAR_COLOR = Color.parseColor("#00dec9");
    private static final int TEXT_COLOR_GRAY = Color.parseColor("#bfbfbf");
    private static final int TEXT_COLOR_GREEN = Color.parseColor("#01c6ab");
    private static final int HALL = 0;
    private static final int MESSAGE = 1;
    private static final int MY = 2;

    private ViewPager mvpFgs;

    private ImageView mivHall;
    private TextView mtvHall;
    private RelativeLayout mrlHall;

    private ImageView mivMessage;
    private TextView mtvMessage;
    private RelativeLayout mrlMessage;

    private ImageView mivMy;
    private TextView mtvMy;
    private RelativeLayout mrlMy;

    private HomePagerAdaper mAdaper;

    private static String[] PERMISSION = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE,
    };

    //双击退出使用
    private int times = 0;
    private Disposable subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR, STATUS_BAR_COLOR);
        bundle.putStringArray(CheckPermissionsActivity.PEMISSION, PERMISSION);
        super.onCreate(bundle);
        setContentView(R.layout.ac_home);
        bindView();
        initView();
        bindEvent();
        selectWhich(HALL);
    }

    private void initView() {
        List<Fragment> list = new ArrayList<>();
        list.add(FgHall.newInstance());
        list.add(Rongyun.getMessageFragment(this));
        list.add(FgMy.newInstance());
        mAdaper = new HomePagerAdaper(list, getSupportFragmentManager());
        mvpFgs.setAdapter(mAdaper);
    }

    private void bindView() {
        mvpFgs = (ViewPager) findViewById(R.id.id_ac_home_viewpager);
        mivHall = (ImageView) findViewById(R.id.id_homedown_hall_iv_img);
        mtvHall = (TextView) findViewById(R.id.id_homedown_hall_tv);
        mivMessage = (ImageView) findViewById(R.id.id_homedown_communicate_iv);
        mtvMessage = (TextView) findViewById(R.id.id_homedown_communicate_tv);
        mivMy = (ImageView) findViewById(R.id.id_homedown_my_iv);
        mtvMy = (TextView) findViewById(R.id.id_homedown_my_tv);

        mrlHall = (RelativeLayout) findViewById(R.id.id_ac_home_down_rl_hall);
        mrlMessage = (RelativeLayout) findViewById(R.id.id_ac_home_down_rl_message);
        mrlMy = (RelativeLayout) findViewById(R.id.id_ac_home_down_rl_my);
    }

    private void bindEvent() {
        mrlHall.setOnClickListener(v -> selectWhich(HALL));
        mrlMessage.setOnClickListener(v -> selectWhich(MESSAGE));
        mrlMy.setOnClickListener(v -> selectWhich(MY));
        mvpFgs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectWhich(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void selectWhich(int index) {
        resetHomeDownStatus();
        switch (index) {
            case HALL:
                mivHall.setImageResource(R.drawable.hall_green);
                mtvHall.setTextColor(TEXT_COLOR_GREEN);
                mvpFgs.setCurrentItem(HALL);
                break;
            case MESSAGE:
                mivMessage.setImageResource(R.drawable.news_green);
                mtvMessage.setTextColor(TEXT_COLOR_GREEN);
                mvpFgs.setCurrentItem(MESSAGE);
                break;
            case MY:
                mivMy.setImageResource(R.drawable.my_green);
                mtvMy.setTextColor(TEXT_COLOR_GREEN);
                mvpFgs.setCurrentItem(MY);
                break;
        }
    }

    private void resetHomeDownStatus() {
        mivMessage.setImageResource(R.drawable.news_gray);
        mtvMessage.setTextColor(TEXT_COLOR_GRAY);
        mivHall.setImageResource(R.drawable.hall_gray);
        mtvHall.setTextColor(TEXT_COLOR_GRAY);
        mivMy.setImageResource(R.drawable.my_gray);
        mtvMy.setTextColor(TEXT_COLOR_GRAY);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown(keyCode, event);
        }
        times++;
        if (1 == times) {
            ToastUtil.toast(AcHome.this, getResources().getString(R.string.click_again_to_exit));
            subscribe = Observable.timer(2, TimeUnit.SECONDS)
                    .subscribe(l -> times = 0);
        }
        if (2 == times) {
            subscribe.dispose();
            finish();
        }
        return true;
    }

    private class HomePagerAdaper extends FragmentPagerAdapter {

        private List<Fragment> mFragments;

        public HomePagerAdaper(List<Fragment> fragments, FragmentManager fragmentManager) {
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
    }
}
