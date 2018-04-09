package com.wpy.cqu.xiaodi.reward;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.application.XiaodiApplication;
import com.wpy.cqu.xiaodi.base_activity.CheckPermissionsActivity;
import com.wpy.cqu.xiaodi.base_activity.ClipBaseActivity;
import com.wpy.cqu.xiaodi.base_activity.StatusBarAppComptActivity;
import com.wpy.cqu.xiaodi.loading.Loading;
import com.wpy.cqu.xiaodi.model.ResultResp;
import com.wpy.cqu.xiaodi.model.Reward;
import com.wpy.cqu.xiaodi.model.Thing;
import com.wpy.cqu.xiaodi.net.RewardRequst;
import com.wpy.cqu.xiaodi.net.resp.IResp;
import com.wpy.cqu.xiaodi.util.DpUtil;
import com.wpy.cqu.xiaodi.util.TimeUtils;
import com.wpy.cqu.xiaodi.util.ToastUtil;
import com.wpy.cqu.xiaodi.view.wheel.PlacePicker;
import com.wpy.cqu.xiaodi.view.wheel.TimePicker;

import java.util.Date;
import java.util.regex.Pattern;

public class AcReward extends ClipBaseActivity {

    private static final int STATUS_BAR_COLOR = Color.parseColor("#00dec9");

