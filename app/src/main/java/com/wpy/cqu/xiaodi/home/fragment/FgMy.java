package com.wpy.cqu.xiaodi.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wpy.cqu.xiaodi.R;
import com.wpy.cqu.xiaodi.base_activity.ClipBaseFragment;
import com.wpy.cqu.xiaodi.register.AcRegister;

public class FgMy extends ClipBaseFragment {

    private Context mContext;

    private TextView mtvContent;

    private ImageView mivImg;

    private ImageView mivSetImg;

    private TextView mtvNickName;

    private TextView mtvRealName;

    private RelativeLayout mrlSignature;

    private RelativeLayout mrlMoney;

    private TextView mtvMoney;

    private RelativeLayout mrlCarryRecord;

    private RelativeLayout mrlAttestation;

    private TextView mtvAttestationStatus;

    private RelativeLayout mrlSetting;

    public FgMy() {
    }

    public static FgMy newInstance() {
        FgMy fragment = new FgMy();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_my, null);
        bindView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        bindEvent();
    }

    private void initView() {
        mtvContent.setText(getResources().getString(R.string.my));
        /**todo
         * 设置用户的姓名
         * 头像
         * 认证状态
         */
    }

    @Override
    public void setImg(Bitmap img,String path) {
        mivImg.setImageBitmap(img);
    }

    private void setNickName(View view) {

    }

    @Override
    public void errorLoadImg() {

    }

    private void bindEvent() {
        mivSetImg.setOnClickListener(v -> showPopupWindow(mivImg));
        mtvNickName.setOnClickListener(this::setNickName);
        // TODO: 2018/3/30
        mrlSetting.setOnClickListener(v -> toNext(AcRegister.class));
        mrlSignature.setOnClickListener(v -> toNext(AcRegister.class));
        mrlMoney.setOnClickListener(v -> toNext(AcRegister.class));
        mrlCarryRecord.setOnClickListener(v -> toNext(AcRegister.class));
        mrlAttestation.setOnClickListener(v -> toNext(AcRegister.class));
    }

    private void toNext(Class<?> next) {
        Intent intent = new Intent(getActivity(), next);
        startActivity(intent);
    }

    private void bindView(View view) {
        mivImg = view.findViewById(R.id.id_fg_my_me_iv_img);
        mivSetImg = view.findViewById(R.id.id_fg_my_me_iv_setimg);
        mtvNickName = view.findViewById(R.id.id_ac_my_me_tv_name);
        mrlSignature = view.findViewById(R.id.id_my_rl_everyday_sign);
        mrlMoney = view.findViewById(R.id.id_my_rl_wallet);
        mrlCarryRecord = view.findViewById(R.id.id_ac_my_rl_carryrecode);
        mrlAttestation = view.findViewById(R.id.id_fg_my_rl_xiaodi_attestation);
        mtvRealName = view.findViewById(R.id.id_ac_my_tv_realName);
        mrlSetting = view.findViewById(R.id.id_ac_my_rl_setting);
        mtvAttestationStatus = view.findViewById(R.id.id_fg_my_tv_is_xiaodi_attestation);
        mtvContent = view.findViewById(R.id.id_top_tv_content);
    }
}
