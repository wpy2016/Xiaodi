package com.wpy.cqu.xiaodi.home;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.base_activity.CheckPermissionsActivity;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;
import com.wpy.cqu.xiaodi.home.fragment.FgHall;
import com.wpy.cqu.xiaodi.home.fragment.FgMy;

import java.util.ArrayList;
import java.util.List;

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

    private ImageView mivMessage;
    private TextView mtvMessage;

    private ImageView mivMy;
    private TextView mtvMy;

    private HomePagerAdaper mAdaper;

    private static String[] PERMISSION = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE,
    };

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
        list.add(FgHall.newInstance());
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
    }

    private void bindEvent() {
        mivHall.setOnClickListener(v -> {
            selectWhich(HALL);
        });
        mtvHall.setOnClickListener(v -> {
            selectWhich(HALL);
        });

        mivMessage.setOnClickListener(v -> {
            selectWhich(MESSAGE);
        });
        mtvMessage.setOnClickListener(v -> {
            selectWhich(MESSAGE);
        });
        mivMy.setOnClickListener(v -> {
            selectWhich(MY);
        });
        mtvMy.setOnClickListener(v -> {
            selectWhich(MY);
        });

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

    /**
     * 首次进入时viewPager的adapter
     */
    public class HomePagerAdaper extends FragmentPagerAdapter {

        private List<Fragment> mFragments;

        private FragmentManager mFragmentManager;

        public HomePagerAdaper(List<Fragment> fragments, FragmentManager fragmentManager) {
            super(fragmentManager);
            this.mFragments = fragments;
            this.mFragmentManager = fragmentManager;
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