    private static final String[] PERMISSION = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE,
    };

    private ImageView mivTakeImg;
    private ImageView mivExpress;
    private ImageView mivFood;
    private ImageView mivPaper;
    private ImageView mivOther;
    private RadioGroup mrgWeight;


    //物品类型头像
    private int miThingType = -1;
    private String mThumbnail = "";

    //重量级
    private int miWeight = -1;
    private String msWeight[] = null;

    //出发地目的地
    private int miPlaceSelect = -1;
    private PopupWindow mPopSelectPlace;
    private PlacePicker mplacePicker;
    private TextView mtvStartPlace;
    private TextView mtvDstPlace;
    private String msStartPlace;
    private String msDstPlace;

    //截止时间
    private PopupWindow mPopSelectTime;
    private TextView mtvDeadLine;
    private String msDeadLine;

    //笑点
    private EditText metXiaodian;
    private EditText metphone;
    private EditText metDescribe;
    private Button mbtnSend;

    //Loading
    private PopupWindow mLoadingPopWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putInt(StatusBarAppComptActivity.STATUS_COLOR_STR, STATUS_BAR_COLOR);
        bundle.putStringArray(CheckPermissionsActivity.PEMISSION, PERMISSION);
        super.onCreate(bundle);
        setContentView(R.layout.ac_reward);
        initData();
        bindView();
        initView();
        bindEvent();
    }

    private void Send(View view) {
        boolean isavaliable = validateParams();
        if (!isavaliable) {
            return;
        }
        Reward reward = createReward();
        if (null == mLoadingPopWindow) {
            mLoadingPopWindow = Loading.getLoadingPopwindown(this);
        }
        Loading.showLoading(this,mLoadingPopWindow);
        RewardRequst.SendReward(reward, XiaodiApplication.mCurrentUser.Id,
                XiaodiApplication.mCurrentUser.Token, new IResp<ResultResp>() {
            @Override
            public void success(ResultResp resp) {
                mLoadingPopWindow.dismiss();
                 finish();
            }

            @Override
            public void fail(ResultResp resp) {
                mLoadingPopWindow.dismiss();
                ToastUtil.toast(AcReward.this, resp.message);
            }
        });
    }

    private Reward createReward() {
        Reward reward = new Reward();
        Thing thing = new Thing();
        reward.thing = thing;
        reward.thing.type = miThingType;
        reward.thing.weight = msWeight[miWeight];
        reward.thing.thumbnail = mThumbnail;
        reward.originLocation = mtvStartPlace.getText().toString();
        reward.dstLocation = mtvDstPlace.getText().toString();
        reward.xiaodian = Integer.parseInt(metXiaodian.getText().toString());
        reward.phone = metphone.getText().toString();
        reward.deadline = mtvDeadLine.getText().toString();
        reward.describe = metDescribe.getText().toString();
        return reward;
    }

    @Override
    public void errorLoadImg() {
        ToastUtil.toast(this, getResources().getString(R.string.photo_load_fail));
    }

    @Override
    public void setImg(Bitmap img, String path) {
        if (-1 == miThingType) {
            ToastUtil.toast(this, getResources().getString(R.string.please_select_good_type));
            return;
        }
        ImageView[] ivs = {mivExpress, mivFood, mivPaper, mivOther};
        ivs[miThingType].setImageBitmap(img);
        mThumbnail = path;
    }


    private void resetImgBg() {
        mivExpress.setBackground(new BitmapDrawable());
        mivFood.setBackground(new BitmapDrawable());
        mivPaper.setBackground(new BitmapDrawable());
        mivOther.setBackground(new BitmapDrawable());
    }

    private void resetImg() {
        mThumbnail = "";
        mivExpress.setImageResource(R.drawable.good_type_express);
        mivFood.setImageResource(R.drawable.good_type_food);
        mivPaper.setImageResource(R.drawable.good_type_paper);
        mivOther.setImageResource(R.drawable.good_type_other);
    }

    private void selectThingType(ImageView iv, int type) {
        resetImgBg();
        resetImg();
        iv.setBackgroundResource(R.drawable.circle_stroke_green_60);
        miThingType = type;
    }

    private void showSelectPlaceWithIndex(int id) {
        miPlaceSelect = id;
        showPopSelectPlace();
    }

    private void showPopSelectPlace() {
        if (mPopSelectPlace == null) {
            View view = getLayoutInflater().inflate(R.layout.pop_select_place, null);
            mPopSelectPlace = new PopupWindow(view, DpUtil.dip2px(getApplicationContext(), 320),
                    LinearLayout.LayoutParams.WRAP_CONTENT, true);
            initPopSelectPlace(view);
        }
        mPopSelectPlace.showAtLocation(mtvStartPlace.getRootView(), Gravity.CENTER, 0, 0);
    }

    private void initPopSelectPlace(View view) {
        mplacePicker = view.findViewById(R.id.id_pop_select_place_tp_place);
        Button btnCancel = view.findViewById(R.id.id_pop_select_place_cancel);
        Button btnConfirm = view.findViewById(R.id.id_pop_select_place_confirm);
        btnCancel.setOnClickListener(v -> mPopSelectPlace.dismiss());
        btnConfirm.setOnClickListener(this::PlaceConfirm);
        mPopSelectPlace.setOutsideTouchable(false);
        mPopSelectPlace.setAnimationStyle(android.R.style.Animation_InputMethod);
    }

    private void PlaceConfirm(View view) {
        switch (miPlaceSelect) {
            case R.id.id_ac_send_reward_tv_start_place:
                msStartPlace = mplacePicker.toString();
                mtvStartPlace.setText(msStartPlace);
                mPopSelectPlace.dismiss();
                break;
            case R.id.id_ac_send_reward_tv_arrive_place:
                msDstPlace = mplacePicker.toString();
                mtvDstPlace.setText(msDstPlace);
                mPopSelectPlace.dismiss();
                break;
        }
    }

    private void showPopSelectTime() {
        if (mPopSelectTime == null) {
            View view = getLayoutInflater().inflate(R.layout.pop_select_time, null);
            mPopSelectTime = new PopupWindow(view, DpUtil.dip2px(getApplicationContext(), 320),
                    LinearLayout.LayoutParams.WRAP_CONTENT, true);
            initPopSelectTime(view);
        }
        mPopSelectTime.showAtLocation(mtvDeadLine.getRootView().getRootView().getRootView(), Gravity.CENTER, 0, 0);
    }

    private void initPopSelectTime(View view) {
        TimePicker timePicker = view.findViewById(R.id.id_pop_select_time_tp_time);
        timePicker.setDate(new Date().getTime());
        Button btnCancel = view.findViewById(R.id.id_pop_select_time_cancel);
        Button btnConfirm = view.findViewById(R.id.id_pop_select_time_confirm);
        btnCancel.setOnClickListener(v -> mPopSelectTime.dismiss());
        btnConfirm.setOnClickListener(v -> {
            msDeadLine = timePicker.toString();
            mtvDeadLine.setText(msDeadLine);
            mPopSelectTime.dismiss();
        });
        mPopSelectTime.setOutsideTouchable(false);
        mPopSelectTime.setAnimationStyle(android.R.style.Animation_InputMethod);
    }

    private void bindView() {
        mivTakeImg = (ImageView) findViewById(R.id.id_ac_send_reward_iv_take_photo_img);
        mivBack = (ImageView) findViewById(R.id.id_top_back_iv_img);
        mtvBack = (TextView) findViewById(R.id.id_top_back_tv);
        mtvContent = (TextView) findViewById(R.id.id_top_tv_content);

        mivExpress = (ImageView) findViewById(R.id.id_ac_send_reward_iv_express_img);
        mivFood = (ImageView) findViewById(R.id.id_ac_send_reward_iv_food_img);
        mivPaper = (ImageView) findViewById(R.id.id_ac_send_reward_iv_paper_img);
        mivOther = (ImageView) findViewById(R.id.id_ac_send_reward_iv_other_img);
        mrgWeight = (RadioGroup) findViewById(R.id.id_ac_send_reward_rg_weight);

        mtvStartPlace = (TextView) findViewById(R.id.id_ac_send_reward_tv_start_place);
        mtvDstPlace = (TextView) findViewById(R.id.id_ac_send_reward_tv_arrive_place);

        mtvDeadLine = (TextView) findViewById(R.id.id_ac_reward_tv_limited_time);

        metXiaodian = (EditText) findViewById(R.id.id_ac_send_reward_et_xd_grade);
        metphone = (EditText) findViewById(R.id.id_ac_send_reward_et_phone);

        metDescribe = (EditText) findViewById(R.id.id_ac_send_reward_et_text_des);
        mbtnSend = (Button) findViewById(R.id.id_ac_send_reward_btn_release);

    }

    private void initData() {
        msWeight = new String[]{getResources().getString(R.string.weight_light),
                getResources().getString(R.string.weight_middle),
                getResources().getString(R.string.weight_heavy)};
    }

    private void initView() {
        mivBack.setImageResource(R.drawable.go_back_white);
        mtvBack.setText(getResources().getString(R.string.hall));
        mtvBack.setTextColor(Color.WHITE);
        mtvContent.setText(getResources().getString(R.string.send_reward));
        metphone.setText(XiaodiApplication.mCurrentUser.Phone);
        mtvDeadLine.setText(TimeUtils.getFormatTime());
        resetImgBg();
    }

    private void bindEvent() {
        mivTakeImg.setOnClickListener(v -> showPopupWindow(mivTakeImg));
        mivBack.setOnClickListener(v -> finish());
        mtvBack.setOnClickListener(v -> finish());
        mivExpress.setOnClickListener(v -> selectThingType(mivExpress, Thing.EXPRESS));
        mivFood.setOnClickListener(v -> selectThingType(mivFood, Thing.FOOD));
        mivPaper.setOnClickListener(v -> selectThingType(mivPaper, Thing.PAPER));
        mivOther.setOnClickListener(v -> selectThingType(mivOther, Thing.OTHER));
        mtvStartPlace.setOnClickListener(v -> showSelectPlaceWithIndex(R.id.id_ac_send_reward_tv_start_place));
        mtvDstPlace.setOnClickListener(v -> showSelectPlaceWithIndex(R.id.id_ac_send_reward_tv_arrive_place));
        mtvDeadLine.setOnClickListener(v -> showPopSelectTime());
        mbtnSend.setOnClickListener(this::Send);
        mrgWeight.setOnCheckedChangeListener((radioGroup, checkId) -> {
            switch (checkId) {
                case R.id.id_ac_send_reward_rb_light:
                    miWeight = Thing.IWEIGHT_LIGHT_INT;
                    break;
                case R.id.id_ac_send_reward_rb_medium:
                    miWeight = Thing.IWEIGHT_MEDIUM_INT;
                    break;
                case R.id.id_ac_send_reward_rb_heavy:
                    miWeight = Thing.IWEIGHT_HEAVY_INT;
                    break;
            }
        });
    }


    private boolean validateParams() {
        if (-1 == miThingType) {
            ToastUtil.toast(this, getResources().getString(R.string.please_select_good_type));
            return false;
        }
        if (-1 == miWeight) {
            ToastUtil.toast(this, getResources().getString(R.string.please_select_good_weight_type));
            return false;
        }
        if (TextUtils.isEmpty(metXiaodian.getText().toString())) {
            ToastUtil.toast(this, getResources().getString(R.string.please_give_smile_point));
            return false;
        }
        if (TextUtils.isEmpty(metphone.getText().toString())) {
            ToastUtil.toast(this, getResources().getString(R.string.phone_no_null));
            return false;
        }
        if (!Pattern.matches("^1[0-9]{10}$",metphone.getText().toString())) {
            ToastUtil.toast(this, getResources().getString(R.string.phone_no_right));
            return false;
        }
        if (TextUtils.isEmpty(mtvStartPlace.getText().toString())) {
            ToastUtil.toast(this, getResources().getString(R.string.good_start_or_end_place_null));
            return false;
        }
        if (TextUtils.isEmpty(mtvDstPlace.getText().toString())) {
            ToastUtil.toast(this, getResources().getString(R.string.good_start_or_end_place_null));
            return false;
        }
        if (TextUtils.isEmpty(mtvDeadLine.getText().toString())) {
            ToastUtil.toast(this, getResources().getString(R.string.set_deadline));
            return false;
        }

        if (!TimeUtils.TimeOverNow(mtvDeadLine.getText().toString())) {
            ToastUtil.toast(this,getResources().getString(R.string.time_over_now));
            return false;
        }
        return true;
    }
}
