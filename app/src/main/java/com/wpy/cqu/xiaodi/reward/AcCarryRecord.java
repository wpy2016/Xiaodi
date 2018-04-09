package com.wpy.cqu.xiaodi.reward;

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
import com.wpy.cqu.xiaodi.reward.fragment.FgCarry;

import java.util.ArrayList;
import java.util.List;

public class AcCarryRecord extends TopBarAppComptAcitity {

    private static final int STATUS_BAR_COLOR = Color.parseColor("#00dec9");
    public static final int MySend = 0;
    public static final int MyCarry = 1;

    private ViewPager mvpFgs;

    private TextView mtvMySend;

    private TextView mtvMyCarry;

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
        setContentView(R.layout.ac_carry_recode);
        bindView();
        initView();
        bindEvent();
        selectWhich(MySend);
    }

    private void bindView() {
        mvpFgs = (ViewPager) findViewById(R.id.id_fg_carry_record_tv_viewPager);
        mtvMySend = (TextView) findViewById(R.id.id_fg_carry_record_tv_my_send);
        mtvMyCarry = (TextView) findViewById(R.id.id_fg_carry_record_tv_my_carry);

        mtvBack = (TextView) findViewById(R.id.id_top_back_tv);
        mivBack = (ImageView) findViewById(R.id.id_top_back_iv_img);
        mtvContent = (TextView) findViewById(R.id.id_top_tv_content);
    }

    private void initView() {
        mtvBack.setText(getResources().getString(R.string.me));
        mtvBack.setTextColor(Color.WHITE);
        mivBack.setImageResource(R.drawable.go_back_white);
        mtvContent.setText(getResources().getString(R.string.carry_recode));

        List<Fragment> list = new ArrayList<>();
        list.add(FgCarry.newInstance(MySend));
        list.add(FgCarry.newInstance(MyCarry));
        CarryRecordPagerAdaper adaper = new CarryRecordPagerAdaper(list, getSupportFragmentManager());
        mvpFgs.setAdapter(adaper);
    }

    private void bindEvent() {
        mtvBack.setOnClickListener(view -> finish());
        mivBack.setOnClickListener(view -> finish());
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
        switch (index) {
            case MySend:
                mtvMySend.setBackgroundColor(Color.parseColor("#ffdab9"));
                mtvMyCarry.setBackgroundResource(R.color.background_color_gary);
                break;
            case MyCarry:
                mtvMySend.setBackgroundResource(R.color.background_color_gary);
                mtvMyCarry.setBackgroundColor(Color.parseColor("#ffdab9"));
                break;
        }
    }

    private class CarryRecordPagerAdaper extends FragmentPagerAdapter {

        private List<Fragment> mFragments;

        private CarryRecordPagerAdaper(List<Fragment> fragments, FragmentManager fragmentManager) {
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
