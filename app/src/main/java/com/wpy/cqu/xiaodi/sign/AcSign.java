package com.wpy.cqu.xiaodi.sign;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.adapter.common.CommonAdapter;
import com.wpy.cqu.xiaodi.adapter.common.ViewHolder;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.base_activity.CheckPermissionsActivity;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.base_activity.TopBarAppComptAcitity;
import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.User;
import com.wpy.cqu.xiaodi.net.UserRequest;
import com.wpy.cqu.xiaodi.net.resp.IResp;
import com.wpy.cqu.xiaodi.util.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AcSign extends TopBarAppComptAcitity {

    private static final int StatusColor = Color.parseColor("#3d6f52");

    private static final String[] PERMISSION = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE,
    };

    private GridView mgvDate;

    private TextView mtvMonth;

    private RelativeLayout mrlTop;

    private TextView mtvAllSignedNums;

    private TextView mtvSignEarnedXiaodian;

    private Button mbtnSign;

    private CommonAdapter mAdapter;

    private List<SignDataBean> marrDate;

    private User mUser;

    private Context mContext;

    private List<String> mDateSigned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR, StatusColor);
        bundle.putStringArray(CheckPermissionsActivity.PEMISSION, PERMISSION);
        super.onCreate(bundle);
        setContentView(R.layout.ac_sign);
        mContext = this;
        bindView();
        bindEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUser = XiaodiApplication.mCurrentUser;
        initView();
    }

    private void bindView() {
        mgvDate = findViewById(R.id.id_sign_gv_date);
        mtvMonth = findViewById(R.id.id_sign_tv_month);
        mivBack = findViewById(R.id.id_top_back_iv_img);
        mtvContent = findViewById(R.id.id_top_tv_content);
        mrlTop = findViewById(R.id.id_top_rl);
        mtvAllSignedNums = findViewById(R.id.id_sign_tv_all_signed_nums);
        mtvSignEarnedXiaodian = findViewById(R.id.id_sign_earn_xd_grade);
        mbtnSign = findViewById(R.id.id_ac_sign_in_btn_confirm);
    }

    private void bindEvent() {
        mivBack.setOnClickListener(view -> finish());
        mbtnSign.setOnClickListener(this::sign);
    }

    private void sign(View view) {
        Calendar calendar = Calendar.getInstance();
        String day = calendar.get(Calendar.DAY_OF_MONTH) + "";
        UserRequest.Sign(XiaodiApplication.mCurrentUser.Id,
                XiaodiApplication.mCurrentUser.Token, day, new IResp<ResultResp>() {
                    @Override
                    public void success(ResultResp object) {
                        ToastUtil.toast(AcSign.this, getResources().getString(R.string.sign_success));
                        finish();
                    }

                    @Override
                    public void fail(ResultResp resp) {
                        ToastUtil.toast(AcSign.this, resp.message);
                    }
                });
    }

    private void initView() {
        mivBack.setImageResource(R.drawable.go_back_white);
        mtvContent.setText(getResources().getString(R.string.sign));
        mrlTop.setBackgroundColor(Color.TRANSPARENT);

        setBgDate();
        setSignData();
    }


    /**
     * 设置签到日期及其他数据
     */
    private void setSignData() {
        Calendar cal = Calendar.getInstance();
        String year = cal.get(Calendar.YEAR) + "";
        String month = (cal.get(Calendar.MONTH) + 1) + "";
        UserRequest.GetSignList(XiaodiApplication.mCurrentUser.Id,
                XiaodiApplication.mCurrentUser.Token, year, month, new IResp<SignResp>() {
                    @Override
                    public void success(SignResp signResp) {
                        Logger.i(signResp.toString());
                        mDateSigned = signResp.Days;
                        showSignData();
                    }

                    @Override
                    public void fail(ResultResp resp) {
                        ToastUtil.toast(AcSign.this, resp.message);
                    }
                });

    }

    private void showSignData() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        int first_day_of_week = cal.get(Calendar.DAY_OF_WEEK);//一号是一周的第几天

        String num = mDateSigned.size() + "";
        int oneEarn = 1;
        mtvAllSignedNums.setText(num);
        String allEarn = oneEarn * (Integer.valueOf(num)) + "";
        mtvSignEarnedXiaodian.setText(allEarn);
        for (int i = 0; i < mDateSigned.size(); i++) {
            int n = Integer.valueOf(mDateSigned.get(i)) + first_day_of_week + 5;
            marrDate.get(n).setSigned(true);
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 初始所有日期
     */
    private void setBgDate() {

        marrDate = new ArrayList<>();
        /**
         * 初始化星期标题
         */
        String[] week_date = getResources().getStringArray(R.array.week_date);
        for (String date : week_date) {
            SignDataBean dateObj = new SignDataBean(0, date, false);
            marrDate.add(dateObj);
        }
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.SUNDAY);

        int month = cal.get(Calendar.MONTH) + 1;//本月
        int day_nums = cal.getActualMaximum(Calendar.DATE);//本月天数

        String s_month = month + "月";
        mtvMonth.setText(s_month);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        int first_day_of_week = cal.get(Calendar.DAY_OF_WEEK);//一号是一周的第几天

        cal.add(Calendar.DAY_OF_MONTH, -1);
        int last_month_day_nums = cal.get(Calendar.DAY_OF_MONTH);//上个月最大天数
        /**
         * 设置上月日期
         */
        for (int i = 1; i < first_day_of_week; i++) {
            String date = (last_month_day_nums - (first_day_of_week - i - 1)) + "";
            SignDataBean dateObj = new SignDataBean(2, date, false);
            marrDate.add(dateObj);
        }
        /**
         * 设置本月日期
         */
        for (int i = 1; i < day_nums + 1; i++) {
            String date = i + "";
            SignDataBean dateObj = new SignDataBean(1, date, false);
            marrDate.add(dateObj);
        }
        /**
         * 设置下月日期
         */
        for (int i = 1; i < 44 - day_nums - first_day_of_week; i++) {
            String date = i + "";
            SignDataBean dateObj = new SignDataBean(2, date, false);
            marrDate.add(dateObj);
        }
        mgvDate.setEnabled(false);
        mAdapter = new CommonAdapter<SignDataBean>(getApplicationContext(), marrDate, R.layout.items_grid_sign) {

            @Override
            public void convert(ViewHolder helper, SignDataBean item) {
                helper.setText(R.id.id_sign_tv_date, item.getDate());
                if (item.getType() == 0) {
                    helper.setTextSize(R.id.id_sign_tv_date, 12);
                } else if (item.getType() == 2) {
                    helper.setTextColor(R.id.id_sign_tv_date, getResources().getColor(R.color.gray));
                }
                if (item.isSigned()) {
                    helper.setTextBackground(R.id.id_sign_tv_date, ContextCompat.getDrawable(getApplicationContext(), R.drawable.circle_stroke_green_50));
                }
            }
        };
        mgvDate.setAdapter(mAdapter);
    }
}
