package com.wpy.cqu.xiaodi.guide;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.login.AcLogin;
import com.wpy.cqu.xiaodi.view.DepthPageTransformer;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Flowable;

public class AcGuide extends StatusBarAppComptActivity {

    private static final int leadingsCount = 3;

    private static final  int[] leadingsIds = new int[]{R.drawable.leading1, R.drawable.leading2, R.drawable.leading3};

    private static final int[] leadingStutaBarsColor = {Color.parseColor("#33ead7"), Color.parseColor("#04dee8"), Color.parseColor("#91c9f4") };

    private List<View> mViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR,leadingStutaBarsColor[0]);
        super.onCreate(bundle);
        setContentView(R.layout.ac_guide);

        initViewPager();
        ViewPager viewpager = (ViewPager) findViewById(R.id.id_ac_guide_vp);
        viewpager.setAdapter(new WelcomePagerAdaper(mViews));
        viewpager.setPageTransformer(true, new DepthPageTransformer());
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeStatusBarColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void changeStatusBarColor(int pos){
        Window window = this.getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(leadingStutaBarsColor[pos]);
    }

    /**
     * 初始化viewpager中的view
     */
    private void initViewPager() {
        Flowable.range(0,leadingsCount)
                .map(index -> leadingsIds[index])
                .subscribe(this :: createView);
    }

    private void createView(int leadingId){
        ImageView imgLeading = new ImageView(this);
        imgLeading.setImageResource(leadingId);
        imgLeading.setScaleType(ImageView.ScaleType.FIT_XY);
        if(leadingId == leadingsIds[leadingsCount - 1])
        {
            imgLeading.setOnClickListener(view -> {
                Intent intent = new Intent(AcGuide.this, AcLogin.class);
                startActivity(intent);
                finish();
            });
        }
        mViews.add(imgLeading);
    }


    /**
     * 首次进入时viewPager的adapter
     */
    private class WelcomePagerAdaper extends PagerAdapter {

        private List<View> mViews;

        WelcomePagerAdaper(List<View> views) {
            this.mViews = views;
        }

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            view.addView(mViews.get(position), ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            return mViews.get(position);
        }
    }
}
